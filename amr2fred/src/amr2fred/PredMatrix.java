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

import static amr2fred.Glossary.FRED;
import static amr2fred.Glossary.NodeStatus.OK;
import static amr2fred.Line.lineComparator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for in-memory load and use of predmatrix table
 *
 * @author anto
 */
public class PredMatrix {

    private static String FILE = getPath() + "resource/predmatrix.txt";
    private static final String FILE2 = "resource/predmatrix.txt";
    private static PredMatrix p;
    private final ArrayList<Line> matrix;

    private PredMatrix() {
        this.matrix = read();
    }

    /**
     * Get PredMatrix instance
     *
     * @return singleton instance of predmatrix
     */
    public static PredMatrix getPredMatrix() {
        if (p == null) {
            p = new PredMatrix();
        }
        return p;
    }

    private ArrayList<Line> read() {
        try {
            ArrayList<Line> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                l.add(new Line(line));
                line = reader.readLine();
            }

            return l;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PredMatrix.class.getName()).log(Level.SEVERE, null, ex);
            FILE = FILE2;
            return read();
        } catch (IOException ex) {
            Logger.getLogger(PredMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns an arraylist of lines matching the requested field with param
     * word, desc sorted by 24_WN_SENSEFREC
     *
     * @param word value of the searching condition
     * @param field searching field
     * @return arraylist of lines matching the conditions
     */
    public ArrayList<Line> find(String word, Glossary.LineFields field) {
        ArrayList<Line> list = new ArrayList<>();
        matrix.stream().filter((l) -> (l.getLine().get(field.ordinal()).equalsIgnoreCase(word))).forEach((l) -> {
            list.add(l);
        });
        list.sort(lineComparator);
        return list;
    }

    /**
     * Returns an arraylist of lines matching the requested fields with param
     * word and param value
     *
     * @param word value of the first searching condition
     * @param field first searching field
     * @param field2 second searching field
     * @param value value of the second searching condition
     * @return arraylist of lines matching the conditions
     */
    public ArrayList<Line> find(String word, Glossary.LineFields field, Glossary.LineFields field2, String value) {
        ArrayList<Line> list = new ArrayList<>();
        matrix.stream().filter((l) -> (l.getLine().get(field.ordinal()).equalsIgnoreCase(word))
                && l.getLine().get(field2.ordinal()).equalsIgnoreCase(value)).forEach((l) -> {
            list.add(l);
        });
        list.sort(lineComparator);
        return list;
    }

    /**
     * Returns an arraylist of lines matching the requested fields with all the
     * implicit roles in the nodes of ArrayList args
     *
     * @param list ArrayList of lines
     * @param args ArrayList of nodes
     * @return arraylist of lines matching the conditions
     */
    public ArrayList<Line> find(ArrayList<Line> list, ArrayList<Node> args) {
        ArrayList<Line> result = new ArrayList<>();
        int num = args.size();
        int cfr;
        //confronta ogni riga della lista fornita con i ruoli impliciti contenuti nella lista di nodi args
        for (Line l : list) {
            String vnClass = l.getLine().get(Glossary.LineFields.VN_CLASS_NUMBER.ordinal());
            String vnSubClass = l.getLine().get(Glossary.LineFields.VN_SUBCLASS_NUMBER.ordinal());
            String lemma = "";
            // verifica se la subclass è null e la class non lo è
            if (vnSubClass.equalsIgnoreCase(Glossary.NULL) && !vnClass.equalsIgnoreCase(Glossary.NULL)) {
                lemma = vnClass;
                //la lista  da confrontare è quella formata dalle righe con la class specificata
                result = find(list, lemma, Glossary.LineFields.VN_CLASS_NUMBER);
            } else if (!vnSubClass.equalsIgnoreCase(Glossary.NULL)) {
                lemma = vnSubClass;
                 //la lista  da confrontare è quella formata dalle righe con la subclass specificata
                result = find(list, lemma, Glossary.LineFields.VN_SUBCLASS_NUMBER);
            }
            cfr = 0;
            for (Node n : args) {
                String r = Glossary.PB + n.relation.substring(4);
                for (Line l1 : result) {
                    if (l1.getLine().get(Glossary.LineFields.PB_ARG.ordinal()).equalsIgnoreCase(r)) {
                        //se il ruolo del nodo trova corrispondenza la variabile cfr viene incrementata
                        cfr++;
                        break;
                    }
                }
            }
            if (cfr >= num) {
                //se tutti i ruoli hanno trovato corrispondenza si restituisce la lista di righe
                return result;
            }
        }
        return null;
    }

    /**
     * Returns an arraylist of lines matching the PB_ARG field with param r
     *
     * @param list ArrayList of lines
     * @param r Number of pb: argument
     * @return arraylist of lines matching the condition
     */
    public ArrayList<Line> find(ArrayList<Line> list, String r) {
        ArrayList<Line> result = new ArrayList<>();
        for (Line l : list) {
            if (l.getLine().get(Glossary.LineFields.PB_ARG.ordinal()).equalsIgnoreCase(r)) {
                result.add(l);
            }
        }
        return result;
    }

    private ArrayList<Line> find(ArrayList<Line> list, String r, Glossary.LineFields field) {
        ArrayList<Line> result = new ArrayList<>();
        for (Line l : list) {
            if (l.getLine().get(field.ordinal()).equalsIgnoreCase(r)) {
                result.add(l);
            }
        }
        return result;
    }

    private static String getPath() {
        try {
            String path = PredMatrix.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            return decodedPath.substring(0, decodedPath.length() - 12);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Amr2Fred.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
