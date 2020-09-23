package Servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 컨텍스트 초기화 매개변수를 사용하는 방법
 *
 *  - 배포설명자 web.xml 파일 내에 <context-param> 태그 작성
 *  - getServletContext() 메서드를 이용해 ServletContext 객체 생성
 *  - getInitParameter() 메서드를 이용해 매개변수 값 가져오기
 *
 */

@WebServlet(
        name= "contextParameterServlet",
        urlPatterns = {"/contextParameters"}
)
public class ContextParameterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext c = this.getServletContext();
        PrintWriter writer =resp.getWriter();

        // 배포설명자 파일 web.xml 에 작성한 <context-param> 을 가져오는 방법
        writer.append("contextParameter1: ").append(c.getInitParameter("contextParameter1"))
                .append(", contextParameter2: ").append(c.getInitParameter("contextParameter2"));
    }
}
