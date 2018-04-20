
/**
* 文件名：ShiroConstants.java
*
* 版本信息：
* 日期：2018年2月11日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro.constant;


/**
*
* 项目名称：ssmybt
* 类名称：ShiroConstants
* 类描述：Shiro常量类
* 创建人：liuc
* 创建时间：2018年2月11日 下午5:04:25
* 修改人：liuc
* 修改时间：2018年2月11日 下午5:04:25
* 修改备注：
* @version
*
*/

public class ShiroConstants {
	/**
	 * 用户登录次数计数  redisKey 前缀
	 */
	public static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
	
	/**
	 * 用户登录是否被锁定    redisKey 前缀
	 */
	public static final String SHIRO_IS_LOCK = "shiro_is_lock_";
	
	public static final String SHIRO_USER = "shiroUser";
	
	public static final String SYS_USER = "sysUser";
	
}
