package httpserver.httprequests;

import httpserver.routing.Method;

import java.net.URI;
import java.util.HashMap;

import static httpserver.httprequests.RequestHeader.*;

public class HTTPRequestParser {

    public HTTPRequest parse(String request) {
        String[] requestElements = request.split(" ");
        Method requestMethod = getMethod(requestElements[0]);
        URI uri = URI.create(requestElements[1]);
        return createRequest(request, uri, requestMethod);
    }

    private HTTPRequest createRequest(String request, URI uri, Method requestMethod) {
        HashMap<RequestHeader, String> headers = new HashMap<>();
        for (RequestHeader requestHeader : RequestHeader.values()) {
            if (request.contains(requestHeader.header())) {
                headers.put(requestHeader, getHeaderData(requestHeader, request, uri));
            }
        }
        if (headers.isEmpty()) {
            return HTTPRequest.create(requestMethod, uri.getPath());
        }
        return HTTPRequest.create(requestMethod, uri.getPath()).withHeaders(headers);
    }

    private String getHeaderData(RequestHeader requestHeader, String request, URI uri) {
        switch (requestHeader) {
            case AUTHORIZATION:
                return getHeaderValue(request, requestHeader, "Basic ");
            case COOKIE:
                return getHeaderValue(request, requestHeader, ": ");
            case PARAMS:
                return uri.getQuery();
            case DATA:
                return getData(request);
            case RANGE:
                return getRange(request);
            case RANGE_START:
                return getRangeStart(request);
            case RANGE_END:
                return getRangeEnd(request);
        }
        return null;
    }

    private String getRangeStart(String request) {
        String[] ranges = splitRangeLine(request);
        if (!ranges[0].equals("")) {
            return ranges[0];
        }
        return null;
    }

    private String getRangeEnd(String request) {
        String[] ranges = splitRangeLine(request);
        if (ranges.length > 1 && !(ranges[1].trim().equals(""))) {
            return ranges[1].trim();
        }
        return null;
    }

    private String[] splitRangeLine(String request) {
        String range = getRange(request);
        return range.split("=")[1].split("-");
    }

    private String getRange(String request) {
        String[] lines = request.split("\n");
        String rangeLine = getHeaderLine(lines, RANGE.header());
        return rangeLine.split(":")[1].trim();
    }

    private String getHeaderValue(String request, RequestHeader requestHeader, String term) {
        String[] lines = request.split("\n");
        String cookieLine = getHeaderLine(lines, requestHeader.header());
        return cookieLine.split(term)[1].trim();
    }

    private String getHeaderLine(String[] lines, String header) {
        String rangeLine = null;
        for (String line : lines) {
            if (line.contains(header)) {
                rangeLine = line;
            }
        }
        return rangeLine;
    }

    private String getData(String request) {
        String[] lines = request.split("\n");
        return lines[lines.length - 1].trim();
    }

    private Method getMethod(String requestMethod) {
        for (Method method : Method.values()) {
            if (methodIsValid(requestMethod, method)) {
                return method;
            }
        }
        return Method.BOGUS;
    }

    private boolean methodIsValid(String requestMethod, Method method) {
        return method.name().equals(requestMethod);
    }
}
