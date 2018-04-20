
/**
* 文件名：ShiroUser.java
*
* 版本信息：
* 日期：2018年2月28日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro;

import java.io.Serializable;
import java.util.List;

/**
*
* 项目名称：ssmybt
* 类名称：ShiroUser
* 类描述：自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
* 创建人：liuc
* 创建时间：2018年2月28日 上午10:37:00
* 修改人：liuc
* 修改时间：2018年2月28日 上午10:37:00
* 修改备注：
* @version
*
*/
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;

	public Integer id; // 主键ID
	public String account; // 账号
	public String name; // 姓名
	public Integer deptId; // 部门id
	public List<Integer> roleList; // 角色集
	public String deptName; // 部门名称
	public List<String> roleNames; // 角色名称集

	public Integer getId() {
		return id;
	}

	public void setId(Integer integer) {
		this.id = integer;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer integer) {
		this.deptId = integer;
	}

	public List<Integer> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Integer> roleList) {
		this.roleList = roleList;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	@Override
	public String toString() {
		return "ShiroUser [id=" + id + ", account=" + account + ", name=" + name + ", deptId=" + deptId + ", roleList="
				+ roleList + ", deptName=" + deptName + ", roleNames=" + roleNames + "]";
	}

}
