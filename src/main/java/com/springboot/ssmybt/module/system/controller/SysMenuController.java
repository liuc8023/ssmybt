
/**
* 文件名：SysMenuController.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.controller;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.common.annotion.Permission;
import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.log.LogObjectHolder;
import com.springboot.ssmybt.core.utils.BeanUtils;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysMenu;
import com.springboot.ssmybt.module.system.service.SysMenuService;
import com.springboot.ssmybt.module.system.warpper.MenuWarpper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 菜单控制器
 * @version
 *
 */
@Api(value = "菜单controller", description = "菜单操作", tags = { "菜单操作接口" })
@RestController
@RequestMapping(value = "menu")
public class SysMenuController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysMenuController.class);
	private static String PREFIX = "system/menu/";

	@Autowired
	SysMenuService sysMenuService;

	/**
	 * 跳转到菜单列表首页
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "")
	public ModelAndView index() {
		logger.info("跳转到菜单列表首页");
		ModelAndView mv = new ModelAndView(PREFIX + "menu.html");
		return mv;
	}

	/**
	 * 获取菜单列表
	 */
	@Permission(Const.ADMIN_NAME)
	@GetMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
		List<Map<String, Object>> menus = sysMenuService.selectMenus(menuName, level);
		return super.warpObject(new MenuWarpper(menus));
	}

	/**
	 * 跳转到菜单新增页面
	 */
	@ApiIgnore//使用该注解忽略这个API
	@GetMapping(value = "/menu_add")
	public ModelAndView menuAdd() {
		logger.info("跳转到菜单新增页面");
		ModelAndView mv = new ModelAndView(PREFIX + "menu_add.html");
		return mv;
	}

	/**
	 * 跳转到菜单详情列表页面
	 */
	@ApiIgnore//使用该注解忽略这个API
	@Permission(Const.ADMIN_NAME)
	@GetMapping(value = "/menu_edit/{menuId}")
	public String menuEdit(@PathVariable Long menuId, Model model) {
		if (StringUtils.isEmpty(menuId)) {
			throw new SystemException(BizExceptionEnum.REQUEST_NULL);
		}
		SysMenu menu = sysMenuService.selectById(menuId);

		// 获取父级菜单的id
		SysMenu temp = new SysMenu();
		temp.setCode(menu.getPcode());
		SysMenu pMenu = sysMenuService.selectOne(temp);

		// 如果父级是顶级菜单
		if (pMenu == null) {
			menu.setPcode("0");
		} else {
			// 设置父级菜单的code为父级菜单的id
			menu.setPcode(String.valueOf(pMenu.getId()));
		}

		Map<String, Object> menuMap = BeanUtils.beanToMap(menu);
		menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
		model.addAttribute("menu", menuMap);
		LogObjectHolder.me().set(menu);
		return PREFIX + "menu_edit.html";
	}

	/**
	 * 获取菜单列表(首页用)
	 */
	@ApiOperation(value="获取菜单列表(首页用)", notes="获取菜单列表(首页用)")
	@GetMapping(value = "/menuTreeList")
	@ResponseBody
	public List<ZTreeNode> menuTreeList() {
		List<ZTreeNode> roleTreeList = this.sysMenuService.selectMenuTreeList();

		return roleTreeList;
	}

	/**
	 * 获取菜单列表(选择父级菜单用)
	 */
	@GetMapping(value = "/selectMenuTreeList")
	@ResponseBody
	@ApiOperation(value="获取菜单列表(选择父级菜单用)", notes="获取菜单列表(选择父级菜单用)")
	public List<ZTreeNode> selectMenuTreeList() {
		List<ZTreeNode> roleTreeList = this.sysMenuService.selectMenuTreeList();
		roleTreeList.add(ZTreeNode.createParent());
		return roleTreeList;
	}
	

    /**
     * 获取角色列表
     */
    @GetMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    @ApiOperation(value="获取角色列表", notes="获取角色列表")
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = this.sysMenuService.getMenuIdsByRoleId(roleId);
        if (StringUtils.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = this.sysMenuService.selectMenuTreeList();
            return roleTreeList;
        } else {
            List<ZTreeNode> roleTreeListByUserId = this.sysMenuService.menuTreeListByMenuIds(menuIds);
            return roleTreeListByUserId;
        }
    }
}
