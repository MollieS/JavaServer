package httpserver.httpresponses;

import httpserver.httpresponse.ResponseMessage;
import httpserver.httpresponse.StatusCode;
import httpserver.Resource;
import org.junit.Test;

import java.nio.charset.Charset;

import static httpserver.httpresponse.StatusCode.NOTALLOWED;
import static httpserver.httpresponse.StatusCode.PARTIAL;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResponseMessageTest {

    @Test
    public void buildsACoffeeResponse() {
        ResponseMessage reponseMessage = ResponseMessage.create(StatusCode.TEAPOT);

        String body = new String(reponseMessage.getBody(), Charset.defaultCharset());

        assertEquals(418, reponseMessage.getStatusCode());
        assertEquals("I'm a teapot", reponseMessage.getReasonPhrase());
        assertEquals("I'm a teapot", body);
        assertEquals("text/plain", reponseMessage.getContentType());
        assertNotNull(reponseMessage.getOriginTime());
    }

    @Test
    public void buildsANotFoundResponse() {
        ResponseMessage responseMessage = ResponseMessage.create(StatusCode.NOTFOUND);

        assertEquals(404, responseMessage.getStatusCode());
        assertEquals("Not Found", responseMessage.getReasonPhrase());
        assertFalse(responseMessage.hasBody());
    }

    @Test
    public void buildsARedirectResponse() {
        ResponseMessage responseMessage = ResponseMessage.create(StatusCode.REDIRECT).withLocation("/");

        assertEquals(302, responseMessage.getStatusCode());
        assertEquals("Found", responseMessage.getReasonPhrase());
        assertEquals("/", responseMessage.getLocation());
    }

    @Test
    public void buildsAnOkResponseWithNoBody() {
        ResponseMessage responseMessage = ResponseMessage.create(StatusCode.OK);

        assertEquals(200, responseMessage.getStatusCode());
        assertEquals("OK", responseMessage.getReasonPhrase());
        assertFalse(responseMessage.hasBody());
    }

    @Test
    public void buildsAnOkResponseWithBody() {
        ResourceFake resourceFake = new ResourceFake();
        ResponseMessage responseMessage = ResponseMessage.create(StatusCode.OK).withBody(resourceFake);

        String body = new String(responseMessage.getBody(), Charset.defaultCharset());

        assertEquals("body", body);
        assertEquals("text/plain", responseMessage.getContentType());
        assertNotNull(responseMessage.getOriginTime());
    }

    @Test
    public void buildsAPartialResponseWithBody() {
        Resource resource = new ResourceFake();
        ResponseMessage responseMessage = ResponseMessage.create(PARTIAL).withBody(resource);
        String body = new String(responseMessage.getBody(), Charset.defaultCharset());

        assertEquals(206, responseMessage.getStatusCode());
        assertEquals("Partial Content", responseMessage.getReasonPhrase());
        assertNotNull(responseMessage.getOriginTime());
        assertEquals("body", body);
        assertEquals(4, responseMessage.getContentRange());
    }

    @Test
    public void buildsAMethodNotAllowedResponse() {
        ResponseMessage responseMessage = ResponseMessage.create(NOTALLOWED);

        assertEquals(405, responseMessage.getStatusCode());
        assertEquals("Method Not Allowed", responseMessage.getReasonPhrase());
    }

    private class ResourceFake implements Resource {

        @Override
        public byte[] getContents() {
            return "body".getBytes();
        }

        @Override
        public String getContentType() {
            return "text/plain";
        }
    }
}
