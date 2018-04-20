
/**
* 文件名：IServiceImpl.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.ssmybt.core.base.service.IService;

import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
*
* 项目名称：ssmybt
* 类名称：IServiceImpl
* 类描述：通用接口实现类
* 创建人：liuc
* 创建时间：2018年2月2日 下午3:48:13
* 修改人：liuc
* 修改时间：2018年2月2日 下午3:48:13
* 修改备注：
* @version
*
*/
public abstract class IServiceImpl<T> implements IService<T> {
	@Autowired
	protected Mapper<T> mapper;

	public Mapper<T> getMapper() {
		return mapper;
	}

	public T selectByKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	public int save(T entity) {
		return mapper.insert(entity);
	}

	public int delete(Object key) {
		return mapper.deleteByPrimaryKey(key);
	}

	public int updateAll(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	public int updateNotNull(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}
}
