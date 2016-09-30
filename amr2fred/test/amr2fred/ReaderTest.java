/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anto
 */
public class ReaderTest {
    
    public ReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of jreader method, of class Reader.
     */
    @Test
    public void testJreader() {
        System.out.println("jreader");
        Element predicate=Reader.jreader("fight");
        List argmaps = predicate.getChildren();
        Iterator argmapIterator= argmaps.iterator();
        while (argmapIterator.hasNext()) {
            Element argmap = (Element) argmapIterator.next();
            Attribute pbRoleset = argmap.getAttribute("pb-roleset");
            Attribute vnClass = argmap.getAttribute("vn-class");
            System.out.println(pbRoleset.getValue()+" "+vnClass.getValue());
            List roles = argmap.getChildren();
            Iterator rolesIterator=roles.iterator();
            while (rolesIterator.hasNext()){
                Element role=(Element) rolesIterator.next();
                Attribute pbArg = role.getAttribute("pb-arg");
                Attribute vnTheta = role.getAttribute("vn-theta");
                System.out.println(pbArg.getValue()+" "+vnTheta.getValue());
            }
        }
    }
    
}
