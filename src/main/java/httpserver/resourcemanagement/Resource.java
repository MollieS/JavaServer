package httpserver.resourcemanagement;

import java.io.File;

public class Resource {

    private final File file;
    private String contents;
    private String type;

    public Resource(File file) {
        this.file = file;
    }

    public boolean isADirectory() {
        return file.isDirectory();
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean exists() {
        return file.exists();
    }

    public String getType() {
        return type;
    }
}
