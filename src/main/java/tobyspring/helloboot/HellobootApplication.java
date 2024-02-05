package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication {

	public static void main(String[] args) {
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		applicationContext.refresh();

		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("dispatcherServlet",
						// 원래 url과 mehtod 별로 프론트 컨트롤러에 매핑시켜줘야 했었지만, dispatcherServlet + controller에 annotation 기재만으로 코드가 간결해졌다.
						new DispatcherServlet(applicationContext)		// 그러나 현재는 서블릿에 어떤 요청이 어떻게 들어간다는 힌트가 없기 떄문에, 요청을 보내도 404이 뜬다.
					).addMapping("/*");
		});
		webServer.start();

	}
}
