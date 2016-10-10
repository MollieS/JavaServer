package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.httprequests.RequestHeader.DATA;
import static httpserver.httpresponse.StatusCode.OK;

public class FormRoute extends Route {

    private final String path;
    private final File file;
    private final static String URI = "/form";

    public FormRoute(String path, Method... methods) {
        super(URI, methods);
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public Response performAction(Request httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            try {
                return getHttpResponse(httpRequest);
            } catch (IOException e) {
                createForm();
            }
        }
        return methodNotAllowed();
    }

    private void createForm() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new FormManagerException(e);
        }
    }

    private Response getHttpResponse(Request httpRequest) throws IOException {
        HTTPResponse httpResponse = HTTPResponse.create(OK);
        switch (httpRequest.getMethod()) {
            case POST:
                writeToFile(httpRequest);
                break;
            case PUT:
                writeToFile(httpRequest);
                break;
            case GET:
                Resource resource = new HTTPResource(readFromFile());
                return httpResponse.withBody(resource);
            case DELETE:
                deleteFileContents();
                break;
        }
        return httpResponse;
    }

    private void deleteFileContents() throws IOException {
        clearForm(file.toPath());
    }

    private byte[] readFromFile() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private void writeToFile(Request httpRequest) throws IOException {
        Path path = Paths.get(this.path);
        if (httpRequest.hasHeader(DATA)) {
            clearForm(path);
            writeToForm(httpRequest, path);
        }
    }

    private void writeToForm(Request httpRequest, Path path) throws IOException {
        Files.write(path, httpRequest.getValue(DATA).getBytes());
    }

    private void clearForm(Path file) throws IOException {
        Files.delete(file);
    }
}
