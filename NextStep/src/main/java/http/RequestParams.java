package http;

import util.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    private Map<String, String> params = new HashMap<>();

    public void addQueryString(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return;
        }

        params.putAll(HttpRequestUtils.parseQueryString(queryString));
    }

    public void addBody(String body){
        addQueryString(body);
    }

    public String getParameter(String paramsName){
        return params.get(paramsName);
    }
}
