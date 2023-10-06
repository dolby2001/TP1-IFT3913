
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TLocCalculator {

    public static void main(String[] args) throws IOException {
        int loc = 0;
        boolean inMultiLineComment = false;
        String filePath = args[0];
        File file = new File(filePath);
        File inputDirectory =  new File( System.getProperty("user.dir") + "/../../" + filePath);
        if(inputDirectory.toPath() != file.toPath()){
            filePath = System.getProperty("user.dir") + "/../../" + filePath;
        }
        System.out.println(filePath);
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

        System.out.println("TLoc : " + loc);
    }
}