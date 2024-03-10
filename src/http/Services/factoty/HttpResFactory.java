package http.Services.factoty;

import java.util.Map;

import http.Services.HttpRes;
import http.core.HttpResponse;
import http.core.ResponseFactoy;

public class HttpResFactory extends ResponseFactoy{

    @Override
    public HttpResponse CreateResposne(int statusCode, Map<String, String> header, String body) {
        return new HttpRes(statusCode, header, body);
    }
    
}
