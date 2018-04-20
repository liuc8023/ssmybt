
/**
* 文件名：SysMenuMapper.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.common.node.MenuNode;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysMenu;

/**
*
* 项目名称：ssmybt
* 类名称：SysMenuMapper
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月9日 上午11:13:45
* 修改人：liuc
* 修改时间：2018年3月9日 上午11:13:45
* 修改备注：
* @version
*
*/
public interface SysMenuMapper extends MyMapper<SysMenu> {
	List<SysMenu> selectAll();

	
	/**
	* @author liuc
	* @date 2018年3月10日 上午1:08:15
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return List<ZTreeNode>    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<ZTreeNode> selectMenuTreeList();


	
	/**
	* @author liuc
	* @date 2018年4月9日 上午11:09:23
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return List<String>    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<String> selectResUrlsByRoleId(Integer roleId);


	
	/**
	* @author liuc
	* @date 2018年4月9日 下午12:01:45
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return List<MenuNode>    返回类型
	* @param @param roleList
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<MenuNode> selectMenusByRoleIds(List<Integer> roleList);


	List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);


	List<ZTreeNode> selectMenuTreeListByMenuIds(List<Long> menuIds);


	List<Long> getMenuIdsByRoleId(Integer roleId);
}
