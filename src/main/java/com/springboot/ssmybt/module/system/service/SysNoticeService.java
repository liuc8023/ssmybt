package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysNotice;


public interface SysNoticeService extends IService<SysNotice>{
	
	List<Map<String, Object>> selectNoticeList(@Param("condition") String condition);

	SysNotice selectById(Integer noticeId);

	void deleteById(Integer noticeId);

	void updateOne(SysNotice old);

	
}
