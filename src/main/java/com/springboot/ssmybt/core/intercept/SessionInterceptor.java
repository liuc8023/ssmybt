
/**
* 文件名：SessionInterceptor.java
*
* 版本信息：
* 日期：2018年3月5日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.intercept;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.utils.HttpSessionHolder;


/**
*
* 项目名称：ssmybt
* 类名称：SessionInterceptor
* 类描述：静态调用session的拦截器
* 创建人：liuc
* 创建时间：2018年3月5日 下午3:03:47
* 修改人：liuc
* 修改时间：2018年3月5日 下午3:03:47
* 修改备注：
* @version
*
*/

@Aspect
@Component
public class SessionInterceptor extends BaseController{

		@Pointcut("execution(* com.springboot.ssmybt.*..controller.*.*(..))")
	    public void cutService() {
	    }

	    @Around("cutService()")
	    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {

	        HttpSessionHolder.put(super.getHttpServletRequest().getSession());
	        try {
	            return point.proceed();
	        } finally {
	            HttpSessionHolder.remove();
	        }
	    }
}
