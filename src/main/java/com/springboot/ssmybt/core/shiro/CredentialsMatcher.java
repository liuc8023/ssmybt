
/**
* 文件名：CredentialsMatcher.java
*
* 版本信息：
* 日期：2018年3月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.ssmybt.core.shiro.constant.ShiroConstants;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.EndecryptUtils;
import com.springboot.ssmybt.module.system.entity.SysUser;

/**
*
* 项目名称：ssmybt
* 类名称：CredentialsMatcher
* 类描述：自定义 密码验证类
* 创建人：liuc
* 创建时间：2018年3月2日 下午3:24:18
* 修改人：liuc
* 修改时间：2018年3月2日 下午3:24:18
* 修改备注：
* @version
*
*/
public class CredentialsMatcher  extends SimpleCredentialsMatcher {
	 
    private final static Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);
 
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    	logger.info("进行自定义密码校验器校验密码");
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
    	  UsernamePasswordToken utoken=(UsernamePasswordToken) token;
          //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
    	  String password = new String((char[]) token.getCredentials());
    	  SysUser user = (SysUser) ShiroUtil.getSession().getAttribute(ShiroConstants.SYS_USER);
          String inPassword = EndecryptUtils.encrypt(utoken.getUsername(), password,user.getSalt());
          //获得数据库中的密码
          String dbPassword=(String) info.getCredentials();
          //进行密码的比对
          return this.equals(inPassword, dbPassword);
    }
}