
/**
* 文件名：SessionTimeoutInterceptor.java
*
* 版本信息：
* 日期：2018年3月5日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.intercept;

import org.apache.shiro.session.InvalidSessionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.HttpUtils;

/**
*
* 项目名称：ssmybt
* 类名称：SessionTimeoutInterceptor
* 类描述：验证session超时的拦截器
* 创建人：liuc
* 创建时间：2018年3月5日 下午2:54:12
* 修改人：liuc
* 修改时间：2018年3月5日 下午2:54:12
* 修改备注：
* @version
*
*/

@Aspect
@Component
@ConditionalOnProperty(prefix = "ssmybt", name = "session-open", havingValue = "true")
public class SessionTimeoutInterceptor extends BaseController{
	
	@Pointcut("execution(* com.springboot.ssmybt.*..controller.*.*(..))")
    public void cutService() {
    }

	@Around("cutService()")
    public Object sessionTimeoutValidate(ProceedingJoinPoint point) throws Throwable {
		boolean b = HttpUtils.isAjax(HttpUtils.getRequest());
		System.out.println("是否为ajax请求："+b);
        String servletPath = HttpUtils.getRequest().getServletPath();
        if (servletPath.equals("/getGifCode") || servletPath.equals("/login") || servletPath.equals("/global/sessionError")) {
            return point.proceed();
        }else{
            if(ShiroUtil.getSession().getAttribute("sessionFlag") == null){
            	ShiroUtil.getSubject().logout();
                throw new InvalidSessionException();
            }else{
                return point.proceed();
            }
        }
    }
}
