package com.wordz.adapters.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.routing.Routes;
import com.wordz.domain.GuessResult;
import com.wordz.domain.Player;
import com.wordz.domain.Wordz;

import java.io.IOException;

import static com.vtence.molecule.http.HttpStatus.*;

public class WordzEndpoint {

    private final Wordz wordz;

    public WordzEndpoint(Wordz wordz, String host, int port) {
        this.wordz = wordz;
        var server = WebServer.create(host, port);

        try {
            server.route(new Routes() {{
                post("/start").to(request -> startGame(request));
                post("/guess").to(request -> guessWord(request));
            }});
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Response guessWord(Request request) {

        try {

            GuessRequest gr = extractGuessRequest(request);
            GuessResult result = wordz.assess(gr.player(), gr.guess());

            return Response.ok()
                    .body(createGuessHttpResponse(result))
                    .done();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    private String createGuessHttpResponse(GuessResult result) {
        GuessHttpResponse httpResponse =
                new GuessHttpResponseMapper().from(result);
        return new Gson().toJson(httpResponse);
    }

    private GuessRequest extractGuessRequest(Request request) throws IOException {
        return new Gson().fromJson(request.body(), GuessRequest.class);
    }

    private Response startGame(Request request) {

        try {
            var player = extractPlayer(request);
            boolean isSuccess = wordz.newGame(player);

            HttpStatus status = isSuccess ? NO_CONTENT : CONFLICT;
            return Response
                    .of(status)
                    .done();
        } catch (IOException | JsonSyntaxException e) {

            return Response.of(HttpStatus.BAD_REQUEST).done();
        }
    }

    private static Player extractPlayer(Request request) throws IOException {
        return new Gson().fromJson(request.body(), Player.class);
    }
}
