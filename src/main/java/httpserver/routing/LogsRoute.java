package httpserver.routing;

import httpserver.Resource;
import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;
import httpserver.resourcemanagement.HTTPResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;

import static httpserver.httpresponse.StatusCode.OK;
import static httpserver.httpresponse.StatusCode.UNAUTHORIZED;

public class LogsRoute extends Route {

    private final static String URI = "/logs";
    private final static String AUTHENTICATION_SCHEME = "Basic";
    private final HashMap<ResponseHeader, byte[]> headers;
    private final Base64.Decoder decoder;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "hunter2";
    private final File logsFile;

    public LogsRoute(String path, Method... methods) {
        super(URI, methods);
        this.headers = getHeaders();
        this.decoder = Base64.getDecoder();
        this.logsFile = new File(path);
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        if (httpRequest.hasAuthorization()) {
            if (isAuthorizationValid(httpRequest)) {
                Resource resource = new HTTPResource(readLogs());
                return HTTPResponse.create(OK).withBody(resource);
            }
        }
        headers.put(ResponseHeader.AUTH, (AUTHENTICATION_SCHEME + " " + "realm=" + URI).getBytes());
        return HTTPResponse.create(UNAUTHORIZED).withHeaders(headers);
    }

    private boolean isAuthorizationValid(HTTPRequest httpRequest) {
        byte[] credentials = decoder.decode(httpRequest.getAuthorization().getBytes());
        String stringCredentials = new String(credentials, Charset.forName("UTF-8"));
        String username = stringCredentials.split(":")[0];
        String password = stringCredentials.split(":")[1];
        return username.equals(USERNAME) && password.equals(PASSWORD);
    }

    private byte[] readLogs() {
        byte[] logs;
        try {
            logs = Files.readAllBytes(logsFile.toPath());
        } catch (IOException e) {
            logs = "Cannot read logs".getBytes();
        }
        return logs;
    }
}
