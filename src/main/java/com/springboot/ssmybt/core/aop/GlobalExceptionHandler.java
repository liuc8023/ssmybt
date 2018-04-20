
/**
* 文件名：GlobalExceptionHandler.java
*
* 版本信息：
* 日期：2018年3月6日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.springboot.ssmybt.common.exception.InvalidKaptchaException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

/**
*
* 项目名称：ssmybt
* 类名称：GlobalExceptionHandler
* 类描述：全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
* 创建人：liuc
* 创建时间：2018年3月6日 下午4:28:39
* 修改人：liuc
* 修改时间：2018年3月6日 下午4:28:39
* 修改备注：
* @version
*
*/
@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String unAuth(AuthenticationException e) {
		logger.error("用户未登陆：", e);
		return "login";
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:38:13
	* @Description: 账号被冻结
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(DisabledAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String accountLocked(DisabledAccountException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结",
//		 getIp()));
		model.addAttribute("tips", "账号被冻结,已经禁止登录");
		return "login";
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:38:01
	* @Description: 账号密码错误
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(CredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String credentials(CredentialsException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username,
//		 "账号密码错误", getIp()));
		model.addAttribute("tips", "账号密码错误");
		return "login";
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:42:33
	* @Description: 未知账户错误
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(UnknownAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String unknownAccount(UnknownAccountException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username,
//		 "账号密码错误", getIp()));
		model.addAttribute("tips", "未知账户错误");
		return "login";
	}
	

	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:38:33
	* @Description: 验证码错误
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(InvalidKaptchaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String credentials(InvalidKaptchaException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误",
//		 getIp()));
		model.addAttribute("tips", "验证码错误");
		return "login";
	}
	
	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午5:10:47
	* @Description: 验证未通过,用户名或密码错误次数大于5次,账户已锁定
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(ExcessiveAttemptsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String excessiveAttempts(ExcessiveAttemptsException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username, "用户名或密码错误次数大于5次,账户已锁定",
//		 getIp()));
		model.addAttribute("tips", "用户名或密码错误次数大于5次,账户已锁定");
		return "login";
	}
	
	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午5:18:14
	* @Description: 用户被锁定的错误
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(LockedAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String lockedAccount(LockedAccountException e, Model model) {
//		String username = HttpUtils.getRequest().getParameter("username");
//		 LogManager.me().executeLog(LogTaskFactory.loginLog(username, "用户被锁定的错误",
//		 getIp()));
		model.addAttribute("tips", "用户已经被锁定不能登录，请与管理员联系！");
		return "login";
	}
	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:40:00
	* @Description: session失效的异常拦截
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(InvalidSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String sessionTimeout(InvalidSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("tips", "session超时");
		assertAjax(request, response);
		return "login";
	}


	/**
	 * 
	* @author liuc
	* @date 2018年3月6日 下午4:40:38
	* @Description: session异常
	* @return String    返回类型
	* @param @param e
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@ExceptionHandler(UnknownSessionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String sessionTimeout(UnknownSessionException e, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("tips", "session超时");
		assertAjax(request, response);
		return "login";
	}

	private void assertAjax(HttpServletRequest request, HttpServletResponse response) {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			// 如果是ajax请求响应头会有，x-requested-with
			response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
		}
	}
}
