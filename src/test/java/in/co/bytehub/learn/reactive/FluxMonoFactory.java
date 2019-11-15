package in.co.bytehub.learn.reactive;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.notification.RunListener.ThreadSafe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxMonoFactory {

    List<String> itemList = Arrays.asList("item 1", "item 2");

    @Test
    public void createFluxFromIterable() {
        Flux<String> stringFlux = Flux.fromIterable(itemList).log();
        StepVerifier.create(stringFlux)
                .expectNext("item 1")
                .expectNext("item 2")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        Flux<String> stringFlux = Flux.fromStream(itemList.stream()).log();
        StepVerifier.create(stringFlux)
                .expectNext("item 1")
                .expectNext("item 2")
                .verifyComplete();
    }

    // MONO using Factory Methods

    @Test
    public void monoUsingJustorEmpty() {
        Mono<Object> empty = Mono.justOrEmpty(null);//
        Mono<Object> empty1 = Mono.empty(); //
        Assert.assertEquals(empty, empty1);

        StepVerifier.create(empty)
                .verifyComplete();

        Mono<String> itemMono = Mono.justOrEmpty("ITEM");
        StepVerifier.create(itemMono)
                .expectNext("ITEM")
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier(){

        Mono<String> stringMono = Mono.fromSupplier(() -> "It's Me");

        StepVerifier.create(stringMono)
                .expectNext("It's Me")
                .verifyComplete();

        Supplier<String> stringSupplier = () -> "It's Me";
        System.out.println(stringSupplier.get());
        Mono<String> stringMono1 = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(stringMono1)
                .expectNext("It's Me")
                .verifyComplete();

    }

}
