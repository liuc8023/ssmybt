
/**
* 文件名：SysUserServiceImpl.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.core.datascope.DataScope;
import com.springboot.ssmybt.module.system.dao.SysUserMapper;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.service.SysUserService;

/**
*
* 项目名称：ssmybt
* 类名称：SysUserServiceImpl
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月2日 下午3:52:00
* 修改人：liuc
* 修改时间：2018年2月2日 下午3:52:00
* 修改备注：
* @version
*
*/
@Service
public class SysUserServiceImpl extends IServiceImpl<SysUser> implements SysUserService {
	private static Logger logger = LoggerFactory.getLogger(SysUserService.class);
	@Autowired
	private SysUserMapper userMapper;

	public SysUser selectUserByNickName(String NickName) {
		logger.info("根据用户名获取用户对象，从数据库中获取");
		return userMapper.selectUserByNickName(NickName);

	}

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysUserService#getByAccount(java.lang.String)
	*/
	
	@Override
	public SysUser selectByAccount(String account) {
		return userMapper.selectByAccount(account);
	}


	@Override
	public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime,
			Integer deptid) {
		return userMapper.selectUsers(dataScope, name, beginTime, endTime, deptid);
	}
	
	@Override
	public SysUser selectById(Integer userId) {
		return userMapper.selectById(userId);
	}


	@Override
	public void insert(SysUser createUser) {
		userMapper.insert(createUser);
	}


	@Override
	public void updateById(SysUser user) {
		userMapper.updateById(user);
	}


	@Override
	public int updateStatus(@Param("userId") Integer userId, @Param("status") int status) {
		return userMapper.updateStatus(userId,status);
	}


	@Override
	public int updateRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds) {
		return userMapper.updateRoles(userId,roleIds);
	}

}
