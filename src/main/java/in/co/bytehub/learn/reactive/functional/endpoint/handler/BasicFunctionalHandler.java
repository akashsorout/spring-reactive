package in.co.bytehub.learn.reactive.functional.endpoint.handler;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BasicFunctionalHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(BasicFunctionalHandler.class);

    public Mono<ServerResponse> fluxStream(ServerRequest serverRequest) {
        LOGGER.info(serverRequest.toString());
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Flux.range(1, 5).delayElements(Duration.ofSeconds(1)).log(), Integer.class);
    }

    public Mono<ServerResponse> mono(ServerRequest serverRequest) {
        LOGGER.info(serverRequest.toString());
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(10).log(), Integer.class);
    }

}
