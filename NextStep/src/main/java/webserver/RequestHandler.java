package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

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
/*
            int index = url.indexOf("?");
            String url2 = url.substring(0,index);
            String query = url.substring(index+1);
            Map<String, String> map = HttpRequestUtils.parseQueryString(query);
            User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
            log.debug("User : {} ", user);
*/

            /* 요구사항 3 - POST 방식으로 회원가입하기
            *  user/form.html 파일의 form태그 method속성을 GET에서 POST로 변경
            *  POST로 전달하면 데이터가 HTTP 본문에 담김
            *  HTTP 본문은 헤더 이후 공백 한 줄 다음부터 시작
            *  본문을 파싱하여 User 객체를 생성하라
            *  */

            // Content-Length 헤더를 찾는 것이 중요!
            int contentLength = 0;
            while(!line.equals("")){
                line = br.readLine();

                if(line.startsWith("Content-Length")){
                    String[] split = line.split(":");
                    contentLength = Integer.parseInt(split[1].trim());
                }
            }

            String contentBody = IOUtils.readData(br, contentLength);
            Map<String, String> map = HttpRequestUtils.parseQueryString(contentBody);
            User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
            log.debug("User : {} ", user);

            File cur = new File("C:/Users/Admin/IdeaProjects/JavaEE_Study/NextStep/src/main");
            byte[] body = Files.readAllBytes(new File(cur + "/webapp" + url).toPath());

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
