package com.springboot.ssmybt.module.code.service;
import java.util.List;
import java.util.Map;
/**
 * 获取数据库所有的表
 * @author liuc
 *
 */
public interface TableService {

	    /**
	     * 获取当前数据库所有的表信息
	     */
	    public List<Map<String, Object>> selectAllTables(String dbName);
	    
}