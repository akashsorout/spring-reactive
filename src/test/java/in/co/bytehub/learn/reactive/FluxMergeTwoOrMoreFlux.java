package in.co.bytehub.learn.reactive;

import java.time.Duration;
import java.util.Arrays;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMergeTwoOrMoreFlux {

    /*
     * Merge is very good when order doesn't matter in use case As execution time is quite low as compared to concat
     *
     * Concat is the tool when we need ordered result
     * */

    @Test
    public void merge2Flux() {

        // delayElements method is used to take the feel of actual calls only
        // And get the advantage of performance

        Flux<String> dbFlux = Flux.fromIterable(Arrays.asList("item1", "item2", "item3"))
                .delayElements(Duration.ofSeconds(1));
        Flux<String> externalServiceFlux = Flux.fromIterable(Arrays.asList("item4", "item5", "item6"))
                .delayElements(Duration.ofSeconds(2));

        // merge method is used to merge to diff flux into one
        // Key-point: Order is not preserved in this operation
        // If Order needs to be preserved we should go with concat method but it is slow.
        Flux<String> stringFlux = Flux.merge(dbFlux, externalServiceFlux).log();
        StepVerifier.create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void concatFlux() {

        Flux<String> dbFlux = Flux.fromIterable(Arrays.asList("item1", "item2", "item3"))
                .delayElements(Duration.ofSeconds(1));
        Flux<String> externalServiceFlux = Flux.fromIterable(Arrays.asList("item4", "item5", "item6"))
                .delayElements(Duration.ofSeconds(2));

        Flux<String> stringFlux = Flux.concat(dbFlux, externalServiceFlux).log();
        StepVerifier.create(stringFlux)
                .expectNextCount(6)
                .expectNext("item1", "item2", "item3", "item4", "item5", "item6")
                .verifyComplete();
    }
}
