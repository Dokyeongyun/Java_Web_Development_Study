package Session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener, HttpSessionAttributeListener {


    private final SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        System.out.println(this.date() + ": 세션 ID " + oldSessionId + " 이 " + event.getSession().getId()+ "로 변경되었습니다");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(this.date() + ": 세션 " + se.getSession().getId() + "이 생성되었습니다.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println(this.date() + ": 세션 " + se.getSession().getId() + "이 종료되었습니다.");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println(this.date() + ": 세션 " + event.getName() + "의 속성이 추가되었습니다.");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println(this.date() + ": 세션 " + event.getName() + "의 속성이 제거되었습니다.");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println(this.date() + ": 세션 " + event.getName() + "의 속성이 변경되었습니다.");
    }



    private String date()
    {
        return this.formatter.format(new Date());
    }
}