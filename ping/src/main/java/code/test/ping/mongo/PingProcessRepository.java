package code.test.ping.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PingProcessRepository extends MongoRepository<PingProcess, String> {

    List<PingProcess> findByLastSecond(Long lastSecond);
}
