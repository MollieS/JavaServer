package httpserver.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static httpserver.ByteArrayConverter.getString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPLoggerTest {

    private String logPath = getClass().getClassLoader().getResource("directory").getPath() + "/logs";
    private File log = new File(logPath);
    private HTTPLogger httpLogger = new HTTPLogger(logPath);

    @Before
    public void setUp() {
        if (log.exists()) {
            log.delete();
        }
    }

    @After
    public void tearDown() {
        if (log.exists()) {
            log.delete();
        }
    }

    @Test
    public void createsANewLogFileIfThereIsNone() {
        httpLogger.log("GET / HTTP1.1");

        File log = new File(logPath);

        assertTrue(log.exists());
    }

    @Test
    public void logsARequest() throws IOException {
        httpLogger.log("GET / HTTP1.1");

        byte[] logContents = Files.readAllBytes(log.toPath());
        String fileContents = getString(logContents);

        assertEquals("GET / HTTP1.1\n", fileContents);
    }

    @Test
    public void canLogMultipleRequests() throws IOException {
        httpLogger.log("GET / HTTP1.1");
        httpLogger.log("PUT / HTTP1.1");

        byte[] logContents = Files.readAllBytes(log.toPath());
        String fileContents = getString(logContents);

        assertEquals("GET / HTTP1.1\nPUT / HTTP1.1\n", fileContents);
    }

    @Test(expected = RuntimeException.class)
    public void throwsAnExceptionIfCannotWriteToLogs() {
        HTTPLogger httpLogger = new HTTPLogger("/bad/path");
        httpLogger.log("GET / HTTP1.1");
    }

}
