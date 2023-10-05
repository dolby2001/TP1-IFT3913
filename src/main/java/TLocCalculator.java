
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TLocCalculator {

    public int tLocCalculator(String filePath) throws IOException {
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
}