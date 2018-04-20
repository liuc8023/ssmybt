
/**
* 文件名：MyAbstractRoutingDataSource.java
*
* 版本信息：
* 日期：2018年2月7日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.mutidatasource;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.springboot.ssmybt.core.mutidatasource.constant.DataSourceType;

/**
*
* 项目名称：ssmybt
* 类名称：MyAbstractRoutingDataSource
* 类描述：动态数据源实现类
* 创建人：liuc
* 创建时间：2018年2月7日 上午10:59:13
* 修改人：liuc
* 修改时间：2018年2月7日 上午10:59:13
* 修改备注：
* @version
*
*/
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
	private static Logger log = LoggerFactory.getLogger(MyAbstractRoutingDataSource.class);
	@Value("${spring.datasource.readSize}")
	private final int dataSourceNumber;
	private AtomicInteger count = new AtomicInteger(0);

	public MyAbstractRoutingDataSource(int dataSourceNumber) {
		this.dataSourceNumber = dataSourceNumber;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		String typeKey = DataSourceContextHolder.getDataSourceType();
		if (typeKey == null) {
			throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
		}

		if (typeKey.equals(DataSourceType.write.getType())) {
			log.error("使用数据库write.............");
			return DataSourceType.write.getType();
		}
		// 读库， 简单负载均衡
		int number = count.getAndAdd(1);
		int lookupKey = number % dataSourceNumber;
		log.error("使用数据库read-" + (lookupKey + 1));
		return DataSourceType.read.getType() + (lookupKey + 1);

	}
}
