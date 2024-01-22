package tobyspring.helloboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			HelloController helloController = new HelloController();

			servletContext.addServlet("frontController", new HttpServlet() {
					@Override
					protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
						// 프론트 컨트롤러 - 인증, 보안, 다국어, 공통 기능
						if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
							// "/hello"와 GET 메소드일 경우만 처리
							String name = req.getParameter("name");

							String ret = helloController.hello(name);	// helloController 안에서 로직이 동작 // "바인딩"

							// http 3요소 - 상태 코드, 헤더, 바디
							resp.setStatus(HttpStatus.OK.value());
							resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
							resp.getWriter().println(ret);
						}
						else if(req.getRequestURI().equals("/user")) {
							// 다른 url 처리
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
