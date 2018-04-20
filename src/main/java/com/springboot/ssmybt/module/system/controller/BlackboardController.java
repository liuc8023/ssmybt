package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.module.system.service.SysNoticeService;

@RestController
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlackboardController.class);

	@Autowired
	SysNoticeService sysNoticeService;

	/**
	 * 跳转到黑板
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView blackboard(Model model) {
		logger.info("跳转到黑板");
		List<Map<String, Object>> notices = sysNoticeService.selectNoticeList(null);
		model.addAttribute("noticeList", notices);
		ModelAndView mv = new ModelAndView("blackboard.html");
		return mv;
	}
}
