import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Tls {
    private TLocCalculator tLocCalculator = new TLocCalculator();
    private TAssertCalculator tAssertCalculator = new TAssertCalculator();

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Veuillez fournir un chemin de répertoire.");
            System.exit(1);
        }

        Tls tls = new Tls();
        if (args.length >= 3 && args[0].equals("-o")) {
            // Écrire la sortie dans un fichier
            String outputPath = args[1];
            String directoryPath = args[2];
            System.out.println("Fichier de sortie: " + outputPath);
            System.out.println("Répertoire d'entrée: " + directoryPath);

            StringBuilder output = new StringBuilder();
            tls.processJavaFilesDirectory(directoryPath, output);

            Files.write(Paths.get(outputPath), output.toString().getBytes());
        } else {
            tls.processJavaFilesDirectory(args[0]);
        }
    }

    public void processJavaFilesDirectory(String directoryPath, StringBuilder output) throws IOException {
        System.out.println("Original directoryPath: " + directoryPath);
    
        
        if (!new File(directoryPath).isAbsolute()) {
            directoryPath = new File(System.getProperty("user.dir") + "/../../" + directoryPath).getCanonicalPath();
        }
        
        File directory = new File(directoryPath);
        
        if (!directory.exists()) {
            System.out.println("directory existe pas");
        }
        if (!directory.isDirectory()) {
            System.out.println("Pas une directory");
        }
        
        if(!directory.exists() || !directory.isDirectory()) {
            System.out.println("Le répertoire d'entrée n'existe pas");
            return;
        }
    
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processJavaFilesDirectory(file.getPath(), output);
                } else if (file.getName().endsWith(".java")) {
                    String result = processJavaFile(file, directoryPath);
                    output.append(result).append("\n");
                }
            }
        }
    }
    
    public void processJavaFilesDirectory(String directoryPath) throws IOException {
        processJavaFilesDirectory(directoryPath, new StringBuilder());
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
