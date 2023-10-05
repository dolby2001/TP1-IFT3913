

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
public class Tropcomp {

    public static void main(String[] args) {

        // Check if the correct number of arguments are provided
        if (args.length != 4 || !args[0].equals("-o")) {
            System.out.println("Usage: tropcomp -o <output.csv> <input> <seuil>");
            return;
        }
        // Parse the command-line arguments
        String outputPath = args[2];
        String inputPath = args[1];
        File inputDirectory = new File("../../"+inputPath);
        double seuil = Double.parseDouble(args[3]);


        try {
            // Call a method to traverse files in the input directory recursively
            traverseFiles(inputDirectory, outputPath, seuil);

            System.out.println("Computation completed successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    // Recursive method to traverse files in a directory
    private static void traverseFiles(File inputDir, String outputPath, double seuil) {

        // Ensure the input directory exists
        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.err.println("Input directory does not exist.");
            return;
        }
        TLocCalculator tLocCalculator = new TLocCalculator();
        Tls tls = new Tls();
        // Get a list of files and subdirectories in the input directory
        File[] filesAndDirs = inputDir.listFiles();
        HashMap<Integer, Float> files = new HashMap<Integer, Float>();
        if (filesAndDirs != null) {

            for (File fileOrDir : filesAndDirs) {
                if (fileOrDir.isFile()) {
                    // Process the file (e.g., apply threshold and write to output)
                    try{
                        int tloc = tLocCalculator.tLocCalculator(fileOrDir.getAbsolutePath());
                        System.out.println(fileOrDir.getAbsolutePath());

                    } catch(Exception e){
                        System.err.println("An error occurred: " + e.getMessage());
                    }

                    
                } else if (fileOrDir.isDirectory()) {
                    // Recursively traverse subdirectories
                    traverseFiles(new File(fileOrDir.getAbsolutePath()), outputPath, seuil);
                }
            }
        }

    }



}