package httpserver.resourcemanagement;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceParserTest {

    private ResourceParser resourceParser = new ResourceParser();
    private String path = getClass().getClassLoader().getResource("directory").getPath();

    @Test
    public void parsesADirectory() {
        File file = new File(path);

        String parsedFile = resourceParser.parse(file);

        assertEquals("file1\nfile2\n", parsedFile);
    }

    @Test
    public void returnsNothingIfFileDoesNotExist() {
        File file = new File("does/not/exist");

        String parsedFile = resourceParser.parse(file);

        assertNull(parsedFile);
    }

    @Test
    public void returnsFileContentsIfNotDirectory() {
        File file = new File(path + "/file1");

        String parsedFile = resourceParser.parse(file);

        assertEquals("this is file1", parsedFile);
    }

    @Test
    public void doesNotParseAnImage() {
        File file = new File(path + "/image.jpeg");


    }
}
