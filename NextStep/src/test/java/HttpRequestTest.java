import http.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory+"/Http_GET.http"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("GET", request.getMethod());
        assertEquals("/user/create", request.getURL());
        assertEquals("keep-alive", request.getHeaders("Connection"));
        assertEquals("DKY", request.getRequestParams("userId"));
    }

    @Test
    public void request_POST() throws Exception{
        InputStream in = new FileInputStream(new File(testDirectory+"/Http_POST.http"));
        HttpRequest request = new HttpRequest(in);

        assertEquals("POST", request.getMethod());
        assertEquals("/user/create", request.getURL());
        assertEquals("keep-alive", request.getHeaders("Connection"));
        assertEquals("DKY", request.getRequestParams("userId"));
    }
}
