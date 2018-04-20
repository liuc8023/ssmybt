
/**
* 文件名：LoginController.java
*
* 版本信息：
* 日期：2018年2月28日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.common.exception.InvalidKaptchaException;
import com.springboot.ssmybt.common.node.MenuNode;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.core.shiro.ShiroUser;
import com.springboot.ssmybt.core.shiro.constant.ShiroConstants;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.ApiMenuFilter;
import com.springboot.ssmybt.core.utils.ImageUtil;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.service.SysMenuService;
import com.springboot.ssmybt.module.system.service.SysUserService;
import org.springframework.ui.Model;

/**
 *
 * 项目名称：ssmybt 类名称：LoginController 类描述：登录控制器 创建人：liuc 创建时间：2018年2月28日 上午10:03:38
 * 修改人：liuc 修改时间：2018年2月28日 上午10:03:38 修改备注：
 * 
 * @version
 *
 */
@RestController
public class LoginController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	SysUserService sysUserService; 
	
	@Autowired
	SysMenuService sysMenuService;

	@Value("${ssmybt.kaptchaOnOff}")
	private boolean kaptchaOnOff;

	protected static String REDIRECT = "redirect:";

	/**
	 * 
	 * @author liuc
	 * @date 2018年3月6日 下午1:13:33
	 * @Description: 跳转到主页
	 * @return String 返回类型
	 * @param @param
	 *            model
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@GetMapping(value = {"/index","/"})
	public ModelAndView index(Model model) {
		// 获取菜单列表
		List<Integer> roleList = ((ShiroUser) ShiroUtil.getSession().getAttribute(ShiroConstants.SHIRO_USER))
				.getRoleList();
		if (roleList == null || roleList.size() == 0) {
			ShiroUtil.getSubject().logout();
			model.addAttribute("tips", "该用户没有角色，无法登陆");
			ModelAndView mv = new ModelAndView("login.html");
			return mv;
		}
		List<MenuNode> menus = sysMenuService.selectMenusByRoleIds(roleList);
		List<MenuNode> titles = MenuNode.buildTitle(menus);
		titles = ApiMenuFilter.build(titles);

		model.addAttribute("titles", titles);

		// 获取用户头像
		 Integer id = ShiroUtil.getUser().getId();
		 SysUser user = sysUserService.selectById(id);
		 String avatar = user.getAvatar();
		 model.addAttribute("avatar", avatar);
		ModelAndView mv = new ModelAndView("index.html");
		return mv;
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年2月28日 上午10:16:04
	 * @Description: 跳转到登录页面
	 * @return String 返回类型
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@GetMapping(value = "/login")
	public ModelAndView login() {
		if (ShiroUtil.isAuthenticated() || ShiroUtil.getUser() != null) {
			ModelAndView mv = new ModelAndView(REDIRECT + "/index");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("login.html");
			return mv;
		}
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年3月6日 上午10:56:02
	 * @Description: 点击登录执行的动作
	 * @return String 返回类型
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@PostMapping(value = "/login")
	public String loginVali() {
		
		String username = super.getPara("username").trim();
		String password = super.getPara("password").trim();
		String vcode = super.getPara("vcode").trim();
		String remember = super.getPara("remember");
		System.out.println("----------------"+username);
		// 验证验证码是否正确
		if (kaptchaOnOff) {
			// String kaptcha = super.getPara("vcode").trim();
			String code = (String) super.getSession().getAttribute("imageCode");
			if (StringUtils.isEmpty(vcode) || !vcode.equalsIgnoreCase(code)) {
				throw new InvalidKaptchaException();
			}
		}
		logger.info("准备登陆用户 => {}", username);
		Subject currentUser = ShiroUtil.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

		if ("on".equals(remember)) {
			token.setRememberMe(true);
		} else {
			token.setRememberMe(false);
		}

		currentUser.login(token);
		ShiroUser shiroUser = ShiroUtil.getUser();
		super.getSession().setAttribute("shiroUser", shiroUser);
		super.getSession().setAttribute("username", shiroUser.getAccount());
		ShiroUtil.getSession().setAttribute("sessionFlag", true);
		return REDIRECT + "/index";
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年3月6日 上午10:54:26
	 * @Description: 获取验证码
	 * @return void 返回类型
	 * @param @param
	 *            req
	 * @param @param
	 *            resp
	 * @param @throws
	 *            IOException(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value = "/getGifCode", method = RequestMethod.GET)
	public void getGifCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = super.getSession();
		Map<String, BufferedImage> map = ImageUtil.createImage();
		String imageCode = map.keySet().iterator().next();
		// 将验证码放到HttpSession里面
		session.setAttribute("imageCode", imageCode);

		// 设置页面不缓存
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		// 设置输出的内容的类型为gif图像
		resp.setContentType("image/gif");
		ServletOutputStream sos = resp.getOutputStream();
		// 写给浏览器
		ImageIO.write(map.get(imageCode), "gif", sos);
		sos.close();
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年3月7日 上午11:19:12
	 * @Description: 退出
	 * @return String 返回类型
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public Map<String, Object> logout() {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 退出
			ShiroUtil.getSubject().logout();
			resultMap.put("result", "success");
			logger.info("用户已安全退出");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年3月8日 下午2:07:11
	 * @Description: 被踢出后跳转的页面
	 * @return String 返回类型
	 * @param @param
	 *            model
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	@RequestMapping(value = "kickout")
	public String kickout() {
		logger.info("被踢出后跳转的页面");
		return "kickout";
	}
}
