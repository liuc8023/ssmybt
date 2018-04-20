
/**
* 文件名：SysUserService.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.core.datascope.DataScope;
import com.springboot.ssmybt.module.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

/**
*
* 项目名称：ssmybt
* 类名称：SysUserService
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月2日 下午3:44:42
* 修改人：liuc
* 修改时间：2018年2月2日 下午3:44:42
* 修改备注：
* @version
*
*/
public interface SysUserService extends IService<SysUser> {
	SysUser selectUserByNickName(String name);

	
	/**
	* @author liuc
	* @date 2018年4月9日 上午10:27:46
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return SysUser    返回类型
	* @param @param account
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	SysUser selectByAccount(String account);

	/**
     * 根据条件查询用户列表
     *
     */
	List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime,
			Integer deptid);


	SysUser selectById(Integer userId);


	void insert(SysUser createUser);


	void updateById(SysUser createUser);

	/**
     * 修改用户状态
     *
     */
    int updateStatus(@Param("userId") Integer userId, @Param("status") int status);
    
    /**
     * 设置用户的角色
     *
     */
    int updateRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

}
