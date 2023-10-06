
import java.util.Collections;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tropcomp {

    public static void main(String[] args) throws IOException {

        // Check if the correct number of arguments are provided
        if (args.length != 4 || !args[0].equals("-o")) {
            System.out.println("Usage: tropcomp -o <output.csv> <input> <seuil>");
            return;
        }
        // Parse the command-line arguments
        String outputPath = args[2];
        double seuil = Double.parseDouble(args[3]);

        // Create File
        String filePath = args[1];
        if (!new File(filePath).isAbsolute()) {
            filePath = new File(System.getProperty("user.dir") + "/../../" + filePath).getCanonicalPath();
            System.out.println("file : " + filePath);
        }
        File inputDirectory = new File(filePath);


        // List of tloc and tcmp values
        ArrayList<Integer> ltloc = new ArrayList<Integer>();
        ArrayList<Integer> ltcmp = new ArrayList<Integer>();

        try {
            // Method to traverse files in the input directory recursively
            processFilesAndDirs(inputDirectory, ltloc, ltcmp);
            System.out.println("Traverse files completed successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        // Calute the seuil
        int lenght = ltloc.size();
        System.out.println("num of files : " + lenght);
        ArrayList<Integer> sortedLtloc = new ArrayList<>(ltloc);;
        ArrayList<Integer> sortedLtcmp = new ArrayList<>(ltcmp);;
        Collections.sort(sortedLtloc);
        Collections.sort(sortedLtcmp);

        int numFiles = (int) (lenght * seuil);
        System.out.println("files that are under the seuil : " + numFiles);

        int tlocVal = sortedLtloc.get(numFiles);
        System.out.println("seuil value for tloc : " + tlocVal);

        int tcmpcVal = sortedLtcmp.get(numFiles);
        System.out.println("seuil value for tcmp : " + tcmpcVal);

        // Create a CSV file and write to it
        String csvFileName = outputPath;
        // Create a FileWriter and PrintWriter to write to the CSV file
        try{
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);    
            
            // Method to traverse files in the input directory recursively and find questionable files
            findFiles(inputDirectory, ltloc, ltcmp, tlocVal, tcmpcVal, printWriter);

            printWriter.close();
            fileWriter.close();

        } catch(Exception e){
            System.err.println("An error occurred: " + e.getMessage());
        }    




    }

    public static void processFilesAndDirs(File directory, ArrayList<Integer> ltloc, ArrayList<Integer> ltcmp) {

        File[] filesAndDirs = directory.listFiles();

    
        if (filesAndDirs != null) {
            for (File fileOrDir : filesAndDirs) {
                if (fileOrDir.isFile()) {
                    // Process files here
                    try{
                        int tlocVal = TLocCalculator.tLocCalculator(fileOrDir.getAbsolutePath());
                        int tassertVal = TAssertCalculator.tAssertCalculator(fileOrDir.getAbsolutePath());
                        ltloc.add(tlocVal);
                        if(tassertVal!=0){
                            ltcmp.add(tlocVal / tassertVal);
                        }else{
                            ltcmp.add(tlocVal); //check if ok
                        }
                        
                    } catch(Exception e){
                        System.err.println("An error occurred: " + e.getMessage());
                    }
                } else if (fileOrDir.isDirectory()) {
                    // Recursively traverse subdirectories
                    processFilesAndDirs(fileOrDir,ltloc,ltcmp);
                }
            }
        }
    }


    private static void findFiles(File directory, ArrayList<Integer> ltloc, ArrayList<Integer> ltcmp, int tlocVal,
            int tcmpcVal, PrintWriter printWriter) {

        File[] filesAndDirs = directory.listFiles();
        Tls tls = new Tls();
        

        if (filesAndDirs != null) {
            for (File fileOrDir : filesAndDirs) {
                if (fileOrDir.isFile()) {
                    // Process files here

                    if (ltloc.get(0) >= tlocVal && ltcmp.get(0) >= tcmpcVal) {
                        try{
                            printWriter.println(tls.processJavaFile(fileOrDir,  ""));
                        } catch (Exception e){
                            System.err.println("An error occurred: " + e.getMessage());
                        }
                    }
                    ltloc.remove(0);
                    ltcmp.remove(0);
                } else if (fileOrDir.isDirectory()) {
                    // Recursively traverse subdirectories
                    findFiles(fileOrDir, ltloc, ltcmp, tlocVal,tcmpcVal, printWriter);
                }
            }
        }
    }



}