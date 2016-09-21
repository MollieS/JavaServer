package httpserver.resourcemanagement;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceParserTest {

    private ResourceParser resourceParser = new ResourceParser();

    @Test
    public void parsesADirectory() {
        String path = getClass().getClassLoader().getResource("directory").getPath();
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
}
