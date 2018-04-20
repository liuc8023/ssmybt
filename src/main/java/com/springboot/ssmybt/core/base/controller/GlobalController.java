
/**
* 文件名：GlobalController.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*
* 项目名称：ssmybt
* 类名称：GlobalController
* 类描述：全局的控制器
* 创建人：liuc
* 创建时间：2018年3月9日 上午9:41:30
* 修改人：liuc
* 修改时间：2018年3月9日 上午9:41:30
* 修改备注：
* @version
*
*/
@Controller
@RequestMapping("/global")
public class GlobalController {
	/**
	 * 
	* @author liuc
	* @date 2018年3月9日 上午9:42:12
	* @Description: 跳转到404页面
	* @return String    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@RequestMapping(path = "/error")
	public String errorPage() {
		return "404.html";
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月9日 上午9:42:48
	* @Description: 跳转到session超时页面
	* @return String    返回类型
	* @param @param model
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	 @RequestMapping(path = "/sessionError")
	    public String errorPageInfo(Model model) {
	        model.addAttribute("tips", "session超时");
	        return "login.html";
	    }
}
