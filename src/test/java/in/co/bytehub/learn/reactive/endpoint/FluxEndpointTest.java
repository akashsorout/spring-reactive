package in.co.bytehub.learn.reactive.endpoint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testFlux() {

        // By default BLOCKING-Call Timeout of WebTestClient is 5 Seconds
        // If call is not finished with-in time period will lead to exception
        Flux<Integer> fluxResult = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(fluxResult.log())
                .expectNext(1, 2, 3)
                .verifyComplete();

    }

    @Test
    public void testFluxStream() {
        Flux<Integer> fluxResult = webTestClient.get().uri("/fluxstream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(fluxResult.log())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();

    }
}
