package http.Services;


import java.util.Map;

import http.core.HttpResponse;

public class HttpRes extends HttpResponse{

    public HttpRes(int statusCode, Map<String, String> header, String body) {
        super(statusCode, header, body);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public boolean isSuccessfull() {
        if(statusCode == 200){
            return true;
        }
        return false;
    }

    @Override
    public String getHeader(String name) {
        return header.get(name);
    }

    @Override
    public Map<String, String> getAllHeaders() {
        return header;
    }
    
}
