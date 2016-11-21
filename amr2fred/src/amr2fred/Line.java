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

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Classe utilizzata nella gestione delle righe della tabella predmatrix
 *
 * @author anto
 */
class Line implements Comparable {

    private static final int INIZIO_CAMPO = 3;
    private static final String NULL_FREQ = "-1";
    private static final int BEFORE = -1;
    private static final int EQUAL = 0;
    private static final int AFTER = 1;
    private static final String SEPARATOR = "\t";
    private final ArrayList<String> line;

    Line(String string) {
        
        this.line = line(string);

    }

    //Trasforma in un ArrayList di stringhe una linea del file predmatrix.txt
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

    ArrayList<String> getLine() {
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
            String thisLine = this.line.get(Glossary.LineFields.WN_SENSEFREC.ordinal()).substring(INIZIO_CAMPO);
            String otherLine = l.getLine().get(Glossary.LineFields.WN_SENSEFREC.ordinal()).substring(INIZIO_CAMPO);
            if (thisLine.equalsIgnoreCase(Glossary.NULL)) {
                thisLine = NULL_FREQ;
            }
            if (otherLine.equalsIgnoreCase(Glossary.NULL)) {
                otherLine = NULL_FREQ;
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

    static Comparator<Line> lineComparator
            = new Comparator<Line>() {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.compareTo(o2);
        }

    };

}
