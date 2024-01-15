package com.wordz.adapters.api;

import com.google.gson.Gson;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.routing.Routes;
import com.wordz.domain.Player;
import com.wordz.domain.Wordz;

import java.io.IOException;

public class WordzEndpoint {

    private final WebServer server;
    private final Wordz wordz;

    public WordzEndpoint(Wordz wordz, String host, int port) {
        this.wordz = wordz;
        server = WebServer.create(host, port);

        try {
            server.route(new Routes() {{
                post("/start")
                        .to(request -> startGame(request));
            }});
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Response startGame(Request request) {

        try {
            var player = new Gson().fromJson(request.body(), Player.class);
            boolean isSuccess = wordz.newGame(player);
            if (isSuccess) {

                return Response
                        .of(HttpStatus.NO_CONTENT)
                        .done();
            }
            return Response
                    .of(HttpStatus.CONFLICT)
                    .done();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
