
/**
* 文件名：MyMapper.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import com.baomidou.mybatisplus.mapper.Wrapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
*
* 项目名称：ssmybt
* 类名称：MyMapper
* 类描述： 继承自己的MyMapper
* 		    这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。 
*
* 创建人：liuc
* 创建时间：2018年2月2日 下午3:18:08
* 修改人：liuc
* 修改时间：2018年2月2日 下午3:18:08
* 修改备注：
* @version
*
*/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
	/**
	* <p>
	* 根据 Wrapper 条件，查询全部记录（并翻页）
	* </p>
	*
	* @param rowBounds 分页查询条件（可以为 RowBounds.DEFAULT）
	* @param wrapper   实体对象封装操作类
	* @return List<Map<String, Object>>
	*/
	List<Map<String, Object>> selectMapsPage(RowBounds rowBounds, @Param("ew") Wrapper<T> wrapper);
}
