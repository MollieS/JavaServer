package httpserver.sessions;

import java.util.HashMap;

public class SessionMock implements Session {

    private boolean dataHasBeenCalled;
    private HashMap data;

    public SessionMock() {
        dataHasBeenCalled = false;
        this.data = new HashMap<>();
    }

    @Override
    public HashMap<String, String> getData() {
        dataHasBeenCalled = true;
        return data;
    }

    @Override
    public String getId() {
        return "1";
    }

    @Override
    public void addData(String key, String value) {
        data.put(key, value);
    }

    @Override
    public boolean hasData(String key) {
        return false;
    }

    public boolean getDataHasBeenCalled() {
        return dataHasBeenCalled;
    }
}
