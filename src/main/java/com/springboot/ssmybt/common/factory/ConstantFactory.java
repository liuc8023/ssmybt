
/**
* 文件名：ConstantFactory.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common.factory;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.common.constant.Cache.Cache;
import com.springboot.ssmybt.common.constant.Cache.CacheKey;
import com.springboot.ssmybt.common.state.ManagerStatus;
import com.springboot.ssmybt.common.state.MenuStatus;
import com.springboot.ssmybt.core.utils.Convert;
import com.springboot.ssmybt.core.utils.SpringContextUtil;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.dao.SysDeptMapper;
import com.springboot.ssmybt.module.system.dao.SysDictMapper;
import com.springboot.ssmybt.module.system.dao.SysMenuMapper;
import com.springboot.ssmybt.module.system.dao.SysRoleMapper;
import com.springboot.ssmybt.module.system.dao.SysUserMapper;
import com.springboot.ssmybt.module.system.entity.SysDept;
import com.springboot.ssmybt.module.system.entity.SysDict;
import com.springboot.ssmybt.module.system.entity.SysMenu;
import com.springboot.ssmybt.module.system.entity.SysRole;
import com.springboot.ssmybt.module.system.entity.SysUser;


/**
*
* 项目名称：ssmybt
* 类名称：ConstantFactory
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:59:11
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:59:11
* 修改备注：
* @version
*
*/
@Component
public class ConstantFactory implements IConstantFactory {

	@Autowired
	private SysUserMapper userMapper;
	
    @Autowired
	private SysMenuMapper menuMapper;
    
    @Autowired
	private SysRoleMapper roleMapper;
    
    @Autowired
	private SysDeptMapper deptMapper;
    
    @Autowired
	private SysDictMapper dictMapper;
    

    public static IConstantFactory me() {
        return SpringContextUtil.getBean(ConstantFactory.class);
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author liuc
     */
    @Override
    public String getUserNameById(Integer userId) {
        SysUser user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    @Override
    public String getUserAccountById(Integer userId) {
    	SysUser user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

	
	/**
	 * 通过角色id获取角色英文名称
	* (non-Javadoc)
	* @see com.springboot.ssmybt.common.factory.IConstantFactory#getSingleRoleTip(java.lang.String)
	*/
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
	public String getSingleRoleTip(Integer roleId) {
    	  if (0 == roleId) {
              return "--";
          }
          SysRole roleObj = roleMapper.selectById(roleId);
          if (StringUtils.isNotEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
              return roleObj.getTips();
          }
          return "";
	}

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.common.factory.IConstantFactory#getDeptName(java.lang.Integer)
	*/
	
    /**
     * 获取部门名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String getDeptName(Integer deptId) {
        SysDept dept = deptMapper.selectById(deptId);
        if (StringUtils.isNotEmpty(dept) && StringUtils.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

	
	/**
	* (non-Javadoc)
	* @see com.springboot.ssmybt.common.factory.IConstantFactory#getSingleRoleName(java.lang.Integer)
	*/
	
	 @Override
	    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
	    public String getSingleRoleName(Integer roleId) {
	        if (0 == roleId) {
	            return "--";
	        }
	        SysRole roleObj = roleMapper.selectById(roleId);
	        if (StringUtils.isNotEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
	            return roleObj.getName();
	        }
	        return "";
	    }

	 /**
	  * 获取性别名称
	 */
	@Override
	public String getSexName(Integer sex) {
		return getDictsByName("性别", sex);
	}

	/**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        SysDict temp = new SysDict();
        temp.setName(name);
        SysDict dict = dictMapper.selectOne(temp);
        if (dict == null) {
            return "";
        } else {
        	EntityWrapper<SysDict> wrapper = new EntityWrapper<>();
        	wrapper.setEntity(dict);
            wrapper.eq("pid", dict.getId());
            List<SysDict> dicts = dictMapper.selectList(wrapper);
            for (SysDict item : dicts) {
                if (item.getNum() != null && item.getNum().equals(val)) {
                    return item.getName();
                }
            }
            return "";
        }
    }

    /**
     * 获取子部门id
     */
    @Override
    public List<Integer> getSubDeptId(Integer deptid) {
        Wrapper<SysDept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + deptid + "]%");
        List<SysDept> depts = this.deptMapper.selectList(wrapper);

        ArrayList<Integer> deptids = new ArrayList<>();

        if(depts != null && depts.size() > 0){
            for (SysDept dept : depts) {
                deptids.add(dept.getId());
            }
        }

        return deptids;
    }

    /**
     * 查询字典
     */
    @Override
    public List<SysDict> findInDict(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        } else {
            EntityWrapper<SysDict> wrapper = new EntityWrapper<>();
            wrapper.eq("pid", id);
            List<SysDict> dicts = dictMapper.selectList(wrapper);
            if (dicts == null || dicts.size() == 0) {
                return null;
            } else {
                return dicts;
            }
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            SysRole roleObj = roleMapper.selectById(role);
            if (StringUtils.isNotEmpty(roleObj) && StringUtils.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StringUtils.removeSuffix(sb.toString(), ",");
    }
    
    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }
    
    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }
	
    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        } else {
            SysMenu param = new SysMenu();
            param.setCode(code);
            SysMenu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
    @Override
    public String getDictName(Integer dictId) {
        if (StringUtils.isEmpty(dictId)) {
            return "";
        } else {
            SysDict dict = dictMapper.selectById(dictId);
            if (dict == null) {
                return "";
            } else {
                return dict.getName();
            }
        }
    }
}
