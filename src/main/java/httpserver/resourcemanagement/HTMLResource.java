package httpserver.resourcemanagement;

import httpserver.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTMLResource implements Resource {

    private File view = null;
    private byte[] contents = null;
    private boolean isDynamic = false;

    public HTMLResource(String filename) {
        String viewFolder = new File("views").getAbsolutePath();
        String viewPath = (viewFolder + filename);
        this.view = new File(viewPath);
    }

    public HTMLResource(byte[] body) {
        this.isDynamic = true;
        this.contents = body;
    }

    @Override
    public byte[] getContents() {
        if (isDynamic) {
            return contents;
        } else {
            return readFile();
        }
    }

    private byte[] readFile() {
        try {
            return Files.readAllBytes(view.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }
}
