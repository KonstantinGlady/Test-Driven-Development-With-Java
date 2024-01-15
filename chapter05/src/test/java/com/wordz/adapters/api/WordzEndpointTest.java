package com.wordz.adapters.api;

import com.google.gson.Gson;
import com.vtence.molecule.http.HttpStatus;
import com.wordz.domain.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;


public class WordzEndpointTest {

    private static final Player PLAYER = new Player("player1");

    @Test
    void startGame() throws IOException, InterruptedException {

        var endpoint = new WordzEndpoint("localhost", 8080);

        var httpClient = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/start"))
                .POST(HttpRequest.BodyPublishers
                        .ofString(new Gson().toJson(PLAYER)))
                .build();
        HttpResponse res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.NO_CONTENT.code);
    }

}
