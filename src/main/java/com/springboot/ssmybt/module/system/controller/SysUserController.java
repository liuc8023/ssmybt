
/**
* 文件名：SysUserController.java
*
* 版本信息：
* 日期：2018年2月2日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.naming.NoPermissionException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.common.annotion.BussinessLog;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.dictmap.UserDict;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.state.ManagerStatus;
import com.springboot.ssmybt.config.properties.SsmybtProperties;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.base.tips.Tip;
import com.springboot.ssmybt.core.datascope.DataScope;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.log.LogObjectHolder;
import com.springboot.ssmybt.core.shiro.ShiroUser;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.EndecryptUtils;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.factory.UserFactory;
import com.springboot.ssmybt.module.system.service.SysUserService;
import com.springboot.ssmybt.module.system.transfer.UserDto;
import com.springboot.ssmybt.module.system.warpper.UserWarpper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;
import io.swagger.annotations.ApiResponse;

/**
 *
 * 项目名称：ssmybt 类名称：SysUserController 类描述： 创建人：liuc 创建时间：2018年2月2日 下午5:05:28
 * 修改人：liuc 修改时间：2018年2月2日 下午5:05:28 修改备注：
 * 
 * @version
 *
 */
@Api(value = "用户controller", description = "用户操作", tags = { "用户操作接口" })
@RestController
@RequestMapping(value = "user")
public class SysUserController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

	private static String PREFIX = "system/user/";
	@Autowired
	SysUserService sysUserService; 
	
	@Resource
	private SsmybtProperties ssmybtProperties;

	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "")
	public ModelAndView userList() {
		logger.info("跳转到用户列表页面");
		ModelAndView mv = new ModelAndView(PREFIX + "user.html");
		return mv;
	}

	 /**
     * 跳转到查看管理员列表的页面
     */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "user_add")
	public ModelAndView addView() {
		logger.info("跳转到新增用户页面");
		ModelAndView mv = new ModelAndView(PREFIX+"user_add.html");
		return mv;
	}
	
	
	 /**
     * 跳转到角色分配页面
     */
	@ApiIgnore//使用该注解忽略这个API
    @Permission
    @GetMapping("/role_assign/{userId}")
    public ModelAndView roleAssign(@PathVariable Integer userId, Model model) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        SysUser user = sysUserService.selectById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        ModelAndView mv = new ModelAndView(PREFIX+"user_roleassign.html");
		return mv;
    }

    /**
     * 跳转到编辑用户信息页面
     */
    @ApiIgnore//使用该注解忽略这个API
    @Permission
    @GetMapping("/user_edit/{userId}")
    public ModelAndView userEdit(@PathVariable Integer userId, Model model) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        SysUser user = sysUserService.selectById(userId);
        model.addAttribute("user", user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
    	ModelAndView mv = new ModelAndView(PREFIX+"user_edit.html");
		return mv;
    }
	
    /**
     * 获取用户列表
     */
    @GetMapping("/list")
	@Permission
	@ResponseBody
	@ApiOperation(value="获取用户列表", notes="获取用户列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "账号/姓名/手机号", required = false, dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "beginTime", value = "注册开始时间", required = false, dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "endTime", value = "注册结束时间", required = false, dataType = "String" ,paramType = "query"),
        @ApiImplicitParam(name = "deptid", value = "机构号", required = false, dataType = "Long" ,paramType = "query")
    })
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
		if (ShiroUtil.isAdmin()) {
			List<Map<String, Object>> users = sysUserService.selectUsers(null, name, beginTime, endTime, deptid);
			return new UserWarpper(users).warp();
		} else {
			DataScope dataScope = new DataScope(ShiroUtil.getDeptDataScope());
			List<Map<String, Object>> users = sysUserService.selectUsers(dataScope, name, beginTime, endTime, deptid);
			return new UserWarpper(users).warp();
		}
	}

    /**
     * 添加用户信息
     */
    @PostMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="添加用户信息", notes="添加用户信息")
    @BussinessLog(value = "添加用户信息", key = "account", dict = UserDict.class)
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断账号是否重复
        SysUser theUser = sysUserService.selectByAccount(user.getAccount());
        if (theUser != null) {
            throw new SystemException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // 完善账号信息
        user.setSalt(ShiroUtil.getRandomSalt());
        user.setPassword(EndecryptUtils.encrypt(user.getAccount(),user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());

        this.sysUserService.insert(UserFactory.createUser(user));
        return SUCCESS_TIP;
    }

    /**
     * 跳转到查看用户详情页面
     */
    @ApiIgnore//使用该注解忽略这个API
    @GetMapping("/user_info")
    public ModelAndView userInfo(Model model) {
        Integer userId = ShiroUtil.getUser().getId();
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        SysUser user = this.sysUserService.selectById(userId);
        model.addAttribute("user",user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        ModelAndView mv = new ModelAndView(PREFIX+"user_view.html");
		return mv;
    }
    
    /**
     * 跳转到修改密码界面
     */
    @GetMapping("/user_chpwd")
    public ModelAndView chPwd() {
        ModelAndView mv = new ModelAndView(PREFIX+"user_chpwd.html");
		return mv;
    }

    /**
     * 修改当前用户的密码
     */
    @PutMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new SystemException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroUtil.getUser().getId();
        SysUser user = sysUserService.selectById(userId);
        String oldMd5 = EndecryptUtils.encrypt(user.getAccount(),oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = EndecryptUtils.encrypt(user.getAccount(),newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            throw new SystemException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }
    
    /**
     * 修改用户信息
     *
     * @throws NoPermissionException
     */
    @PutMapping("/edit")
    @ResponseBody
    @ApiOperation(value="修改用户信息", notes="修改用户信息")
    @BussinessLog(value = "修改用户信息", key = "account", dict = UserDict.class)
    public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroUtil.hasRole(Const.ADMIN_NAME)) {
            this.sysUserService.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            assertAuth(user.getId());
            ShiroUser shiroUser = ShiroUtil.getUser();
            if (shiroUser.getId().equals(user.getId())) {
                this.sysUserService.updateById(UserFactory.createUser(user));
                return SUCCESS_TIP;
            } else {
                throw new SystemException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }
    
    /**
     * 删除用户信息（逻辑删除）
     */
    @DeleteMapping(value = "/delete/{userId}")
    @Permission
    @ResponseBody
    @ApiOperation(value="删除用户信息", notes="删除用户信息")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    public Object delete(@PathVariable("userId") Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new SystemException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.sysUserService.updateStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @GetMapping("/view/{userId}")
    @ResponseBody
    public SysUser view(@PathVariable Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.sysUserService.selectById(userId);
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/reset")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="重置用户密码", notes="重置用户密码")
    @BussinessLog(value = "重置用户密码", key = "userId", dict = UserDict.class)
    public Tip reset(@RequestParam Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        SysUser user = this.sysUserService.selectById(userId);
        user.setSalt(ShiroUtil.getRandomSalt());
        user.setPassword(EndecryptUtils.encrypt(user.getAccount(),Const.DEFAULT_PWD, user.getSalt()));
        this.sysUserService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @PutMapping("/freeze")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="冻结用户", notes="冻结用户")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    public Tip freeze(@RequestParam Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new SystemException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.sysUserService.updateStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @PutMapping("/unfreeze")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="解除冻结用户", notes="解除冻结用户")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    public Tip unfreeze(@RequestParam Integer userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.sysUserService.updateStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
    @PutMapping("/setRole")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    @ApiOperation(value="分配角色", notes="分配角色")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (StringUtils.isOneEmpty(userId, roleIds)) {
            throw new SystemException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new SystemException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        this.sysUserService.updateRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @ApiIgnore//使用该注解忽略这个API
    @PostMapping(path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String pictureName = UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = ssmybtProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new SystemException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }
	
	 /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
    private void assertAuth(Integer userId) {
        if (ShiroUtil.isAdmin()) {
            return;
        }
        List<Integer> deptDataScope = ShiroUtil.getDeptDataScope();
        SysUser user = this.sysUserService.selectById(userId);
        Integer deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        } else {
            throw new SystemException(BizExceptionEnum.NO_PERMITION);
        }

    }

}
