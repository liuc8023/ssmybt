package com.springboot.ssmybt.module.system.factory;

import org.springframework.beans.BeanUtils;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.transfer.UserDto;

/**
 * 用户创建工厂
 * @author liuc
 *
 */
public class UserFactory {
	 public static SysUser createUser(UserDto userDto){
	        if(userDto == null){
	            return null;
	        }else{
	        	SysUser user = new SysUser();
	            BeanUtils.copyProperties(userDto,user);
	            return user;
	        }
	    }
}
