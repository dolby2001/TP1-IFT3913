
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TLocCalculator {

    public static void main(String[] args) throws IOException {
        
        String filePath = args[0];
        if (!new File(filePath).isAbsolute()) {
            filePath = new File(System.getProperty("user.dir") + "/../../" + filePath).getCanonicalPath();
        }

        int loc = tLocCalculator(filePath);

        System.out.println("TLoc : " + loc);
    }

    public static int tLocCalculator(String filePath) throws FileNotFoundException, IOException{
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