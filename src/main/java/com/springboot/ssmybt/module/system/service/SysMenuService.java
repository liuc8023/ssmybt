
/**
* 文件名：SysMenuService.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.common.node.MenuNode;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysMenu;

/**
*
* 项目名称：ssmybt
* 类名称：SysMenuService
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月9日 上午11:01:59
* 修改人：liuc
* 修改时间：2018年3月9日 上午11:01:59
* 修改备注：
* @version
*
*/
public interface SysMenuService extends IService<SysMenu> {

	
	/**
	* @author liuc
	* @date 2018年3月10日 上午1:07:22
	* @Description: 获取菜单列表树
	* @return List<ZTreeNode>    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<ZTreeNode> selectMenuTreeList();


	
	/**
	* @author liuc
	* @date 2018年4月9日 上午10:10:36
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return List<MenuNode>    返回类型
	* @param @param roleList
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<MenuNode> selectMenusByRoleIds(List<Integer> roleList);


	
	/**
	* @author liuc
	* @date 2018年4月9日 上午10:36:52
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return List<String>    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	List<String> selectResUrlsByRoleId(Integer roleId);


	 /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);


	SysMenu selectById(Long menuId);


	SysMenu selectOne(SysMenu temp);
	
	List<Long> getMenuIdsByRoleId(Integer roleId);

	List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);


}
