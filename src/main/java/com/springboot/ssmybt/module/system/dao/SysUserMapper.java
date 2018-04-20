package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.core.datascope.DataScope;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysUser;

public interface SysUserMapper extends MyMapper<SysUser> {

	SysUser selectUserByNickName(String nickName);

	void updateById(SysUser user);

	List<SysUser> getUserList(SysUser sysUser);

	List<?> getUserNamesByUserIds(Map<String, Object> params);

	
	/**
	* @author liuc
	* @date 2018年4月9日 上午11:01:49
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return SysUser    返回类型
	* @param @param userId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	SysUser selectById(Integer userId);

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午3:54:46
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return SysUser    返回类型
	* @param @param account
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	SysUser selectByAccount(String account);

	List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

	int updateStatus(@Param("userId") Integer userId, @Param("status") int status);

	int updateRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

}