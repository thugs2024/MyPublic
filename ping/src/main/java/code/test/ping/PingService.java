package code.test.ping;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


@SpringBootApplication
@EnableScheduling
@Slf4j
public class PingService {
    public static void main(String[] args) {

        //
        //
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processName = runtimeMXBean.getName();
        MDC.put("processName-date", "ping-service-"+processName + "-" + java.time.LocalDate.now().toString());
        SpringApplication.run(PingService.class, args);
        log.info("ping service has already run");
    }
}