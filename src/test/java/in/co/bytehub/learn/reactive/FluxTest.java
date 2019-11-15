package in.co.bytehub.learn.reactive;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void basicFluxTest(){
        Flux.just("Flux 1","Flux 2")
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void basicFluxWithAssertTest(){
        Flux<String> flux = Flux.just("Flux 1", "Flux 2")
                .log();

        StepVerifier.create(flux)
                .expectNext("Flux 1")
                .expectNext("Flux 2")
                .verifyComplete()
                ;
    }

    @Test
    public void basicFluxWithErrorAssertTest(){
        Flux<String> flux = Flux.just("Flux 1", "Flux 2")
                .concatWith(Flux.error(new RuntimeException()))
                .log();

        StepVerifier.create(flux)
                .expectNext("Flux 1", "Flux 2")
                .expectError()
                .verify()
        ;
    }
}
