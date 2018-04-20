package com.springboot.ssmybt.module.code.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.ssmybt.module.code.dao.TableMapper;
import com.springboot.ssmybt.module.code.service.TableService;

@Service
public class TableServiceImpl implements TableService{
	@Autowired
	TableMapper tableMapper;


	@Override
	public List<Map<String, Object>> selectAllTables(String dbName) {
		 return  tableMapper.selectAllTables(dbName);
	}

}
