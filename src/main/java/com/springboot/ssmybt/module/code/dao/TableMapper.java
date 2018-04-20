package com.springboot.ssmybt.module.code.dao;

import java.util.List;
import java.util.Map;

public interface TableMapper {

	List<Map<String, Object>> selectAllTables(String dbName);

}
