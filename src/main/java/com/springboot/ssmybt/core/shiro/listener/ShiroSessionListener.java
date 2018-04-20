
/**
* 文件名：SessionListener.java
*
* 版本信息：
* 日期：2018年3月12日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
*
* 项目名称：ssmybt
* 类名称：SessionListener
* 类描述：实现HttpSessionListener接口，监视session的动态 
* 创建人：liuc
* 创建时间：2018年3月12日 下午4:02:12
* 修改人：liuc
* 修改时间：2018年3月12日 下午4:02:12
* 修改备注：
* @version
*
*/
@WebListener
public class ShiroSessionListener implements SessionListener{

	
	/**
	* (non-Javadoc)
	* @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session)
	*/
	
	@Override
	public void onStart(Session session) {
		System.out.println("会话创建");
	}

	
	/**
	* (non-Javadoc)
	* @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)
	*/
	
	@Override
	public void onStop(Session session) {
		System.out.println("会话停止");
	}

	
	/**
	* (non-Javadoc)
	* @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)
	*/
	
	@Override
	public void onExpiration(Session session) {
		System.out.println("会话过期");
	} 
  
  
  
} 
