package httpserver.resourcemanagement;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceHandlerTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();
    private final HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());

    @Test
    public void returnsADirectoryResource() {
        Resource resource = resourceHandler.getResource("/");

        assertTrue(resource.isADirectory());
    }

    @Test
    public void returnsAResourceThatContainsDirectoryContent() {
        Resource resource = resourceHandler.getResource("/");

        String body = new String(resource.getContents(), Charset.forName("UTF-8"));

        assertTrue(body.contains("file1"));
    }

    @Test
    public void returnsANonExistentResourceForAFileThatDoesNotExist() {
        Resource resource = resourceHandler.getResource("/foobar");

        assertFalse(resource.exists());
    }
}
