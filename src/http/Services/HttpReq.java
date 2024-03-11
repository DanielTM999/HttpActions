package http.Services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.Services.factoty.HttpResFactory;
import http.core.FormConfig;
import http.core.FormData;
import http.core.HttpRequest;
import http.core.HttpResponse;
import http.core.ResponseFactoy;

public class HttpReq implements HttpRequest{
    private String url;
    private String endpoint;
    private HttpMethod method;
    private Map<String,String> headers;
    private String body;
    private boolean json;
    private boolean multPartform;
    private FormData form;
    private ResponseFactoy responseFactoy;

    public HttpReq(){
        this.responseFactoy = new HttpResFactory();
    }

    public HttpReq(ResponseFactoy responseFactoy){
        this.responseFactoy = responseFactoy;
    }

    public static HttpRequest http(){
        return new HttpReq();
    }

    @Override
    public HttpRequest Url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public HttpRequest endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public HttpRequest httpMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpRequest headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public HttpRequest body(String body) {
        this.body = body;
        return this;
    }

    @Override
    public HttpRequest json() {
        this.json = true;
        return this;
    }

    @Override
    public HttpRequest multPartform(){
        this.multPartform = true;
        return this;
    }

    @Override
    public HttpRequest formData(FormConfig formConfig){
        form = new Form();
        formConfig.form(form);
        return this;
    }

    @Override
    public HttpResponse send() throws Exception{
        url = prepareUrl(endpoint);
        validate();
        HttpURLConnection connection = createConnection(url);
        connection.setRequestMethod(method.name());
        connection = addHeader(connection);
        connection = ConfigureBody(connection);
        int responseCode = connection.getResponseCode();
        String body = getBody(connection);
        Map<String, String> hMap = getHeaderMap(connection);

        return responseFactoy.CreateResposne(responseCode, hMap, body);
    }

    private synchronized  Map<String, String> getHeaderMap(HttpURLConnection connection){
        Map<String, String> hMap = new HashMap<>();
        Map<String, List<String>> headersBase = connection.getHeaderFields();

        for (Map.Entry<String, List<String>> entry : headersBase.entrySet()){
            String key = entry.getKey();
            List<String> values = entry.getValue();
            if (key != null) {
                for (String value : values) {
                    hMap.put(key, value);
                }
            }
        }
        return hMap;
    }

    private synchronized  String getBody(HttpURLConnection connection) throws Exception{
        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while((line = reader.readLine()) != null){
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    private HttpURLConnection ConfigureBody(HttpURLConnection connection) throws Exception{
        if(body != null && !body.isEmpty() && method != HttpMethod.GET && json){
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(body);
            outputStream.flush();
            outputStream.close();
        }else if(multPartform && method != HttpMethod.GET && form != null){
            String boundary = "*****";
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            if(form != null && !form.getTextData().isEmpty()){
                for(Map.Entry<String, String> entry : form.getTextData().entrySet()){
                    outputStream.writeBytes("--" + boundary + "\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                    outputStream.writeBytes(entry.getValue() + "\r\n");
                }
            }

            if(form != null && !form.getFileData().isEmpty()){
                for(Map.Entry<String, byte[]> entry : form.getFileData().entrySet()){
                    outputStream.writeBytes("--" + boundary + "\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getKey() + "\"\r\n");
                    outputStream.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
                    outputStream.write(entry.getValue());
                    outputStream.writeBytes("\r\n");
                }
            }

            outputStream.writeBytes("--" + boundary + "--\r\n");
            outputStream.flush();
            outputStream.close();
        }

        return connection;
    }

    private HttpURLConnection addHeader(HttpURLConnection connection){
        if(headers != null){
            for(Map.Entry<String, String> entry : this.headers.entrySet()){
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return connection;
    } 

    private HttpURLConnection createConnection(String url) throws Exception{
        URL reqUrl = new URI(url).toURL();
        HttpURLConnection connection = (HttpURLConnection) reqUrl.openConnection();
        return connection;
    }

    private void validate() throws Exception{
        if(url == null){
            throw new Exception("Url is null");
        }else if(method == null){
            throw new Exception("method is null");
        }

    }

    private String prepareUrl(String endpoint){
        try {
            if(endpoint == null){
                return url;
            }else{
                
                char last = url.charAt(url.length() -1);
                char first = endpoint.charAt(0);
    
                if(last == '/'){
                    if(first == '/') endpoint = endpoint.substring(1);
                }else{
                    url += "/";
                    if(first == '/') endpoint = endpoint.substring(1);
                }
            }
            return (url + endpoint);
        } catch (Exception e) {
            return null;
        }
    }
  
    @SuppressWarnings("unused")
    private void awaitAll(Thread... Threads){
        for (Thread thread : Threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
