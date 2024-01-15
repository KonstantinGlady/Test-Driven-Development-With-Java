package com.wordz.adapters.api;

import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.routing.Routes;

import java.io.IOException;

public class WordzEndpoint {

    private final WebServer server;

    public WordzEndpoint(String host, int port) {
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
        return Response
                .of(HttpStatus.NOT_IMPLEMENTED)
                .done();
    }
}
