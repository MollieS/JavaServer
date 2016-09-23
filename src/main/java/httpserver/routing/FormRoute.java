package httpserver.routing;

import httpserver.httpmessages.HTTPRequest;
import httpserver.httpmessages.HTTPResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static httpserver.httpmessages.HTTPResponseCode.OK;
import static httpserver.routing.Method.*;

public class FormRoute extends Route {

    private final String path;
    private final File file;

    public FormRoute(String uri, String path, Method... methods) {
        super(uri, methods);
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        HTTPResponse httpResponse;
        if (methodIsAllowed(httpRequest.getMethod())) {
            httpResponse = new HTTPResponse(OK.code, OK.reason);
            if (httpRequest.getMethod() == POST || httpRequest.getMethod() == PUT) {
                writeToFile(httpRequest);
            } else if (httpRequest.getMethod() == GET) {
                httpResponse.setContentType("text/plain");
                httpResponse.setBody(readFromFile());
            }
            return httpResponse;
        }
        return methodNotAllowed();
    }

    private byte[] readFromFile() {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private void writeToFile(HTTPRequest httpRequest) {
        Path file = Paths.get(path);
        if (httpRequest.getData() != null) {
            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Files.write(file, httpRequest.getData().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
