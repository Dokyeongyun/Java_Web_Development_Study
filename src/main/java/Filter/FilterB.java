package Filter;


import javax.servlet.*;
import java.io.IOException;

public class FilterB implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterB.doFilter() 호출");
        chain.doFilter(request, response);
        System.out.println("FilterB.doFilter() 종료");    }

    @Override
    public void destroy() {

    }
}
