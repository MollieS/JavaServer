package httpserver.routing;

import httpserver.Route;
import httpserver.httpmessages.HTTPResponse;

import java.util.ArrayList;
import java.util.List;

import static httpserver.httpmessages.HTTPResponseCode.TEAPOT;

public class CoffeeRoute implements Route {

    private List<Method> allowedMethods;

    public CoffeeRoute(Method...methods) {
        this.allowedMethods = new ArrayList<>();
        for (Method method : methods) {
            allowedMethods.add(method);
        }
    }

    public HTTPResponse performAction(Method method) {
        if (method == Method.GET) {
            HTTPResponse httpResponse = new HTTPResponse(TEAPOT.code, TEAPOT.reason);
            httpResponse.setAllowedMethods(allowedMethods);
        }
        return null;
    }
}
