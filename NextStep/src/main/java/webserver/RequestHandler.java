package webserver;

import db.DataBase;
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
            boolean isLogined = false;
            while (!line.equals("")) {
                line = br.readLine();

                if (line.startsWith("Content-Length")) {
                    String[] split = line.split(":");
                    contentLength = Integer.parseInt(split[1].trim());
                }
                if(line.startsWith("Cookie")){
                    String[] split = line.split(":");
                    Map<String, String> cookies = HttpRequestUtils.parseCookies(split[1].trim());
                    if(Boolean.parseBoolean(cookies.get("logined"))){
                        isLogined = true;
                    }
                }
            }

            if (url.equals("/user/create")) {
                String contentBody = IOUtils.readData(br, contentLength);
                Map<String, String> map = HttpRequestUtils.parseQueryString(contentBody);
                User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
                log.debug("User : {} ", user);

                /* 요구사항 5 - 로그인하기
                *  회원가입 후 DB에 User 데이터 저장
                *  로그인 시 HTTP 응답헤더에 Set-Cookie 로 성공여부 전달 */
                DataBase.addUser(user);

                /* 요구사항 4 - 302 Status Code 적용
                 *  회원가입 완료 후 /index.html 로 리디렉션 */
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            } else if(url.equals("/user/login")) {
                /* 요구사항 5 - 로그인하기
                 *  회원가입 후 DB에 User 데이터 저장
                 *  로그인 시 HTTP 응답헤더에 Set-Cookie 로 성공여부 전달 */

                String contentBody = IOUtils.readData(br, contentLength);
                Map<String, String> map = HttpRequestUtils.parseQueryString(contentBody);
                User loginUser = DataBase.findUserById(map.get("userId"));

                if(loginUser == null){
                    // ID 존재하지 않음
                    responseLoginFailed(out);
                    return;
                }

                if(loginUser.getPassword().equals(map.get("password"))){
                    // 로그인 성공 -> 302 응답헤더
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderSetCookie(dos);
                }else{
                    // 로그인 실패
                    responseLoginFailed(out);
                }
            } else if(url.equals("/user/list")){
                /* 요구사항 6 - 사용자 목록 출력
                *  접근한 사용자가 로그인 상태라면 사용자 목록을 출력
                *  로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동 */

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
