package Project;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


// [고객 지원 어플리케이션 v7 : 필터를 이용해 인증확인(로그인 상태) 절차를 간소화 ]
@WebListener
public class Configurator implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        FilterRegistration.Dynamic registration
                = context.addFilter("authenticationFilter", AuthenticationFilter.class);

        registration.setAsyncSupported(true);
        // 로그인 해야볼 수 있는 tickets 페이지와 sessions 페이지에 접근 시 인증확인!
        registration.addMappingForUrlPatterns(null, false, "/tickets", "/sessions");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
