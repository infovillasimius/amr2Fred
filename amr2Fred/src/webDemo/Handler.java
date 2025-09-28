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
import amr2fred.Glossary;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Arrays;
import static webDemo.Glossary.*;
import org.json.JSONObject;

/**
 * Handler for amr2fred REST service
 *
 * @author anto
 */
public class Handler implements HttpHandler {

    Amr2fredWeb amr2fred;

    public Handler(Amr2fredWeb amr2fred) {
        this.amr2fred = amr2fred;

    }

    @Override
    public void handle(HttpExchange he) throws IOException {

        String amr = "";
        String altFredUri = "";
        File tmp = null;
        boolean cb = false, proMode = false, png = false, svg = false;
        int check = 0, writerMode = 0;

        String request = URLDecoder.decode(he.getRequestURI().toASCIIString(), ENC);

        String par = request;
        try {
            if (null == he.getRequestMethod()) {
                amr = "No AMR";
            } else {
                switch (he.getRequestMethod()) {
                    case "GET":
                        // controllo alternate Fred Uri
                        if (par.contains(ALT_FRED_URI)) {
                            int pos = par.indexOf(ALT_FRED_URI);
                            int pos1 = par.indexOf("&", pos + 13);
                            if (pos1 < pos + 13) {
                                altFredUri = par.substring(pos + 13);
                            } else {
                                altFredUri = par.substring(pos + 13, pos1);
                            }
                            par = par.replace(ALT_FRED_URI + altFredUri, "");
                            Glossary.FRED_NS = altFredUri;
                            Glossary.NAMESPACE[0] = altFredUri;
                        } else {
                            Glossary.FRED_NS = Glossary.DEFAULT_FRED_NS;
                            Glossary.NAMESPACE[0] = Glossary.DEFAULT_FRED_NS;
                        }   //controllo per promode
                        if (par.contains(PROMODE)) {
                            par = par.replace(PROMODE, "");
                            proMode = false;
                        } else {
                            proMode = true;
                        }   //controllo per modo output
                        if (par.contains(MODE + RDF_XML)) {
                            par = par.replace(MODE + RDF_XML, "");
                            writerMode = (Glossary.RdfWriteMode.RDF_XML.ordinal());
                        } else if (par.contains(MODE + RDF_XML_ABBREV)) {
                            par = par.replace(MODE + RDF_XML_ABBREV, "");
                            writerMode = (Glossary.RdfWriteMode.RDF_XML_ABBREV.ordinal());
                        } else if (par.contains(MODE + N_TRIPLES)) {
                            par = par.replace(MODE + N_TRIPLES, "");
                            writerMode = (Glossary.RdfWriteMode.N_TRIPLES.ordinal());
                        } else if (par.contains(MODE + TURTLE)) {
                            par = par.replace(MODE + TURTLE, "");
                            writerMode = (Glossary.RdfWriteMode.TURTLE.ordinal());
                        } else if (par.contains(MODE + GRAPHIC)) {
                            par = par.replace(MODE + GRAPHIC, "");
                            proMode = (false);
                            svg = true;
                        } else if (par.contains(MODE + IMG)) {
                            par = par.replace(MODE + IMG, "");
                            png = (true);
                        } else {
                            proMode = (true);
                        }   //controllo per elimina errori dalla struttura dati
                        if (par.contains(RID_ERR)) {
                            par = par.replace(RID_ERR, "");
                            cb = (true);
                            check = (1);
                        } else {
                            cb = (false);
                            check = (0);
                        }
                        request = par;
                        if (request.length() > 6 && request.contains(AMR)) {

                            int pos = request.indexOf(AMR);
                            amr = request.substring(pos + 6);
                            if (amr.startsWith("\n")) {
                                amr = amr.replace("\n", "");
                            }
                            if (png) {
                                tmp = amr2fred.goPng(amr);
                            } else {
                                amr = amr2fred.go(amr, writerMode, check, cb, proMode);
                            }
                        }
                        break;
                    case "POST":
                        boolean go = true;
                        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                        BufferedReader br = new BufferedReader(isr);
                        String query = br.readLine();
                        query = URLDecoder.decode(query, ENC);
                        while (query.startsWith(" ")) {
                            query = query.substring(1);
                        }
                        if (query.length() <= 0 || query.length() <= query.indexOf("=") + 1) {
                            amr = "No AMR";
                            go = false;
                        } else if (!query.startsWith("{") && query.contains("=") && query.length() > query.indexOf("=") + 1) {
                            query = query.substring(query.indexOf("=") + 1);
                        }

                        if (go) {
                            JSONObject obj = new JSONObject(query);
                            amr = obj.optString(JSON_AMR, "No AMR");
                            cb = obj.optBoolean(JSON_RID, false);
                            if (cb) {
                                check = (1);
                            } else {
                                check = (0);
                            }
                            String format = obj.optString(JSON_FORMAT, IMG);
                            int index = Arrays.asList(MODES).indexOf(format);
                            if (index > -1 && index < 4) {
                                writerMode = index;
                                png = false;
                                proMode = true;
                            } else if (index == 4) {
                                proMode = (false);
                                svg = true;
                                png = false;
                            } else {
                                png = true;
                            }

                            if (obj.has(JSON_ALT) && obj.getString(JSON_ALT).length() > 0) {
                                altFredUri = obj.getString(JSON_ALT);
                                Glossary.FRED_NS = altFredUri;
                                Glossary.NAMESPACE[0] = altFredUri;
                            } else {
                                Glossary.FRED_NS = Glossary.DEFAULT_FRED_NS;
                                Glossary.NAMESPACE[0] = Glossary.DEFAULT_FRED_NS;
                            }

                            if (png) {
                                tmp = amr2fred.goPng(amr);
                            } else {
                                amr = amr2fred.go(amr, writerMode, check, cb, proMode);
                            }
                        }
                        break;
                    default:
                        amr = "No AMR";
                        break;
                }
            }
        } catch (Exception ex) {
            amr = "No AMR";
        }
        Headers responseHeaders = he.getResponseHeaders();
        //settaggio del mime_type
        if (!png || tmp == null) {
            String response = amr;
            if (svg) {
                responseHeaders.set(TYPE, SVG);
            } else {
                responseHeaders.set(TYPE, TXT);
            }
            he.sendResponseHeaders(200, response.length());
            try ( OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            responseHeaders.set(TYPE, PNG);
            he.sendResponseHeaders(200, tmp.length());
            try ( OutputStream os = he.getResponseBody()) {
                Files.copy(tmp.toPath(), os);
                tmp.delete();
            }
        }
    }

}
