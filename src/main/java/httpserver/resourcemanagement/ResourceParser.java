package httpserver.resourcemanagement;

import java.io.*;

public class ResourceParser {

    public String parse(File directory) {
        return getFiles(directory);
    }

    private String getFiles(File directory) {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                return returnFiles(directory);
            } else {
                try {
                    FileReader fileReader = new FileReader(directory);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    return bufferedReader.readLine();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String returnFiles(File directory) {
        String files = "";
        for (File fileEntry : directory.listFiles()) {
            files += fileEntry.getName() + "\n";
        }
        return files;
    }
}
