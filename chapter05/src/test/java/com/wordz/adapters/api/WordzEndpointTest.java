package com.wordz.adapters.api;

import com.vtence.molecule.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static com.vtence.molecule.testing.http.HttpResponseAssert.assertThat;


public class WordzEndpointTest {

    @Test
    void startGame() {

        HttpResponse res = null ;
        assertThat(res).hasStatusCode(HttpStatus.NO_CONTENT.code);
    }

}
