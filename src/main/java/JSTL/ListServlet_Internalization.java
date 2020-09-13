package JSTL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.util.Collections;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

@WebServlet(
        name = "listServlet_Internalization",
        urlPatterns = {"/list_internalization"}
)
public class ListServlet_Internalization extends HttpServlet {
    private static final SortedSet<Contact> contacts = new TreeSet<>();

    static {
        contacts.add(new Contact("KyeongYun", "Do", "010-5544-9999", "인하로 100",
                MonthDay.of(Month.JANUARY, 15),
                Instant.parse("2020-02-01T05:30:23Z")
        ));
        contacts.add(new Contact("JunSu", "Kim", "010-1111-4444", "인하 1길 154",
                null, Instant.parse("2019-10-15T12:39:24Z")
        ));
        contacts.add(new Contact("YeongJin", "Lee", "010-8894-8888", "인하 97길 550",
                MonthDay.of(Month.APRIL, 10),
                Instant.parse("2018-04-04T02:10:50Z")
        ));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                // 국제화 및 지역화를 위한 언어 설정
        String language = req.getParameter("language");
        if ("english".equalsIgnoreCase(language)) {
            Config.set(req, Config.FMT_LOCALE, Locale.ENGLISH);
        } else if ("french".equalsIgnoreCase(language)) {
            Config.set(req, Config.FMT_LOCALE, Locale.FRANCE);
        }


        if (req.getParameter("empty") != null) {
            req.setAttribute("contacts", Collections.<Contact>emptySet());
        } else {
            req.setAttribute("contacts", contacts);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/view/list_internalization.jsp").forward(req, resp);
    }
}


