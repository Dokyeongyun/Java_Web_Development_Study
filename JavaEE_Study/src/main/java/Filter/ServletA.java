package Filter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "servletA",
        urlPatterns = {"/filterTest/servletA"}
)
public class ServletA extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletA.doGet() 호출");
        resp.getWriter().write("Servlet A");
        System.out.println("ServletA.doGet() 종료");
    }
}
