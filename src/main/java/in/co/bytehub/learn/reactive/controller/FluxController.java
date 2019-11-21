package in.co.bytehub.learn.reactive.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FluxController {

    /*
     * This endpoint is returning a Flux here with delay of 1 seconds.
     * When we hit this endpoint from browser. Browser wait for 3 seconds
     * i.e. to get all elements from flux. As default Media-Type is application/json
     * After onComplete call it displays the data in one go.
     *
     * Which implies still it is a BLOCKING nature endpoint
     * */
    @GetMapping("/flux")
    public Flux<Integer> integerFlux() {
        return Flux.range(1, 3)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    /*
     * This endpoint has produces Media-Type : MediaType.APPLICATION_STREAM_JSON_VALUE which is introduced us SPRING 5.0
     * Now, Browser will not wait or (BLOCKED) for all elements to receive
     *
     * It will start displaying data once it start receiving.
     * This is really reactive and NON-BLOCKING
     * */
    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> integerFluxStream() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }
}
