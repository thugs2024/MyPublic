package code.test.pong.filter;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RequestRateLimitFilter implements WebFilter {

    // 令牌桶容量
    private static final int BUCKET_CAPACITY = 1;
    // 令牌生成速率（每秒生成1个令牌）
    private static final int TOKEN_RATE = 1;
    // 用于存储当前令牌数量
    private final AtomicInteger tokens = new AtomicInteger(BUCKET_CAPACITY);
    // 用于记录上次生成令牌的时间
    private final AtomicLong lastRefillTime = new AtomicLong(System.currentTimeMillis());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long currentTime = System.currentTimeMillis();
        refillTokens(currentTime);

        if (exchange == null && chain == null){
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
            return exchange.getResponse().setComplete();
        }

        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(429));
            return exchange.getResponse().setComplete();
        }
    }

    private void refillTokens(long currentTime) {
        long timeSinceLastRefill = currentTime - lastRefillTime.get();
        int tokensToAdd = (int) (timeSinceLastRefill / 1000 * TOKEN_RATE);
        tokensToAdd = Math.min(tokensToAdd, BUCKET_CAPACITY - tokens.get());
        tokens.addAndGet(tokensToAdd);
        lastRefillTime.set(currentTime);
    }
}