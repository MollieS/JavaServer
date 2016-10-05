package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.HTTPResponseDate;

import static httpserver.httpresponse.StatusCode.OK;

public class ParameterRoute extends Route {

    private final String PARAM_NOTATION = "variable_";

    public ParameterRoute(Method... methods) {
        super("/parameters", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            HTTPResponse httpResponse = new HTTPResponse(OK.code, OK.reason, new HTTPResponseDate());
            if (httpRequest.hasParams()) {
                httpResponse.setBody(formatParameters(httpRequest));
            }
            return httpResponse;
        }
        return methodNotAllowed();
    }

    private byte[] formatParameters(HTTPRequest httpRequest) {
        String[] allParams = httpRequest.getParams().split(PARAM_NOTATION);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < allParams.length; i++) {
            stringBuilder.append(String.format("variable_%s = ", String.valueOf(i)));
            String params = allParams[i].replace(String.valueOf(i) + "=", "");
            stringBuilder.append(params);
            if (stringBuilder.toString().endsWith("&")) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString().getBytes();
    }
}
