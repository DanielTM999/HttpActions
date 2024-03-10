package http.core;

import java.util.Map;

public abstract class ResponseFactoy {
    
    public abstract HttpResponse CreateResposne(int statusCode, Map<String, String> header, String body);

}
