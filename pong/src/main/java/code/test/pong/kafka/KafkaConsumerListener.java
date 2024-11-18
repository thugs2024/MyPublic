package code.test.pong.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerListener {

    //本地无kafka环境，只是代码搭了个架子，本次code test的交互还是以http做的

    @KafkaListener(topics = "TOPIC_PONG_HELLO")
    public void receiveMessage(String message) {

        log.info("Received message: " + message);
    }
}