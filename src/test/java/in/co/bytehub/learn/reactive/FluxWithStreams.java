package in.co.bytehub.learn.reactive;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxWithStreams {

    private List<String> nameList = Arrays.asList("Nikita", "Nishant", "David", "Kali");

    @Test
    public void fluxWithFilter() {
        Flux<String> stringFlux = Flux.fromIterable(nameList)
                .filter(x -> x.startsWith("N"))
                .map(String::toUpperCase)
                .repeat(2); // Repeat the same flux again 2 times

        StepVerifier.create(stringFlux)
                .expectNext("NIKITA", "NISHANT")
                .expectNext("NIKITA", "NISHANT")
                .expectNext("NIKITA", "NISHANT")
                .verifyComplete();
    }


    @Test
    public void fluxWithFlatMap() {

        // Here we are depicting a DB call / external service call which returns Flux.
        // So, map method is not doing something great it's just to make a call

        Flux<Flux<String>> mapFlux = Flux.fromIterable(nameList)
                .map(this::externalServiceCall);

        // We need to flatten version of this for further processing easily

        Flux<String> stringFlux = mapFlux.flatMap(x -> x);

        StepVerifier.create(stringFlux)
                .expectNext("Nikita", "NIKITA")
                .expectNext("Nishant", "NISHANT")
                .expectNext("David", "DAVID")
                .expectNext("Kali", "KALI")
                .verifyComplete();
    }

    private Flux<String> externalServiceCall(String name) {
        return Flux.fromIterable(Arrays.asList(name, name.toUpperCase()));
    }
}
