package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private Map<String, String> headers = new HashMap<>();

    void add(String line){
        String[] headerSplit = line.split(":");
        if(headerSplit.length>=2){
            headers.put(headerSplit[0], headerSplit[1].trim());
        }
    }

    public int getContentLength(){
        if(headers.get("Content-Length")!=null){
            return Integer.parseInt(headers.get("Content-Length"));
        }
        return 0;
    }

    public String getHeader(String field){
        return headers.get(field);
    }

}
