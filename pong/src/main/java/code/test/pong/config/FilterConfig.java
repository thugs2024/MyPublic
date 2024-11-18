package code.test.pong.config;

import code.test.pong.filter.RequestRateLimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FilterConfig {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE -1)
    public RequestRateLimitFilter requestRateLimitFilter() {
        return new RequestRateLimitFilter();
    }
}