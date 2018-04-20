
/**
* 文件名：Application.java
*
* 版本信息：
* 日期：2018年1月25日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
*
* 项目名称：ssmybt
* 类名称：Application
* 类描述：
* 创建人：liuc
* 创建时间：2018年1月25日 上午9:28:47
* 修改人：liuc
* 修改时间：2018年1月25日 上午9:28:47
* 修改备注：
* @version
*
*/

@SpringBootApplication
@EnableSwagger2
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(Application.class);

	// 入口
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Java EE应用服务器配置，
	 * 如果要使用tomcat来加载jsp的话就必须继承SpringBootServletInitializer类并且重写其中configure方法
	* (non-Javadoc)
	* @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/**
	* (non-Javadoc)
	* @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	*/

	@Override
	public void run(String... args) throws Exception {
		log.info("ssmybt项目启动完成！");
	}
	
}
