package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan		// component 붙은 클래스 찾아서 Bean 등록으로 해달라 (하위 패키지 전부 뒤져서 Bean으로 등록한다.) -> 구성 정보를 따로 등록하지 않아도 된다.
// 문제가 있는데? 많은 Bean들이 등록되면 스프링이 시작될 때 어떤게 등록되어 있는지 파악 불가능
public class HellobootApplication {

	public static void main(String[] args) {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this)
					).addMapping("/*");
				});
				webServer.start();
			}
		};
		applicationContext.register(HellobootApplication.class);	// 자바 코드로 된 구성 정보(configuration) class를 등록 해줘야 한다
		applicationContext.refresh();
	}
}
