package code.test.ping.service.impl;

import code.test.ping.service.ToPongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ToPongServiceImplTest {

    @Autowired
    ToPongService toPongService;

    @Test
    void test() {

        toPongService.helloWorld("requestNo", "content");
    }
}
