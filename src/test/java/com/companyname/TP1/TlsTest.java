package com.companyname.TP1;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TlsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testProcessJavaFilesDirectory() throws Exception {
        Tls tls = new Tls();
        tls.processJavaFilesDirectory("src/main/java/com/companyname/TP1"); 
        String expectedOutput = 
            "./App.java, com.companyname.TP1, App, 8, 0, 0.00 \n" + 
            "./TAssertCalculator.java, com.companyname.TP1, TAssertCalculator, 22, 2, 11.00 \n" + 
            "./TLocCalculator.java, com.companyname.TP1, TLocCalculator, 29, 0, 0.00 \n" + 
            "./Tls.java, com.companyname.TP1, Tls, 53, 0, 0.00 \n" +
            "./Tropcomp.java, com.companyname.TP1, Tropcomp, 1, 0, 0.00 ";


            System.setOut(originalOut);  
            System.out.println("Expected Output:");
            System.out.println(expectedOutput);
            System.out.println("\nActual Output:");
            System.out.println("Expected length: " + expectedOutput.length());
            System.out.println("Actual length: " + outContent.toString().length());
            System.out.println(outContent.toString());


        
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}
