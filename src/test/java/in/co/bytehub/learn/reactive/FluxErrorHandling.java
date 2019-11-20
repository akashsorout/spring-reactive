package in.co.bytehub.learn.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxErrorHandling {

    @Test
    public void fluxHandleErrors() {
        Flux<String> fluxWithException = Flux.just("1", "2", "3")
                .concatWith(Flux.error(new RuntimeException("Exception occured in flux")))
                .onErrorResume(e -> Flux.just("After Error Resume")) // Will execute when an Error occurred
                .concatWith(Flux.just("4"));

        StepVerifier.create(fluxWithException)
                .expectNext("1","2","3","After Error Resume","4")
                .verifyComplete();
    }
}
