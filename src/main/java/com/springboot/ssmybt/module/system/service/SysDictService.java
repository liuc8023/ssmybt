package com.springboot.ssmybt.module.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.core.base.service.IService;
import com.springboot.ssmybt.module.system.entity.SysDict;

public interface SysDictService extends IService<SysDict> {

	List<Map<String, Object>> selectDictList(@Param("condition") String condition);

	SysDict selectById(Integer dictId);

	List<SysDict> selectList(@Param("ew")Wrapper<SysDict> wrapper);

	void addDict(String dictName, String dictValues);

	void editDict(Integer dictId, String dictName, String dictValues);

	void delteDict(Integer dictId);

}
