package com.wordz.adapters.api;

import com.google.gson.Gson;
import com.vtence.molecule.http.HttpStatus;
import com.wordz.domain.Player;
import com.wordz.domain.Wordz;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WordzEndpointTest {

    private static final Player PLAYER = new Player("player1");
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Mock
    private Wordz mockWordz;
    private WordzEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new WordzEndpoint(mockWordz, "localhost", 8080);
    }

    @Test
    void startGame() throws IOException, InterruptedException {

        when(mockWordz.newGame(PLAYER))
                .thenReturn(true);

        HttpRequest req = requestBuilder("start")
                .POST(asJsonBody(PLAYER))
                .build();
        HttpResponse res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.NO_CONTENT.code);
    }

    @NotNull
    private static HttpRequest.BodyPublisher asJsonBody(Object source) {
        return HttpRequest.BodyPublishers
                .ofString(new Gson().toJson(source));
    }

    private static HttpRequest.Builder requestBuilder(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/" + path));
    }

}
