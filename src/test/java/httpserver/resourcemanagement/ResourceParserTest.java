package httpserver.resourcemanagement;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ResourceParserTest {

    private ResourceParser resourceParser = new ResourceParser();
    private String path = getClass().getClassLoader().getResource("directory").getPath();

    @Test
    public void parsesADirectory() {
        File file = new File(path);

        String parsedFile = new String(resourceParser.parse(file), Charset.forName("UTF-8"));

        assertEquals("file1\nfile2\nimage.jpeg\n", parsedFile);
    }

    @Ignore
    @Test
    public void returnsNothingIfFileDoesNotExist() {
        File file = new File("does/not/exist");

        String parsedFile = new String(resourceParser.parse(file), Charset.forName("UTF-8"));

        assertNull(parsedFile);
    }

    @Test
    public void returnsFileContentsIfNotDirectory() {
        File file = new File(path + "/file1");

        String parsedFile = new String(resourceParser.parse(file), Charset.forName("UTF-8"));

        assertEquals("this is file1", parsedFile);
    }

    @Test
    public void doesNotParseAnImage() {
        File file = new File(path + "/image.jpeg");


    }
}
