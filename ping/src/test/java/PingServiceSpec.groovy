
// 导入必要的依赖和注解


import code.test.ping.job.ToPongServiceTask
import code.test.ping.service.ToPongService
import code.test.ping.service.impl.ToPongServiceImpl
import spock.lang.Specification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

// 使用@SpringBootTest注解来加载Spring应用上下文
@SpringBootTest
class PingServiceSpec extends Specification {

    private ToPongService toPongService ;

    private ToPongServiceTask toPongServiceTask ;

    // 编写单元测试方法
    def "test ToPongService method"() {
        given:
        // 设置测试的前提条件
        def requestId = "test_id_1"
        def content = "hello"
        toPongService = Mock()
        when:
        // 调用被测试的方法
        def result = toPongService.helloWorld(requestId, content)

        then:
        1 * toPongService.helloWorld(requestId, content)
    }

    def "test job method"() {
        given:
        // 设置测试的前提条件
        toPongServiceTask = Mock();
        when:
        // 调用被测试的方法
        def result = toPongServiceTask.ToPongServiceTask()

        then:
        // 验证结果是否符合预期
        1 * toPongServiceTask.ToPongServiceTask()
    }
}