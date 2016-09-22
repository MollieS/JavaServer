package httpserver.resourcemanagement;

import java.io.File;

public class Resource {

    private final File file;
    private byte[] contents;
    private String type;

    public Resource(File file) {
        this.file = file;
    }

    public boolean isADirectory() {
        return file.isDirectory();
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public boolean exists() {
        return file.exists();
    }

    public String getType() {
        return type;
    }
}
