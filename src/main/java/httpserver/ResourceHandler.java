package httpserver;

import httpserver.resourcemanagement.Resource;

public interface ResourceHandler {

    Resource getResource(String path);
}
