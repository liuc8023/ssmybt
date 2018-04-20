package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.module.system.dao.SysLoginLogMapper;
import com.springboot.ssmybt.module.system.entity.SysLoginLog;
import com.springboot.ssmybt.module.system.service.SysLogService;

@Service
public class SysLogServiceImpl extends IServiceImpl<SysLoginLog> implements  SysLogService{
	@Autowired
	private SysLoginLogMapper loginLogMapper;
	
	@Override
	public List<Map<String, Object>> selectLoginLogs(Page<SysLoginLog> page, String beginTime, String endTime,
			String logName, String orderByField, boolean asc) {
		return loginLogMapper.selectLoginLogs(page,beginTime,endTime,logName,orderByField,asc);
	}

}
