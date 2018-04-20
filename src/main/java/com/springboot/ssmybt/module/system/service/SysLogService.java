package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysLoginLog;
public interface SysLogService extends IService<SysLoginLog> {

	List<Map<String, Object>> selectLoginLogs(Page<SysLoginLog> page, String beginTime, String endTime, String logName,
			String orderByField, boolean asc);

}
