package httpserver.sessions;

import java.util.Random;

public class SessionTokenGenerator {

    public int generateToken() {
        return new Random().nextInt();
    }
}
