
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TAssertCalculator {
    public int testAssert(String filePath) {
        int tAssertCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains a JUnit assertion method
                if ((line.contains("assertEquals") || line.contains("assertFalse") || line.contains("assertThrows") ||
                        line.contains("fail")) && line.contains("(") && line.contains(")")) {
                    tAssertCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            System.exit(2);
        }
        return tAssertCount;
    }
}
