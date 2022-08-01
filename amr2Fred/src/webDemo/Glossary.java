/*
 * Copyright (C) 2017 Antonello
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

/**
 * Glossary class for strings, arrays and enum used in webdemo.
 *
 * @author anto
 */
public class Glossary {

    /**
     * WebServer parameters
     */
    public static final String RAW_ADDRESS = "127.0.0.1";
    public static final String RAW_PORT = "80";
    public static final String PAGESDIR = "pages/";
    public static final String INDEX = "index.html";
    public static final String JQUERY = "jquery.js";
    public static final String JQUERY_UI = "jquery-ui.min.js";
    public static final String SCRIPT = "go.js";
    public static final String STYLE = "style.css";
    public static final String LISTA = "lista.txt";
    public static final String LOGO = "Logo_UniCa.png";
    public static final String AMR2FRED = "/amr2fred";
    public static final String IP_REGEX = "[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]*";
    public static final String FRED = "/fred";
    public static final String WEB_ROOT = "/";
    public static final int MAX_REQUESTS = 10;
    public static final String LOGO_CNR = "cnr-logo.png";
    public static final String LOGO_STLAB = "logoSTLABISTCCNR.gif";
    public static final String LOGO_STLAB_BG = "logo-stlab.png";
    public static final String LOGO_KTOOLS = "ktools_logo_short.png";
    public static final String HELP = "help.html";
    public static final String HELP_SCRIPT = "help.js";
    public static final String LOADING = "loading.gif";
    public static final String COMPARE = "/compare";
    public static final String NOFREDTEXT = "fred_not_reachable.txt";
    public static final String NOFREDPNG = "fred_not_reachable.png";
    public static final String NOTRIPLE = "no_triple.png";

    /**
     * FRED parameters
     */
    public static final String FREDHOST = "wit.istc.cnr.it";
    public static final String COMMAND = "http://wit.istc.cnr.it/stlab-tools/fred?text=";
    public static final String COMMAND2 = "&wfd_profile=b&textannotation=earmark&semantic-subgraph=true";
    public static final String FRED_RDF = "application/rdf+xml";
    public static final String FRED_TURTLE = "text/turtle";
    public static final String FRED_N_TRIPLES = "text/rdf+nt";
    public static final String FRED_N3 = "text/rdf+n3";
    public static final String FRED_IMAGE = "image/png";
    public static final String FRED_AUTHORIZATION = "";
    public static final int FRED_RECHARGE_TIME=15;
    public static final String TEXT = "&?text=";
    public static final int TIMEOUT = 5000;
    public static final int FRED_CACHE = 200;
    public static final String FRED_CACHE_FILE = "cache.txt";
    public static final String FRED_CACHE_URL_REQUEST = "<FredRequestUrl>";
    public static final String FRED_CACHE_URL_END_REQUEST = "</FredRequestUrl>";
    public static final String FRED_CACHE_RESULT = "<FredResult>";
    public static final String FRED_CACHE_END_RESULT = "</FredResult>";
    public static int FRED_QUERIES=4;

    /**
     * AMR2FRED parameters
     */
    public static final String AMR = "&?amr=";
    public static final String MODE = "&?format=";
    public static final String PROMODE = "&?proMode=ON";
    public static final String RDF_XML = "RDF_XML";
    public static final String RDF_XML_ABBREV = "RDF_XML_ABBREV";
    public static final String N_TRIPLES = "N_TRIPLES";
    public static final String TURTLE = "TURTLE";
    public static final String GRAPHIC = "DIGRAPH";
    public static final String IMG = "png";
    public static final String RID_ERR = "&?rid_err=ON";
    public static final String RES_OBJ = "&?objAsRes";
    public static final String ENC = "UTF-8";
    public static final String SENTENCE = "&?sentence=";
    public static final String COMMONS = "&?commons=";
    public static final String ALT_LABEL = "&?alt_label";

    public static final String TMP_FILE_NAME = "amr2fred";
    public static final String TMP_FILE_EXT = "tmp";
    
    public static final String TYPE = "content-type";
    public static final String CSS = "text/css";
    public static final String GIF = "image/gif";
    public static final String HTML = "text/html";
    public static final String ICO = "image/x-icon";
    public static final String JS = "application/x-javascript";
    public static final String PNG = "image/png";
    public static final String TXT = "text/plain";
    public static final String SVG = "image/svg+xml";

    public static final String[] MIME = {"css", "gif", "html", "ico", "js", "png", "txt"};
    public static final String[] MIME_TYPE = {CSS, GIF, HTML, ICO, JS, PNG, TXT};

}
