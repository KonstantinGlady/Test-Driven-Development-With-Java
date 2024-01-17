package com.wordz.adapters.api;

import com.google.gson.Gson;
import com.vtence.molecule.http.HttpStatus;
import com.wordz.domain.GuessResult;
import com.wordz.domain.Player;
import com.wordz.domain.Score;
import com.wordz.domain.Wordz;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WordzEndpointTest {

    //todo insert into word values (1, 'ARISE'), (2, 'SHINE'), (3, 'LIGHT'), (4, 'SLEEP'), (5, 'BEARS'), (6, 'GREET'), (7, 'GRATE');
    private final Player PLAYER = new Player("player1");
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private Wordz mockWordz;
    private WordzEndpoint endpoint;

    @BeforeAll
    void setUp() {
        mockWordz = mock(Wordz.class);
        endpoint = new WordzEndpoint(mockWordz, "localhost", 8080);
    }

    @Test
    void startGame() throws Exception {
        when(mockWordz.newGame(PLAYER))
                .thenReturn(true);

        var req = requestBuilder("start")
                .POST(asJsonBody(PLAYER))
                .build();

        var res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.NO_CONTENT.code);
    }

    @Test
    void rejectsRestart() throws Exception {
        when(mockWordz.newGame(PLAYER))
                .thenReturn(false);

        var req = requestBuilder("start")
                .POST(asJsonBody(PLAYER))
                .build();

        var res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.CONFLICT.code);
    }

    @Test
    void rejectsMalformedRequest() throws Exception {
        var req = requestBuilder("start")
                .POST(ofString("malformed"))
                .build();

        var res = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        assertThat(res).hasStatusCode(HttpStatus.BAD_REQUEST.code);
    }

    @Test
    void partiallyCorrectGuess() throws Exception {

        var score = new Score("-U--G");
        score.assess("GUESS");

        var result = new GuessResult(score, false, false);
        when(mockWordz.assess(PLAYER, "GUESS"))
                .thenReturn(result);

        var guessRequest = new GuessRequest(PLAYER, "GUESS");
        var body = new Gson().toJson(guessRequest);

        var req = requestBuilder("guess")
                .POST(ofString(body))
                .build();

        var res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        var response = new Gson().fromJson(res.body(), GuessHttpResponse.class);

        //key to letters in score:
        // C correct P part correct X incorrect
        Assertions.assertThat(response.score()).isEqualTo("PCXXX");
        Assertions.assertThat(response.isGameOver()).isFalse();

    }

    @Test
    void reportsGameOver() throws Exception {

        var guessResult = new GuessResult(new Score("-----"), true, false);
        when(mockWordz.assess(PLAYER, "GUESS"))
                .thenReturn(guessResult);

        var request = new GuessRequest(PLAYER, "GUESS");
        var body = new Gson().toJson(request);
        var req = requestBuilder("guess")
                .POST(ofString(body))
                .build();

        var res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        var response = new Gson().fromJson(res.body(), GuessHttpResponse.class);

        Assertions.assertThat(response.isGameOver()).isTrue();
    }

    @NotNull
    private static HttpRequest.BodyPublisher asJsonBody(Object source) {
        return ofString(new Gson().toJson(source));
    }

    private static HttpRequest.Builder requestBuilder(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/" + path));
    }

}
