package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(JoinController.class);

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getRequestParams("userId"),
                httpRequest.getRequestParams("password"),
                httpRequest.getRequestParams("name"),
                httpRequest.getRequestParams("email"));
        log.debug("User : {} ", user);

        DataBase.addUser(user);

        httpResponse.sendRedirect("/index.html");
    }

}
