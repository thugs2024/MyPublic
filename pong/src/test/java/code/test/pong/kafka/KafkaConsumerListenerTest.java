package code.test.pong.kafka;

import code.test.pong.handler.HelloWorldHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaConsumerListenerTest {

    @Autowired
    KafkaConsumerListener kafkaConsumerListener;

    @Test
    void test() {
        kafkaConsumerListener.receiveMessage("content");

    }
}