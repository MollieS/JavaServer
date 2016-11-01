package httpserver.resourcemanagement;

import httpserver.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTMLResource implements Resource {

    private final File view;

    public HTMLResource(String filename) {
        String baseLocation = new File("").getAbsolutePath();
        String viewFolder = new File("/src/main/java/httpserver/views").getAbsolutePath();
        String viewPath = (baseLocation + viewFolder + filename);
        this.view = new File(viewPath);
    }

    @Override
    public byte[] getContents() {
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
