package http.core;

import java.util.Map;

public abstract class HttpResponse {
    protected int statusCode;
    protected Map<String, String> header;
    protected String body;

    protected HttpResponse(int statusCode, Map<String, String> header, String body){
        this.statusCode = statusCode;
        this.header = header;
        this.body = body;
    }

    public abstract int getStatusCode();
    public abstract String getBody();
    public abstract boolean isSuccessfull();
    public abstract String getHeader(String name);
    public abstract Map<String, String> getAllHeaders();
}
