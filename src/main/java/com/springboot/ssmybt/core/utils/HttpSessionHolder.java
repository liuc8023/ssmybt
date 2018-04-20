
/**
* 文件名：HttpSessionHolder.java
*
* 版本信息：
* 日期：2018年3月5日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import javax.servlet.http.HttpSession;

/**
*
* 项目名称：ssmybt
* 类名称：HttpSessionHolder
* 类描述：非Controller中获取当前session的工具类
* 创建人：liuc
* 创建时间：2018年3月5日 下午3:05:13
* 修改人：liuc
* 修改时间：2018年3月5日 下午3:05:13
* 修改备注：
* @version
*
*/
public class HttpSessionHolder {
	private static ThreadLocal<HttpSession> tl = new ThreadLocal<HttpSession>();

    public static void put(HttpSession s) {
        tl.set(s);
    }

    public static HttpSession get() {
        return tl.get();
    }

    public static void remove() {
        tl.remove();
    }
}
