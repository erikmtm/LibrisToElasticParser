/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class HttpClient {

    public static HttpResponse<String> httpGet(String uri) throws IOException, InterruptedException {

        java.net.http.HttpClient client = newHttpClient();
        HttpRequest request = newBuilder().uri(URI.create(uri)).build();
        HttpResponse<String> response = client.send(request, ofString());
        return response;
    }

}
