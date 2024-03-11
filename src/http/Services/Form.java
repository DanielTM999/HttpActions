package http.Services;

import java.util.HashMap;
import java.util.Map;

import http.core.FormData;

public class Form implements FormData{
    private Map<String, byte[]> files;
    private Map<String, String> text;

    public Form(){
        files = new HashMap<>();
        text = new HashMap<>();
    }

    @Override
    public FormData addDocument(String fileName, byte[] bytes) {
        files.put(fileName, bytes);
        return this;
    }

    @Override
    public FormData addtext(String field, String data) {
        text.put(field, data);
        return this;
    }

    @Override
    public Map<String, byte[]> getFileData() {
        return files;
    }

    @Override
    public Map<String, String> getTextData() {
        return text;
    }

    
}
