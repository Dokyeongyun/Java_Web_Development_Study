import http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void responseForward() throws Exception {
        HttpResponse httpResponse = new HttpResponse(createOutputStream("Http_Forword.txt"));
        httpResponse.forwardBody("/index.html");
    }

    @Test
    public void responseCookies() throws Exception{
        HttpResponse httpResponse = new HttpResponse(createOutputStream("Http_Cookie.txt"));
        httpResponse.addHeader("Set-Cookie", "logined=true");
        httpResponse.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String file) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + file));
    }
}
