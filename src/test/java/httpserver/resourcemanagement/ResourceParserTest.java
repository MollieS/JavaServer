package httpserver.resourcemanagement;

import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class ResourceParserTest {

    private final ResourceParser resourceParser = new ResourceParser();
    private final String path = getClass().getClassLoader().getResource("directory").getPath();

    @Test
    public void parsesADirectory() {
        File file = new File(path);

        String parsedFile = new String(resourceParser.parse(file), Charset.forName("UTF-8"));

        assertTrue(parsedFile.contains("file1"));
    }

    @Test
    public void returnsNothingIfFileDoesNotExist() {
        File file = new File("does/not/exist");

        byte[] parsedFile = resourceParser.parse(file);

        assertNull(parsedFile);
    }

    @Test
    public void returnsFileContentsIfNotDirectory() {
        File file = new File(path + "/file1");

        String parsedFile = new String(resourceParser.parse(file), Charset.forName("UTF-8"));

        assertEquals("this is file1", parsedFile);
    }

    @Test
    public void canParseAnImage() {
        File file = new File(path + "/image.jpeg");

        byte[] image = resourceParser.parse(file);

        assertNotNull(image);
    }
}
