package code.test.pong;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
@SpringBootApplication
@Slf4j
public class PongService {
    public static void main(String[] args) {

        //
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processName = runtimeMXBean.getName();
        MDC.put("processName-date", "pongservice-"+processName + "-" + java.time.LocalDate.now().toString());
        SpringApplication.run(PongService.class, args);
        log.info("pong service has already run");
    }
}