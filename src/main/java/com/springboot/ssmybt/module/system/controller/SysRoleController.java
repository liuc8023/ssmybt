package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.dictmap.RoleDict;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.base.tips.Tip;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.log.LogObjectHolder;
import com.springboot.ssmybt.core.utils.Convert;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysRole;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.service.SysRoleService;
import com.springboot.ssmybt.module.system.service.SysUserService;
import com.springboot.ssmybt.module.system.warpper.RoleWarpper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色控制器
 * 
 * @author liuc
 *
 */
@Api(value = "角色controller", description = "角色操作", tags = { "角色操作接口" })
@RestController
@RequestMapping("role")
public class SysRoleController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	private static String PREFIX = "system/role/";

	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	SysUserService sysUserService; 

	/**
	 * 跳转到角色列表页面
	 */
	@GetMapping(value = "")
	public ModelAndView index() {
		logger.info("跳转到角色列表页面");
		ModelAndView mv = new ModelAndView(PREFIX + "role.html");
		return mv;
	}

	/**
	 * 跳转到添加角色
	 */
	@GetMapping(value = "/role_add")
	public ModelAndView roleAdd() {
		ModelAndView mv = new ModelAndView(PREFIX + "role_add.html");
		return mv;
	}
	
	 /**
     * 跳转到修改角色
     */
    @Permission
    @GetMapping(value = "/role_edit/{roleId}")
    public ModelAndView roleEdit(@PathVariable Integer roleId, Model model) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        SysRole role = this.sysRoleService.selectById(roleId);
        model.addAttribute("role",role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        LogObjectHolder.me().set(role);
        ModelAndView mv = new ModelAndView(PREFIX+"role_edit.html");
		return mv;
    }

    /**
     * 跳转到角色分配
     */
    @Permission
    @GetMapping(value = "/role_assign/{roleId}")
    public ModelAndView roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        ModelAndView mv = new ModelAndView(PREFIX+"role_assign.html");
		return mv;
    }

	/**
	 * 获取角色列表
	 */
	@Permission
	@GetMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String roleName) {
		List<Map<String, Object>> roles = sysRoleService.selectRoles(super.getPara("roleName"));
		return super.warpObject(new RoleWarpper(roles));
	}
	
	/**
     * 角色新增
     */
    @PostMapping(value = "/add")
    @BussinessLog(value = "添加角色", key = "name", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid SysRole role, BindingResult result) {
        if (result.hasErrors()) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setId(null);
        this.sysRoleService.insert(role);
        return SUCCESS_TIP;
    }

    /**
     * 角色修改
     */
    @PutMapping(value = "/edit")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="修改角色", notes="修改角色")
    @BussinessLog(value = "修改角色", key = "name", dict = RoleDict.class)
    public Object edit(@Valid SysRole role, BindingResult result) {
        if (result.hasErrors()) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        this.sysRoleService.updateById(role);

//        //删除缓存
//        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 删除角色
     */
    @DeleteMapping(value = "/remove/{roleId}")
    @Permission
    @ResponseBody
    @ApiOperation(value="删除角色", notes="删除角色")
    @BussinessLog(value = "删除角色", key = "roleId", dict = RoleDict.class)
    public Tip remove(@PathVariable Integer roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if(roleId.equals(Const.ADMIN_ROLE_ID)){
            throw new SystemException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        this.sysRoleService.deleteRoleById(roleId);

//        //删除缓存
//        CacheKit.removeAll(Cache.CONSTANT);
        return SUCCESS_TIP;
    }

    /**
     * 查看角色
     */
    @GetMapping(value = "/view/{roleId}")
    @ResponseBody
    public Tip view(@PathVariable Integer roleId) {
        if (StringUtils.isEmpty(roleId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        this.sysRoleService.selectById(roleId);
        return SUCCESS_TIP;
    }

    /**
     * 配置权限
     */
    @PutMapping("/setAuthority")
    @BussinessLog(value = "配置权限", key = "roleId,ids", dict = RoleDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (StringUtils.isOneEmpty(roleId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        this.sysRoleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 获取角色列表
     */
    @GetMapping(value = "/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> roleTreeList() {
        List<ZTreeNode> roleTreeList = this.sysRoleService.roleTreeList();
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }


	
	/**
     * 获取角色列表
     */
    @GetMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Integer userId) {
        SysUser theUser = this.sysUserService.selectById(userId);
        String roleid = theUser.getRoleid();
        if (StringUtils.isEmpty(roleid)) {
            List<ZTreeNode> roleTreeList = this.sysRoleService.selectRoleTreeList();
            return roleTreeList;
        } else {
            String[] strArray = Convert.toStrArray(",", roleid);
            List<ZTreeNode> roleTreeListByUserId = this.sysRoleService.selectRoleTreeListByRoleId(strArray);
            return roleTreeListByUserId;
        }
    }

}
