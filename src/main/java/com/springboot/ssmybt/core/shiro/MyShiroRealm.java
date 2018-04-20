
/**
* 文件名：MyShiroRealm.java
*
* 版本信息：
* 日期：2018年2月11日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.springboot.ssmybt.core.log.LogManager;
import com.springboot.ssmybt.core.log.factory.LogTaskFactory;
import com.springboot.ssmybt.core.shiro.constant.ShiroConstants;
import com.springboot.ssmybt.core.shiro.factory.IShiro;
import com.springboot.ssmybt.core.shiro.factory.ShiroFactroy;
import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.Convert;
import com.springboot.ssmybt.core.utils.HttpUtils;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysUser;
import com.springboot.ssmybt.module.system.service.SysDeptService;
import com.springboot.ssmybt.module.system.service.SysRoleService;
import com.springboot.ssmybt.module.system.service.SysUserService;


/**
*
* 项目名称：ssmybt
* 类名称：MyShiroRealm
* 类描述：shiro身份校验核心类
* 创建人：liuc
* 创建时间：2018年2月11日 下午4:44:45
* 修改人：liuc
* 修改时间：2018年2月11日 下午4:44:45
* 修改备注：
* @version
*
*/
public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	SysRoleService sysRoleService;
	
	@Autowired
	SysDeptService sysDeptService;

	/**
	* (non-Javadoc)
	* @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	*/

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");
		  IShiro shiroFactory = ShiroFactroy.me();
	        ShiroUser shiroUser = (ShiroUser) ShiroUtil.getSession().getAttribute(ShiroConstants.SHIRO_USER);
	        List<Integer> roleList = shiroUser.getRoleList();

	        Set<String> permissionSet = new HashSet<>();
	        Set<String> roleNameSet = new HashSet<>();

	        for (Integer roleId : roleList) {
	            List<String> permissions = shiroFactory.selectPermissionsByRoleId(roleId);
	            if (permissions != null) {
	                for (String permission : permissions) {
	                    if (StringUtils.isNotEmpty(permission)) {
	                        permissionSet.add(permission);
	                    }
	                }
	            }
	            String roleName = shiroFactory.findRoleNameByRoleId(roleId);
	            roleNameSet.add(roleName);
	        }

	        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	        info.addStringPermissions(permissionSet);
	        info.addRoles(roleNameSet);
	        return info;
	}

	/**
	* 认证信息.(身份验证) : Authentication 是用来验证用户身份
	* (non-Javadoc)
	* @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	*/

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String name = token.getUsername();
		// 从数据库获取对应用户名密码的用户
		SysUser user = sysUserService.selectByAccount(name);
		 ShiroUser shiroUser = new ShiroUser();

	        shiroUser.setId(user.getId());            // 账号id
	        shiroUser.setAccount(user.getAccount());// 账号
	        shiroUser.setDeptId(user.getDeptid());    // 部门id
	        shiroUser.setDeptName(sysDeptService.selectDeptName(user.getDeptid()));// 部门名称
	        shiroUser.setName(user.getName());        // 用户名称

	        Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
	        List<Integer> roleList = new ArrayList<Integer>();
	        List<String> roleNameList = new ArrayList<String>();
	        for (Integer roleId : roleArray) {
	            roleList.add(roleId);
	            roleNameList.add(sysRoleService.selectSingleRoleName(roleId));
	        }
	        shiroUser.setRoleList(roleList);
	        shiroUser.setRoleNames(roleNameList);
	        
		// 访问一次，计数一次
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.increment(ShiroConstants.SHIRO_LOGIN_COUNT + name, 1);
		// 计数大于5时，设置用户被锁定
		if (Integer.parseInt(opsForValue.get(ShiroConstants.SHIRO_LOGIN_COUNT + name)) >= 5) {
			// 向redis里存入数据
			opsForValue.set(ShiroConstants.SHIRO_IS_LOCK + name, "LOCK");
			// 设置过期时间,过期时间是3分钟
			stringRedisTemplate.expire(ShiroConstants.SHIRO_IS_LOCK + name, 3, TimeUnit.MINUTES);
		}
		if ("LOCK".equals(opsForValue.get(ShiroConstants.SHIRO_IS_LOCK + name))) {
			// 由于密码输入错误次数大于5次，帐号已经禁止登录！
			throw new ExcessiveAttemptsException();
		}

		if (user != null) {
			// 登录成功
			// 更新登录时间 last login time
			// user.setLastLoginTime(new Date());
			// sysUserService.updateUserById(user);
			// 清空登录计数
			opsForValue.set(ShiroConstants.SHIRO_LOGIN_COUNT + name, "0");
		}
		logger.info("身份认证成功，登录用户：" + name);
		// 得到加密密码的盐值
		ByteSource salt = ByteSource.Util.bytes(user.getCredentialsSalt());
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(), // 用户名
				user.getPassword(), // 密码
				salt, // //加密的盐值salt=nickname+salt
				getName() // realm name
		);
		 
		// 当验证都通过后，把用户信息放在session里
		Session session = ShiroUtil.getSession();
		session.setAttribute(ShiroConstants.SHIRO_USER, shiroUser);
		session.setAttribute(ShiroConstants.SYS_USER, user);
		session.setAttribute("userSessionId", user.getId());
		session.setAttribute("sessionFlag", true);
		LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpUtils.getIp()));
		return authenticationInfo;
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		// 该句作用是重写shiro的密码验证，让shiro用我自己的验证
		setCredentialsMatcher(new CredentialsMatcher());

	}
}
