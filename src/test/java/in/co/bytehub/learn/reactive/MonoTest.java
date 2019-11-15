package in.co.bytehub.learn.reactive;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest() {
        Mono<String> mono = Mono.just("Mono");

        StepVerifier.create(mono)
                .expectNext("Mono")
                .verifyComplete();
    }

    @Test
    public void monoWithErrorTest() {
        // We can't use concatWith like as Fulx reason being MONO is used for
        // Single entity. which is an error in this case
        Mono<String> mono = Mono.error(new RuntimeException());


        StepVerifier.create(mono)
                .expectError()
                .verify();
    }
}
