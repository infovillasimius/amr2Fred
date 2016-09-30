/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author anto
 */
public class Reader {

    public static String file = "resource/vnpbMappings";
    public static String word = "lemma";

    public static Element jreader(String lemma) {

        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(new File(file));
            Element root = document.getRootElement();
            List children = root.getChildren();
            Iterator iterator = children.iterator();
            while (iterator.hasNext()) {
                Element predicate = (Element) iterator.next();
                if (predicate.getAttributeValue(word).equalsIgnoreCase(lemma)) {
                    return predicate;
                }
            }

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    public static String getFile() {
        return file;
    }

    public static void setFile(String file) {
        Reader.file = file;
    }

    public static String getWord() {
        return word;
    }

    public static void setWord(String word) {
        Reader.word = word;
    }

    public static String getRole(String bpArg, Element predicate) {
        List argmaps = predicate.getChildren();
        if (argmaps.size() == 1) {

        }
        Iterator argmapIterator = argmaps.iterator();
        while (argmapIterator.hasNext()) {
            Element argmap = (Element) argmapIterator.next();
            Attribute pbRoleset = argmap.getAttribute("pb-roleset");
            Attribute vnClass = argmap.getAttribute("vn-class");
            if (vnClass != null && pbRoleset != null) {
                System.out.println(pbRoleset.getValue() + " " + vnClass.getValue());
            }
            List roles = argmap.getChildren();
            Iterator rolesIterator = roles.iterator();
            while (rolesIterator.hasNext()) {
                Element role = (Element) rolesIterator.next();
                Attribute pbArg = role.getAttribute("pb-arg");
                Attribute vnTheta = role.getAttribute("vn-theta");
                System.out.println(pbArg.getValue() + " " + vnTheta.getValue());
            }
        }

        return null;
    }

}
