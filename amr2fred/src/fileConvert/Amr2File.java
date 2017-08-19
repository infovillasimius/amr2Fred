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

import amr2fred.Amr2fredWeb;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import webDemo.FredHandler;

/**
 *
 * @author anto
 */
public class Amr2File {

    public static void main(String[] args) {

        if (args != null) {
            int l = args.length;

            if (l == 1) {

            } else if (l > 1) {
                File f = new File(args[0]);
                File o = new File(args[1]);
                if (f.isFile() && !o.isFile()) {
                    doConvert(f, o);
                }
            }

        }
    }

    private static void doConvert(File f, File o) {
        Amr2fredWeb amr2fred = new Amr2fredWeb();
        boolean flag = false;
        String line;
        String amr,sentence,rdf1,rdf2;

        try {
            ArrayList<String> l = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(o.getAbsolutePath()));
            line = reader.readLine();

            while (line != null) {
                
                if (line.contains("</sntamr>")) {
                    flag = false;
                    sentence=getSentence(l);
                    amr=getAmr(l);
                    rdf1=amr2fred.go(amr, 1, 0, true, true, true);
                    rdf2=FredHandler.getFredString(sentence, webDemo.Glossary.FRED_RDF);
                    
                    writer.append(sentence);
                    writer.newLine();
                    writer.newLine();
                    writer.append(amr);
                    writer.newLine();
                    writer.newLine();
                    writer.append(rdf2);
                    writer.newLine();
                    writer.newLine();
                    writer.append(rdf1);
                    writer.newLine();
                    writer.newLine();
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
            
            writer.flush();

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

            if(s.contains("<sentence")){
                int i=s.indexOf(">");
                int i2=s.indexOf("</sentence>");
                temp=s.substring(i+1, i2);
                return temp;
            }

        }
        return null;
    }
    
    private static String getAmr(ArrayList<String> l){
        String temp = "";
        boolean flag = false;
        
        for (String s : l) {
            
            if(s.contains("</amr>")){
                return temp;
            }
                       
            if(flag){
                temp+=s;
                //System.out.println(temp);
            }
            
            
            if(s.contains("<amr")){
                flag=true;
            }

        }
        
        return null;
    }

}
