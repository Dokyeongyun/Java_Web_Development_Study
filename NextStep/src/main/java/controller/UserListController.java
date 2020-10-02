package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import util.HttpRequestUtils;

import java.util.Collection;
import java.util.Map;

public class UserListController  extends  AbstractController{
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
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
    }
}
