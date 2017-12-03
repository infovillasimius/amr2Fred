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
package amr2fred;

/**
 *
 * @author anto
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(serializeClass("vn.data:Desire_32"));
    }

    private static String serializeClass(String vnClass) {
        int i = vnClass.indexOf("_");
        String classe = "", sottoclasse = "";
        String result = vnClass.substring(0, i + 1);
        vnClass = vnClass.substring(i + 1);
        System.out.println(vnClass);

        i = vnClass.indexOf(".");
        int j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + vnClass + "00000000".substring(vnClass.length());
        }
        if (i != -1) {
            classe += vnClass.substring(0, i);
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            classe += vnClass.substring(0, j);
            vnClass = vnClass.substring(j + 1);
        }

        i = vnClass.indexOf(".");
        j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + classe + vnClass + "00000000".substring((classe + vnClass).length());
        }
        if (i != -1) {
            sottoclasse = vnClass.substring(0, i);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            sottoclasse = vnClass.substring(0, j);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(j + 1);
        }

        i = vnClass.indexOf(".");
        j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + classe + vnClass + "00000000".substring((classe + vnClass).length());
        }
        if (i != -1) {
            sottoclasse = vnClass.substring(0, i);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            sottoclasse = vnClass.substring(0, j);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(j + 1);
        }
        vnClass = vnClass.replace(".", "");
        classe += vnClass.replace("-", "");

        return result + classe + "00000000".substring(((classe).length()) % 9);
    }

}
