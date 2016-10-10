package httpserver;

import httpserver.resourcemanagement.FileResource;

import java.net.URI;

public interface ResourceHandler {

    FileResource getResource(URI path);

    void createResource(URI path, String etag, String contents);
}

