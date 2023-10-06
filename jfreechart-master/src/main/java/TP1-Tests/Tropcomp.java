
import java.util.Collections;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Tropcomp {

    public static void main(String[] args) throws IOException {

        if (args.length != 4 || !args[0].equals("-o")) {
            System.out.println("Usage: tropcomp -o <output.csv> <input> <seuil>");
            return;
        }
        String outputPath = args[1];
        double seuil = Double.parseDouble(args[3]);


        String filePath = args[2];
        if (!new File(filePath).isAbsolute()) {
            filePath = new File(System.getProperty("user.dir") + "/../../" + filePath).getCanonicalPath();
            System.out.println("file : " + filePath);
        }
        File inputDirectory = new File(filePath);


        ArrayList<Integer> ltloc = new ArrayList<Integer>();
        ArrayList<Integer> ltcmp = new ArrayList<Integer>();

        try {
            processFilesAndDirs(inputDirectory, ltloc, ltcmp);
            System.out.println("Traverse files completed successfully!");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }


        int lenght = ltloc.size();
        System.out.println("num of files : " + lenght);
        ArrayList<Integer> sortedLtloc = new ArrayList<>(ltloc);;
        ArrayList<Integer> sortedLtcmp = new ArrayList<>(ltcmp);;
        Collections.sort(sortedLtloc);
        Collections.sort(sortedLtcmp);

        int numFiles = (int) (lenght * (1-seuil));
        System.out.println("files that are under the seuil : " + numFiles);

        int tlocVal = sortedLtloc.get(numFiles);
        System.out.println("seuil value for tloc : " + tlocVal);

        int tcmpcVal = sortedLtcmp.get(numFiles);
        System.out.println("seuil value for tcmp : " + tcmpcVal);


        String csvFileName = outputPath;

        try{
            FileWriter fileWriter = new FileWriter(csvFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);    
            
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

                    try{
                        int tlocVal = TLocCalculator.tLocCalculator(fileOrDir.getAbsolutePath());
                        int tassertVal = TAssertCalculator.tAssertCalculator(fileOrDir.getAbsolutePath());
                        ltloc.add(tlocVal);
                        if(tassertVal!=0){
                            ltcmp.add(tlocVal / tassertVal);
                        }else{
                            ltcmp.add(0); 
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
                    findFiles(fileOrDir, ltloc, ltcmp, tlocVal,tcmpcVal, printWriter);
                }
            }
        }
    }



}