package httpserver.resourcemanagement;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ResourceTest {

    @Test
    public void knowsIfItExists() {
        File file = new File("/does/not/exist/");

        Resource resource = new Resource(file);

        assertFalse(resource.exists());
    }

    @Ignore
    @Test
    public void hasAContentType() {
        String path = getClass().getClassLoader().getResource("file1").getPath();
        File file = new File(path);

        Resource resource = new Resource(file);

        assertEquals("text/plain", resource.getType());
    }
}
