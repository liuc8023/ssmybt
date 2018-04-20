
/**
* 文件名：MultiSourceExAop.java
*
* 版本信息：
* 日期：2018年2月6日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.mutidatasource.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import com.springboot.ssmybt.core.mutidatasource.DataSourceContextHolder;

/**
*
* 项目名称：ssmybt
* 类名称：MultiSourceExAop
* 类描述：多数据源切换的aop
* 创建人：liuc
* 创建时间：2018年2月6日 下午4:29:45
* 修改人：liuc
* 修改时间：2018年2月6日 下午4:29:45
* 修改备注：
* @version
*
*/
@Configuration
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class MultiSourceExAop implements Ordered {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${ssmybt.muti-datasource-open}")
	private boolean mutiDataSourceOpen;

	/**
	 * 
	* @author liuc
	* @date 2018年2月7日 下午3:39:03
	* @Description: aop拦截dao层所有以insert、save、update、delete开头的方法
	* @return void    返回类型
	* @param (参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@Pointcut("(execution(* com.springboot.ssmybt.module.*.dao..*.insert*(..))) "
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.save*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.set*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.update*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.delete*(..)))")
	public void write() {

	}

	/**
	 * 
	* @author liuc
	* @date 2018年2月7日 下午3:44:33
	* @Description: aop拦截dao层所有以select、get或以find开头的方法
	* @return void    返回类型
	* @param (参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@Pointcut("(execution(* com.springboot.ssmybt.module.*.dao..*.select*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.get*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.find*(..)))"
			+ " || (execution(* com.springboot.ssmybt.module.*.dao..*.query*(..)))")
	public void read() {

	}

	@Around("read()")
	public Object aroundRead(ProceedingJoinPoint point) throws Throwable {
		/**
		 * 通过配置文件application.yml中的muti-datasource-open判断是否开启了多数据源；
		 * 如果没有开启多数据源，也就是说不支持读写分离，那就直接将数据源切换到写数据源去读取数据
		 */
		if (mutiDataSourceOpen) {
			DataSourceContextHolder.setRead();
			log.info("dataSource切换到：Read");
		} else {
			DataSourceContextHolder.setWrite();
			log.info("dataSource切换到：write");
		}
		try {
			return point.proceed();
		} finally {
			/**
			 * 使用完DataSource本地线程之后，将其清空，以免占据线程造成溢出
			 */
			log.debug("清空数据源信息！");
			DataSourceContextHolder.clearDataSourceType();
		}
	}

	@Around("write()")
	public Object aroundWrite(ProceedingJoinPoint point) throws Throwable {
		DataSourceContextHolder.setWrite();
		log.info("dataSource切换到：write");
		try {
			return point.proceed();
		} finally {
			/**
			 * 使用完DataSource本地线程之后，将其清空，以免占据线程造成溢出
			 */
			DataSourceContextHolder.clearDataSourceType();
		}
	}

	/**
	* aop的顺序要早于spring的事务
	* (non-Javadoc)
	* @see org.springframework.core.Ordered#getOrder()
	*/
	@Override
	public int getOrder() {
		/**
		 * 值越小，越优先执行
		 * 要优于事务的执行
		 * 在启动类中加上了@EnableTransactionManagement(order = 10) 
		 */
		return 2;
	}

}
