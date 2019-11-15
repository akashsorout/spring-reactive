package in.co.bytehub.learn.reactive;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxWithStreams {

    private List<String> nameList = Arrays.asList("Nikita","Nishant","David","Kali");

    @Test
    public void fluxWithFilter(){
        Flux<String> stringFlux = Flux.fromIterable(nameList)
                .filter(x -> x.startsWith("N"))
                .map(String::toUpperCase);

        StepVerifier.create(stringFlux)
                .expectNext("NIKITA","NISHANT")
                .verifyComplete();
    }



}
