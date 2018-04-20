
/**
* 文件名：BasicType.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common;

import java.util.HashMap;
import java.util.Map;

/**
*
* 项目名称：ssmybt
* 类名称：BasicType
* 类描述：基本变量类型的枚举
* 创建人：liuc
* 创建时间：2018年4月9日 下午3:18:51
* 修改人：liuc
* 修改时间：2018年4月9日 下午3:18:51
* 修改备注：
* @version
*
*/
public enum BasicType {
BYTE, SHORT, INT, INTEGER, LONG, DOUBLE, FLOAT, BOOLEAN, CHAR, CHARACTER, STRING;
	
	/** 原始类型为Key，包装类型为Value，例如： int.class -> Integer.class. */
	public static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>(8);
	/** 包装类型为Key，原始类型为Value，例如： Integer.class -> int.class. */
	public static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>(8);
	
	static {
		wrapperPrimitiveMap.put(Boolean.class, boolean.class);
		wrapperPrimitiveMap.put(Byte.class, byte.class);
		wrapperPrimitiveMap.put(Character.class, char.class);
		wrapperPrimitiveMap.put(Double.class, double.class);
		wrapperPrimitiveMap.put(Float.class, float.class);
		wrapperPrimitiveMap.put(Integer.class, int.class);
		wrapperPrimitiveMap.put(Long.class, long.class);
		wrapperPrimitiveMap.put(Short.class, short.class);

		for (Map.Entry<Class<?>, Class<?>> entry : wrapperPrimitiveMap.entrySet()) {
			primitiveWrapperMap.put(entry.getValue(), entry.getKey());
		}
	}
}
