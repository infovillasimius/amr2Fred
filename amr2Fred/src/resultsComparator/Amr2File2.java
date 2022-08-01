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
import static java.lang.Math.min;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import webDemo.Glossary;

/**
 * Class Amr2File Static class containing algorithms for converting amrs in a
 * file
 *
 * @author anto
 */
public class Amr2File2 {

    public static ArrayList<String> amr = new ArrayList<>();
    public static ArrayList<String> sentence = new ArrayList<>();
    public static ArrayList<String> rdf1 = new ArrayList<>();
    public static ArrayList<String> rdf2 = new ArrayList<>();
    public static int counter = 0;
    public static Amr2fredWeb amr2fred = new Amr2fredWeb();
    public static File o = null;
    public static File result = null;
    private static File data = null;
    private static File datafile = null;
    private static Path folder;
    private static boolean imgs = false;
    private static int mode = 0;

    public static void main(String[] args) {
        mode = 0;
        File f;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        o = new File(date.format(formatter) + "-amr2fred_test.html");
        result = new File(date.format(formatter) + "-amr2fred_test_results.txt");
        data = new File(date.format(formatter) + "-data.txt");
        folder = Paths.get(o.getAbsolutePath()).getParent();

        if (args != null && args.length > 0) {
            int l = args.length;

            f = new File(args[0]);
            if (f.isFile()) {
                doConvert(f);
            } else {
                System.exit(1);
            }
            for (int i = 1; i < l; i++) {
                if (args[i].equalsIgnoreCase("-imgs")) {
                    imgs = true;
                }
                if (args[i].equalsIgnoreCase("-datafile")) {
                    mode = 1;
                    datafile = new File(args[++i]);
                    Amr2File2.get_datafile(datafile);
                }
            }

            for (String _amr : Amr2File2.amr) {
                String amrS;
                try {
                    amrS = amr2fred.go(_amr, 2, 1, false, true);
                    if (imgs) {
                        File img = amr2fred.goPng(_amr);
                        File imgdir = new File(folder + "/amr2fred_test");
                        if (!imgdir.isFile()) {
                            imgdir.mkdir();
                        }
                        Files.copy(img.toPath(), new File(folder + "/amr2fred_test" + "/img" + counter + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    //System.out.println(amrS);
                } catch (Exception e) {
                    System.out.println("Error in Sentence " + counter + " out of " + amr.size());
                    System.out.println(_amr);
                    amrS = "";
                }

                rdf1.add(amrS);
                if (counter % 50 == 0) {
                    System.out.println("Sentence " + (counter + 1) + " out of " + amr.size());
                }
                counter++;
            }
            doNewConvert(o, data);
        }
        if (o.isFile()) {
            try {
                Files.copy(o.toPath(), new File(folder + "/amr2fred_test/amr2fred_test.html").toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Error copying file");
            }
        }
    }

    /**
     * get data from the file
     *
     * @param f
     */
    private static void doConvert(File f) {

        boolean flag = false;
        String line, sentenceS, amrS;

        try {
            ArrayList<String> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {

                if (line.contains("</sntamr>")) {
                    flag = false;

                    sentenceS = getSentence(l);
                    if (sentenceS.endsWith(".")) {
                        sentenceS = sentenceS.substring(0, sentenceS.length() - 1);
                    }

                    amrS = getAmr(l);
                    while (amrS.contains("  ")) {
                        amrS = amrS.replaceAll("  ", " ");
                    }

                    Amr2File2.amr.add(amrS);
                    Amr2File2.sentence.add(sentenceS);
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
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static String getSentence(ArrayList<String> l) {

        String temp;
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

        try {
            if (mode == 0) {
                BufferedWriter dataWriter = new BufferedWriter(new FileWriter(data.getAbsolutePath()));
                for (int x = 0; x < rdf1.size(); x++) {
                    dataWriter.append("<s>");
                    dataWriter.append(rdf1.get(x));   //.replaceAll("\r\n|\r|\n", " ")
                    dataWriter.append("</s>");
                    dataWriter.newLine();
                }
                dataWriter.flush();
            }
            if (mode == 1) {
                Comparator c;
                BufferedWriter writer = new BufferedWriter(new FileWriter(result.getAbsolutePath()));
                int dim = min(rdf1.size(), rdf2.size());
                boolean flag = false;
                for (int x = 0; x < dim; x++) {
                    c = new Comparator(rdf2.get(x), rdf1.get(x), false);
                    if (c.getFma() < 1 || c.getAmf() < 1) {
                        flag = true;
                        writer.append("Sentence " + (x + 1));
                        writer.newLine();
                        writer.newLine();
                        writer.append("previous \\ current = " + c.getFma() * 100 + "%");
                        writer.newLine();

                        for (Triple t : c.getfMinusA()) {
                            writer.append(t.toString());
                            //writer.newLine();
                        }
                        writer.newLine();
                        writer.newLine();

                        writer.append("current \\ previous = " + c.getAmf() * 100 + "%");
                        writer.newLine();

                        for (Triple t : c.getaMinusF()) {
                            writer.append(t.toString());
                            //writer.newLine();
                        }
                        writer.newLine();
                        writer.newLine();
                    }
                }
                if (!flag) {
                    writer.append("No difference on " + dim + " sentences");
                    writer.newLine();
                }
                writer.flush();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(o.getAbsolutePath()));

            for (int x = 0; x < rdf1.size(); x++) {
                writer.append("<p><b>SENTENCE ");
                writer.append(x + 1 + "</b><br/><br/>");
                writer.append(sentence.get(x));
                writer.append("</p>");
                writer.newLine();
                writer.newLine();
                writer.append("<p><br/><b>AMR</b><br/>");
                writer.append(amr.get(x));
                writer.append("</p>");
                writer.newLine();
                writer.newLine();
                writer.append("<p>");
                writer.append("<img src=\"" + "img" + x + ".png\"/>");
                writer.append("</p>");
                writer.newLine();
                writer.newLine();
                writer.append("<p>");
                writer.append("<b>amr2fred</b>");
                writer.append("</p>");
                writer.newLine();
                writer.append("<p>");
                writer.append(rdf1.get(x).replaceAll("<", "&lt").replaceAll(">", "&gt").replaceAll(" [.]", " .<br/>"));
                writer.append("<br/><br/></p>");
                writer.newLine();
                writer.newLine();
            }
            writer.flush();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * get data from the file
     *
     * @param f
     */
    private static void get_datafile(File f) {
        String line, rdf = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {
                if (!line.contains("</s>")) {
                    rdf += line.replace("<s>", "");
                } else {
                    rdf += line.replace("</s>", "");
                    rdf2.add(rdf);

                    rdf = "";
                }
                line = reader.readLine();
            }

            System.out.println(rdf2.size());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Amr2File2.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
