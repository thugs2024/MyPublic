package code.test.ping.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ToPongServiceTaskTest {

    @Autowired
    ToPongServiceTask toPongServiceTask;

    @Test
    void test() throws IOException {
        toPongServiceTask.ToPongServiceTask();
    }
}
