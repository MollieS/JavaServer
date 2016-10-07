package httpserver;

public interface HTTPRouter {

    Response route(Request httpRequest);
}
