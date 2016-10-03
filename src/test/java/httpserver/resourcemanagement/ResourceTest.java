package httpserver.resourcemanagement;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResourceTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();

    @Test
    public void knowsIfItExists() {
        File file = new File("/does/not/exist/");

        Resource resource = new Resource(file);

        assertFalse(resource.exists());
    }

    @Test
    public void hasAContentTypeForATextFile() {
        File file = new File(path + "/file1");

        Resource resource = new Resource(file);

        assertEquals("text/plain", resource.getContentType());
    }

    @Test
    public void hasAContentTypeForAJPEGFile() {
        File file = new File(path + "image.jpeg");

        Resource resource = new Resource(file);

        assertEquals("image/jpeg", resource.getContentType());
    }

    @Test
    public void hasAContentTypeForAPNGFile() {
        File file = new File(path + "image.png");

        Resource resource = new Resource(file);

        assertEquals("image/png", resource.getContentType());
    }

    @Test
    public void hasAContentTypeForAGIF() {
        File file = new File(path + "image.gif");

        Resource resource = new Resource(file);

        assertEquals("image/gif", resource.getContentType());
    }

    @Test
    public void hasCorrectContentTypeForDirectory() {
        File file = new File(path);

        Resource resource = new Resource(file);

        assertEquals("text/html", resource.getContentType());
    }
}
