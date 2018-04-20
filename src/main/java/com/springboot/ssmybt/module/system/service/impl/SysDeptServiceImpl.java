
/**
* 文件名：SysDeptServiceImpl.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.module.system.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.springboot.ssmybt.common.constant.Cache.Cache;
import com.springboot.ssmybt.common.constant.Cache.CacheKey;
import com.springboot.ssmybt.common.node.ZTreeNode;
import com.springboot.ssmybt.core.base.service.impl.IServiceImpl;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.dao.SysDeptMapper;
import com.springboot.ssmybt.module.system.entity.SysDept;
import com.springboot.ssmybt.module.system.service.SysDeptService;

/**
*
* 项目名称：ssmybt
* 类名称：SysDeptServiceImpl
* 类描述：
* 创建人：liuc
* 创建时间：2018年4月9日 下午4:38:38
* 修改人：liuc
* 修改时间：2018年4月9日 下午4:38:38
* 修改备注：
* @version
*
*/
@Service
public class SysDeptServiceImpl  extends IServiceImpl<SysDept> implements SysDeptService {
	
	@Autowired
	private SysDeptMapper deptMapper;
	
	/**
	 * 获取部门名称
	* (non-Javadoc)
	* @see com.springboot.ssmybt.module.system.service.SysDeptService#getDeptName(java.lang.Integer)
	*/
    @Override
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String selectDeptName(Integer deptId) {
        SysDept dept = deptMapper.selectById(deptId);
        if (StringUtils.isNotEmpty(dept) && StringUtils.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

	@Override
	public List<ZTreeNode> selectDeptTree() {
		return deptMapper.selectDeptTree();
	}

	@Override
	public List<Map<String, Object>> selectDeptList(@Param("condition") String condition) {
		return deptMapper.selectDeptList(condition);
	}

	@Override
	public Object insert(SysDept dept) {
		return mapper.insert(dept);
	}

	@Override
	public SysDept selectById(int pid) {
		return deptMapper.selectById(pid);
	}

	@Override
	public void updateById(SysDept dept) {
		deptMapper.updateByPrimaryKey(dept);
	}

	@Override
	 public void deleteDept(Integer deptId) {

        SysDept dept = deptMapper.selectById(deptId);
        Wrapper<SysDept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<SysDept> subDepts = deptMapper.selectList(wrapper);
        for (SysDept temp : subDepts) {
        	deptMapper.deleteById(temp.getId());
        }
        deptMapper.deleteById(deptId);
    }

}
