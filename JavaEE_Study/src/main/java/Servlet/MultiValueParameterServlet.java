package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * [ 여러 개의 매개변수를 전송하는 Servlet 요청에 응답하는 방법 ]
 *
 *  - doGet() 메소드에서 체크박스 형태로 input 을 선택한 후 POST 방식으로 전송
 *  - doPost() 에서 요청을 수신한 후 getParameterValues() 메서드로 수신한 매개변수를 배열에 저장
 *  - 수신한 매개변수를 조작 및 활용
 *
 */
@WebServlet(
        name ="multiValueParameterServlet",
        urlPatterns = {"/checkboxes"}
)

public class MultiValueParameterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>Hello User Application</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n")
                .append("        <form action=\"checkboxes\" method=\"POST\">\r\n")
                .append("Select the fruits you like to eat:<br/>\r\n")
                .append("<input type=\"checkbox\" name=\"fruit\" value=\"Banana\"/>")
                .append(" Banana<br/>\r\n")
                .append("<input type=\"checkbox\" name=\"fruit\" value=\"Apple\"/>")
                .append(" Apple<br/>\r\n")
                .append("<input type=\"checkbox\" name=\"fruit\" value=\"Orange\"/>")
                .append(" Orange<br/>\r\n")
                .append("<input type=\"checkbox\" name=\"fruit\" value=\"Guava\"/>")
                .append(" Guava<br/>\r\n")
                .append("<input type=\"checkbox\" name=\"fruit\" value=\"Kiwi\"/>")
                .append(" Kiwi<br/>\r\n")
                .append("<input type=\"submit\" value=\"Submit\"/>\r\n")
                .append("        </form>")
                .append("    </body>\r\n")
                .append("</html>\r\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 이름이 fruit 로 설정된 모든 Parameter 값을 배열로 리턴
        String[] fruits = req.getParameterValues("fruit");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>Hello User Application</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n")
                .append("        <h2>Your Selections</h2>\r\n");

        if(fruits==null){
            writer.append("    You did not select any fruits. \r\n");
        }else{
            writer.append("    <ul>\r\n");
            for(int i=0; i<fruits.length; i++){
                writer.append("    <li>").append(fruits[i]).append("</li>\r\n");
            }
            writer.append("    </ul>\r\n");
        }

        writer.append("    </body>\r\n").append("</html>\r\n");
    }
}
