
/**
* 文件名：IConstantFactory.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common.factory;

import java.util.List;

import com.springboot.ssmybt.module.system.entity.SysDict;

/**
*
* 项目名称：ssmybt
* 类名称：IConstantFactory
* 类描述：常量生产工厂的接口
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:57:41
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:57:41
* 修改备注：
* @version
*
*/
public interface IConstantFactory {
	/**
     * 根据用户id获取用户名称
     *
     * @author stylefeng
     * @Date 2017/5/9 23:41
     */
    String getUserNameById(Integer userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    String getUserAccountById(Integer userId);

	
	/**
	* @author liuc
	* @date 2018年4月9日 上午11:09:56
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	String getSingleRoleTip(Integer roleId);

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午2:42:29
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @param @param deptid
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	String getDeptName(Integer deptid);

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午3:20:04
	* @Description: 通过角色id获取角色名称
	* @return String    返回类型
	* @param @param roleId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	String getSingleRoleName(Integer roleId);

	 /**
     * 获取性别名称
     */
	String getSexName(Integer integer);

	 /**
     * 根据字典名称和字典中的值获取对应的名称
     */
	String getDictsByName(String name, Integer val);

	/**
     * 获取子部门id
     */
	List<Integer> getSubDeptId(Integer deptId);

	 /**
     * 查询字典
     */
	List<SysDict> findInDict(Integer id);
	
	 /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);

	/**
     * 通过角色ids获取角色名称
     */
	String getRoleName(String string);

	 /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
	String getMenuStatusName(Integer status);

	 /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);
   

}
