/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author anto
 */
public class Line implements Comparable {

    public static final ArrayList<String> FIRSTLINE = new ArrayList<>();

    /**
     * The value is used in the compareTo() to determine what field has to be
     * used in ordering method
     */
    public static Glossary.lineFields field = Glossary.lineFields.WN_SENSEFREC;
    public static int inizioCampo = 3;
    private final ArrayList<String> line;
    private final int BEFORE = -1;
    private final int EQUAL = 0;
    private final int AFTER = 1;

    public Line(String string) {
        if (FIRSTLINE.isEmpty()) {

            FIRSTLINE.add("2_ID_POS");
            FIRSTLINE.add("3_ID_PRED");
            FIRSTLINE.add("4_ID_ROLE");
            FIRSTLINE.add("5_VN_CLASS");
            FIRSTLINE.add("6_VN_CLASS_NUMBER");
            FIRSTLINE.add("7_VN_SUBCLASS");
            FIRSTLINE.add("8_VN_SUBCLASS_NUMBER");
            FIRSTLINE.add("9_VN_LEMA");
            FIRSTLINE.add("10_VN_ROLE");
            FIRSTLINE.add("11_WN_SENSE");
            FIRSTLINE.add("12_MCR_iliOffset");
            FIRSTLINE.add("13_FN_FRAME");
            FIRSTLINE.add("14_FN_LE");
            FIRSTLINE.add("15_FN_FRAME_ELEMENT");
            FIRSTLINE.add("16_PB_ROLESET");
            FIRSTLINE.add("17_PB_ARG");
            FIRSTLINE.add("18_MCR_BC");
            FIRSTLINE.add("19_MCR_DOMAIN");
            FIRSTLINE.add("20_MCR_SUMO");
            FIRSTLINE.add("21_MCR_TO");
            FIRSTLINE.add("22_MCR_LEXNAME");
            FIRSTLINE.add("23_MCR_BLC");
            FIRSTLINE.add("24_WN_SENSEFREC");
            FIRSTLINE.add("25_WN_SYNSET_REL_NUM");
            FIRSTLINE.add("26_ESO_CLASS");
            FIRSTLINE.add("27_ESO_ROLE");
        }

        this.line = line(string);

    }

    private ArrayList<String> line(String string) {

        int inizio, fine;
        String word;
        ArrayList<String> list = new ArrayList<>();

        try {
            inizio = 0;
            while (string.length() > 1) {

                fine = string.indexOf("\t", inizio);
                if (fine == -1) {
                    fine = string.length();
                }

                word = string.substring(inizio, fine);

                string = string.substring(fine);
                list.add(word);
                inizio = string.indexOf("\t") + 1;
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {

            return null;
        }

        return list;
    }

    public ArrayList<String> getLine() {
        return line;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return EQUAL;
        }
        Line l;
        if (o instanceof Line) {
            l = (Line) o;
            String thisLine = this.line.get(field.ordinal()).substring(inizioCampo);
            String otherLine = l.getLine().get(field.ordinal()).substring(inizioCampo);
            if (thisLine.equalsIgnoreCase("NULL")) {
                thisLine = "-1";
            }
            if (otherLine.equalsIgnoreCase("NULL")) {
                otherLine = "-1";
            }
            
            if (Integer.parseInt(thisLine) > Integer.parseInt(otherLine)) {
                return BEFORE;
            } else if (Integer.parseInt(thisLine) < Integer.parseInt(otherLine)) {
                return AFTER;
            } else {
                return EQUAL;
            }
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Comparator<Line> lineComparator
            = new Comparator<Line>() {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.compareTo(o2);
        }

    };

}
