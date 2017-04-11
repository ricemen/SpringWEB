package springbook.learningtest.spring.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

public class ConfigurableDispatcherServlet extends DispatcherServlet { 
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3356836648922140498L;
	

	protected Class<?>[] classes;		// class 설정 방법
    private String[] locations;			// XML 설정 방법
    
    /**
     * 테스트를 위해 컨트롤로가 리턴하는 모델,뷰 정보
     */
    private ModelAndView modelAndView;
    
    public ConfigurableDispatcherServlet(String[] locations) {
    	this.locations = locations;
	}

	public ConfigurableDispatcherServlet(Class<?> ...classes) { 
        this.classes = classes; 
    }
    
    public void setLocations(String ...locations) {
		this.locations = locations;
	}
    /**
     * 주어진 클래스로 부터 상대적인 위치의 클래스패스를 지정
     * @param clazz Path 를 찾기 위한 class
     * @param relativeLocations
     */
    public void setRelativeLocations(Class clazz, String ...relativeLocations) {
    	String[] locations = new String[relativeLocations.length];
    	String currentPath = ClassUtils.classPackageAsResourcePath(clazz) + "/";
    	for(int i=0; i<relativeLocations.length; i++) {
    		locations[i] = currentPath + relativeLocations[i]; 
    	}
    	this.setLocations(locations);
    }

	public void setClasses(Class<?> ...classes) {
		this.classes = classes;
	}

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		modelAndView = null;
		super.service(req, res);
	}
	
	/**
	 * DispatcherServlet 의 서블릿 컨텍스트를 생성하는 메소드를 오버라이딩 해서 테스트용 메타정보를 이용해 서블릿 컨텍스트를 생성
	 */
	protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) { 
        AbstractRefreshableWebApplicationContext wac = new AbstractRefreshableWebApplicationContext() { 
            protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) 
                    throws BeansException, IOException {
            	if (locations != null) {
	            	XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanFactory);
	            	xmlReader.loadBeanDefinitions(locations);
            	}
                AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory); 
                if (classes != null) {
	                reader.register(classes); 
            	}
            } 
        };

        wac.setServletContext(getServletContext()); 
        wac.setServletConfig(getServletConfig()); 
        wac.refresh(); 
        
        return wac; 
    }

	/**
	 * 뷰를 실행하는 과정을 가로채서 컨트롤러가 돌려주는 ModelAndView 정보를 따로 저장해둔다.
	 */
	protected void render(ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.modelAndView = mv;
		super.render(mv, request, response);
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}
}


