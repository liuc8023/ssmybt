
/**
* 文件名：WebConfig.java
*
* 版本信息：
* 日期：2018年3月28日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springboot.ssmybt.core.intercept.ErrorInterceptor;
import com.springboot.ssmybt.core.xss.XssFilter;

/**
*
* 项目名称：ssmybt
* 类名称：WebConfig
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月28日 下午5:24:43
* 修改人：liuc
* 修改时间：2018年3月28日 下午5:24:43
* 修改备注：
* @version
*
*/
@SuppressWarnings("deprecation")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter  {
	 @Override 
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    }
	 
	 	/**
	     * xssFilter注册
	     */
	    @Bean
	    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
	        XssFilter xssFilter = new XssFilter();
	        xssFilter.setUrlExclusion(Arrays.asList("/notice/update","/notice/add"));
	        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<XssFilter>(xssFilter);
	        registration.addUrlPatterns("/*");
	        return registration;
	    }
	    
	    /**
	     * 404、500异常拦截器注册
	     */
	    @Override  
	    public void addInterceptors(InterceptorRegistry registry) {  
	        registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");  
	        super.addInterceptors(registry);  
	    }  
}
