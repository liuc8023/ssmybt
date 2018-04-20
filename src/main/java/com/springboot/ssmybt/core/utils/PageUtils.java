
/**
* 文件名：PageUtils.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;


/**
*
* 项目名称：ssmybt
* 类名称：PageUtils
* 类描述：分页工具类
* 创建人：liuc
* 创建时间：2018年4月9日 下午3:00:20
* 修改人：liuc
* 修改时间：2018年4月9日 下午3:00:20
* 修改备注：
* @version
*
*/
public class PageUtils {
	/**
	 * 将页数和每页条目数转换为开始位置和结束位置<br>
	 * 此方法用于不包括结束位置的分页方法<br>
	 * 例如：<br>
	 * 页码：1，每页10 -> [0, 10]<br>
	 * 页码：2，每页10 -> [10, 20]<br>
	 * 。。。<br>
	 * 
	 * @param pageNo
	 *            页码（从1计数）
	 * @param countPerPage
	 *            每页条目数
	 * @return 第一个数为开始位置，第二个数为结束位置
	 */
	public static int[] transToStartEnd(int pageNo, int countPerPage) {
		if (pageNo < 1) {
			pageNo = 1;
		}

		if (countPerPage < 1) {
			countPerPage = 0;
//			LogKit.warn("Count per page  [" + countPerPage + "] is not valid!");
		}

		int start = (pageNo - 1) * countPerPage;
		int end = start + countPerPage;

		return new int[] { start, end };
	}

	/**
	 * 根据总数计算总页数
	 * 
	 * @param totalCount
	 *            总数
	 * @param numPerPage
	 *            每页数
	 * @return 总页数
	 */
	public static int totalPage(int totalCount, int numPerPage) {
		if (numPerPage == 0) {
			return 0;
		}
		return totalCount % numPerPage == 0 ? (totalCount / numPerPage)
				: (totalCount / numPerPage + 1);
	}
}
