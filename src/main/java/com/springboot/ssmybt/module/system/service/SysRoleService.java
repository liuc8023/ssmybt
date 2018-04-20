
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

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysRole;


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
public interface SysRoleService extends IService<SysRole> {

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午4:34:29
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	String selectSingleRoleName(Integer roleId);

	List<Map<String, Object>> selectRoles(@Param("condition")String condition);

	/**
     * 获取角色列表树
     *
     */
    List<ZTreeNode> selectRoleTreeList();

    /**
     * 获取角色列表树
     *
     */
    List<ZTreeNode> selectRoleTreeListByRoleId(String[] roleId);

	SysRole selectById(Integer roleId);

	void insert(@Valid SysRole role);

	void updateById(@Valid SysRole role);

	void deleteRoleById(Integer roleId);

	void setAuthority(Integer roleId, String ids);

	List<ZTreeNode> roleTreeList();
}
