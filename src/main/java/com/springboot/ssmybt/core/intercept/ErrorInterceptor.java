package com.springboot.ssmybt.core.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorInterceptor implements HandlerInterceptor {  
	   
	    /* 
	     * preHandle方法 
	     *  
	     * 
	     */  
	    @Override  
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  
	            throws Exception {  
	        return true;  
	    }  
	  
	    @Override  
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  
	            ModelAndView modelAndView) throws Exception {  
	        if(response.getStatus()==500){  
	            modelAndView.setViewName("500.html");  
	            /* 
	             * setViewName(String viewName); 
	             * 为此ModelAndView设置视图名称，由DispatcherServlet通过ViewResolver解析。 将覆盖任何预先存在的视图名称或视图。 
	             */  
	        }else if(response.getStatus()==404){  
	            modelAndView.setViewName("404.html");  
	        }  
	    }  
	  
	    @Override  
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
	            throws Exception {  
	    }  
}
