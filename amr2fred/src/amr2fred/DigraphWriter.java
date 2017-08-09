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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import static webDemo.Glossary.*;

/**
 *
 * @author anto
 */
public class DigraphWriter {

    static public String nodeToDigraph(Node root) {

        String digraph = Glossary.DIGRAPH_INI;
        digraph += toDigraph(root);
        return digraph + "}";
    }

    static private String toDigraph(Node root) {

        String digraph = "";
        digraph += "\"" + root.getNodeId() + "\" [label=\"" + root.var + "\", shape=box ];\n";
        if (!root.list.isEmpty() && root.getTreStatus() == 0) {
            for (Node a : root.list) {
                digraph += "\"" + a.getNodeId() + "\" [label=\"" + a.var + "\", shape=box ];\n";
                if (!a.relation.equalsIgnoreCase(Glossary.TOP)) {
                    digraph += "\"" + root.getNodeId() + "\" -> \"" + a.getNodeId() + "\" [label=\"" + a.relation + "\"];\n";
                }
                digraph += toDigraph(a);
            }
        }
        return digraph;
    }

    static public File toPng(Node root) {

        File tmpOut = null;
        try {
            File tmp = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            tmpOut = File.createTempFile(TMP_FILE_NAME, TMP_FILE_EXT);
            Path tmpOutPath=tmpOut.getAbsoluteFile().toPath();
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
        //System.out.println(tmpOut.getAbsolutePath());
        return output.toString();
    }
}
