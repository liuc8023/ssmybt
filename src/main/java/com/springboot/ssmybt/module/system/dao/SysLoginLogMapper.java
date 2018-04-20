package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysLoginLog;

public interface SysLoginLogMapper extends MyMapper<SysLoginLog> {

    /**
     * 获取登录日志
     *
     * @author stylefeng
     * @Date 2017/4/16 23:48
     */
	List<Map<String, Object>> selectLoginLogs(@Param("page") Page<SysLoginLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
