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

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Arrays;
import static webDemo.Glossary.*;

/**
 * Handle static requests to webserver
 *
 * @author anto
 */
public class StaticHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {

        //Decodifica url passato al server e lo rende come stringa
        String request = URLDecoder.decode(he.getRequestURI().toASCIIString(), "UTF-8");
        
        //ottiene l'header per la risposta (modificabile)
        Headers responseHeaders = he.getResponseHeaders();

        File f = null;
        FileHandler ext = new FileHandler();

        request = request.substring(1);
              
        //analizza stringa di richiesta
        switch (request) {
            case "":
                ext.getFile(INDEX);
                f = new File(PAGESDIR + INDEX);
                break;
            case JQUERY:
                ext.getFile(JQUERY);
                f = new File(PAGESDIR + JQUERY);
                break;
            case SCRIPT:
                ext.getFile(SCRIPT);
                f = new File(PAGESDIR + SCRIPT);
                break;
            case STYLE:
                ext.getFile(STYLE);
                f = new File(PAGESDIR + STYLE);
                break;
            case LISTA:
                ext.getFile(LISTA);
                f = new File(PAGESDIR + LISTA);
                break;
            case LOGO_CNR:
                ext.getFile(LOGO_CNR);
                f = new File(PAGESDIR + LOGO_CNR);
                break;
            case LOGO_STLAB:
                ext.getFile(LOGO_STLAB);
                f = new File(PAGESDIR + LOGO_STLAB);
                break;
            case LOGO_STLAB_BG:
                ext.getFile(LOGO_STLAB_BG);
                f = new File(PAGESDIR + LOGO_STLAB_BG);
                break;
            case LOGO_KTOOLS:
                ext.getFile(LOGO_KTOOLS);
                f = new File(PAGESDIR + LOGO_KTOOLS);
                break;
            case JQUERY_UI:
                ext.getFile(JQUERY_UI);
                f = new File(PAGESDIR + JQUERY_UI);
                break;
            case HELP:
                ext.getFile(HELP);
                f = new File(PAGESDIR + HELP);
                break;
            case HELP_SCRIPT:
                ext.getFile(HELP_SCRIPT);
                f = new File(PAGESDIR + HELP_SCRIPT);
                break;
            case LOADING:
                ext.getFile(LOADING);
                f = new File(PAGESDIR + LOADING);
                break;
            
            //qualsiasi richiesta al server non ricompresa nelle precedenti ottiene come risposta il logo di UniCA
            default:
                ext.getFile(LOGO);
                f = new File(PAGESDIR + LOGO);

        }

        //recupera l'estensione del file da inviare per settare correttamente il mime type nell'header
        String est = f.getName().substring(f.getName().lastIndexOf('.') + 1);

        int x = Arrays.asList(MIME).indexOf(est);
        if (x > -1 && x < 7) {
            responseHeaders.set("content-type", MIME_TYPE[x]);
        }
        
        //invia il file
        he.sendResponseHeaders(200, f.length());
        try (OutputStream os = he.getResponseBody()) {
            Files.copy(f.toPath(), os);
        }
    }

}
