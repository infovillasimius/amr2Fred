/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import org.apache.jena.rdf.model.Resource;
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
public class RdfTest {
    
    public RdfTest() {
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
     * Test of getResource method, of class Rdf.
     */
    @Test
    public void testGetResource() {
        System.out.println("getResource");
        Rdf instance = new Rdf();
        Resource expResult = null;
        Resource result = instance.getResource();
        //assertEquals(expResult, result);
        System.out.println(result);
    }
    
}
