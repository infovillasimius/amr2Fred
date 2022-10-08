/*
 * Copyright (C) 2016 anto
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
package amr2fred;

import static amr2fred.Glossary.FRED_NS;
import static amr2fred.Glossary.PREFIX_NUM;
import static amr2fred.Glossary.TOP;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.rdf.model.*;
import org.apache.log4j.BasicConfigurator;

/**
 * Translate Fred elements from the internal format to one from ["RDF/XML",
 * "RDF/XML-ABBREV", "N-TRIPLE", "TURTLE"]
 *
 * @author anto
 */
public class RdfWriter {

    //Istanza singleton di RdfWriter
    private static RdfWriter writer;

    //Jena model
    private Model model;

    //Coda per la lettura dell'albero per livelli
    private LinkedBlockingQueue<Node> list;

    //Modo di output
    private String mode = Glossary.RDF_MODE[Glossary.RdfWriteMode.TURTLE.ordinal()];

    //Controllo per inserimento elemento object come stringa o come resource

    private RdfWriter() {

    }

    /**
     * Get singleton instance of RdfWriter
     *
     * @return instance of RdfWriter
     */
    public static RdfWriter getWriter() {
        if (writer == null) {
            BasicConfigurator.configure();
            writer = new RdfWriter();
        }
        writer.model = ModelFactory.createDefaultModel();
        writer.list = new LinkedBlockingQueue<>();
        return writer;
    }

    /**
     * Set output mode
     *
     * @param mode A valid value from Glossary.RDF_MODE
     */
    public void setMode(Glossary.RdfWriteMode mode) {
        if (mode.ordinal() >= 0 && mode.ordinal() < Glossary.RDF_MODE_MAX) {
            this.mode = Glossary.RDF_MODE[mode.ordinal()];
        }
    }

    /**
     * Traslate nodes from internal format to the chosen one
     *
     * @param root Node in internel language
     * @return The tree in the chosen format
     */
    public String writeRdf(Node root) {
        toRdf(root);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        model.write(b, mode);
        String rdf = b.toString();
        return rdf;
    }

    /*
    Effettua la traduzione aggiungendo al modello Jena i nodi, a partire da quello radice,
    con una visita per livello. 
     */
    private void toRdf(Node root) {

        try {
            this.list.put(root);
        } catch (InterruptedException ex) {
            Logger.getLogger(RdfWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!list.isEmpty()) {
            Node n = list.poll();
            for (Node n1 : n.list) {
                try {
                    list.put(n1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RdfWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            for (Node n1 : n.list) {

                Resource r = model.createResource(this.getUri(n.var));
                if (!n1.relation.equalsIgnoreCase(TOP)) {

                    Property p = model.createProperty(getPref(n1.relation), getLocal(n1.relation));

                    if (n1.var.matches(Glossary.NN_INTEGER2)) {
                        Literal o = model.createTypedLiteral(n1.var, Glossary.NN_INTEGER_NS);
                        model.add(model.createStatement(r, p, o));
                    } else if (n1.var.matches(Glossary.DATE_SCHEMA)) {
                        Literal o = model.createTypedLiteral(n1.var, Glossary.DATE_SCHEMA_NS);
                        model.add(model.createStatement(r, p, o));
                    } else if (n1.relation.equalsIgnoreCase(Glossary.RDFS_LABEL) || n1.var.matches(Glossary.NN_RATIONAL)) {
                        Literal o = model.createTypedLiteral(n1.var, Glossary.STRING_SCHEMA_NS);
                        model.add(model.createStatement(r, p, o));
                    } else {
                        Resource o = model.createResource(getUri(n1.var));
                        model.add(model.createStatement(r, p, o));
                    }
                }
            }
        }
    }

    /*
    Restituisce Uri della risorsa o del literal a partire dal nome usato nel formato interno
    (etichette usate nel grafico generato da FRED all'indirizzo http://etna.istc.cnr.it/kore/fred)
    La traduzione avviene mediante confronto con i PREFIX contenuti nel relativo array di stringhe
    e sostituzione del corrispondente valore dell'array dei namespace.
     */
    private String getUri(String s) {
        String pref;
        String name;
        int dp = s.indexOf(':');
        if (dp < 0) {
            name = s;
            return name;
        } else {
            pref = s.substring(0, dp + 1);
            name = s.substring(dp + 1);
        }
        for (int n = 0; n < PREFIX_NUM; n++) {
            if (pref.equalsIgnoreCase(Glossary.PREFIX[n])) {
                return Glossary.NAMESPACE[n] + name;
            }
        }
        if (pref.equalsIgnoreCase("_:")) {
            return s;
        }
        return FRED_NS + "notFound/" + name;
    }

    /*
    Restituisce il namespace a partire dal nome usato nel formato interno
    (etichette usate nel grafico generato da FRED all'indirizzo http://etna.istc.cnr.it/kore/fred)
    La traduzione avviene mediante confronto con i PREFIX contenuti nel relativo array di stringhe
    e sostituzione del corrispondente valore dell'array dei namespace.
     */
    private String getPref(String s) {
        String pref;
        int dp = s.indexOf(':');
        if (dp < 0) {
            pref = "";
            //return pref;
        } else {
            pref = s.substring(0, dp + 1);
        }
        for (int n = 0; n < PREFIX_NUM; n++) {
            if (pref.equalsIgnoreCase(Glossary.PREFIX[n])) {
                return Glossary.NAMESPACE[n];
            }
        }
        return "http://notFound/";
    }

    /*Restituisce il local name a partire dal nome usato nel formato interno
    (etichette usate nel grafico generato da FRED all'indirizzo http://etna.istc.cnr.it/kore/fred)
    La traduzione avviene mediante confronto con i PREFIX contenuti nel relativo array di stringhe
    e sostituzione del corrispondente valore dell'array dei namespace.
     */
    private String getLocal(String s) {
        String name;
        int dp = s.indexOf(':');
        if (dp < 0) {
            name = s;
        } else {
            name = s.substring(dp + 1);
        }
        return name;
    }

}
