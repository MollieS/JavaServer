package httpserver.resourcemanagement;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceHandlerTest {

    private String path = getClass().getClassLoader().getResource("directory").getPath();
    private HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());

    @Test
    public void returnsADirectoryResource() {
        Resource resource = resourceHandler.getResource("/");

        assertTrue(resource.isADirectory());
    }

    @Test
    public void returnsAResourceThatContainsDirectoryContent() {
        Resource resource = resourceHandler.getResource("/");

        String body = new String(resource.getContents(), Charset.forName("UTF-8"));

        assertEquals("file1\nfile2\nimage.jpeg\n", body);
    }

    @Test
    public void returnsANonExistentResourceForAFileThatDoesNotExist() {
        Resource resource = resourceHandler.getResource("/foobar");

        assertFalse(resource.exists());
    }
}
