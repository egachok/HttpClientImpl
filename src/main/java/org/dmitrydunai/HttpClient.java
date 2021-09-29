package org.dmitrydunai;

import java.util.Map;

public interface HttpClient {
    Response get(String url, Map<String,String> headers);
    Response post(String url, Map<String,String> headers, byte[] payload);
    Response post(String url, Map<String,String> headers, String payload);
    Response put(String url, Map<String,String> headers, byte[] payload);
    Response put(String url, Map<String,String> headers, String payload);
    Response delete(String url, Map<String,String> headers, byte[] payload);
    Response delete(String url, Map<String,String> headers, String payload);
}