package code.test.ping.service.impl;

import code.test.ping.service.ToPongService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

@Service
@Slf4j
public class ToPongServiceImpl implements ToPongService {

    private WebClient webClient;

    public ToPongServiceImpl() {
        this.webClient = WebClient.create("http://localhost:8090");
    }

    private static final Logger logger = LoggerFactory.getLogger(ToPongServiceImpl.class);

    public void helloWorld(String requestId, String content){
        setMDC();
        logger.info("ping send to pong ,requestId is {}, content is {}", requestId, content);
        Mono<String> response = webClient.get()
                .uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(clientResponse -> {
                    HttpStatus statusCode = (HttpStatus) clientResponse.statusCode();
                    setMDC();
                    if (statusCode.is2xxSuccessful()) {
                        // 处理2xx成功状态码
                        return clientResponse.bodyToMono(String.class).flatMap(body -> {
                            logger.info("pong response success ,requestId is {}, response content is {}", requestId, body);
                            return Mono.empty();
                        });
                    } else if (statusCode.is4xxClientError()) {

                        // 处理4xx客户端错误状态码,被限流
                        if (statusCode.value() == 429){
                            logger.warn("too many request, the request Pong throttled it. request id is {}", requestId);
                        }
                        // 可以在此处抛出异常或进行其他处理
                        return Mono.error(new Exception("Unknown Status Code: " + statusCode));

                    } else {
                        // 处理其他状态码
                        logger.error("request error ,request id is {}", requestId);
                        return Mono.error(new Exception("Unknown Status Code: " + statusCode));
                    }
                });


        response.subscribe();
    }

    private void setMDC(){

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processName = runtimeMXBean.getName();
        MDC.put("processName-date", "ping-service-"+processName + "-" + java.time.LocalDate.now().toString());

    }
}
