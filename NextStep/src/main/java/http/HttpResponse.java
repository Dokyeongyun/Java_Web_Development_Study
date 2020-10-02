package http;

/* 리팩토링 2단계 : 응답 데이터를 처리하는 로직을 별도의 클래스로 분리
*   - 응답 헤더 정보를 Map<String, String> 으로 관리
*   - 응답을 보낼 때 HTML, CSS, JS 파일을 직접 읽어 응답으로 보내는 메서드는 forward()
*   - 다른 URL로 리다이렉트하는 메서드는 sendRedirect() 메서드로 구현 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    static File cur = new File("C:/Users/Admin/IdeaProjects/JavaEE_Study/NextStep/src/main");

    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out){
        dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    /* HTML, CSS, JS 파일별로 Content-Type을 다르게 구현 */
    public void forward(String url) throws IOException {
        byte[] body = Files.readAllBytes(new File(cur + "/webapp" + url).toPath());

        if(url.endsWith(".css")){
            headers.put("Content-Type", "text/css");
        }else if(url.endsWith(".js")){
            headers.put("Content-Type", "application/javascript");
        }else{
            headers.put("Content-Type", "text/html;charset=utf-8");
        }
        headers.put("Content-Length", String.valueOf(body.length));
        response200(body);
    }

    public void forwardBody(String body){
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(contents.length));
        response200(contents);
    }

    private void response200(byte[] body) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processHeaders(){
        try{
            Set<String> keys = headers.keySet();
            for(String key:keys){
                dos.writeBytes(key+": "+headers.get(key)+ " \r\n");
            }
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }

    /* sendRedirect() 메서드로 리다이렉트 => 302 응답 코드 */
    public void sendRedirect(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            processHeaders();
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
