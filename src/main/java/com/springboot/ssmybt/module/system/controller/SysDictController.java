package com.springboot.ssmybt.module.system.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.dictmap.DeptDict;
import com.springboot.ssmybt.common.dictmap.DictMap;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.log.LogObjectHolder;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysDict;
import com.springboot.ssmybt.module.system.service.SysDictService;
import com.springboot.ssmybt.module.system.warpper.DictWarpper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "字典controller", description = "字典操作", tags = { "字典操作接口" })
@RestController
@RequestMapping("/dict")
public class SysDictController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysDictController.class);

	private String PREFIX = "system/dict/";

	@Autowired
	SysDictService sysDictService;

	/**
	 * 跳转到字典管理首页
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "")
	public ModelAndView index() {
		logger.info("跳转到字典管理首页");
		ModelAndView mv = new ModelAndView(PREFIX + "dict.html");
		return mv;
	}

	/**
	 * 跳转到添加字典
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping("/dict_add")
	@BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
	public ModelAndView dictAdd() {
		ModelAndView mv = new ModelAndView(PREFIX + "dict_add.html");
		return mv;
	}

	   /**
     * 跳转到修改字典
     */
	@ApiIgnore//使用该注解忽略这个API
    @Permission(Const.ADMIN_NAME)
    @GetMapping("/dict_edit/{dictId}")
    public ModelAndView deptUpdate(@PathVariable Integer dictId, Model model) {
        SysDict dict = sysDictService.selectById(dictId);
        model.addAttribute("dict", dict);
        List<SysDict> subDicts = sysDictService.selectList(new EntityWrapper<SysDict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        ModelAndView mv = new ModelAndView(PREFIX + "dict_edit.html");
		return mv;
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */

    @PostMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="添加字典记录", notes="添加字典记录")
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = DictMap.class)
    public Object add(String dictName, String dictValues) {
        if (StringUtils.isOneEmpty(dictName, dictValues)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        this.sysDictService.addDict(dictName, dictValues);
        return SUCCESS_TIP;
    }
    
	/**
	 * 获取所有字典列表
	 */
	@GetMapping(value = "list")
	@Permission(Const.ADMIN_NAME)
	@ResponseBody
	@ApiOperation(value="获取所有字典列表", notes="获取所有字典列表")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object list(String condition) {
		List<Map<String, Object>> list = sysDictService.selectDictList(condition);
		return super.warpObject(new DictWarpper(list));
	}
	
	 /**
     * 字典详情
     */
    @GetMapping(value = "/detail/{dictId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return sysDictService.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @PutMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="修改字典", notes="修改字典")
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = DictMap.class)
    public Object update(Integer dictId, String dictName, String dictValues) {
        if (StringUtils.isOneEmpty(dictId, dictName, dictValues)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        sysDictService.editDict(dictId, dictName, dictValues);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @DeleteMapping(value = "/delete/{dictId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="刪除字典", notes="刪除字典")
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = DictMap.class)
    public Object delete(@PathVariable("dictId") Integer dictId) {
        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));
        this.sysDictService.delteDict(dictId);
        return SUCCESS_TIP;
    }
}
