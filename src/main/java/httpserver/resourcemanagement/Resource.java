package httpserver.resourcemanagement;

import java.io.File;

import static httpserver.resourcemanagement.ResourceContentType.TEXT;

public class Resource {

    private final File file;
    private byte[] contents;

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

    public String getContentType() {
        for (ResourceContentType type : ResourceContentType.values()) {
            if (file.getName().contains(type.extension)) {
                return type.contentType;
            }
        }
        return TEXT.contentType;
    }
}
