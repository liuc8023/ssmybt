
/**
* 文件名：SysRoleMapper.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.dao;

import org.apache.ibatis.annotations.Param;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysRole;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


/**
*
* 项目名称：ssmybt
* 类名称：SysRoleMapper
* 类描述：角色相关的dao
* 创建人：liuc
* 创建时间：2018年4月9日 上午11:23:06
* 修改人：liuc
* 修改时间：2018年4月9日 上午11:23:06
* 修改备注：
* @version
*
*/
public interface SysRoleMapper extends MyMapper<SysRole>{
	/**
     * 根据条件查询角色列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> selectRoleTreeList();

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> selectRoleTreeListByRoleId(String[] roleId);

	
	/**
	* @author liuc
	* @date 2018年4月9日 上午11:59:18
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return SysRole    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	SysRole selectById(Integer roleId);

	void updateById(@Valid SysRole role);

	List<ZTreeNode> getRoleTreeList();

	void deleteById(Integer roleId);
}
