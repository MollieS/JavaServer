package httpserver.resourcemanagement;

import java.io.*;

public class ResourceParser {

    public String parse(File directory) {
        return getFiles(directory);
    }

    private String getFiles(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return returnFiles(file);
            } else {
                String fileContents = readFile(file);
                if (fileContents != null) return fileContents;
            }
        }
        return null;
    }

    private String readFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
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
