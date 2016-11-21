/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

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
public class PredMatrixTest {
    
    public PredMatrixTest() {
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
     * Test of getPredMatrix method, of class PredMatrix.
     */
    @Test
    public void testGetPredMatrix() {
        System.out.println("getPredMatrix");
        PredMatrix expResult = null;
        PredMatrix result = PredMatrix.getPredMatrix();
        
        
        System.out.println(result.find("id:want.01", Glossary.LineFields.ID_PRED).get(0).getLine());
    }
    
}
