package CustomTag;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@WebServlet(
        name = "indexServlet",
        urlPatterns = {"/index"}
)
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String view = "hello";
        if(req.getParameter("dates") != null) { // 날짜 서식지정 커스텀 태그 사용하는 테스트
            req.setAttribute("date", new Date());
            req.setAttribute("calendar", Calendar.getInstance());
            req.setAttribute("instant", Instant.now());
            view = "dates";
        } else if(req.getParameter("text") != null) { // abbreviate 메서드를 EL 함수로 만들어 손쉽게 사용하는 테스트
            req.setAttribute("shortText", "This is short text.");
            req.setAttribute(
                    "longText",
                    "This is really long text that should get cut off at 32 chars."
            );
            view = "text";
        }
        req.getRequestDispatcher("/WEB-INF/jsp/view/" + view + ".jsp").forward(req, resp);
    }
}
