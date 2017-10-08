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
package fileConvert;

import java.util.ArrayList;

/**
 *
 * @author anto
 */
public class Comparator {

    private ArrayList<Triple> fredResult;
    private ArrayList<Triple> a2fResult;
    private ArrayList<Triple> fMinusA;
    private ArrayList<Triple> aMinusF;
    private float fma;
    private float amf;
    private static int correct = 0;

    public Comparator(String fred, String a2f) {
        //System.out.println(fred+"\n"+a2f);
        fredResult = getList(fred);
        a2fResult = getList(a2f);
        fMinusA = new ArrayList<>();
        aMinusF = new ArrayList<>();
        subtract();
        calculate();
    }

    private ArrayList<Triple> getList(String result) {
        ArrayList<Triple> list = new ArrayList<>();
        String temp = "", temp1 = "", temp2 = "";
        int start, end;
        Triple triple;

        while (result.contains(">")) {

            start = result.indexOf("<");
            end = result.indexOf(">");

            if (start > -1 && end > -1 && end > start && end <= result.length()) {
                temp = result.substring(start + 1, end);
                result = result.substring(end + 2);
            }

            start = result.indexOf("<");
            end = result.indexOf(">");

            if (start > -1 && end > -1 && end > start && end <= result.length()) {
                temp1 = result.substring(start + 1, end);
                result = result.substring(end + 2);
            }

            if(result.startsWith(" \"") || result.startsWith("\"")){
             start=result.indexOf(" ");
            } else {
                start = result.indexOf("<");
            }
            end = result.indexOf(">");

            if (start > -1 && end > -1 && end > start && end <= result.length()) {
                temp2 = result.substring(start + 1, end);
                result = result.substring(end + 2);
            }

            triple = new Triple(temp, temp1, temp2);

            if (!list.contains(triple)) {
                list.add(triple);
            }
        }
        return list;
    }

    private void subtract() {
        for (Triple t : fredResult) {
            if (!a2fResult.contains(t)) {
                fMinusA.add(t);
            } else {
                correct += 1;
            }
        }

        for (Triple t : a2fResult) {
            if (!fredResult.contains(t)) {
                aMinusF.add(t);
            } else {
                correct += 1;
            }
        }

    }

    private void calculate() {
        this.amf = 1 - ((float) this.aMinusF.size() / this.a2fResult.size());
        System.out.println(this.aMinusF.size() + " " + this.a2fResult.size() + " " + this.amf);
        this.fma = 1 - ((float) this.fMinusA.size() / this.fredResult.size());
    }

    public ArrayList<Triple> getfMinusA() {
        return fMinusA;
    }

    public ArrayList<Triple> getaMinusF() {
        return aMinusF;
    }

    public float getFma() {
        return fma;
    }

    public float getAmf() {
        return amf;
    }

    public static int getCorrect() {
        return correct;
    }

    public static double getAverage() {
        return (Math.ceil((double) correct / Triple.gettNum() * 10000)) / 100;
    }

}
