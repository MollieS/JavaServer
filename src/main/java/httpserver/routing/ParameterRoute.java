package httpserver.routing;

import httpserver.Request;
import httpserver.Resource;
import httpserver.Response;
import httpserver.httpresponse.HTTPResponse;
import httpserver.resourcemanagement.HTTPResource;

import static httpserver.httprequests.RequestHeader.PARAMS;
import static httpserver.httpresponse.StatusCode.OK;

public class ParameterRoute extends Route {

    private final String PARAM_NOTATION = "variable_";

    public ParameterRoute(Method... methods) {
        super("/parameters", methods);
    }

    @Override
    public Response performAction(Request httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            HTTPResponse httpResponse = HTTPResponse.create(OK);
            addBody(httpRequest, httpResponse);
            return httpResponse;
        }
        return methodNotAllowed();
    }

    private void addBody(Request httpRequest, HTTPResponse httpResponse) {
        if (httpRequest.hasHeader(PARAMS)) {
            Resource resource = new HTTPResource(formatParameters(httpRequest));
            httpResponse.withBody(resource);
        }
    }

    private byte[] formatParameters(Request httpRequest) {
        String[] allParams = httpRequest.getValue(PARAMS).split(PARAM_NOTATION);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < allParams.length; i++) {
            stringBuilder.append(String.format("variable_%s = ", String.valueOf(i)));
            String params = allParams[i].replace(String.valueOf(i) + "=", "");
            stringBuilder.append(params);
            addMultipleParameters(stringBuilder);
        }
        return stringBuilder.toString().getBytes();
    }

    private void addMultipleParameters(StringBuilder stringBuilder) {
        if (stringBuilder.toString().endsWith("&")) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("\n");
        }
    }
}
