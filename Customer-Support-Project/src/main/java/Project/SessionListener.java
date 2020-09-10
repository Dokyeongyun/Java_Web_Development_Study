package Project;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


// 세션리스너를 어노테이션을 이용해 설정한다.
// 어노테이션외에도 배포설명자에서 <listener> 태그로 선언 할 수도있다.
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {
    private final SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");


    // changeSessionID() 메서드를 통해 세션 ID가 변경될 때마다 호출됨
    @Override
    public void sessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        System.out.println(this.date() + ": 세션 ID " + oldSessionId + " 이 " + event.getSession().getId()+ "로 변경되었습니다");
        SessionRegistry.updateSessionId(event.getSession(), oldSessionId);
    }


    // 세션 생성시 호출
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println(this.date() + ": 세션 " + se.getSession().getId() + "이 생성되었습니다.");
        SessionRegistry.addSession(se.getSession());
    }

    // 세션 종료시 호출
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println(this.date() + ": Session " + se.getSession().getId() + "이 종료되었습니다.");
        SessionRegistry.removeSession(se.getSession());
    }

    private String date()
    {
        return this.formatter.format(new Date());
    }

}
