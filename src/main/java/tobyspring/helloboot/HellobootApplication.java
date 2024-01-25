package tobyspring.helloboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.registerBean(HelloController.class);		// 빈으로 등록함
		applicationContext.registerBean(SimpleHelloService.class);	// 어떤 클래스를 가지고 빈을 만들건지 지정 -> HelloController에서 생성자로 등록
		applicationContext.refresh();								// 빈 오브젝트를 만든다

		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("frontController", new HttpServlet() {
					@Override
					protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
						// 프론트 컨트롤러 - 인증, 보안, 다국어, 공통 기능
						if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
							// "/hello"와 GET 메소드일 경우만 처리
							String name = req.getParameter("name");

							HelloController helloController = applicationContext.getBean(HelloController.class);	// class로 get 가능
							String ret = helloController.hello(name);	// helloController 안에서 로직이 동작 // "바인딩"

							// http 3요소 - 상태 코드, 헤더, 바디
							resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
							resp.getWriter().println(ret);
						}
						else {
							resp.setStatus(HttpStatus.NOT_FOUND.value());
						}

					}
				}).addMapping("/*");
		});	// 슬래시 밑으로 들어오는 모든 요청 등록 -> 이 순간부터 프론트 컨트롤러
		webServer.start();

	}

}
