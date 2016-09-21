package httpserver.resourcemanagement;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

        assertEquals("file1\nfile2\n", resource.getContents());
    }
}
