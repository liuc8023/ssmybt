package com.springboot.ssmybt.module.system.warpper;

import java.util.List;
import java.util.Map;

import com.springboot.ssmybt.common.constant.IsMenu;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.base.warpper.BaseControllerWarpper;

/**
 * 菜单列表的包装类
 * @author liuc
 *
 */
public class MenuWarpper extends BaseControllerWarpper {

    public MenuWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", IsMenu.valueOf((Integer) map.get("ismenu")));
    }

}