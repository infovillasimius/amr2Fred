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
package amr2fred;

import static amr2fred.Glossary.NodeStatus.REMOVE;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webDemo.Glossary.*;
import resultsComparator.Converter;

/**
 * Returns graphic representation of translated root
 *
 * @author anto
 */
public class DigraphWriter {

    /**
     * Returns root Node translated into .dot graphic language
     *
     * @param root Node
     * @return String
     */
    static public String nodeToDigraph(Node root) {

        /*
        RdfWriter writer = RdfWriter.getWriter();
        writer.setMode(Glossary.RdfWriteMode.N_TRIPLES);
        Node new_root = check_visibility(root);
        String rdf = writer.writeRdf(new_root);
        new_root = Converter.toNode(rdf);
         */
        Node new_root = root;

        String digraph = Glossary.DIGRAPH_INI;
        digraph += toDigraph(new_root);
        return digraph + Glossary.DIGRAPH_END;
    }

    static private String toDigraph(Node root) {
        String shape = "box";
        if (root.isMalformed()) {
            shape = "ellipse";
        }
        String digraph = "";
        digraph += "\"" + root.var /*getNodeId()*/ + "\" [label=\"" + root.var + "\", shape=" + shape + ",";
        if (root.var.startsWith(amr2fred.Glossary.FRED)) {
            digraph += " color=\"0.5 0.3 0.5\" ];\n";
        } else {
            digraph += " color=\"1.0 0.3 0.7\" ];\n";
        }

        if (!root.list.isEmpty() && root.getTreStatus() == 0) {
            for (Node a : root.list) {
                if (a.visibility) {
                    if (a.isMalformed()) {
                        shape = "ellipse";
                    }
                    digraph += "\"" + a.var /*getNodeId()*/ + "\" [label=\"" + a.var + "\", shape="+ shape + ",";
                    if (a.var.startsWith(amr2fred.Glossary.FRED)) {
                        digraph += " color=\"0.5 0.3 0.5\" ];\n";
                    } else {
                        digraph += " color=\"1.0 0.3 0.7\" ];\n";
                    }
                    if (!a.relation.equalsIgnoreCase(Glossary.TOP)) {
                        digraph += "\"" + root.var /*getNodeId()*/ + "\" -> \"" + a.var /*getNodeId()*/ + "\" [label=\"" + a.relation + "\"];\n";
                    }
                    digraph += toDigraph(a);
                }
            }
        }
        return digraph;
    }

    /**
     * Returns an image file (png) of the translated root node
     *
     * @param root translated root node
     * @return image file (png)
     */
    static public File toPng(Node root) {

        File tmpOut = null;
        try {
            File tmp = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            tmpOut = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            Path tmpOutPath = tmpOut.getAbsoluteFile().toPath();
            tmpOut.delete();
            BufferedWriter buff = new BufferedWriter(new FileWriter(tmp));
            buff.write(nodeToDigraph(root));
            buff.close();
            Process p = Runtime.getRuntime().exec("dot -Tpng " + tmp.getAbsolutePath());
            InputStream in = (p.getInputStream());
            Files.copy(in, tmpOutPath);
            p.destroy();
            tmp.delete();
            tmpOut.deleteOnExit();

        } catch (IOException ex) {
            Logger.getLogger(DigraphWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpOut;
    }

    /**
     * Return a String containing a SVG image of translated root node
     *
     * @param root translated root node
     * @return String containing a SVG image
     */
    static public String toSvgString(Node root) {

        StringBuffer output = new StringBuffer();
        try {

            File tmp = File.createTempFile("file", ".tmp");
            BufferedWriter buff = new BufferedWriter(new FileWriter(tmp));
            buff.write(nodeToDigraph(root));
            buff.close();
            Process p = Runtime.getRuntime().exec("dot -Tsvg " + tmp.getAbsolutePath());
            InputStreamReader in = new InputStreamReader(p.getInputStream());
            BufferedReader reader = new BufferedReader(in);
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            p.destroy();
            reader.close();
            tmp.delete();

        } catch (IOException ex) {
            Logger.getLogger(DigraphWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output.toString();
    }

    static private Node check_visibility(Node root) {

        for (Node n : root.getList()) {

            if (!n.visibility) {
                n.setStatus(REMOVE);
            }
        }

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() == REMOVE) {
                it.remove();
            }
        }

        for (Node n : root.getList()) {

            n = check_visibility(n);
        }

        return root;
    }
}
