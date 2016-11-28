package httpserver.sessions;

import java.util.HashMap;

public class HTTPSession implements Session {

    private final String id;
    private final HashMap<String, String> data;

    public HTTPSession(String id) {
        this.id = id;
        this.data = new HashMap<>();
    }

    @Override
    public HashMap<String, String> getData() {
        return data;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void addData(String key, String value) {
        data.put(key, value);
    }

    @Override
    public boolean hasData(String key) {
        return data.containsKey(key);
    }

}
