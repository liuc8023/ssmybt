package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.dictmap.DeptDict;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.base.tips.Tip;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.log.LogObjectHolder;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysDept;
import com.springboot.ssmybt.module.system.service.SysDeptService;
import com.springboot.ssmybt.module.system.warpper.DeptWarpper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 部门控制器
 * 
 * @author liuc
 *
 */
@Api(value = "部门controller", description = "部门操作", tags = { "部门操作接口" })
@RestController
@RequestMapping("/dept")
public class SysDeptController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysDeptController.class);
	private String PREFIX = "system/dept/";

	@Autowired
	SysDeptService sysDeptService;

	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "")
	public ModelAndView dept() {
		logger.info("跳转到部门管理首页");
		ModelAndView mv = new ModelAndView(PREFIX + "dept.html");
		return mv;
	}

	/**
	 * 跳转到添加部门
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping("/dept_add")
	public ModelAndView deptAdd() {
		ModelAndView mv = new ModelAndView(PREFIX + "dept_add.html");
		return mv;
	}

	/**
	 * 跳转到修改部门
	 */
	@ApiIgnore//使用该注解忽略这个API
	@Permission
	@GetMapping("/dept_update/{deptId}")
	@ResponseBody
	public ModelAndView deptUpdate(@PathVariable Integer deptId) {
		ModelAndView mv = new ModelAndView();
		SysDept dept = sysDeptService.selectById(deptId);
		mv.addObject("dept", dept);
		mv.addObject("pName", ConstantFactory.me().getDeptName(dept.getPid()));
		mv.setViewName(PREFIX + "dept_edit.html");
		return mv;
	}

	/**
	 * 获取所有部门列表
	 */
	@GetMapping("/list")
	@Permission
	@ResponseBody
	@ApiOperation(value="获取所有部门列表", notes="获取所有部门列表")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object list(String condition) {
		List<Map<String, Object>> list = sysDeptService.selectDeptList(condition);
		return super.warpObject(new DeptWarpper(list));
	}

	/**
	 * 新增部门
	 */
	@PostMapping(value = "/add")
	@Permission
	@ResponseBody
	@ApiOperation(value="添加部门", notes="添加部门")
	@BussinessLog(value = "添加部门", key = "simplename", dict = DeptDict.class)
	public Object add(SysDept dept) {
		if (StringUtils.isOneEmpty(dept, dept.getSimplename())) {
			throw new SystemException(BizExceptionEnum.REQUEST_NULL);
		}
		// 完善pids,根据pid拿到pid的pids
		deptSetPids(dept);
		return this.sysDeptService.insert(dept);
	}

	/**
	 * 部门详情
	 */
	@GetMapping(value = "/detail/{deptId}")
	@Permission
	@ResponseBody
	public Object detail(@PathVariable("deptId") Integer deptId) {
		return sysDeptService.selectById(deptId);
	}

	/**
	 * 修改部门
	 */
	@PutMapping(value = "/update")
	@Permission
	@ResponseBody
	@ApiOperation(value="修改部门", notes="修改部门")
	@BussinessLog(value = "修改部门", key = "simplename", dict = DeptDict.class)
	public Object update(SysDept dept) {
		if (StringUtils.isEmpty(dept) || dept.getId() == null) {
			throw new SystemException(BizExceptionEnum.REQUEST_NULL);
		}
		deptSetPids(dept);
		sysDeptService.updateById(dept);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除部门
	 */
	@DeleteMapping("/delete/{deptId}")
	@Permission
	@ResponseBody
	@ApiOperation(value="删除部门", notes="删除部门")
	@BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
	public Tip delete(@PathVariable("deptId") Integer deptId) {

		// 缓存被删除的部门名称
		LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));
		sysDeptService.deleteDept(deptId);
		return SUCCESS_TIP;
	}

	/**
	 * 获取部门的tree列表
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "/tree")
	@ResponseBody
	public List<ZTreeNode> tree() {
		List<ZTreeNode> tree = sysDeptService.selectDeptTree();
		tree.add(ZTreeNode.createParent());
		return tree;
	}

	private void deptSetPids(SysDept dept) {
		if (StringUtils.isEmpty(dept.getPid()) || dept.getPid().equals(0)) {
			dept.setPid(0);
			dept.setPids("[0],");
		} else {
			int pid = dept.getPid();
			SysDept temp = sysDeptService.selectById(pid);
			String pids = temp.getPids();
			dept.setPid(pid);
			dept.setPids(pids + "[" + pid + "],");
		}
	}
}
