package tobyspring.helloboot;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* Custom Component */
@Retention(RetentionPolicy.RUNTIME)    // 언제까지 유지?
@Target(ElementType.TYPE)              // 타깃 지정
@Component
public @interface MyComponent {

    // 어떤 컴포넌트인지 정의하고 싶을 때 사용할 수 있다.
    // ex. ServiceComponent 정의 -> 이게 그 @Service 이거야!

}
