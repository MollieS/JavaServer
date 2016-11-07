package httpserver.sessions;

import java.util.HashMap;

public interface Session {

    HashMap<String, String> getData();
    String getId();
    void addData(String key, String value);
    boolean hasData(String key);
}
