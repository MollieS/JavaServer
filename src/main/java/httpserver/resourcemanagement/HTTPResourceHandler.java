package httpserver.resourcemanagement;

import httpserver.ResourceHandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

public class HTTPResourceHandler implements ResourceHandler {

    private final String filepath;
    private final ResourceParser resourceParser;

    public HTTPResourceHandler(String path, ResourceParser resourceParser) {
        this.filepath = path;
        this.resourceParser = resourceParser;
    }

    public FileResource getResource(URI path) {
        File file = new File(filepath + path);
        FileResource resource = new FileResource(file);
        if (resource.exists()) {
            resource.setContents(resourceParser.parse(file));
        }
        return resource;
    }

    public void createResource(URI path, String etag, String contents) {
        File file = new File(filepath + path + URI.create(etag));
        if (!file.exists()) {
            try {
                file.createNewFile();
                Files.write(file.toPath(), contents.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
