
/**
* 文件名：SysPermissionInitMapper.java
*
* 版本信息：
* 日期：2018年3月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysPermissionInit;

/**
*
* 项目名称：ssmybt
* 类名称：SysPermissionInitMapper
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月2日 下午2:55:32
* 修改人：liuc
* 修改时间：2018年3月2日 下午2:55:32
* 修改备注：
* @version
*
*/
public interface SysPermissionInitMapper extends MyMapper<SysPermissionInit> {
	List<SysPermissionInit> selectAll();
}
