package Filter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "servletC",
        urlPatterns = {"/filterTest/servletC"}
)
public class ServletC extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletC.doGet() 호출");
        resp.getWriter().write("Servlet C");
        System.out.println("ServletC.doGet() 종료");
    }
}
