package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysOperationLog;

public interface SysOperationLogService extends IService<SysOperationLog> {

	List<Map<String, Object>> getOperationLogs(Page<SysOperationLog> page, String beginTime, String endTime,
			String logName, String valueOf, String orderByField, boolean asc);

	SysOperationLog selectById(Integer id);

}
