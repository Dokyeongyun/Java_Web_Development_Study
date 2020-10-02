package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
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

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            String url = httpRequest.getURL();

            if (url.equals("/user/create")) {
                User user = new User(httpRequest.getRequestParams("userId"),
                        httpRequest.getRequestParams("password"),
                        httpRequest.getRequestParams("name"),
                        httpRequest.getRequestParams("email"));
                log.debug("User : {} ", user);

                DataBase.addUser(user);

                httpResponse.sendRedirect("/index.html");
            } else if(url.equals("/user/login")) {
                User loginUser = DataBase.findUserById(httpRequest.getRequestParams("userId"));

                if(loginUser == null){
                    // ID 존재하지 않음
                    httpResponse.forward("/user/login_failed.html");
                    return;
                }

                if(loginUser.getPassword().equals(httpRequest.getRequestParams("password"))){
                    // 로그인 성공 -> 302 응답헤더
                    httpResponse.addHeader("Set-Cookie", "logined=true");
                    httpResponse.sendRedirect("/index.html");
                }else{
                    // 로그인 실패
                    httpResponse.forward("/user/login_failed.html");
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
                    httpResponse.forwardBody(sb.toString());
                }else{
                    httpResponse.sendRedirect("/user/login.html");
                }
            } else {
                httpResponse.forward(url);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
