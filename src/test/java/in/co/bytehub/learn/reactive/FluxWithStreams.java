package in.co.bytehub.learn.reactive;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
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

        Flux<String> stringFlux = mapFlux.flatMap(x -> x).log();

        StepVerifier.create(stringFlux)
                .expectNext("Nikita", "NIKITA")
                .expectNext("Nishant", "NISHANT")
                .expectNext("David", "DAVID")
                .expectNext("Kali", "KALI")
                .verifyComplete();
    }

    private Flux<String> externalServiceCall(String name) {
        try {
            Thread.sleep(1000); // Just to take the feel of external call
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.fromIterable(Arrays.asList(name, name.toUpperCase()));
    }

    @Test
    public void prefWithFlatMap() {
        // This approach took 4 seconds to complete the operation as external service is taking
        // one second on each call
        // And calls are in sequence
        Flux<String> stringFlux = Flux.fromIterable(nameList)
                .flatMap(this::externalServiceCall).log();

        StepVerifier.create(stringFlux)
                .expectNext("Nikita", "NIKITA")
                .expectNext("Nishant", "NISHANT")
                .expectNext("David", "DAVID")
                .expectNext("Kali", "KALI")
                .verifyComplete();
    }

    @Test
    public void parallelExecution() {

//        Flux<String> stringFlux = Flux.fromIterable(nameList)
//                .window(2) // It transform Whole flux in Flux of chunk of Flux i.e Flux<Flux<String>>
//                .map(x -> x.map(t -> externalServiceCall(t))
//                        .subscribeOn(Schedulers.parallel()))
//                .flatMap(z -> z)
//                .flatMap(z -> z)
//                .log();


        /* Little bit nicer way */
        Flux<String> stringFlux = Flux.fromIterable(nameList)
                .window(2)
                .flatMap(x -> x.flatMap(t -> externalServiceCall(t)).subscribeOn(Schedulers.parallel()))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(8)
                .verifyComplete();

        // I this approach order is not preserved
    }


    @Test
    public void parallelExecution_PreserveOrder() {

        /*
         * flatMapSequential is the key method to preserve the order in flux
         * use flatMapSequential method instead of flatMap when order must be preserve
         * */

        Flux<String> stringFlux = Flux.fromIterable(nameList)
                .window(1)
                .flatMapSequential(x -> x.flatMap(t -> externalServiceCall(t))
                        .subscribeOn(Schedulers.parallel()));

        StepVerifier.create(stringFlux.log())
                .expectNextCount(8)
                .verifyComplete();
    }
}
