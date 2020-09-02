package Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 서블릿이 GET 요청에 응답하여 "Hello, Java EE World!" 라는 문자열을 반환한다.
        resp.getWriter().println("Hello, Java EE World!");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[Servlet] " + this.getServletName() + " 이 시작되었습니다.");
    }

    @Override
    public void destroy() {
        System.out.println("[Servlet] " + this.getServletName() + " 이 종료되었습니다.");
    }
}



