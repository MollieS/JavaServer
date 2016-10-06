package httpserver.routing;

import httpserver.Response;
import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseHeader;

import java.nio.charset.Charset;
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

    public LogsRoute(Method... methods) {
        super(URI, methods);
        this.headers = getHeaders();
        this.decoder = Base64.getDecoder();
    }

    @Override
    public Response performAction(HTTPRequest httpRequest) {
        if (httpRequest.hasAuthorization()) {
            byte[] credentials = decoder.decode(httpRequest.getAuthorization().getBytes());
            String stringCredentials = new String(credentials, Charset.forName("UTF-8"));
            String username = stringCredentials.split(":")[0];
            String password = stringCredentials.split(":")[1];
            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                return HTTPResponse.create(OK);
            }
        }
        headers.put(ResponseHeader.AUTH, (AUTHENTICATION_SCHEME + " " + "realm=" + URI).getBytes());
        return HTTPResponse.create(UNAUTHORIZED).withHeaders(headers);
    }
}
