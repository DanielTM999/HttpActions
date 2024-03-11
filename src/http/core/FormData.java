package http.core;

import java.util.Map;

public interface FormData {
    FormData addDocument(String fileName, byte[] bytes);
    FormData addtext(String field, String data);
    Map<String, byte[]> getFileData();
    Map<String, String> getTextData();
}
