package Servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 아래 주석처럼 @WebServlet 어노테이션을 이용하여 초기화 매개변수를 선언할 수도 있다.
 * 하지만 이 방식은 애플리케이션을 다시 컴파일 하지 않고서는 매개변수의 값을 변경할 수 없다는 단점이 있다.
 */
/*
@WebServlet(
        name="servletParameterServlet",
        urlPatterns = {"/servletParameters"},
        initParams = {
                @WebInitParam(name="servletParameter1", value = "servlet_1"),
                @WebInitParam(name="servletParameter2", value = "servletParameter2")
        }
)
*/
public class ServletParameterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext c1 = this.getServletContext();
        ServletConfig c2 = this.getServletConfig();

        PrintWriter writer = resp.getWriter();

        // 컨텍스트 초기화 매개변수 출력
        writer.append("contextParameter1: ").append(c1.getInitParameter("contextParameter1"))
                .append(", contextParameter2: ").append(c1.getInitParameter("contextParameter2"));

        // 서블릿 초기화 매개변수 출력
        writer.append(", servletParameter1: ").append(c2.getInitParameter("servletParameter1"))
                .append(", servletParameter2: ").append(c2.getInitParameter("servletParameter2"));

    }
}
