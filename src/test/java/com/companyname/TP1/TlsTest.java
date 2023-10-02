package com.companyname.TP1;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class TlsTest {

    @Test
    public void testTls() throws IOException {
        Tls tls = new Tls();


        String expectedResultApp = "./App.java, com.companyname.TP1, App, 8, 0, 0.00";
        String expectedResultTAssertCalculator = "./TAssertCalculator.java, com.companyname.TP1, TAssertCalculator, 22, 2, 11.00";
        String expectedResultTLocCalculator = "./TLocCalculator.java, com.companyname.TP1, TLocCalculator, 29, 0, 0.00";
        String expectedResultTls = "./Tls.java, com.companyname.TP1, Tls, 40, 0, 0.00";


        assertEquals(expectedResultApp, tls.processJavaFile(new File("src/main/java/com/companyname/TP1/App.java")));
        assertEquals(expectedResultTAssertCalculator, tls.processJavaFile(new File("src/main/java/com/companyname/TP1/TAssertCalculator.java")));
        assertEquals(expectedResultTLocCalculator, tls.processJavaFile(new File("src/main/java/com/companyname/TP1/TLocCalculator.java")));
        assertEquals(expectedResultTls, tls.processJavaFile(new File("src/main/java/com/companyname/TP1/Tls.java")));
    }
}
