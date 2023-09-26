package com.companyname.TP1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

    public static int tloc(String filePath) throws IOException {
        int loc = 0;
        boolean inMultiLineComment = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }

                if (line.startsWith("/*")) {
                    inMultiLineComment = true;
                }

                if (!inMultiLineComment && !line.startsWith("*/")) {
                    loc++;
                }

                if (line.endsWith("*/")) {
                    inMultiLineComment = false;
                }
            }
        }

        return loc;
    }

    public static void testTloc(String filePath) {
        // Replace with the actual path to your Java source file

        try {
            int expectedLOC = 8; // Replace with the expected LOC count
            int actualLOC = tloc(filePath);
            assertEquals(expectedLOC, actualLOC);
            System.out.println("TLOC : " + actualLOC);
        } catch (Exception e) {
            fail("An exception occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // Example usage
        String filePath = "src/main/java/com/companyname/TP1/App.java";

        testTloc(filePath);
    }


}
