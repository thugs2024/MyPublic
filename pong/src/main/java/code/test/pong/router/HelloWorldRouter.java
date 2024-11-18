package code.test.pong.router;

import code.test.pong.handler.HelloWorldHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class HelloWorldRouter {

    @Bean
    public RouterFunction<ServerResponse> route(HelloWorldHandler handler) {
        return RouterFunctions.route(GET("/hello"), handler::helloWorld);
    }


}

