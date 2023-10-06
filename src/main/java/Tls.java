

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Tls {
    private TLocCalculator tLocCalculator = new TLocCalculator();
    private TAssertCalculator tAssertCalculator = new TAssertCalculator();

    //traite tout fichier dans un directory
    public void processJavaFilesDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processJavaFilesDirectory(file.getPath());
                } else if (file.getName().endsWith(".java")) {
                    String result = processJavaFile(file, directoryPath);
                    System.out.println(result);
                }
            }
        }
    }

    public String processJavaFile(File file, String baseDirectory) throws IOException {
        long tloc = tLocCalculator.tLocCalculator(file.getPath());
        long tassert = tAssertCalculator.tAssertCalculator(file.getPath());
        double tcmp = tCmpCalculator(tloc, tassert);

        String packageName = getPackageName(file.getPath());
        String className = getClassName(file.getName());
        String simplePath = getsimplePath(file.getPath(), baseDirectory);

        return String.format("%s, %s, %s, %d, %d, %.2f",
                simplePath, packageName, className, tloc, tassert, tcmp);
    }

    public String getsimplePath(String fullPath, String baseDirectory) {
        return "./" + new File(baseDirectory).toURI().relativize(new File(fullPath).toURI()).getPath();
    }


    public double tCmpCalculator(long tloc, long tassert) {
        if (tassert == 0) return 0;
        return (double) tloc / tassert;
    }

    //aide avec chatgpt
    public String getPackageName(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            if (line.startsWith("package ")) {
                return line.split(" ")[1].replace(";", "");
            }
        }
        return "";
    }
    public String getClassName(String fileName) {
        return fileName.replace(".java", "");
    }
}
