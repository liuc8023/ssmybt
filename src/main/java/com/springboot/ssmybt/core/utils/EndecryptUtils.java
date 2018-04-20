
/**
* 文件名：EndecryptUtils.java
*
* 版本信息：
* 日期：2018年3月7日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;

import com.springboot.ssmybt.module.system.entity.SysUser;

/**
*
* 项目名称：ssmybt
* 类名称：EndecryptUtils
* 类描述：shiro进行加密解密的工具类封装
* 创建人：liuc
* 创建时间：2018年3月7日 上午9:28:11
* 修改人：liuc
* 修改时间：2018年3月7日 上午9:28:11
* 修改备注：
* @version
*
*/
public class EndecryptUtils {
	private static String algorithmName = "md5";
	@Value("${spring.shiro.md5.hashIterations}")
	private static int hashIterations;

	public static void encryptPassword(SysUser user) {
		// 随机生成盐salt
		SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		String newPassword = new SimpleHash(algorithmName, user.getPassword(),
				ByteSource.Util.bytes(user.getAccount() + salt), hashIterations).toHex();
		user.setPassword(newPassword);
		user.setSalt(salt);

	}
	
	public static String encrypt(String username, String pswd,String salt) {
		String newPassword = new SimpleHash(algorithmName, pswd, ByteSource.Util.bytes(username + salt),
				hashIterations).toHex();
		return newPassword;
	}

}
