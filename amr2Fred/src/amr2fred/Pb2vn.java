/*
 * Copyright (C) 2022 meloni.antonello
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
public class Pb2vn {
    
    private static final String SEPARATOR = "\t";
    private static final String FILE4 = "pb2vn.txt";
    private static Pb2vn p;
    private final ArrayList<ArrayList<String>> matrix;
    
    private Pb2vn() {
        this.matrix = read();
    }
    
    /**
     * Get Pb2vn instance
     *
     * @return singleton instance of Pb2vn
     */
    public static Pb2vn getPb2vn() {
        if (p == null) {
            p = new Pb2vn();
        }
        return p;
    }
    
    public ArrayList<String> find(String verb, String role){
        
        return null;
    }
    
    public ArrayList<ArrayList<String>> find(String word, Glossary.Pb2vnFields field) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }
    
    public ArrayList<ArrayList<String>> find(String word, Glossary.Pb2vnFields field, Glossary.Pb2vnFields field2, String value) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        matrix.stream().filter((l) -> (l.get(field.ordinal()).equalsIgnoreCase(word))
                && l.get(field2.ordinal()).equalsIgnoreCase(value)).forEach((l) -> {
            list.add(l);
        });
        //System.out.println(list);
        return list;
    }
    
     private ArrayList<ArrayList<String>> read() {
        try {
            ArrayList<ArrayList<String>> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(getClass().getResourceAsStream(FILE4)));
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                l.add(line(line));
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
    * @param list ArrayList of lines
    * @param args ArrayList of nodes
    * @return arraylist of lines matching the conditions
    */
    public ArrayList<ArrayList<String>> find(ArrayList<ArrayList<String>> list, ArrayList<Node> args) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        int num = args.size();
        int cfr;
        //confronta ogni riga della lista fornita con i ruoli impliciti contenuti nella lista di nodi args
        for (ArrayList<String> l : list) {
            String vnClass = l.get(Glossary.Pb2vnFields.VN_Sense.ordinal());
            String lemma = "";
            // verifica se la subclass è null e la class non lo è
            if (!vnClass.equalsIgnoreCase(Glossary.NULL)) {
                lemma = vnClass;
                //la lista  da confrontare è quella formata dalle righe con la class specificata
                result = find(lemma, Glossary.Pb2vnFields.VN_Sense);
            } 
            cfr = 0;
            for (Node n : args) {
                String r = n.relation.substring(1);
                for (ArrayList<String> l1 : result) {
                    //System.out.println(r + " " + l1.get(Glossary.Pb2vnFields.PB_Role.ordinal()));
                    if (l1.get(Glossary.Pb2vnFields.PB_Role.ordinal()).equalsIgnoreCase(r)) {
                        //se il ruolo del nodo trova corrispondenza la variabile cfr viene incrementata
                        cfr++;
                        break;
                    }
                }
            }
            
            if (cfr >= num) {
                //se tutti i ruoli hanno trovato corrispondenza si restituisce la lista di righe
                //System.out.println(result);
                return result;
            }
        }
        return null;
    }
}
