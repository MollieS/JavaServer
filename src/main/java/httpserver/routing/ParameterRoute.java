package httpserver.routing;

import httpserver.httprequests.HTTPRequest;
import httpserver.httpresponse.HTTPResponse;
import httpserver.httpresponse.ResponseMessage;
import httpserver.resourcemanagement.HTTPResource;
import httpserver.Resource;

import static httpserver.httpresponse.StatusCode.OK;

public class ParameterRoute extends Route {

    private final String PARAM_NOTATION = "variable_";

    public ParameterRoute(Method... methods) {
        super("/parameters", methods);
    }

    @Override
    public HTTPResponse performAction(HTTPRequest httpRequest) {
        if (methodIsAllowed(httpRequest.getMethod())) {
            ResponseMessage responseMessage = ResponseMessage.create(OK);
            addBody(httpRequest, responseMessage);
            return responseMessage;
        }
        return methodNotAllowed();
    }

    private void addBody(HTTPRequest httpRequest, ResponseMessage responseMessage) {
        if (httpRequest.hasParams()) {
            Resource resource = new HTTPResource(formatParameters(httpRequest));
            responseMessage.withBody(resource);
        }
    }

    private byte[] formatParameters(HTTPRequest httpRequest) {
        String[] allParams = httpRequest.getParams().split(PARAM_NOTATION);
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
