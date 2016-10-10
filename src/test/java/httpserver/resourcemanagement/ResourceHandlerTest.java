package httpserver.resourcemanagement;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;

import static httpserver.httpresponses.HTTPResponseTest.getString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResourceHandlerTest {

    private final String path = getClass().getClassLoader().getResource("directory").getPath();
    private final HTTPResourceHandler resourceHandler = new HTTPResourceHandler(path, new ResourceParser());

    @After
    public void tearDown() {
        File file = new File(path + "/fileetag");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void returnsADirectoryResource() {
        FileResource resource = resourceHandler.getResource(URI.create("/"));

        assertTrue(resource.isADirectory());
    }

    @Test
    public void returnsAResourceThatContainsDirectoryContent() {
        FileResource resource = resourceHandler.getResource(URI.create("/"));

        String body = new String(resource.getContents(), Charset.forName("UTF-8"));

        assertTrue(body.contains("file1"));
    }

    @Test
    public void returnsANonExistentResourceForAFileThatDoesNotExist() {
        FileResource resource = resourceHandler.getResource(URI.create("/foobar"));

        assertFalse(resource.exists());
    }

    @Test
    public void createsAResourceIfItDoesNotExist() {
        resourceHandler.createResource(URI.create("/file"), "etag", "contents");

        FileResource resource = resourceHandler.getResource(URI.create("/fileetag"));

        assertTrue(resource.exists());
    }

    @Test
    public void writesToFileIfEtagRequestHasBody() {
        resourceHandler.createResource(URI.create("/file"), "etag", "contents");

        FileResource resource = resourceHandler.getResource(URI.create("/fileetag"));
        String fileContents = getString(resource.getContents());

        assertEquals("contents", fileContents);
    }
}
