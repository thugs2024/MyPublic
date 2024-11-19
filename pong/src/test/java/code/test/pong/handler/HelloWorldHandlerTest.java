package code.test.pong.handler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootTest
public class HelloWorldHandlerTest {

    @Autowired
    HelloWorldHandler helloWorldHandler;

    @Test
    void test() {
        helloWorldHandler.helloWorld(null);
    }
}