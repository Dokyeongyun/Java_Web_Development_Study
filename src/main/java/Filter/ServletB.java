package Filter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "servletB",
        urlPatterns = {"/filterTest/servletB"}
)
public class ServletB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ServletB.doGet() 호출");
        resp.getWriter().write("Servlet B");
        System.out.println("ServletB.doGet() 종료");
    }
}
