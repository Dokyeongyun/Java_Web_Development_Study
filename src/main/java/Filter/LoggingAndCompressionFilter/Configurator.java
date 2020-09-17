package Filter.LoggingAndCompressionFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


// 프로그래밍 방식으로 필터 선언 및 매핑하기
@WebListener
public class Configurator implements ServletContextListener {

    // contextInitialized() 메서드 내에서 선언 및 매핑해야함
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        FilterRegistration.Dynamic registration =
                context.addFilter("requestLogFilter", RequestLogFilter.class);

        // dispatcherTypes 을 null 로 하면 request
        registration.addMappingForUrlPatterns(null, false, "/*");


        registration = context.addFilter("compressionFilter", CompressionFilter.class);
        registration.setAsyncSupported(true);
        registration.addMappingForUrlPatterns(null, false, "/*");

    }
}
