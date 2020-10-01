package http;

public class RequestLine {
    private String method;
    private String URL;
    private String httpVersion;
    private String queryString;

    public RequestLine(String firstLine){
        if (firstLine == null) {
            return;
        }
        /* split[0] : Http Method, split[1] : URL, split[2] : Http Version
        * 단, GET 요청의 경우 URL 에 QueryString 이 포함되므로 이를 고려해야 함 */
        String[] split = firstLine.split(" ");

        this.method = split[0];

        String[] urlSplit = split[1].split("\\?");
        this.URL = urlSplit[0];
        if(urlSplit.length==2){
            this.queryString = urlSplit[1];
        }

        this.httpVersion = split[2];
    }

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return URL;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getQueryString() {
        return queryString;
    }
}
