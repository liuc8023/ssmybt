
/**
* 文件名：ToolBoxException.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.exception;

import com.springboot.ssmybt.core.utils.StringUtils;

/**
*
* 项目名称：ssmybt
* 类名称：ToolBoxException
* 类描述：工具类初始化异常
* 创建人：liuc
* 创建时间：2018年4月9日 下午2:53:53
* 修改人：liuc
* 修改时间：2018年4月9日 下午2:53:53
* 修改备注：
* @version
*
*/
public class ToolBoxException extends RuntimeException{
	private static final long serialVersionUID = 8247610319171014183L;

	public ToolBoxException(Throwable e) {
		super(e.getMessage(), e);
	}
	
	public ToolBoxException(String message) {
		super(message);
	}
	
	public ToolBoxException(String messageTemplate, Object... params) {
		super(StringUtils.format(messageTemplate, params));
	}
	
	public ToolBoxException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ToolBoxException(Throwable throwable, String messageTemplate, Object... params) {
		super(StringUtils.format(messageTemplate, params), throwable);
	}
}
