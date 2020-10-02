package webserver;

import db.DataBase;
import http.HttpRequest;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

// 스레드를 상속함,
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    static File cur = new File("C:/Users/Admin/IdeaProjects/JavaEE_Study/NextStep/src/main");

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            /* 요구사항 1 - index.html 응답하기 */
            HttpRequest httpRequest = new HttpRequest(in);
            String url = httpRequest.getURL();
            System.out.println(httpRequest.getHeaders("Connection"));

            if (url.equals("/user/create")) {
                User user = new User(httpRequest.getRequestParams("userId"),
                        httpRequest.getRequestParams("password"),
                        httpRequest.getRequestParams("name"),
                        httpRequest.getRequestParams("email"));
                log.debug("User : {} ", user);

                DataBase.addUser(user);

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            } else if(url.equals("/user/login")) {
                User loginUser = DataBase.findUserById(httpRequest.getRequestParams("userId"));

                if(loginUser == null){
                    // ID 존재하지 않음
                    responseLoginFailed(out);
                    return;
                }

                if(loginUser.getPassword().equals(httpRequest.getRequestParams("password"))){
                    // 로그인 성공 -> 302 응답헤더
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderSetCookie(dos);
                }else{
                    // 로그인 실패
                    responseLoginFailed(out);
                }
            } else if(url.equals("/user/list")){

                boolean isLogined = false;
                Map<String, String> cookie = HttpRequestUtils.parseCookies(httpRequest.getHeaders("Cookie"));
                if(cookie.get("logined")!=null){
                    isLogined = Boolean.parseBoolean(cookie.get("logined"));
                }
                if(isLogined){
                    Collection<User> users = DataBase.findAll();
                    StringBuilder sb = new StringBuilder();
                    sb.append("<table border='1'>");
                    sb.append("  <tr class=\"success\">\n" +
                            "    <td>ID</td>\n" +
                            "    <td>이름</td>\n" +
                            "    <td>이메일</td>\n" +
                            "  </tr>");

                    for (User user : users) {
                        sb.append("<tr>");
                        sb.append("<td>" + user.getUserId() + "</td>");
                        sb.append("<td>" + user.getName() + "</td>");
                        sb.append("<td>" + user.getEmail() + "</td>");
                        sb.append("</tr>");
                    }

                    sb.append("</table>");
                    byte[] body = sb.toString().getBytes();
                    DataOutputStream dos = new DataOutputStream(out);
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                }else{
                    DataOutputStream dos = new DataOutputStream(out);
                    response302Header(dos, "/user/login.html");
                    return;
                }

            } else if(url.endsWith(".css")){
                /* 요구사항 7 - CSS 지원하기
                *  응답헤더의 Content-Type을 text/css로 하여 css 파일이라는 걸 알려줌 */
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = Files.readAllBytes(new File(cur + "/webapp" + url).toPath());
                response200CSSHeader(dos, body.length);
                responseBody(dos, body);
            } else {
                byte[] body = Files.readAllBytes(new File(cur + "/webapp" + url).toPath());

                DataOutputStream dos = new DataOutputStream(out);
                // byte[] body = "Hello World".getBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /* 로그인 실패시 login_failed.html로 이동*/
    private void responseLoginFailed(OutputStream out) throws IOException {
        byte[] body = Files.readAllBytes(new File(cur + "/webapp/user/login_failed.html").toPath());

        DataOutputStream dos = new DataOutputStream(out);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    /* 로그인 성공시 Set-Cookie 설정 및 /index.html로 리디렉션 */
    private void response302HeaderSetCookie(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + "/index.html" + "\r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("\r\n");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* 302 응답 헤더 */
    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void response200CSSHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
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
