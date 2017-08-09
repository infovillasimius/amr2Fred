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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webDemo.Glossary.*;

/**
 * Handler for FRED access requests
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

        try {
            String request = URLEncoder.encode(text, ENC);
            String url = COMMAND + request + COMMAND2;
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            //determina l'output di FRED
            conn.setRequestProperty("accept", mode);
            InputStream in = conn.getInputStream();
            tmp = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            Path tmpPath = tmp.getAbsoluteFile().toPath();
            tmp.delete();
            Files.copy(in, tmpPath);

        } catch (IOException e) {
            Logger.getLogger(FredHandler.class.getName()).log(Level.SEVERE, null, e);
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
        
        //System.out.println(request);
        
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

}
