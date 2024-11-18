package code.test.ping.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ProcessLimitControl")
@Data
public class PingProcess {

    @Id
    private String id;

    private Integer count;

    private Long lastSecond;
}
