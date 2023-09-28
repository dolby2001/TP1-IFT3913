package com.companyname.TP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;

public class TassertTest extends TestCase {

    @Test
    public void testTassert(){
        TAssertCalculator tassert = new TAssertCalculator();
        String filePath = "src/test/java/com/companyname/TP1/TlocTest.java";
        try {
            int expectedASSERT = 2; // Replace with the expected LOC count
            int actualASSERT = tassert.testAssert(filePath);
            assertEquals(expectedASSERT, actualASSERT);
            System.out.println("TASSERT : " + actualASSERT);
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }
}
