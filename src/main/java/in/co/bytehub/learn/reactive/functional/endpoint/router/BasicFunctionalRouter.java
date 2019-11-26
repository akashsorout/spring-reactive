package in.co.bytehub.learn.reactive.functional.endpoint.router;

import in.co.bytehub.learn.reactive.functional.endpoint.handler.BasicFunctionalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class BasicFunctionalRouter {

    private BasicFunctionalHandler handler;

    public BasicFunctionalRouter(BasicFunctionalHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .route(GET("/functional/fluxstream")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::fluxStream)

                .andRoute(GET("/functional/mono"), handler::mono);
    }
}
