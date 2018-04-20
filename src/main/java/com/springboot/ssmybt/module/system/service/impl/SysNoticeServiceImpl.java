package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.module.system.dao.SysNoticeMapper;
import com.springboot.ssmybt.module.system.entity.SysNotice;
import com.springboot.ssmybt.module.system.service.SysNoticeService;

@Service
public class SysNoticeServiceImpl extends IServiceImpl<SysNotice> implements SysNoticeService {

	@Autowired
	private SysNoticeMapper noticeMapper;
	@Override
	public List<Map<String, Object>> selectNoticeList(@Param("condition") String condition) {
		return noticeMapper.selectNoticeList(condition);
	}
	@Override
	public SysNotice selectById(Integer noticeId) {
		return noticeMapper.selectById(noticeId);
	}
	@Override
	public void deleteById(@Param("noticeId")Integer noticeId) {
		noticeMapper.deleteById(noticeId);
	}
	@Override
	public void updateOne(SysNotice notice) {
		noticeMapper.updateOne(notice);
	}
	
	
}