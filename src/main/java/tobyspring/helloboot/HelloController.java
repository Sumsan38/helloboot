package tobyspring.helloboot;

public class HelloController {


    public String hello(String name){
        // 아주 간단한 hello 로직
        return "Hello " + name;
    }
}
