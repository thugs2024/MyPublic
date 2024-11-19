package code.test.pong.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

public class RequestRateLimitFilterTest {

    @Autowired
    RequestRateLimitFilter requestRateLimitFilter;

    @Test
    void test() {

        if (requestRateLimitFilter == null){

            requestRateLimitFilter = new RequestRateLimitFilter();
        }
        requestRateLimitFilter.filter(null, null);
    }


}