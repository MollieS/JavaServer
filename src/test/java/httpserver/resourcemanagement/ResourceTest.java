package httpserver.resourcemanagement;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

public class ResourceTest {

    @Test
    public void knowsIfItExists() {
        File file = new File("/does/not/exist/");

        Resource resource = new Resource(file);

        assertFalse(resource.exists());
    }
}
