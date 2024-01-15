package com.wordz.adapters.api;

import com.vtence.molecule.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;


public class WordzEndpointTest {

    @Test
    void startGame() throws IOException, InterruptedException {

        var httpClient = HttpClient.newHttpClient();
        HttpRequest req = null;
        HttpResponse res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.NO_CONTENT.code);
    }

}
