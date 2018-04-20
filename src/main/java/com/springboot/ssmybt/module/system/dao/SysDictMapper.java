package com.springboot.ssmybt.module.system.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.core.utils.MyMapper;
import com.springboot.ssmybt.module.system.entity.SysDict;

public interface SysDictMapper  extends MyMapper<SysDict> {

	List<Map<String,Object>> selectDictList(@Param("condition") String conditiion);

	List<SysDict> selectList(@Param("ew")Wrapper<SysDict> wrapper);

	SysDict selectById(Integer dictId);

	void deleteChirld(@Param("ew")Wrapper<SysDict> dictEntityWrapper);

	void deleteById(Integer dictId);



}
