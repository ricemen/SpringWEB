package springbook.temp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public abstract class SimpleController implements Controller  {
	
	// 필수 파라미터 정의
	private String[] requiredParams;
	private String viewName;
	
	public void setRequiredParams(String[] requiredParams) {
		this.requiredParams = requiredParams;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	final public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) {
		if(viewName == null) throw new IllegalStateException();
		
		Map<String, String> params = new HashMap<String, String>();
		for(String param : requiredParams) {
			String value = req.getParameter(param);
			if(value == null) throw new IllegalStateException();
			params.put(param, value);
		}
		
		Map<String, Object> model = new HashMap<>();
		this.control(params, model);
		
		return new ModelAndView(this.viewName, model);
	}

	public abstract void control(Map<String, String> param, Map<String, Object> model);
}
