package org.dmitrydunai;

import java.util.HashMap;
import java.util.Map;

public class TestHttpClientImpl {
    public static void main(String[] args) {

        HttpClientImpl httpClient = new HttpClientImpl();
        Response response;
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Connection", "text/html");

        response = httpClient.get("http://www.google.com", headers);
        System.out.println(response);
    }
}
