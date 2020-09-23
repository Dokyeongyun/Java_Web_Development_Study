package Project;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(
        name = "loginServlet",
        urlPatterns = {"/login"}
)
/**
 * 고객 지원 어플리케이션 V3 부터 적용
 */
public class LoginServlet extends HttpServlet {
    // 로그인 시 사용될 사용자 데이터베이스를 정의
    private static final Map<String, String> userDatabase = new Hashtable<>();

    static {
        userDatabase.put("Kim", "red");
        userDatabase.put("Do", "blue");
        userDatabase.put("Song", "white");
        userDatabase.put("Lim", "yellow");
        userDatabase.put("Lee", "green");
    }


    /**
     * 사용자가 로그인한 경우 티켓 페이지로 리디렉션
     * 사용자가 로그인하지 않은 경우 로그인 페이지로 요청 전달
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if(req.getParameter("logout") !=null){
            session.invalidate();
            resp.sendRedirect("login");
            return;
        }else if (session.getAttribute("username") != null) {
            resp.sendRedirect("tickets");
            return;
        }

        req.setAttribute("loginFailed", false);
        req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 사용자가 로그인한 경우 티켓 페이지로 리디렉션
        if (session.getAttribute("username") != null) {
            resp.sendRedirect("tickets");
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 사용자이름, 비밀번호가 null 이거나, 데이터베이스에 사용자이름이 없거나 비밀번호가 틀린경우 다시 로그인 페이지로 요청 전달
        if(username==null|| password==null
                || !LoginServlet.userDatabase.containsKey(username)
                || !password.equals(LoginServlet.userDatabase.get(username))){
            req.setAttribute("loginFailed",true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);
        }else{ // 로그인 정보가 정확한 경우 보안을 위해 세션 ID를 변경한후 티켓 페이지로 리디렉션
            session.setAttribute("username", username);
            req.changeSessionId(); // 세션 마이그레이션을 통한 세션 고정 공격 예방
            resp.sendRedirect("tickets");
        }

    }
}
