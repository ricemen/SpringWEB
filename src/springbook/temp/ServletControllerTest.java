package springbook.temp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

public class ServletControllerTest extends AbstractDispatcherServletTest {

	@Test
	public void helloServletController() throws UnsupportedEncodingException, ServletException, IOException {
		// 핸들러 어뎁터(SimpleServletHandlerAdapter)와 컨트롤러(HelloServlet)를 빈으로 등록
		setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
		initRequest("/hello").addParameter("name", "Postvisual");
		assertThat(runService().getContentAsString(), is("Fuck Postvisual"));
	}
	
	@Component("/hello")	// 빈스캐너 자동인식이 아닌 이름을 부여해 주기 위해 사용
	static class HelloServlet extends HttpServlet {
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
			String name = req.getParameter("name");
			res.getWriter().print("Fuck " + name);
		}
	}
}
