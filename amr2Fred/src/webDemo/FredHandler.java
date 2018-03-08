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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import static webDemo.Glossary.*;

/**
 * Handler for FRED access requests
 *
 * @author anto
 */
public class FredHandler implements HttpHandler {

    /**
     *
     * @param text the text to send to FRED
     * @param mode output type
     * @return
     */
    private File getFred(String text, String mode) {
        File tmp = null;

        if (!isIpReachable()) {

            FileHandler ext = new FileHandler();
            if (mode.equalsIgnoreCase(Glossary.FRED_IMAGE)) {
                ext.getFile(NOFREDPNG);
                tmp = new File(PAGESDIR + NOFREDPNG);
            } else {
                ext.getFile(NOFREDTEXT);
                tmp = new File(PAGESDIR + NOFREDTEXT);
            }

            return tmp;
        }

        try {
            String request = URLEncoder.encode(text, ENC);
            String url = COMMAND + request + COMMAND2;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            //determina l'output di FRED
            conn.setRequestProperty("accept", mode);
                    
            conn.setRequestProperty("Authorization",  "Bearer " +  Glossary.FRED_AUTHORIZATION);
            
            InputStream in = conn.getInputStream();
            tmp = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            Path tmpPath = tmp.getAbsoluteFile().toPath();
            tmp.delete();
            Files.copy(in, tmpPath);

        } catch (IOException e) {
            Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, e);
            FileHandler ext = new FileHandler();
            if (mode.equalsIgnoreCase(Glossary.FRED_IMAGE)) {
                ext.getFile(NOFREDPNG);
                tmp = new File(PAGESDIR + NOFREDPNG);
            } else {
                ext.getFile(NOFREDTEXT);
                tmp = new File(PAGESDIR + NOFREDTEXT);
            }
            return tmp;
        }

        return tmp;
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

    public static String getFredString(String text, String mode) {

        if (!isIpReachable()) {
            return "FRED is not Reachable!";
        }
        String tmp = null;
        try {
            String request = URLEncoder.encode(text, ENC);
            String url = COMMAND + request + COMMAND2;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(TIMEOUT);
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            //determina l'output di FRED
            conn.setRequestProperty("accept", mode);
            conn.setRequestProperty("Authorization",  "Bearer " +  Glossary.FRED_AUTHORIZATION);
            
            InputStream in = conn.getInputStream();
            StringBuilder textBuilder = new StringBuilder();
            Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())));
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
            tmp = textBuilder.toString();

        } catch (IOException e) {
            Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, e);
            return "FRED is not Reachable!";
        }

        return tmp;
    }

    public static boolean isIpReachable() {
        boolean state = false;

        Process p;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 -w 1 " + Glossary.FREDHOST);
            InputStream in = (p.getInputStream());
            String test = IOUtils.toString(in, Glossary.ENC);

            if (test.contains("1 packets transmitted, 1 received, 0% packet loss")) {
                state = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }

}
