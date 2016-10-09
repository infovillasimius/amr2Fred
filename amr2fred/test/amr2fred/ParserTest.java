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
public class ParserTest {

    public ParserTest() {
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
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String amr = "(w / want-01 :polarity - :arg0 (b / boy) :arg1 (b2 / believe-01 :arg0 (g / girl) :arg1 b))";
        //String amr = "(f / fight-01 :prep-against (d / dog) :mode imperative)";
        //String amr ="(r / recommend-01 :ARG1 (g / go-02 :ARG0 (b / boy)) :polarity -)";
        //String amr = "(p / possible-01 :ARG1 (r / rain-01))";
        //String amr = "(y / yippee :mode expressive)";
        Parser instance = Parser.getInstance();
        Node expResult = null;
        Node result = instance.parse(amr);
        System.out.println(amr);
        System.out.println(result);
        assertTrue(expResult != result);

    }

}
