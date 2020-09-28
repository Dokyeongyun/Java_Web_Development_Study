package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

// 스레드를 상속함,
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            /* 요구사항 1 - index.html 응답하기 */
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();

            if (line == null) {
                return;
            }
            String url = HttpRequestUtils.getUrl(line);

            /* 요구사항 2 - GET 방식으로 회원가입하기
            *  GET /user/create?userId=java&password=password&name=Kyeongyun&email=aservmz%40naver.com
            *  위 형태로 값이 전달되는데, 이를 파싱하여 User 클래스에 저장한다. */
            int index = url.indexOf("?");
            String url2 = url.substring(0,index);
            String query = url.substring(index+1);
            Map<String, String> map = HttpRequestUtils.parseQueryString(query);
            User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
            log.debug("User : {} ", user);

            File cur = new File("C:/Users/Admin/IdeaProjects/JavaEE_Study/NextStep/src/main");
            byte[] body = Files.readAllBytes(new File(cur + "/webapp" + url2).toPath());

            DataOutputStream dos = new DataOutputStream(out);
            // byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
