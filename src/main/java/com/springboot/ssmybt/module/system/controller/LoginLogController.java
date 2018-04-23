package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.factory.PageFactory;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.module.system.entity.SysLoginLog;
import com.springboot.ssmybt.module.system.service.SysLogService;
import com.springboot.ssmybt.module.system.warpper.LogWarpper;

/**
 * 日志管理的控制器
 * @author liuc
 *
 */
@RestController
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "system/log/";

    @Autowired
    private SysLogService sysLogService;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public ModelAndView index() {
    	ModelAndView mv = new ModelAndView(PREFIX + "login_log.html");
        return mv;
    }

    /**
     * 查询登录日志列表
     */
    @GetMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName) {
        Page<SysLoginLog> page = new PageFactory<SysLoginLog>().defaultPage();
        List<Map<String, Object>> result = sysLogService.selectLoginLogs(page, beginTime, endTime, logName, page.getOrderByField(), page.isAsc());
        page.setRecords((List<SysLoginLog>) new LogWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 清空日志
     */
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        sysLogService.deleteAll();
        return super.SUCCESS_TIP;
    }
}
