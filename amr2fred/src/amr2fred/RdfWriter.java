package amr2fred;

import static amr2fred.Glossary.FRED_NS;
import static amr2fred.Glossary.PREFIX_NUM;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

/**
 *
 * @author anto
 */
public class RdfWriter {

    private static RdfWriter writer;
    private Model model;
    private LinkedBlockingQueue<Node> list;
    private String mode = "Turtle";

    private RdfWriter() {

    }

    public static RdfWriter getWriter() {
        if (writer == null) {
            writer = new RdfWriter();
        }
        writer.model = ModelFactory.createDefaultModel();
        writer.list = new LinkedBlockingQueue<>();
        return writer;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(int mode) {
        if (mode < 5 && mode >= 0) {
            this.mode = Glossary.rdfMode[mode];
        }
    }

    public String writeRdf(Node root) {

        toRdf(root);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        model.write(b, mode);

        String rdf = b.toString();
        return rdf;
    }

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
                Property p = model.createProperty(getPref(n1.relation), getLocal(n1.relation));
                Resource o = model.createResource(getUri(n1.var));
                r.addProperty(p, o);
            }
        }

    }

    private String getUri(String s) {

        String pref;
        String name;
        int dp = s.indexOf(':');
        if (dp < 0) {
            pref = "";
            name = s;
        } else {
            pref = s.substring(0, dp + 1);
            name = s.substring(dp + 1);
        }
        for (int n = 0; n < PREFIX_NUM; n++) {

            if (pref.equalsIgnoreCase(Glossary.prefix[n])) {

                return Glossary.namespace[n] + name;
            }

        }
        return FRED_NS + "notFound";
    }

    private String getPref(String s) {
        String pref;
        //String name;
        int dp = s.indexOf(':');
        if (dp < 0) {
            pref = "";
            //name = s;
        } else {
            pref = s.substring(0, dp + 1);
            //name = s.substring(dp + 1);
        }
        for (int n = 0; n < PREFIX_NUM; n++) {

            if (pref.equalsIgnoreCase(Glossary.prefix[n])) {

                return Glossary.namespace[n];
            }

        }
        return "http://notFound/";
    }

    private String getLocal(String s) {
        //String pref;
        String name;
        int dp = s.indexOf(':');
        if (dp < 0) {
            //pref = "";
            name = s;
        } else {
            //pref = s.substring(0, dp + 1);
            name = s.substring(dp + 1);
        }

        return name;
    }

}
