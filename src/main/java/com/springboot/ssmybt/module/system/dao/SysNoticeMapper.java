package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysNotice;

public interface SysNoticeMapper extends MyMapper<SysNotice> {

	List<Map<String, Object>> selectNoticeList(@Param("condition") String condition);

	void deleteById(@Param("noticeId")Integer noticeId);

	SysNotice selectById(Integer noticeId);

	void updateOne(SysNotice notice);



}
