package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.module.system.dao.SysOperationLogMapper;
import com.springboot.ssmybt.module.system.entity.SysOperationLog;
import com.springboot.ssmybt.module.system.service.SysOperationLogService;

@Service
public class SysOperationLogServiceImpl extends IServiceImpl<SysOperationLog> implements  SysOperationLogService{
	@Autowired
	private SysOperationLogMapper operationLogMapper;

	@Override
	public List<Map<String, Object>> getOperationLogs(Page<SysOperationLog> page, String beginTime, String endTime,
			String logName, String valueOf, String orderByField, boolean asc) {
		return operationLogMapper.getOperationLogs(page,beginTime,endTime,logName,valueOf,orderByField,asc);
	}

	@Override
	public SysOperationLog selectById(Integer id) {
		return operationLogMapper.selectById(id);
	}
	
}
