package httpserver.routing;

import java.util.ArrayList;
import java.util.List;

public class Routes {

    private List<Route> registeredRoutes = new ArrayList<>();

    public void register(Route route) {
        registeredRoutes.add(route);
    }

    public List<Route> registered() {
        return registeredRoutes;
    }
}
