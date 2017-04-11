package springbook.temp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;

import springbook.learningtest.spring.web.ConfigurableDispatcherServlet;

public class ServletTest {

	//@Test
	public void init() throws ServletException, IOException {
		
		
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
		req.addParameter("name", "Spring");
		
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		SimpleGetServlet servlet = new SimpleGetServlet();
		servlet.service(req, res);	
		System.out.println(res.getContentAsString());
		assertThat(res.getContentAsString(), is("<html><body>Hello Spring</body></html>"));
	}
	
	@Test
	public void configTest() throws ServletException, IOException {
		ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
		servlet.setRelativeLocations(getClass(), "spring-servlet.xml");
		
		servlet.setClasses(HelloSpring.class);
		servlet.init(new MockServletConfig("spring"));
		
		MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
		req.addParameter("name", "Spring");
		
		MockHttpServletResponse res = new MockHttpServletResponse();
		
		servlet.service(req, res);	
//		System.out.println("res : " + res.getContentAsString());
//		assertThat(res.getContentAsString(), is("<html><body>Hello Spring</body></html>"));
		
		ModelAndView mav = servlet.getModelAndView();
		assertThat(mav.getViewName(), is("/WEB-INF/view/hello.jsp"));
		assertThat((String)mav.getModel().get("message"), is("Hello Spring"));
		
	}
}
