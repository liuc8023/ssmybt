
/**
* 文件名：a.java
*
* 版本信息：
* 日期：2018年3月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service;

import java.util.List;

import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysPermissionInit;

/**
*
* 项目名称：ssmybt
* 类名称：a
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月2日 下午2:50:39
* 修改人：liuc
* 修改时间：2018年3月2日 下午2:50:39
* 修改备注：
* @version
*
*/
public interface SysPermissionInitService extends IService<SysPermissionInit>{
	List<SysPermissionInit> selectAll();
}
