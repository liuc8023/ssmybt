package com.springboot.ssmybt.module.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysNotice;
import com.springboot.ssmybt.module.system.service.SysNoticeService;
import com.springboot.ssmybt.module.system.warpper.NoticeWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "通知controller", description = "通知操作", tags = { "通知操作接口" })
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysNoticeController.class);
	private String PREFIX = "system/notice/";

	@Autowired
	SysNoticeService sysNoticeService;

	/**
	 * 跳转到通知列表首页
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "")
	public ModelAndView index() {
		logger.info("跳转到通知列表首页");
		ModelAndView mv = new ModelAndView(PREFIX + "notice.html");
		return mv;
	}

	/**
	 * 跳转到添加通知
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "notice_add")
	public ModelAndView noticeAdd() {
		logger.info("跳转到通知列表首页");
		ModelAndView mv = new ModelAndView(PREFIX + "notice_add.html");
		return mv;
	}

	/**
	 * 跳转到修改通知
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "/notice_update/{noticeId}")
	public ModelAndView noticeUpdate(@PathVariable Integer noticeId, Model model) {
		logger.info("跳转到修改通知");
		SysNotice notice = sysNoticeService.selectById(noticeId);
		model.addAttribute("notice", notice);
		ModelAndView mv = new ModelAndView(PREFIX + "notice_edit.html");
		return mv;
	}

	/**
	 * 跳转到首页通知
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "hello")
	public ModelAndView hello() {
		logger.info("跳转到首页通知");
		List<Map<String, Object>> notices = sysNoticeService.selectNoticeList(null);
		super.setAttr("noticeList", notices);
		ModelAndView mv = new ModelAndView("blackboard.html");
		return mv;
	}

	/**
	 * 获取通知列表
	 */
	@GetMapping(value = "list")
	@ResponseBody
	@ApiOperation(value="获取通知列表", notes="获取通知列表")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object list(String condition) {
		List<Map<String, Object>> list = sysNoticeService.selectNoticeList(condition);
		return super.warpObject(new NoticeWrapper(list));
	}

	/**
	 * 新增通知
	 */
	@PostMapping(value = "/add")
	@ResponseBody
	@ApiOperation(value="新增通知", notes="新增通知")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object add(SysNotice notice) {
		if (StringUtils.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
			throw new SystemException(BizExceptionEnum.REQUEST_NULL);
		}
		notice.setCreater(ShiroUtil.getUser().getId());
		notice.setCreatetime(new Date());
		sysNoticeService.save(notice);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除通知
	 */
	@DeleteMapping(value = "/delete")
	@ResponseBody
	@ApiOperation(value=" 删除通知", notes=" 删除通知")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object delete(@RequestParam Integer noticeId) {

		// 缓存通知名称
		// LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

		sysNoticeService.deleteById(noticeId);

		return SUCCESS_TIP;
	}

	/**
	 * 修改通知
	 */
	@PutMapping(value = "/update")
	@ResponseBody
	@ApiOperation(value=" 修改通知", notes=" 修改通知")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object update(SysNotice notice) {
		if (StringUtils.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
			throw new SystemException(BizExceptionEnum.REQUEST_NULL);
		}
		SysNotice old = sysNoticeService.selectById(notice.getId());
		old.setTitle(notice.getTitle());
		old.setContent(notice.getContent());
		sysNoticeService.updateOne(old);
		return super.SUCCESS_TIP;
	}

}
