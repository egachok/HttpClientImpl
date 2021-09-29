package org.dmitrydunai;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@AllArgsConstructor
public class HttpClientImpl implements HttpClient{

    @Override
    public Response get(String url, Map<String, String> headers) {
        return connection("get", url, headers, null);
    }

    @Override
    public Response post(String url, Map<String, String> headers, byte[] payload) {
        return connection("post", url, headers, payload);
    }

    @Override
    public Response post(String url, Map<String, String> headers, String payload) {
        return connection("post", url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Response put(String url, Map<String, String> headers, byte[] payload) {
        return connection("put", url, headers, payload);
    }

    @Override
    public Response put(String url, Map<String, String> headers, String payload) {
        return connection("put", url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Response delete(String url, Map<String, String> headers, byte[] payload) {
        return connection("delete", url, headers, payload);
    }

    @Override
    public Response delete(String url, Map<String, String> headers, String payload) {
        return connection("delete", url, headers, payload.getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, String> headerTransformer(Map<String, List<String>> headerFields) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : headerFields.entrySet()) {
            map.put(entry.getKey(), entry.getValue().get(0));
        }
        return map;
    }
    private Response connection(String request, String url, Map<String, String> headers, byte[] payload) {
        Response response = new Response();
        try {
            URL testUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) testUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request.toUpperCase(Locale.ROOT));
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            if (payload != null) {
                connection.getOutputStream().write(payload);
                connection.getOutputStream().flush();
            }

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            response.setPayload(stringBuilder.toString());
            response.setHeaders(headerTransformer(connection.getHeaderFields()));
            response.setStatusCode(connection.getResponseCode());
            response.setStatusText(connection.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}