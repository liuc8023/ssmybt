
/**
* 文件名：DataSourceContextHolder.java
*
* 版本信息：
* 日期：2018年2月6日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.mutidatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.ssmybt.core.mutidatasource.constant.DataSourceType;

/**
*
* 项目名称：ssmybt
* 类名称：DataSourceContextHolder
* 类描述： 动态数据源持有者，负责利用ThreadLocal存取数据源名称。
* 		    由于我们的数据源信息要保证在同一线程下切换后不要被其他线程修改，所以我们将数据源信息保存在ThreadLocal共享中。
* 创建人：liuc
* 创建时间：2018年2月6日 下午4:16:41
* 修改人：liuc
* 修改时间：2018年2月6日 下午4:16:41
* 修改备注：
* @version
*
*/
public class DataSourceContextHolder {
	private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

	// 线程本地环境
	private static final ThreadLocal<String> local = new ThreadLocal<String>();

	public static ThreadLocal<String> getLocal() {
		return local;
	}

	/**
	 * 读库
	 */
	public static void setRead() {
		local.set(DataSourceType.read.getType());
		log.info("数据库切换到读库...");
	}

	/**
	 * 写库
	 */
	public static void setWrite() {
		local.set(DataSourceType.write.getType());
		log.info("数据库切换到写库...");
	}

	/**
	 * 获取数据源类型
	 */
	public static String getDataSourceType() {
		return local.get();
	}

	/**
	 * 清除数据源类型
	 */
	public static void clearDataSourceType() {
		local.remove();
		log.debug("清空数据源信息！");
	}
}
