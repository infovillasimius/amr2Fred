/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import static amr2fred.Line.lineComparator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anto
 */
public class PredMatrix {

    private static String file = "resource/predmatrix.txt";
    //public static String file="resource/PredicateMatrix.v1.3.txt";
    private static String file2 = "resource/prova.txt";
    private static PredMatrix p;
    private ArrayList<Line> matrix;

    private PredMatrix() {
        this.matrix = read();
    }

    public static PredMatrix getPredMatrix() {
        if (p == null) {
            p = new PredMatrix();
        }
        return p;
    }

    private ArrayList<Line> read() {
        try {
            ArrayList<Line> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                l.add(new Line(line));
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

    private void scriviFile() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            String line = reader.readLine();
            FileWriter f = new FileWriter(this.file2);
            BufferedWriter bw = new BufferedWriter(f);
            bw.write(line + "\n");

            while (line != null) {

                line = reader.readLine();
                if (line != null && line.startsWith("id:eng")) {
                    bw.write(line + "\n");

                }
            }

            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
