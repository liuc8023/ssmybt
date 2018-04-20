
/**
* 文件名：a.java
*
* 版本信息：
* 日期：2018年3月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.module.system.dao.SysPermissionInitMapper;
import com.springboot.ssmybt.module.system.entity.SysPermissionInit;
import com.springboot.ssmybt.module.system.service.SysPermissionInitService;

/**
*
* 项目名称：ssmybt
* 类名称：a
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月2日 下午2:51:37
* 修改人：liuc
* 修改时间：2018年3月2日 下午2:51:37
* 修改备注：
* @version
*
*/
@Service
public class SysPermissionInitServiceImpl extends IServiceImpl<SysPermissionInit> implements SysPermissionInitService {
	@Autowired
	private SysPermissionInitMapper mapper;
	
	public List<SysPermissionInit> selectAll() {
		return mapper.selectAll();
	}
}
