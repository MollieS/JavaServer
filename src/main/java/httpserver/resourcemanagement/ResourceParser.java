package httpserver.resourcemanagement;

import java.io.*;

public class ResourceParser {

    public byte[] parse(File directory) {
        return getFiles(directory);
    }

    private byte[] getFiles(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return returnFiles(file);
            } else {
                byte[] fileContents = readFile(file);
                if (fileContents != null) return fileContents;
            }
        }
        return null;
    }

    private byte[] readFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.readLine().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] returnFiles(File directory) {
        String files = "";
        for (File fileEntry : directory.listFiles()) {
            files += fileEntry.getName() + "\n";
        }
        return files.getBytes();
    }
}
