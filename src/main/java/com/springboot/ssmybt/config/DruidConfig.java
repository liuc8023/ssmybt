
/**
* 文件名：DruidConfig.java
*
* 版本信息：
* 日期：2018年1月27日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
*
* 项目名称：ssmybt
* 类名称：DruidConfig
* 类描述： Druid的DataResource配置类
* 凡是被Spring管理的类，实现接口 EnvironmentAware 重写方法 setEnvironment 可以在工程启动时，
*  获取到系统环境变量和application配置文件中的变量。 还有一种方式是采用注解的方式获取 @value("${变量的key值}")
*  获取application配置文件中的变量。 这里采用第一种要方便些
* 创建人：liuc
* 创建时间：2018年1月27日 下午5:19:13
* 修改人：liuc
* 修改时间：2018年1月27日 下午5:19:13
* 修改备注：
* @version
*
*/
@Configuration
@EnableTransactionManagement
public class DruidConfig {
	@Bean
	public ServletRegistrationBean<StatViewServlet> druidServlet() {
		ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
		servletRegistrationBean.setServlet(new StatViewServlet());
		servletRegistrationBean.addUrlMappings("/druid/*");
		Map<String, String> initParameters = new HashMap<String, String>();
		// initParameters.put("loginUsername", "admin");// 用户名
		// initParameters.put("loginPassword", "admin");// 密码
		initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
		initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
		// initParameters.put("deny", "192.168.20.38");// IP黑名单
		// (存在共同时，deny优先于allow)
		initParameters.put("logSlowSql", "true");
		servletRegistrationBean.setInitParameters(initParameters);
		return servletRegistrationBean;
	}

	/**
	 * 注册一个：DruidWebStatFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<WebStatFilter> DruidWebStatFilter() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		// session最大数量配置, 缺省1000
		filterRegistrationBean.addInitParameter("sessionStatMaxCount", "1000");
		// 开启session统计功能
		filterRegistrationBean.addInitParameter("sessionStatEnable", "true");
		// 使得druid能够知道当前的session的用户是谁.根据需要，把其中的xxx.user修改为你user信息保存在session中的sessionName.
		// 注意：如果你session中保存的是非string类型的对象，需要重载toString方法。
		filterRegistrationBean.addInitParameter("principalSessionName", "user");
		// 能够监控单个url调用的sql列表
		filterRegistrationBean.addInitParameter("profileEnable", "true");
		return filterRegistrationBean;
	}

}
