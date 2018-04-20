
/**
* 文件名：ShiroFactroy.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro.factory;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.state.ManagerStatus;
import com.springboot.ssmybt.core.shiro.ShiroUser;
import com.springboot.ssmybt.core.utils.Convert;
import com.springboot.ssmybt.core.utils.SpringContextUtil;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.service.SysDeptService;
import com.springboot.ssmybt.module.system.service.SysMenuService;
import com.springboot.ssmybt.module.system.service.SysRoleService;
import com.springboot.ssmybt.module.system.service.SysUserService;




/**
*
* 项目名称：ssmybt
* 类名称：ShiroFactroy
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:25:05
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:25:05
* 修改备注：
* @version
*
*/
@Service
public class ShiroFactroy implements IShiro {

	@Autowired
	SysUserService sysUserService;
	
	@Autowired
	SysMenuService sysMenuService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	SysDeptService sysDeptService;
	
	 public static IShiro me() {
	        return SpringContextUtil.getBean(IShiro.class);
	    }
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.core.shiro.factory.IShiro#user(java.lang.String)
	*/
	
	@Override
	public SysUser user(String account) {
		 SysUser user = sysUserService.selectByAccount(account);

	        // 账号不存在
	        if (null == user) {
	            throw new CredentialsException();
	        }
	        // 账号被冻结
	        if (user.getStatus() != ManagerStatus.OK.getCode()) {
	            throw new LockedAccountException();
	        }
	        return user;
	}

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.core.shiro.factory.IShiro#shiroUser(com.springboot.ssmybt.module.system.entity.SysUser)
	*/
	
	@Override
	public ShiroUser shiroUser(SysUser user) {
		 ShiroUser shiroUser = new ShiroUser();

	        shiroUser.setId(user.getId());            // 账号id
	        shiroUser.setAccount(user.getAccount());// 账号
	        shiroUser.setDeptId(user.getDeptid());    // 部门id
	        shiroUser.setDeptName(sysDeptService.selectDeptName(user.getDeptid()));// 部门名称
	        shiroUser.setName(user.getName());        // 用户名称

	        Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
	        List<Integer> roleList = new ArrayList<Integer>();
	        List<String> roleNameList = new ArrayList<String>();
	        for (Integer roleId : roleArray) {
	            roleList.add(roleId);
	            roleNameList.add(sysRoleService.selectSingleRoleName(roleId));
	        }
	        shiroUser.setRoleList(roleList);
	        shiroUser.setRoleNames(roleNameList);

	        return shiroUser;
	}

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.core.shiro.factory.IShiro#findPermissionsByRoleId(java.lang.Integer)
	*/
	
	@Override
	public List<String> selectPermissionsByRoleId(Integer roleId) {
		 List<String> resUrls = sysMenuService.selectResUrlsByRoleId(roleId);
	     return resUrls;
	}

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.core.shiro.factory.IShiro#findRoleNameByRoleId(java.lang.Integer)
	*/
	
	@Override
	public String findRoleNameByRoleId(Integer roleId) {
		 return ConstantFactory.me().getSingleRoleTip(roleId);
	}


}
