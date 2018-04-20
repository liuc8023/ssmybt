package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysOperationLog;

public interface SysOperationLogMapper extends MyMapper<SysOperationLog> {

    /**
     * 获取操作日志
     *
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<SysOperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

	SysOperationLog selectById(Integer id);
}
