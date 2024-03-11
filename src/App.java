import http.Services.HttpMethod;
import http.Services.HttpReq;
import http.core.HttpRequest;
import http.core.HttpResponse;

public class App {
    public static void main(String[] args) throws Exception {
        byte[] arquivo = {};
        HttpRequest http = HttpReq.http();
        HttpResponse  response = http
            .Url("https://api.example.com")
            .endpoint("/users")
            .httpMethod(HttpMethod.POST)
            .multPartform()
            .formData(form -> form
                .addDocument("atquivo", arquivo)
                .addtext("info", "data")
                .addDocument("atquivo1", arquivo)
                .addtext("info1", "data"))      
            .send();

        if (response.isSuccessfull()) {
            System.out.println("Resposta recebida com sucesso:");
            System.out.println("Código de Status: " + response.getStatusCode());
            System.out.println("Corpo da Resposta: " + response.getBody());
        } else {
            System.out.println("Erro ao enviar a requisição.");
        }

    }
}
