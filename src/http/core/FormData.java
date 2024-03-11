package http.core;

public interface FormData {
    FormData addDocument(String fileName, byte[] bytes);
    FormData addtext(String field, String data);
    
}
