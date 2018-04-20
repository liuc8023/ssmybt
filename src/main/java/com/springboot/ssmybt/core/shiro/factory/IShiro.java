
/**
* 文件名：IShiro.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro.factory;

import java.util.List;
import com.springboot.ssmybt.core.shiro.ShiroUser;
import com.springboot.ssmybt.module.system.entity.SysUser;


/**
*
* 项目名称：ssmybt
* 类名称：IShiro
* 类描述：定义shirorealm所需数据的接口
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:22:43
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:22:43
* 修改备注：
* @version
*
*/
public interface IShiro {
	 /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    SysUser user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    ShiroUser shiroUser(SysUser user);

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    List<String> selectPermissionsByRoleId(Integer roleId);

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     */
    String findRoleNameByRoleId(Integer roleId);


}
