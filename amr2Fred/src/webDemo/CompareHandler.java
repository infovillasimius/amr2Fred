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

import amr2fred.Amr2fredWeb;
import amr2fred.DigraphWriter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import resultsComparator.Comparator;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webDemo.Glossary.AMR;
import static webDemo.Glossary.COMMONS;
import static webDemo.Glossary.ENC;
import static webDemo.Glossary.GRAPHIC;
import static webDemo.Glossary.IMG;
import static webDemo.Glossary.LOGO;
import static webDemo.Glossary.NOFREDPNG;
import static webDemo.Glossary.NOTRIPLE;
import static webDemo.Glossary.PAGESDIR;
import static webDemo.Glossary.PNG;
import static webDemo.Glossary.SENTENCE;
import static webDemo.Glossary.SVG;
import static webDemo.Glossary.TXT;
import static webDemo.Glossary.TYPE;

/**
 *
 * @author anto
 */
public class CompareHandler implements HttpHandler {

    public CompareHandler() {
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        File tmp = null;
        String amr = "", sentence;
        String request = URLDecoder.decode(he.getRequestURI().toASCIIString(), ENC);
        String par = request;
        String rdf1, rdf2;
        Amr2fredWeb amr2fred = new Amr2fredWeb();
        int commonsType = 0;

        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 11 && request.contains(COMMONS + GRAPHIC)) {
            commonsType = 1;
            request = request.replace(COMMONS + GRAPHIC, "");
        } else if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 11 && request.contains(COMMONS + IMG)) {
            commonsType = 2;
            request = request.replace(COMMONS + GRAPHIC, "");
        } else {
            commonsType = 0;
        }

        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 6 && request.contains(AMR)) {
            int pos = request.indexOf(AMR);
            int pos1 = request.indexOf(SENTENCE, pos + 1);
            if (pos1 < 0) {
                pos1 = request.length();
            }
            amr = request.substring(pos + 6, pos1);
        } else {
            amr = "No AMR";
        }

        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 11 && request.contains(SENTENCE)) {
            int pos = request.indexOf(SENTENCE);
            int pos1 = request.indexOf(AMR, pos + 1);
            if (pos1 < 0) {
                pos1 = request.length();
            }
            sentence = request.substring(pos + 11, pos1);
        } else {
            sentence = "No sentence";
        }

        rdf1 = amr2fred.go(amr, 2, 1, true, true);
        rdf2 = FredHandler.getFredString(sentence, webDemo.Glossary.FRED_N_TRIPLES);
        //System.out.println(rdf2);
        if (!rdf2.contains("FRED is not Reachable!")) {
            Comparator c = new Comparator(rdf2, rdf1);
            Headers responseHeaders = he.getResponseHeaders();
            switch (commonsType) {
                case 0: {
                    responseHeaders.set(TYPE, TXT);
                    String response = "Amr2Fred -> FRED = " + Math.ceil(c.getAmf() * 10000) / 100
                            + "%\n\nFRED -> Amr2Fred = " + Math.ceil(c.getFma() * 10000) / 100
                            + "%\n\nAmr2Fred \\ FRED\n" + c.getaMinusF()
                            + "\n\nFRED \\ Amr2Fred\n" + c.getfMinusA()
                            + "\n\nCommon triples: \n" + c.getCommons();
                    he.sendResponseHeaders(200, response.length());
                    try (OutputStream os = he.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    break;
                }
                case 1: {
                    responseHeaders.set(TYPE, SVG);
                    if (c.getRoot() != null) {
                        String response = DigraphWriter.toSvgString(c.getRoot());
                        he.sendResponseHeaders(200, response.length());
                        try (OutputStream os = he.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        noTriple(he);
                    }
                    break;
                }
                default:
                    if (c.getRoot() != null) {
                        responseHeaders.set(TYPE, PNG);
                        tmp = DigraphWriter.toPng(c.getRoot());
                        he.sendResponseHeaders(200, tmp.length());
                        try (OutputStream os = he.getResponseBody()) {
                            Files.copy(tmp.toPath(), os);
                            tmp.delete();
                        }
                    } else {
                        noTriple(he);
                    }
                    break;
            }

        } else {
            Headers responseHeaders = he.getResponseHeaders();
            switch (commonsType) {
                case 0: {
                    responseHeaders.set(TYPE, TXT);
                    String response = "FRED is not Reachable!";
                    he.sendResponseHeaders(200, response.length());
                    try (OutputStream os = he.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    break;
                }

                default:
                    FileHandler ext = new FileHandler();
                    responseHeaders.set(TYPE, PNG);
                    ext.getFile(NOFREDPNG);
                    tmp = new File(PAGESDIR + NOFREDPNG);
                    he.sendResponseHeaders(200, tmp.length());
                    try (OutputStream os = he.getResponseBody()) {
                        Files.copy(tmp.toPath(), os);
                        tmp.delete();
                    }
                    break;
            }

        }

    }

    private void noTriple(HttpExchange he) {

        try {
            Headers responseHeaders = he.getResponseHeaders();
            FileHandler ext = new FileHandler();
            File tmp;
            responseHeaders.set(TYPE, PNG);
            ext.getFile(NOTRIPLE);
            tmp = new File(PAGESDIR + NOTRIPLE);
            he.sendResponseHeaders(200, tmp.length());
            try (OutputStream os = he.getResponseBody()) {
                Files.copy(tmp.toPath(), os);
                tmp.delete();
            }
        } catch (IOException ex) {
            Logger.getLogger(CompareHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
