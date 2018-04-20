package com.springboot.ssmybt.module.system.warpper;


import java.util.Map;

import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.base.warpper.BaseControllerWarpper;
import com.springboot.ssmybt.core.utils.StringUtils;
/**
 * 部门列表的包装
 * @author liuc
 *
 */
public class DeptWarpper extends BaseControllerWarpper {

    public DeptWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        Integer pid = (Integer) map.get("pid");

        if (StringUtils.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }

}