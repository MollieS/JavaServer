package httpserver;

import httpserver.resourcemanagement.Resource;

import java.net.URI;

public interface ResourceHandler {

    Resource getResource(URI path);
}
