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
    private ArrayList<Triple> commons;
    private double fma;
    private double amf;
    private static int correct = 0;

    public Comparator(String fred, String a2f) {
        //System.out.println(fred+"\n"+a2f);
        fredResult = Converter.getList(fred);
        a2fResult = Converter.getList(a2f);
        fMinusA = new ArrayList<>();
        aMinusF = new ArrayList<>();
        commons = new ArrayList<>();
        subtract();
        calculate();
    }

    private void subtract() {

        for (Triple t : a2fResult) {
            if (!fredResult.contains(t)) {
                aMinusF.add(t);
            } else {
                correct += 1;
                commons.add(t);
            }
        }

        for (Triple t : fredResult) {
            if (!a2fResult.contains(t)) {
                fMinusA.add(t);
            } else {
                correct += 1;
                if (!commons.contains(t)) {
                    commons.add(t);
                }
            }
        }

    }

    private void calculate() {
        this.amf = 1.0 - ((float) this.aMinusF.size() / this.a2fResult.size());
        this.fma = 1.0 - ((float) this.fMinusA.size() / this.fredResult.size());
    }

    public ArrayList<Triple> getfMinusA() {
        return fMinusA;
    }

    public ArrayList<Triple> getaMinusF() {
        return aMinusF;
    }

    public double getFma() {
        return fma;
    }

    public double getAmf() {
        return amf;
    }

    public static int getCorrect() {
        return correct;
    }

    public static double getAverage() {
        return (Math.ceil((double) correct / Triple.gettNum() * 10000)) / 100;
    }

    public ArrayList<Triple> getCommons() {
        return commons;
    }

}
