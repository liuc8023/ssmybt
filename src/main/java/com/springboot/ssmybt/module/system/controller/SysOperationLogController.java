package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.BizLogType;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.factory.PageFactory;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.utils.BeanUtils;
import com.springboot.ssmybt.module.system.entity.SysOperationLog;
import com.springboot.ssmybt.module.system.service.SysOperationLogService;
import com.springboot.ssmybt.module.system.warpper.LogWarpper;

/**
 * 日志管理的控制器
 * @author liuc
 *
 */
@RestController
@RequestMapping("/log")
public class SysOperationLogController extends BaseController {

    private static String PREFIX = "system/log/";

    @Autowired
    private SysOperationLogService sysOperationLogService;



    /**
     * 跳转到日志管理的首页
     */
    @GetMapping("")
    public ModelAndView index() {
    	ModelAndView mv = new ModelAndView(PREFIX + "log.html");
        return mv;
    }

    /**
     * 查询操作日志列表
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        Page<SysOperationLog> page = new PageFactory<SysOperationLog>().defaultPage();
        List<Map<String, Object>> result = sysOperationLogService.getOperationLogs(page, beginTime, endTime, logName, BizLogType.valueOf(logType), page.getOrderByField(), page.isAsc());
        page.setRecords((List<SysOperationLog>) new LogWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping(value = "/detail/{id}" ,method = RequestMethod.GET)
   // @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        SysOperationLog operationLog = sysOperationLogService.selectById(id);
        Map<String, Object> stringObjectMap = BeanUtils.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @DeleteMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from sys_operation_log");
        return super.SUCCESS_TIP;
    }
}
