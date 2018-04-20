
/**
* 文件名：SpringContextUtil.java
*
* 版本信息：
* 日期：2018年1月27日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
*
* 项目名称：ssmybt
* 类名称：SpringContextUtil
* 类描述：
* 创建人：liuc
* 创建时间：2018年1月27日 下午11:10:27
* 修改人：liuc
* 修改时间：2018年1月27日 下午11:10:27
* 修改备注：
* @version
*
*/
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtil.applicationContext == null) {
			SpringContextUtil.applicationContext = applicationContext;
		}

	}

	public static ApplicationContext getApplicationContext() {
		assertApplicationContext();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		assertApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType) {
		assertApplicationContext();
		return applicationContext.getBean(requiredType);
	}

	private static void assertApplicationContext() {
		if (SpringContextUtil.applicationContext == null) {
			throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextUtil!");
		}
	}

}
