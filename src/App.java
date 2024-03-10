import http.Services.HttpMethod;
import http.Services.HttpReq;
import http.core.HttpRequest;
import http.core.HttpResponse;

public class App {
    public static void main(String[] args) throws Exception {
        HttpRequest http = HttpReq.http();
        HttpResponse  response = http
            .Url("https://google.com")
            .httpMethod(HttpMethod.GET)
            .send();

        System.out.println(response.getHeader("Server"));

    }
}
