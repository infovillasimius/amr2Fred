/*
 * Copyright (C) 2022 anto
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author meloni.antonello
 */
public class Propbank {

    private static final String SEPARATOR = "\t";
    private static final String FILE1 = "propbankrolematrix.tsv";
    private static final String FILE2 = "propbankframematrix.tsv";
    private static Propbank p;
    private final ArrayList<ArrayList<String>> role_matrix;
    private final ArrayList<ArrayList<String>> frame_matrix;

    private Propbank() {
        this.role_matrix = read(FILE1, 4);
        this.frame_matrix = read(FILE2, 5);
    }

    /**
     * Get Pb2vn instance
     *
     * @return singleton instance of Pb2vn
     */
    public static Propbank getPropbank() {
        if (p == null) {
            p = new Propbank();
        }
        return p;
    }

    public ArrayList<String> find(String verb, String role) {

        return null;
    }

    public ArrayList<ArrayList<String>> find(String word, Glossary.PropbankFrameFields field) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        this.frame_matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }

    public ArrayList<ArrayList<String>> find(String word, Glossary.PropbankFrameFields field, Glossary.PropbankFrameFields field2, String value) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        this.frame_matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))
                && l.get(field2.ordinal()).equalsIgnoreCase(value)).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }

    private ArrayList<ArrayList<String>> read(String file, int n) {
        try {
            ArrayList<ArrayList<String>> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(getClass().getResourceAsStream(file)));
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                ArrayList<String> newline = line(line);
                for (int i = newline.size(); i < n; i++) {
                    newline.add(null);
                }

                l.add(newline);
                line = reader.readLine();
            }

            return l;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PredMatrix.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(PredMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private ArrayList<String> line(String string) {

        int inizio, fine;
        String word;
        ArrayList<String> list = new ArrayList<>();

        try {
            inizio = 0;
            while (string.length() > 1) {

                fine = string.indexOf(SEPARATOR, inizio);
                if (fine == -1) {
                    fine = string.length();
                }

                word = string.substring(inizio, fine);

                string = string.substring(fine);
                list.add(word);
                inizio = string.indexOf(SEPARATOR) + 1;
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {

            return null;
        }

        return list;
    }

    /**
     * Returns an arraylist of lines matching the requested fields with all the
     * implicit roles in the nodes of ArrayList args
     *
     * @param word base form of verb from amr
     * @param args ArrayList of nodes
     * @return arraylist of lines matching the conditions
     */
    public ArrayList<ArrayList<String>> find(String word, ArrayList<Node> args) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        int num = args.size();
        int cfr;
        if (!word.contains(Glossary.PB_DATA)) {
            word = Glossary.PB_DATA + word;
        }
        cfr = 0;
        ArrayList<ArrayList<String>> list = find(word, Glossary.PropbankFrameFields.PB_Frame);

        for (Node n : args) {
            String r = (word + "__" + n.relation.substring(4));

            for (ArrayList<String> l1 : list) {
                //System.out.println(r + " " + l1.get(Glossary.PropbankFrameFields.PB_Role.ordinal()));
                if (l1.get(Glossary.PropbankFrameFields.PB_Role.ordinal()).equalsIgnoreCase(r)) {
                    //se il ruolo del nodo trova corrispondenza la variabile cfr viene incrementata
                    cfr++;
                    result.addAll(find(r, Glossary.PropbankRoleFields.PB_Role));
                    //System.out.println(r + " " + l1.get(Glossary.PropbankFrameFields.PB_Role.ordinal()));
                    break;
                }
            }
        }

        if (cfr >= num) {
            //se tutti i ruoli hanno trovato corrispondenza si restituisce la lista di righe
            //System.out.println(result);
            return result;
        }

        return null;
    }

    public ArrayList<ArrayList<String>> find(String word, Glossary.PropbankRoleFields field) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        this.role_matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }
    
    public ArrayList<ArrayList<String>> find(String word, Glossary.PropbankRoleFields field, String value, Glossary.PropbankRoleFields field2) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        this.role_matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))
                && l.get(field2.ordinal()).equalsIgnoreCase(value)).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }

}
