package Filter.LoggingAndCompressionFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;


// 응답 객체를 래핑하여 한번에 응답하도록 할 수 있게 하는 압축필터
public class CompressionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /*
         if문 : Accept-Encoding 요청 헤더에 gzip 인코딩이 포함되어 있는지 확인
         포함되지 않았다면 클라이언트가 gzip 압축 응답을 해제할 수 없다는 것이므로 꼭 확인 해야 함
         포함되어 있다면 Content-Encoding 헤더를 gzip 으로 설정 후
         ResponseWrapper 로 응답 객체를 래핑
         */
        if (((HttpServletRequest) request).getHeader("Accept-Encoding").contains("gzip")) {
            System.out.println("Encoding requested");
            ((HttpServletResponse) response).setHeader("Content-Encoding", "gzip");
            ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);

            try {
                // 응답 객체 매개변수로 래핑된 응답 객체 ResponseWrapper 를 전달
                chain.doFilter(request, wrapper);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    wrapper.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Encoding not requested");
            chain.doFilter(request, response);
        }
    }

    /*
     GZIPServletOutputStream 클래스를 이용해 클라이언트로 데이터를 전송하는
     PrintWriter, ServletOutputStream 등을 래핑함
     */
    private static class ResponseWrapper extends HttpServletResponseWrapper {

        private GZIPServletOutputStream outputStream; // GZIPOutputStream 의 내용이 기록된 ServletOutputStream
        private PrintWriter writer;

        // 생성자
        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public synchronized GZIPServletOutputStream getOutputStream() throws IOException {
            if (this.writer != null) {
                throw new IllegalStateException("getWriter() 가 이미 호출되었습니다.");
            }

            if (this.outputStream == null) {
                this.outputStream = new GZIPServletOutputStream(super.getOutputStream());
            }
            return this.outputStream;
        }


        @Override
        public synchronized PrintWriter getWriter() throws IOException {
            if (this.writer == null && this.outputStream != null) {
                throw new IllegalStateException("getOutputStream() 이 이미 호출되었습니다.");
            }

            if (this.writer == null) {
                this.outputStream = new GZIPServletOutputStream(super.getOutputStream());
                this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, this.getCharacterEncoding()));
            }

            return this.writer;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (this.writer != null) {
                this.writer.flush();
            } else if (this.outputStream != null) {
                this.outputStream.flush();
            }
            super.flushBuffer();
        }

        // 응답을 압축하기 전에는 콘텐츠 길이를 알 수 없으므로 콘텐츠 길이 설정 못하게 오버라이딩함
        @Override
        public void setContentLength(int len) {
        }

        @Override
        public void setContentLengthLong(long len) {
        }

        @Override
        public void setHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setHeader(name, value);
            }
        }

        @Override
        public void addHeader(String name, String value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setHeader(name, value);
            }
        }

        @Override
        public void setIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setIntHeader(name, value);
            }
        }

        @Override
        public void addIntHeader(String name, int value) {
            if (!"content-length".equalsIgnoreCase(name)) {
                super.setIntHeader(name, value);
            }
        }

        public void finish() throws IOException {
            if (this.writer != null) {
                this.writer.close();
            } else if (this.outputStream != null) {
                this.outputStream.finish();
            }
        }
    }


    /*
    응답에 기록하는 내용은 GZIPOutputStream 에 먼저 기록되고
    요청이 완료되면 압축하여 압축된 응답이 래핑된 ServletOutputStream 에 기록
     */
    private static class GZIPServletOutputStream extends ServletOutputStream {

        private final ServletOutputStream servletOutputStream;
        private final GZIPOutputStream gzipStream;

        // 생성자
        public GZIPServletOutputStream(ServletOutputStream servletOutputStream) throws IOException {
            this.servletOutputStream = servletOutputStream;
            this.gzipStream = new GZIPOutputStream(servletOutputStream);
        }

        @Override
        public boolean isReady() {
            return this.servletOutputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.servletOutputStream.setWriteListener(writeListener);
        }

        @Override
        public void write(int b) throws IOException {
            this.gzipStream.write(b);
        }

        @Override
        public void close() throws IOException {
            this.gzipStream.close();
        }

        @Override
        public void flush() throws IOException {
            this.gzipStream.flush();
        }

        public void finish() throws IOException {
            this.gzipStream.finish();
        }
    }
}
