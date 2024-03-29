package http.core;

import java.util.Map;

import http.Services.HttpMethod;

public interface HttpRequest {
    HttpRequest Url(String url);
    HttpRequest endpoint(String endpoint);
    HttpRequest httpMethod(HttpMethod method);
    HttpRequest headers(Map<String,String> headers);
    HttpRequest body(String body);
    HttpRequest formData(FormConfig formConfig);
    HttpRequest json();
    HttpRequest multPartform();
    HttpResponse send() throws Exception;
}
