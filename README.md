# http-core

A biblioteca `http-core` oferece uma interface simplificada para realizar requisições HTTP em Java, incluindo suporte para envio de dados de formulário multipartes.

## Como Usar

Para utilizar a biblioteca, siga os passos abaixo:

1. **Configuração**: Instale a biblioteca em seu projeto e importe as classes necessárias.

2. **Criação de Requisição**: Utilize a interface `HttpRequest` para configurar sua requisição HTTP. Você pode definir a URL, o método HTTP, os cabeçalhos, o corpo da requisição e até mesmo enviar dados de formulário multipartes.

3. **Envio da Requisição**: Chame o método `send()` para enviar a requisição e receber a resposta na forma de uma instância de `HttpResponse`.

4. **Processamento da Resposta**: A classe `HttpResponse` fornece métodos para acessar o código de status, o corpo da resposta e os cabeçalhos retornados pelo servidor.

## API

### HttpRequest

A interface `HttpRequest` define métodos para configurar e enviar requisições HTTP.

#### Métodos

- `Url(String url)`: Define a URL do endpoint para enviar a requisição.
- `endpoint(String endpoint)`: Define o endpoint específico para a requisição, que será anexado à URL base.
- `httpMethod(HttpMethod method)`: Define o método HTTP da requisição (GET, POST, PUT, DELETE, etc.).
- `headers(Map<String,String> headers)`: Define os cabeçalhos da requisição.
- `body(String body)`: Define o corpo da requisição.
- `formData(FormConfig formConfig)`: Define os dados do formulário a serem enviados em uma requisição multipartes.
- `json()`: Indica que o corpo da requisição está no formato JSON.
- `multPartform()`: Indica que a requisição deve ser enviada como multipart/form-data.
- `send()`: Envia a requisição e retorna uma instância de `HttpResponse`.

### FormData

A interface `FormData` define métodos para adicionar dados de formulário (texto e documentos) a serem enviados em uma requisição multipartes.

#### Métodos

- `addDocument(String fileName, byte[] bytes)`: Adiciona um documento (arquivo) ao formulário.
- `addText(String field, String data)`: Adiciona um campo de texto ao formulário.
- `getFileData()`: Retorna um mapa dos documentos adicionados ao formulário.
- `getTextData()`: Retorna um mapa dos campos de texto adicionados ao formulário.

### HttpResponse

A classe abstrata `HttpResponse` representa a resposta recebida após o envio de uma requisição HTTP.

#### Métodos

- `getStatusCode()`: Retorna o código de status HTTP da resposta.
- `getBody()`: Retorna o corpo da resposta.
- `isSuccessfull()`: Retorna verdadeiro se a resposta indicar sucesso (código de status 2xx).
- `getHeader(String name)`: Retorna o valor do cabeçalho especificado.
- `getAllHeaders()`: Retorna todos os cabeçalhos da resposta em um mapa.

## Exemplos

Abaixo está um exemplo básico de como utilizar a biblioteca para enviar uma requisição HTTP GET:

```java
HttpRequest request = HttpReq.http()
    .Url("https://api.example.com")
    ..endpoint("/users")
    .httpMethod(HttpMethod.GET)
    .send();

HttpResponse response = request.send();

if (response.isSuccessfull()) {
    System.out.println("Resposta recebida com sucesso:");
    System.out.println("Código de Status: " + response.getStatusCode());
    System.out.println("Corpo da Resposta: " + response.getBody());
} else {
    System.out.println("Erro ao enviar a requisição.");
}
```

```java
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
```
