package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController // (3)-3. 스프링 부트 3부터는 DispatcherServlet이 Controller까지 기재해야하도록 바뀌었다
@RequestMapping(value = "/hello")     // (3)-1. 세번째 방법 // DispatcherServlet이 class 레벨에 있는 Mapping 부터 참고한다
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    /* DispatcherServlet 에 annotation 으로 힌트를 준다 */
//    @RequestMapping(value = "/hello", method = RequestMethod.GET) // (1) 첫번째 방법
//    @GetMapping("/hello")       // (2) 두 번째 방법
    @GetMapping         // (3)-2. 세번째 방법
    public String hello(String name){
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
