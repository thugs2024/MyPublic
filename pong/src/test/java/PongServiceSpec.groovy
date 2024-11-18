import code.test.pong.filter.RequestRateLimitFilter
import code.test.pong.handler.HelloWorldHandler
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest

// 使用@SpringBootTest注解来加载Spring应用上下文
@SpringBootTest
class PongServiceSpec extends Specification {

    private RequestRateLimitFilter rateLimitFilter;
    // 编写单元测试方法
    def "test rateLimitFilter method"() {
        given:
        // 设置测试的前提条件
        def requestId = 1L;
        rateLimitFilter = Mock()
        ServerWebExchange exchange = Mock()
        WebFilterChain chain = Mock();
        when:
        // 调用被测试的方法
        def result = rateLimitFilter.filter(exchange, chain)

        then:
        1 * rateLimitFilter.filter(exchange, chain)
    }

    def "test HelloWorldHandler method"() {
        given:
        // 设置测试的前提条件
        HelloWorldHandler handler = Mock();
        ServerRequest serverRequest = Mock();
        when:
        // 调用被测试的方法
        def result = handler.helloWorld(serverRequest)
        handler.helloWorld >> null

        then:
        // 验证结果是否符合预期
        result == null
    }
}