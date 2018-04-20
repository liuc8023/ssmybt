
/**
* 文件名：SysRoleServiceImpl.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.common.constant.Cache.Cache;
import com.springboot.ssmybt.common.constant.Cache.CacheKey;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.core.utils.Convert;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.dao.SysRelationMapper;
import com.springboot.ssmybt.module.system.dao.SysRoleMapper;
import com.springboot.ssmybt.module.system.entity.SysRelation;
import com.springboot.ssmybt.module.system.entity.SysRole;
import com.springboot.ssmybt.module.system.service.SysRoleService;


/**
*
* 项目名称：ssmybt
* 类名称：SysRoleServiceImpl
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 下午4:45:23
* 修改人：liuc
* 修改时间：2018年4月9日 下午4:45:23
* 修改备注：
* @version
*
*/
@Service
public class SysRoleServiceImpl  extends IServiceImpl<SysRole> implements SysRoleService {
	
	@Autowired
	private SysRoleMapper roleMapper;
	
	@Autowired
	private SysRelationMapper relationMapper;
	
	/**
	 * 通过角色id获取角色名称
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysRoleService#getSingleRoleName(java.lang.Integer)
	*/

    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    public String selectSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        SysRole roleObj = roleMapper.selectById(roleId);
        if (StringUtils.isNotEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

	@Override
	public List<Map<String, Object>> selectRoles(@Param("condition")String condition) {
		return roleMapper.selectRoles(condition);
	}

	@Override
	public List<ZTreeNode> selectRoleTreeList() {
		return roleMapper.selectRoleTreeList();
	}

	@Override
	public List<ZTreeNode> selectRoleTreeListByRoleId(String[] roleId) {
		return roleMapper.selectRoleTreeListByRoleId(roleId);
	}

	@Override
	public SysRole selectById(Integer roleId) {
		return roleMapper.selectById(roleId);
	}

	@Override
	public void insert(@Valid SysRole role) {
		roleMapper.insert(role);
	}

	@Override
	public void updateById(@Valid SysRole role) {
		roleMapper.updateById(role);
	}

	@Override
	public void deleteRoleById(Integer roleId) {
		//删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
	}

	@Override
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            SysRelation relation = new SysRelation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

	@Override
	public List<ZTreeNode> roleTreeList() {
		return roleMapper.getRoleTreeList();
	}

}
