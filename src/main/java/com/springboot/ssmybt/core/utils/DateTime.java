package com.springboot.ssmybt.core.utils;

import java.util.Date;

/**
 * 
*
* 项目名称：ssmybt
* 类名称：DateTime
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 下午3:17:26
* 修改人：liuc
* 修改时间：2018年4月9日 下午3:17:26
* 修改备注：
* @version
*
 */
public class DateTime extends Date{
	private static final long serialVersionUID = -5395712593979185936L;
	
	/**
	 * 转换JDK date为 DateTime
	 * @param date JDK Date
	 * @return DateTime
	 */
	public static DateTime parse(Date date) {
		return new DateTime(date);
	}
	
	/**
	 * 当前时间
	 */
	public DateTime() {
		super();
	}
	
	/**
	 * 给定日期的构造
	 * @param date 日期
	 */
	public DateTime(Date date) {
		this(date.getTime());
	}
	
	/**
	 * 给定日期毫秒数的构造
	 * @param timeMillis 日期毫秒数
	 */
	public DateTime(long timeMillis) {
		super(timeMillis);
	}
	
	@Override
	public String toString() {
		return DateUtils.formatDateTime(this);
	}
	
	public String toString(String format) {
		return DateUtils.format(this, format);
	}
	
	/**
	 * @return 输出精确到毫秒的标准日期形式
	 */
	public String toMsStr() {
		return DateUtils.format(this, DateUtils.NORM_DATETIME_MS_PATTERN);
	}
	
	/**   
	 * @return java.util.Date
	*/
	public Date toDate() {
		return new Date(this.getTime());
	}
}
