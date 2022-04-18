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
package resultsComparator;

import amr2fred.Amr2fredWeb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import webDemo.FredHandler;
import webDemo.Glossary;

/**
 * Class Amr2File
 * Static class containing algorithms for converting amrs in a file
 * @author anto
 */
public class Amr2File {

    public static ArrayList<String> amr = new ArrayList<>();
    public static ArrayList<String> sentence = new ArrayList<>();
    public static ArrayList<String> rdf1 = new ArrayList<>();
    public static ArrayList<String> rdf2 = new ArrayList<>();
    public static int counter = 0;
    public static Amr2fredWeb amr2fred = new Amr2fredWeb();
    public static File o = null;
    private static File data = null;
    private static int from = 0;
    private static int numberOfSenteces;
    private static int time = 30;

    public static void main(String[] args) {
        File f = null;

        if (args != null) {
            int l = args.length;

            if (l > 4 && args[4].equalsIgnoreCase("-r")) {
                time = 1;
            }

            if (l == 1) {

            } else if (l > 3) {
                f = new File(args[0]);
                o = new File(args[1] + args[2] + ".txt");
                data = new File("data" + args[2] + ".txt");
                from = Integer.parseInt(args[2]);

                numberOfSenteces = Integer.parseInt(args[3]);
                if (f.isFile()) {
                    doConvert(f);

                }
            }

            Timer timer2 = new Timer();
            timer2.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    FredHandler.timeIncreaseQueries();
                }
            }, 1 * 1000, Glossary.FRED_RECHARGE_TIME * 1000);

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    String amrS = "", fred;
                    //Amr2fredWeb amr2fred = new Amr2fredWeb();
                    if (from + counter < amr.size()) {
                        //System.out.println("" + from + " " + counter + " - amr.size " + amr.size());
                        //System.out.println(amr.get(from + counter));
                        try {
                            amrS = amr2fred.go(amr.get(from + counter), 2, 1, true, true, true, false);
                        } catch (Exception e) {
                            amrS = "";
                        }

                        fred = FredHandler.getFredString(sentence.get(from + counter), webDemo.Glossary.FRED_N_TRIPLES);

                        if (!fred.contains("FRED is not Reachable!")) {
                            rdf1.add(amrS);
                            if (fred.contains("Server Error")) {
                                rdf2.add("");
                            } else {
                                rdf2.add(fred);
                            }

                            System.out.println();
                            System.out.println("Sentence n. " + (from + counter + 1));
                            System.out.println(amr.get(from + counter));
                            //System.out.println(rdf1.get(counter));
                            System.out.println(sentence.get(from + counter));
                            //System.out.println(rdf2.get(counter));
                            counter++;
                        }
                    }
                    if (from + counter >= amr.size() || counter >= numberOfSenteces) {
                        this.cancel();
                        if (o != null) {
                            doNewConvert(o, data);
                        }
                        System.exit(0);
                    }
                }
            }, 5 * 1000, time * 1000);

        }
    }

    private static void doConvert(File f) {

        boolean flag = false;
        String line, sentence, amr;

        try {
            ArrayList<String> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {

                if (line.contains("</sntamr>")) {
                    flag = false;

                    sentence = getSentence(l);
                    if (sentence.endsWith(".")) {
                        sentence = sentence.substring(0, sentence.length() - 1);
                    }
                    
                    amr = getAmr(l);
                    while (amr.contains("  ")) {
                        amr = amr.replaceAll("  ", " ");
                    }

                    
                    Amr2File.amr.add(amr);
                    Amr2File.sentence.add(sentence);
                    l = new ArrayList<>();
                }

                if (flag) {
                    l.add(line);
                }

                if (line.contains("<sntamr>")) {
                    flag = true;
                }

                line = reader.readLine();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String getSentence(ArrayList<String> l) {

        String temp = "";
        boolean flag = false;

        for (String s : l) {

            if (s.contains("<sentence")) {
                int i = s.indexOf(">");
                int i2 = s.indexOf("</sentence>");
                temp = s.substring(i + 1, i2);
                
                return temp;
            }

        }
        return null;
    }

    private static String getAmr(ArrayList<String> l) {
        String temp = "";
        boolean flag = false;

        for (String s : l) {

            if (s.contains("</amr>")) {
                return temp;
            }

            if (flag) {
                temp += s;
                
            }

            if (s.contains("<amr")) {
                flag = true;
            }

        }

        return null;
    }

    private static void doNewConvert(File o, File data) {

        Comparator c;

        try {

            BufferedWriter dataWriter = new BufferedWriter(new FileWriter(data.getAbsolutePath()));
            dataWriter.append("Triple_Fred,Triple_amr2fred,Triple_Fred-amr2fred,Triple_amr2fred-Fred,_triple_comuni");
            dataWriter.newLine();

            BufferedWriter writer = new BufferedWriter(new FileWriter(o.getAbsolutePath()));

            for (int x = 0; x < rdf2.size(); x++) {

                c = new Comparator(rdf2.get(x), rdf1.get(x));

                writer.append(sentence.get(from + x));
                writer.newLine();
                writer.newLine();
                writer.append(amr.get(from + x));
                writer.newLine();
                writer.newLine();
                writer.append("Fred");
                writer.newLine();
                writer.append(rdf2.get(x));
                writer.newLine();
                writer.newLine();
                writer.append("amr2fred");
                writer.newLine();
                writer.append(rdf1.get(x));
                writer.newLine();
                writer.newLine();

                writer.append("Fred \\ amr2fred = " + c.getFma() * 100 + "%");

                writer.newLine();

                for (Triple t : c.getfMinusA()) {
                    writer.append(t.toString());
                    //writer.newLine();
                }
                writer.newLine();
                writer.newLine();

                writer.append("amr2fred \\ Fred = " + c.getAmf() * 100 + "%");
                writer.newLine();
                for (Triple t : c.getaMinusF()) {
                    writer.append(t.toString());
                    //writer.newLine();
                }
                writer.newLine();
                writer.newLine();

                dataWriter.append("" + c.getFredResult().size() + "," + c.getA2fResult().size() + "," + c.getfMinusA().size() + "," + c.getaMinusF().size() + "," + c.getCommons().size());
                dataWriter.newLine();
            }

            writer.newLine();

            writer.append(
                    "Total generated triples = " + Triple.gettNum());
            writer.newLine();

            writer.append(
                    "Total correct triples = " + Comparator.getCorrect());
            writer.newLine();

            writer.append(
                    "Average = " + Comparator.getAverage());
            writer.flush();

            dataWriter.flush();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
