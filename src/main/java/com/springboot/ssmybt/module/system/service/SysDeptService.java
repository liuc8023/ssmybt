
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

import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysDept;


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
public interface SysDeptService extends IService<SysDept> {

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午4:38:24
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return String    返回类型
	* @param @param deptid
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	String selectDeptName(Integer deptid);

	List<ZTreeNode> selectDeptTree();

	List<Map<String, Object>> selectDeptList(@Param("condition") String condition);

	Object insert(SysDept dept);

	SysDept selectById(int pid);

	void updateById(SysDept dept);

	void deleteDept(Integer deptId);


}
