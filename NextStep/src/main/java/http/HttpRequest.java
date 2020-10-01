package http;


import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/* 리팩토링 1단계 : 요청 데이터를 처리하는 로직을 별도의 클래스로 분리
 *  - 클라이언트 요청 데이터를 담고 있는 InputStream을 생성자로 받아,
 *    HTTP 메소드, URL, 헤더, 본문을 분리하는 작업을 수행
 *  - 헤더는 Map<String, String>에 저장해 관리하고
 *    getHeader("field name") 메서드를 통해 접근가능하도록 구현
 *  - GET, POST 메서드에 따라 전달되는 인자를 Map<String, String>에 저장해
 *    관리하고 getParameter("params name") 메서드를 통해 접근가능하도록 구현 */

public class HttpRequest {
    RequestLine requestLine;
    HttpHeaders headers = new HttpHeaders();
    RequestParams requestParams = new RequestParams();;

    /* InputStream 을 생성자로 받아 요청을 분리 */
    public HttpRequest(InputStream in) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        /* 요청 데이터의 첫 줄 => Http Method, URL, Http 버전 순서 */
        String line = br.readLine();
        requestLine = new RequestLine(line);

        /* HTTP 헤더를 읽어 Map에 저장 */
        while (!line.equals("")) {
            line = br.readLine();
            headers.add(line);
        }

        /* HTTP 본문을 읽어 저장*/
        requestParams.addBody(IOUtils.readData(br, headers.getContentLength()));

        /* QueryString을 통해 전달되는 인자를 Map에 저장 */
        requestParams.addQueryString(requestLine.getQueryString());

    }

    public String getHeaders(String field) {
        return headers.getHeader(field);
    }

    public String getRequestParams(String name) {
        return requestParams.getParameter(name);
    }

    public String getURL(){
        return requestLine.getURL();
    }

    public String getMethod(){
        return requestLine.getMethod();
    }

    public String getHttpVersion(){
        return requestLine.getHttpVersion();
    }
}
