package httpserver.resourcemanagement;

import java.io.File;

public class ResourceParser {

    public String parse(File directory) throws ResourceNotFoundException {
        return getFiles(directory);
    }

    private String getFiles(File directory) {
        if (directory.exists()) {
            String files = "";
            for (File fileEntry : directory.listFiles()) {
                files += fileEntry.getName() + "\n";
            }
            return files;
        }
        return null;
    }
}
