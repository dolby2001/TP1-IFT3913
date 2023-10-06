import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class TAssertCalculator {
    public static void main(String[] args) {
        int tAssertCount = 0;
        String filePath = args[0];
        File file = new File(filePath);
        File inputDirectory =  new File( System.getProperty("user.dir") + "/../../" + filePath);
        if(inputDirectory.toPath() != file.toPath()){
            filePath = System.getProperty("user.dir") + "/../../" + filePath;
        }
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
        System.out.println("TAssert : " + tAssertCount);
    }
}
