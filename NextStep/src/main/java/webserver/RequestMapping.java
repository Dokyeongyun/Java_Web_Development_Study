package webserver;

import controller.Controller;
import controller.JoinController;
import controller.LoginController;
import controller.UserListController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<String, Controller>();

    static {
        controllers.put("/user/create", new JoinController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new UserListController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }
}
