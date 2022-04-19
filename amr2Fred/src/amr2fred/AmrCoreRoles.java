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
 * @author infov
 */
public class AmrCoreRoles {

    private static final String SEPARATOR = "  ";
    private static final String FILE4 = "propbank-amr-frames-arg-descr.txt";
    private static AmrCoreRoles a;
    //private final ArrayList<ArrayList<String>> matrix;
    private final ArrayList<Lemma> matrix;

    private AmrCoreRoles() {
        this.matrix = read();
    }

    /**
     * Get AmrCoreRoles instance
     *
     * @return singleton instance of Pb2vn
     */
    public static AmrCoreRoles getAmrCoreRoles() {
        if (a == null) {
            a = new AmrCoreRoles();
        }
        return a;
    }

    private ArrayList<Lemma> read() {
        ArrayList<Lemma> l = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(getClass().getResourceAsStream(FILE4)));
            String line = reader.readLine();

            while (line != null) {
                Lemma lem = lineAnalysis(line);
                l.add(lem);
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

    private Lemma lineAnalysis(String string) {

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
                list.add(word.trim());
                inizio = string.indexOf(SEPARATOR) + 1;
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {

            return null;
        }

        Lemma lem = new Lemma(list.get(0));
        list.remove(0);
        for (String arg : list) {
            if (arg.startsWith("ARG")) {
                int pos = arg.indexOf(":");
                if (pos != -1) {
                    String r = arg.substring(0, pos).trim();
                    String d = arg.substring(pos + 1).trim().replaceAll(" ", "_");
                    CoreRole ro = new CoreRole(r, d);
                    lem.roles.add(ro);
                }
            }
        }
        return lem;
    }

    class Lemma {

        String lemma;
        ArrayList<CoreRole> roles = new ArrayList<>();

        Lemma(String lemma) {
            this.lemma = lemma;
        }

        void addRole(CoreRole role) {
            this.roles.add(role);
        }

        CoreRole getArg(String arg) {
            for (CoreRole role : this.roles) {
                if (arg.equalsIgnoreCase(role.role)) {
                    return role;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "Lemma{" + "lemma=" + lemma + ", roles=" + roles + '}';
        }

    }

    class CoreRole {

        String role;
        String description;

        CoreRole(String role, String description) {
            this.role = role;
            this.description = description;
        }

        @Override
        public String toString() {
            return "CoreRole{" + "role=" + role + ", description=" + description + '}';
        }

    }

    public String find(String lemma, String role) {
        Lemma l;
        for (Lemma lem : matrix) {
            if (lemma.equalsIgnoreCase(lem.lemma)) {
                l = lem;
                for (CoreRole ro : l.roles) {
                    
                    if (role.equalsIgnoreCase(ro.role)) {
                        return ro.description;
                    }
                }
            }
        }
        return null;
    }

    public boolean find(String lemma) {
        for (Lemma lem : matrix) {
            if (lemma.equalsIgnoreCase(lem.lemma)) {
                return true;
            }
        }
        return false;
    }

}
