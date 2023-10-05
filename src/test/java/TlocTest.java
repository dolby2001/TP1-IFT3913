

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;




public class TlocTest extends TestCase {

    @Test
    public void testTloc() {
        String filePath = "src/main/java/App.java";
        TLocCalculator tloc = new TLocCalculator();

        try {
            int expectedLOC = 7; // Replace with the expected LOC count
            int actualLOC = tloc.tLocCalculator(filePath);
            assertEquals(expectedLOC, actualLOC);
            System.out.println("TLOC : " + actualLOC);
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }


}
