package httpserver.resourcemanagement;

import httpserver.ResourceHandler;

import java.io.File;
import java.net.URI;

public class HTTPResourceHandler implements ResourceHandler {

    private final String filepath;
    private final ResourceParser resourceParser;

    public HTTPResourceHandler(String path, ResourceParser resourceParser) {
        this.filepath = path;
        this.resourceParser = resourceParser;
    }

    public Resource getResource(URI path) {
        File file = new File(filepath + path);
        Resource resource = new Resource(file);
        if (resource.exists()) {
            resource.setContents(resourceParser.parse(file));
        }
        return resource;
    }
}
