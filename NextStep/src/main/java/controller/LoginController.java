package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

public class LoginController extends  AbstractController{
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User loginUser = DataBase.findUserById(httpRequest.getRequestParams("userId"));

        if(loginUser == null){
            // ID 존재하지 않음
            httpResponse.sendRedirect("/user/login_failed.html");
            return;
        }

        if(loginUser.getPassword().equals(httpRequest.getRequestParams("password"))){
            // 로그인 성공 -> 302 응답헤더
            httpResponse.addHeader("Set-Cookie", "logined=true");
            httpResponse.sendRedirect("/index.html");
        }else{
            // 로그인 실패
            httpResponse.sendRedirect("/user/login_failed.html");
        }
    }
}
