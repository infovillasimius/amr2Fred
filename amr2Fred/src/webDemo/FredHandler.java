/*
 * Copyright (C) 2017 anto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package webDemo;

import amr2fred.DigraphWriter;
import amr2fred.Node;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import resultsComparator.Amr2File2;
import resultsComparator.Converter;
import static webDemo.Glossary.*;

/**
 * Handler for FRED access requests
 *
 * @author anto
 */
public class FredHandler implements HttpHandler {

    private static Queue<String> urls = new LinkedList<String>();
    private static HashMap<String, String> map = loadMap();
    private static final int INITIAL_QUERIES = 1;
    private static final Semaphore AVAILABLE = new Semaphore(INITIAL_QUERIES, true);

    public FredHandler() {
        if (map == null) {
            map = loadMap();
        }
    }

    private static boolean getToken() throws InterruptedException {
        AVAILABLE.acquire();
        return true;
    }

    public static void timeIncreaseQueries() {
        if (AVAILABLE.availablePermits() < Glossary.FRED_QUERIES) {
            AVAILABLE.release();
        }

    }

    @Override
    public void handle(HttpExchange he) throws IOException {

        String text = "";
        String mode = "";
        FileHandler ext = new FileHandler();
        File tmp = null;
        String request = URLDecoder.decode(he.getRequestURI().toASCIIString(), ENC);
        String par = request;

        //sono stati previsti esclusivamente i modi corrispondenti a quelli utilizzati da amr2fred
        if (par.contains(MODE + IMG)) {
            par = par.replace(MODE + IMG, "");
            mode = FRED_IMAGE;
        } else if (par.contains(MODE + RDF_XML)) {
            par = par.replace(MODE + RDF_XML, "");
            mode = FRED_RDF;
        } else if (par.contains(MODE + N_TRIPLES)) {
            par = par.replace(MODE + N_TRIPLES, "");
            mode = FRED_N_TRIPLES;
        } else if (par.contains(MODE + TURTLE)) {
            par = par.replace(MODE + TURTLE, "");
            mode = FRED_TURTLE;
        }

        request = par;

        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 5 && request.contains(TEXT)) {

            int pos = request.indexOf(TEXT);

            text = request.substring(pos + 7);

            tmp = getFred(text, mode);
        }

        //se qualcosa va storto serve il logo UniCa altrimenti invia quanto ottenuto da FRED
        if (tmp == null || !tmp.isFile()) {
            ext.getFile(LOGO);
            tmp = new File(PAGESDIR + LOGO);

        }

        he.sendResponseHeaders(200, tmp.length());
        try (OutputStream os = he.getResponseBody()) {
            Files.copy(tmp.toPath(), os);
            tmp.delete();
        }
    }

    /**
     *
     * @param text the text to send to FRED
     * @param mode output type
     * @return
     */
    private File getFred(String text, String mode) {
        File tmp = null;
        
        if (!mode.equalsIgnoreCase(Glossary.FRED_IMAGE)) {
            try {
                String result = getFredString(text, mode);
                InputStream in = new ByteArrayInputStream(result.getBytes());
                tmp = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
                Path tmpPath = tmp.getAbsoluteFile().toPath();
                tmp.delete();
                Files.copy(in, tmpPath);

            } catch (IOException e) {
                Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, e);
                FileHandler ext = new FileHandler();
                ext.getFile(NOFREDTEXT);
                tmp = new File(PAGESDIR + NOFREDTEXT);
                return tmp;
            }

            return tmp;
        }
        return getFredImage(text, tmp);
    }

    public static String getFredString(String text, String mode) {

        String tmp = null;
        try {
            String request = URLEncoder.encode(text, ENC);
            String url = COMMAND + request + COMMAND2;
            String result = getFromCache(url,mode);
            if (result == null) {
                try {
                    getToken();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setConnectTimeout(TIMEOUT);
                conn.setReadTimeout(TIMEOUT);
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                //determina l'output di FRED
                conn.setRequestProperty("accept", mode);
                conn.setRequestProperty("Authorization", "Bearer " + Glossary.FRED_AUTHORIZATION);

                InputStream in = conn.getInputStream();
                StringBuilder textBuilder = new StringBuilder();
                Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())));
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
                tmp = textBuilder.toString();
                result = tmp;

                addToCache(url, result, mode);

            } else {
                tmp = result;
            }

        } catch (IOException e) {
            Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, e);
            if (!e.getMessage().contains("500 for URL")) {
                return "FRED is not Reachable!";
            }

            return "Server Error";

        }

        return tmp;
    }

    private File getFredImage(String sentence, File tmp) {

        String rdf2 = FredHandler.getFredString(sentence, webDemo.Glossary.FRED_N_TRIPLES);
        Node root = Converter.toNode(rdf2);
        if (root != null) {

            tmp = DigraphWriter.toPng(root);

        } else {
            FileHandler ext = new FileHandler();
            ext.getFile(NOFREDPNG);
            tmp = new File(PAGESDIR + NOFREDPNG);
        }
        return tmp;
    }

    public static void append(String url, String fredResult) {

        try {
            FileWriter fstream = new FileWriter(Glossary.FRED_CACHE_FILE, true);
            BufferedWriter fbw = new BufferedWriter(fstream);
            fbw.write(Glossary.FRED_CACHE_URL_REQUEST);
            fbw.newLine();
            fbw.write(url);
            fbw.newLine();
            fbw.write(Glossary.FRED_CACHE_URL_END_REQUEST);
            fbw.newLine();
            fbw.write(Glossary.FRED_CACHE_RESULT);
            fbw.newLine();
            fbw.write(fredResult);
            fbw.write(Glossary.FRED_CACHE_END_RESULT);
            fbw.newLine();
            fbw.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getFromCache(String url,String mode) {
        String result = map.get(url+mode);
        if (result == null) {
            result = cacheSearch(url+mode);
        }
        return result;
    }

    public static void addToCache(String url, String fredResult, String mode) {
        if (map.size() > Glossary.FRED_CACHE) {
            String remove = urls.poll();
            map.remove(remove);
        }
        urls.add(url+mode);
        map.put(url+mode, fredResult);
        append(url+mode, fredResult);
    }

    private static HashMap<String, String> loadMap() {
        HashMap<String, String> map = new HashMap<>();

        String line, url, result;

        try {
            ArrayList<String> l = new ArrayList<>();
            File f = new File(Glossary.FRED_CACHE_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {
                url = "";
                result = "";
                if (line.contains(Glossary.FRED_CACHE_URL_REQUEST)) {

                    line = reader.readLine();
                    url = line;
                    urls.add(url);
                    line = reader.readLine();
                    line = reader.readLine();
                }

                if (line.contains(Glossary.FRED_CACHE_RESULT)) {

                    line = reader.readLine();
                    while (!line.contains(Glossary.FRED_CACHE_END_RESULT)) {
                        result = result + line;
                        line = reader.readLine();
                    }

                    if (map.size() > Glossary.FRED_CACHE) {
                        String remove = urls.poll();
                        map.remove(remove);
                    }
                    urls.add(url);
                    map.put(url, result);

                }

                line = reader.readLine();

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File2.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    private static String cacheSearch(String searchedUrl) {

        String line, url, result;

        try {
            ArrayList<String> l = new ArrayList<>();
            File f = new File(Glossary.FRED_CACHE_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {
                url = "";
                result = "";
                if (line.contains(Glossary.FRED_CACHE_URL_REQUEST)) {

                    line = reader.readLine();
                    url = line;
                    if (url.equalsIgnoreCase(searchedUrl)) {

                        line = reader.readLine();
                        line = reader.readLine();
                        if (line.contains(Glossary.FRED_CACHE_RESULT)) {

                            line = reader.readLine();
                            while (!line.contains(Glossary.FRED_CACHE_END_RESULT)) {
                                result = result + line;
                                line = reader.readLine();
                            }

                        }

                        if (map.size() > Glossary.FRED_CACHE) {
                            String remove = urls.poll();
                            map.remove(remove);
                        }
                        urls.add(url);
                        map.put(url, result);
                        return result;
                    }

                }

                line = reader.readLine();

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File2.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
}
