
/**
* 文件名：DataSourceType.java
*
* 版本信息：
* 日期：2018年2月7日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.mutidatasource.constant;

/**
*
* 项目名称：ssmybt
* 类名称：DataSourceType
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月7日 上午10:16:41
* 修改人：liuc
* 修改时间：2018年2月7日 上午10:16:41
* 修改备注：
* @version
*
*/
public enum DataSourceType {

	read("read", "从库"), write("write", "主库");

	private String type;

	private String name;

	DataSourceType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
