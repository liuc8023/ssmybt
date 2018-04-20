
/**
* 文件名：IService.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.base.service;

import org.springframework.stereotype.Service;
import java.util.List;

/**
*
* 项目名称：ssmybt
* 类名称：IService
* 类描述：通用接口
* 创建人：liuc
* 创建时间：2018年2月2日 下午3:46:11
* 修改人：liuc
* 修改时间：2018年2月2日 下午3:46:11
* 修改备注：
* @version
*
*/
@Service
public interface IService<T> {
	T selectByKey(Object key);

	int save(T entity);

	int delete(Object key);

	int updateAll(T entity);

	int updateNotNull(T entity);

	List<T> selectByExample(Object example);
}
