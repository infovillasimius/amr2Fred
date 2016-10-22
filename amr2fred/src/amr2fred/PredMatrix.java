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
 *
 * @author anto
 */
public class PredMatrix {

   
    private static String FILE =getPath()+"resource/predmatrix.txt";
    private static final String FILE2 = "resource/predmatrix.txt";
    private static PredMatrix p;
    private final ArrayList<Line> matrix;

    private PredMatrix() {
        this.matrix = read();
    }

    public static PredMatrix getPredMatrix() {
        if (p == null) {
            
            //System.out.println(FILE);
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
            FILE=FILE2;
            return read();
        } catch (IOException ex) {
            Logger.getLogger(PredMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    public ArrayList<Line> getMatrix() {
        return matrix;
    }

    public ArrayList<Line> find(String word, Glossary.lineFields field) {
        ArrayList<Line> list=new ArrayList<>();
        matrix.stream().filter((l) -> (l.getLine().get(field.ordinal()).equalsIgnoreCase(word))).forEach((l) -> {
            list.add(l);
        });
        list.sort(lineComparator);
        return list;
    }
    
        public ArrayList<Line> find(String word, Glossary.lineFields field,Glossary.lineFields field2, String value) {
        ArrayList<Line> list=new ArrayList<>();
        matrix.stream().filter((l) -> (l.getLine().get(field.ordinal()).equalsIgnoreCase(word)) 
                && l.getLine().get(field2.ordinal()).equalsIgnoreCase(value)).forEach((l) -> {
            list.add(l);
        });
        list.sort(lineComparator);
        return list;
    }
        
        private static String getPath(){
        try {
            String path = PredMatrix.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            return decodedPath.substring(0, decodedPath.length()-12);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Amr2Fred.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
