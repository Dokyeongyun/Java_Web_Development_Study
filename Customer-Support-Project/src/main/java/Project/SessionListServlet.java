package Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "sessionListServlet",
        urlPatterns = {"/sessions"}
)
public class SessionListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // [고객 지원 어플리케이션 v7 : 인증확인 절차를 필터를 이용해 수행 ]
        /*        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("login");
            return;
        }*/

        req.setAttribute("numberOfSessions", SessionRegistry.getNumberOfSessions());
        req.setAttribute("sessionList", SessionRegistry.getAllSessions());
        req.setAttribute("timestamp", System.currentTimeMillis());
        req.getRequestDispatcher("/WEB-INF/jsp/view/sessions.jsp").forward(req, resp);
    }
}
