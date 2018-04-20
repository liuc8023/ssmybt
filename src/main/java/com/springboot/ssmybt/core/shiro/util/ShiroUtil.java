
/**
* 文件名：ShiroUtil.java
*
* 版本信息：
* 日期：2018年2月11日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.shiro.util;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.shiro.ShiroUser;
import com.springboot.ssmybt.core.shiro.constant.ShiroConstants;

/**
 *
 * 项目名称：ssmybt 类名称：ShiroUtil 类描述：shiro工具类 创建人：liuc 创建时间：2018年2月11日 下午4:53:12
 * 修改人：liuc 修改时间：2018年2月11日 下午4:53:12 修改备注：
 * 
 * @version
 *
 */
public class ShiroUtil {
	
	private static final String NAMES_DELIMETER = ",";
	/**
	 * 
	 * @author liuc
	 * @date 2018年2月11日 下午4:54:22
	 * @Description: 获取当前 Subject
	 * @return Subject 返回类型
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 
	 * @author liuc
	 * @date 2018年2月11日 下午4:55:20
	 * @Description: 从shiro获取session
	 * @return Session 返回类型
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	public static Session getSession() {
		return getSubject().getSession();
	}

	/**
	 * @author liuc
	 * @date 2018年2月28日 上午10:32:38
	 * @Description: 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
	 * @return 通过身份验证：true，否则false
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */

	public static boolean isAuthenticated() {
		return getSubject() != null && getSubject().isAuthenticated();
	}

	/**
	 * @author liuc
	 * @date 2018年2月28日 上午10:34:05
	 * @Description: 获取封装的 ShiroUser
	 * @return ShiroUser
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */

	public static ShiroUser getUser() {
		if (isGuest()) {
			return null;
		} else {
			return ((ShiroUser) ShiroUtil.getSession().getAttribute(ShiroConstants.SHIRO_USER));
		}
	}

	/**
	 * @author liuc
	 * @date 2018年2月28日 上午10:40:52
	 * @Description: 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
	 * @return 访客：true，否则false
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	public static boolean isGuest() {
		return !isUser();
	}

	/**
	 * @author liuc
	 * @date 2018年2月28日 上午10:39:59
	 * @Description: 认证通过或已记住的用户。与guset搭配使用。
	 * @return 用户：true，否则 false
	 * @param @return(参数)
	 * @throws @since
	 *             CodingExample Ver(编码范例查看) 1.1
	 */
	private static boolean isUser() {
		return getSubject() != null && getSubject().getPrincipal() != null;
	}

	/**
	 * 判断当前用户是否是超级管理员
	 */
	public static boolean isAdmin() {
		List<Integer> roleList = getUser().getRoleList();
		for (Integer integer : roleList) {
			String singleRoleTip = ConstantFactory.me().getSingleRoleTip(integer);
			if (singleRoleTip.equals(Const.ADMIN_NAME)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前用户的部门数据范围的集合
	 */
	public static List<Integer> getDeptDataScope() {
		Integer deptId = getUser().getDeptId();
		List<Integer> subDeptIds = ConstantFactory.me().getSubDeptId(deptId);
		subDeptIds.add(deptId);
		return subDeptIds;
	}

	/**
	 * 获取随机盐值
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomSalt() {
		// 随机生成盐salt
		SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
		String salt = secureRandomNumberGenerator.nextBytes().toHex();
		return salt;
	}
	

    /**
     * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
     *
     * @param roleName
     *            角色名
     * @return 属于该角色：true，否则false
     */
    public static boolean hasRole(String roleName) {
        return getSubject() != null && roleName != null
                && roleName.length() > 0 && getSubject().hasRole(roleName);
    }
    
    /**
     * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && permission.length() > 0
                && getSubject().isPermitted(permission);
    }
    
    /**
     * 与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过。
     *
     * @param permission
     *            权限名
     * @return 拥有权限：true，否则false
     */
    public static boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }
    
    /**
     * 验证当前用户是否属于以下任意一个角色。
     *
     * @param roleNames
     *            角色列表
     * @return 属于:true,否则false
     */
    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }
}
