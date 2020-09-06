package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * [ 단일 매개변수를 전송하는 요청에 대응하는 방법 ]
 *
 *  - 유저의 이름을 입력받고 간단한 인삿말을 출력하는 웹페이지 제작
 *  - 처음 접속할 때는 입력받은 유저의 이름이 없으므로 임시로 Guest 라는 이름을 부여
 *  - 이후 유저의 이름을 입력받은 후 유저의 이름을 포함한 인삿말을 출력
 *  - POST 방식으로 요청을 전송하게 되는데
 *  - doPost() 에서는 이 요청을 그대로 doGet() 으로 위임함
 *
 */


// 서블릿 선언 및 매핑을 간결하게 할 수 있게 해주는 @WebServlet 어노테이션
@WebServlet(
        name = "ServletTest",
        urlPatterns = {"/test", "/test2", "/test3"},
        loadOnStartup = 1
)
public class ServletTest extends HttpServlet {

    private static final String DEFAULT_USER = "Guest";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html"); // 콘텐츠 타입 설정
        resp.setCharacterEncoding("UTF-8"); // 문자 인코딩 UTF-8

        // 서블릿이 GET 요청에 응답하여 "Hello, Java EE World!" 라는 문자열을 반환한다.
        PrintWriter writer = resp.getWriter(); // 문자형식으로 작성할 것이기 때문에 getWriter() 사용

        writer.append("Welcome, Java EE World!\r\n");

        String user = req.getParameter("user");
        if (user == null) {
            user = ServletTest.DEFAULT_USER;
        }

        // 간단한 인삿말 출력과, 사용자 이름을 입력받을 수 있는 폼
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>Hello User Application</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n")
                .append("        Hello, ").append(user).append("!<br/><br/>\r\n")
                .append("        <form action=\"test\" method=\"POST\">\r\n")
                .append("            Enter your name:<br/>\r\n")
                .append("            <input type=\"text\" name=\"user\"/><br/>\r\n")
                .append("            <input type=\"submit\" value=\"Submit\"/>\r\n")
                .append("        </form>\r\n")
                .append("    </body>\r\n")
                .append("</html>\r\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // POST 형식으로 요청을 수신했을 때, doGet() 메소드에게 위임
        this.doGet(req, resp);
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


