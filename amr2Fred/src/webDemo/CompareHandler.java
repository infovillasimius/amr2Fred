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
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fileConvert.Comparator;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import static webDemo.Glossary.AMR;
import static webDemo.Glossary.ENC;
import static webDemo.Glossary.SENTENCE;
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
        String amr = "", sentence;
        String request = URLDecoder.decode(he.getRequestURI().toASCIIString(), ENC);
        String par = request;
        String rdf1,rdf2;
        Amr2fredWeb amr2fred = new Amr2fredWeb();

        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 6 && request.contains(AMR)) {
            int pos = request.indexOf(AMR);
            int pos1 = request.indexOf(SENTENCE, pos+1);
            if (pos1 < 0) {
                pos1 = request.length();
            }
            amr = request.substring(pos + 6, pos1);
        } else {
            amr = "No AMR";
        }
        
        if (he.getRequestMethod().equalsIgnoreCase("GET") && request.length() > 11 && request.contains(SENTENCE)) {
            int pos = request.indexOf(SENTENCE);
            int pos1 = request.indexOf(AMR, pos+1);
            if (pos1 < 0) {
                pos1 = request.length();
            }
            sentence = request.substring(pos + 11, pos1);
        } else {
            sentence = "No sentence";
        }
        
        rdf1 = amr2fred.go(amr, 2, 1, true, true, true);
        rdf2 = FredHandler.getFredString(sentence, webDemo.Glossary.FRED_N_TRIPLES);
        
        Comparator c=new Comparator(rdf2, rdf1);
        
        //System.out.println(c.getaMinusF()+"\n\n"+c.getfMinusA());

        Headers responseHeaders = he.getResponseHeaders();
        responseHeaders.set(TYPE, TXT);
        String response ="Amr2Fred -> FRED = "+Math.ceil(c.getAmf()*10000)/100
                +"%\n\nFRED -> Amr2Fred = "+Math.ceil(c.getFma()*10000)/100
                +"%\n\nAmr2Fred \\ FRED\n"+c.getaMinusF()
                +"\n\nFRED \\ Amr2Fred\n"+c.getfMinusA()
                +"\n\nCommon triples: \n"+c.getCommons(); /* amr+"\n "+sentence;*/
        he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }

    }

}
