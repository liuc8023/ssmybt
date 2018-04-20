package com.springboot.ssmybt.module.system.warpper;

import java.util.List;
import java.util.Map;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.base.warpper.BaseControllerWarpper;

/**
 * 角色列表的包装类
 * @author liuc
 *
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}