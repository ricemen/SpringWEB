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
		// �ڵ鷯 ���(SimpleServletHandlerAdapter)�� ��Ʈ�ѷ�(HelloServlet)�� ������ ���
		setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
		initRequest("/hello").addParameter("name", "Postvisual");
		assertThat(runService().getContentAsString(), is("Fuck Postvisual"));
	}
	
	@Component("/hello")	// ��ĳ�� �ڵ��ν��� �ƴ� �̸��� �ο��� �ֱ� ���� ���
	static class HelloServlet extends HttpServlet {
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
			String name = req.getParameter("name");
			res.getWriter().print("Fuck " + name);
		}
	}
}
