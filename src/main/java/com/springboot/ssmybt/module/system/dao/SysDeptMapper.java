
/**
* 文件名：SysDeptMapper.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysDept;

/**
*
* 项目名称：ssmybt
* 类名称：SysDeptMapper
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 下午4:41:30
* 修改人：liuc
* 修改时间：2018年4月9日 下午4:41:30
* 修改备注：
* @version
*
*/
public interface SysDeptMapper extends MyMapper<SysDept> {

	
	/**
	* @author liuc
	* @date 2018年4月9日 下午4:42:28
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @return SysDept    返回类型
	* @param @param deptId
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	
	SysDept selectById(Integer deptId);

	List<SysDept> selectList(@Param("ew")Wrapper<SysDept> wrapper);

	List<ZTreeNode> selectDeptTree();

	List<Map<String, Object>> selectDeptList(@Param("condition") String condition);

	void deleteById(@Param("deptId")Integer deptId);

}
