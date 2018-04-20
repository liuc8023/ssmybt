
/**
* 文件名：SysMenuServiceImpl.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.common.node.MenuNode;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.module.system.dao.SysMenuMapper;
import com.springboot.ssmybt.module.system.entity.SysMenu;
import com.springboot.ssmybt.module.system.service.SysMenuService;


/**
*
* 项目名称：ssmybt
* 类名称：SysMenuServiceImpl
* 类描述：菜单服务实现类
* 创建人：liuc
* 创建时间：2018年3月9日 上午11:11:57
* 修改人：liuc
* 修改时间：2018年3月9日 上午11:11:57
* 修改备注：
* @version
*
*/
@Service
public class SysMenuServiceImpl extends IServiceImpl<SysMenu> implements SysMenuService {

	@Autowired
	private SysMenuMapper menuMapper;
	
	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysMenuService#menuTreeList()
	*/
	
	@Override
	public List<ZTreeNode> selectMenuTreeList() {
		return this.menuMapper.selectMenuTreeList();
	}


	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysMenuService#getMenusByRoleIds(java.util.List)
	*/
	
	@Override
	public List<MenuNode> selectMenusByRoleIds(List<Integer> roleList) {
		return menuMapper.selectMenusByRoleIds(roleList);
	}


	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysMenuService#getResUrlsByRoleId(java.lang.String)
	*/
	
	@Override
	public List<String> selectResUrlsByRoleId(Integer roleId) {
		return menuMapper.selectResUrlsByRoleId(roleId);
	}


	@Override
	public List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level) {
		return menuMapper.selectMenus(condition,level);
	}


	@Override
	public SysMenu selectById(Long menuId) {
		return menuMapper.selectByPrimaryKey(menuId);
	}


	@Override
	public SysMenu selectOne(SysMenu temp) {
		return menuMapper.selectOne(temp);
	}



	@Override
	public List<Long> getMenuIdsByRoleId(Integer roleId) {
		return menuMapper.getMenuIdsByRoleId(roleId);
	}



	@Override
	public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
		return menuMapper.selectMenuTreeListByMenuIds(menuIds);
	}
}
