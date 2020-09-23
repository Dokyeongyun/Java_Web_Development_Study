package EL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;
import java.util.stream.Stream;

@WebServlet(
        name = "profileServlet",
        urlPatterns = {"/profile"}
)
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        user.setUserId(10000L);
        user.setUsername("aservmz");
        user.setFirstName("Kyeongyun");
        user.setLastName("Do");

        Hashtable<String, Boolean> permissions = new Hashtable<>();
        permissions.put("user", true);
        permissions.put("moderator", true);
        permissions.put("admin", false);
        user.setPermissions(permissions);

        // 요청의 속성으로 user 설정
        req.setAttribute("user",user);
        // 세션의 속성으로 user 설정
        //req.getSession().setAttribute("user", user);
        // 애플리케이션의 속성으로 user 설정
        //req.getServletContext().setAttribute("user", user);
        req.getRequestDispatcher("WEB-INF/jsp/view/profile.jsp").forward(req, resp);
    }
}
