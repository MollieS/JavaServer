package httpserver;

import java.nio.charset.Charset;

public class ByteArrayConverter {

    public static String getString(byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }
}
