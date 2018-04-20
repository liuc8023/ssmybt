package com.springboot.ssmybt.module.system.warpper;

import java.util.List;
import java.util.Map;
import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.core.base.warpper.BaseControllerWarpper;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.entity.SysDict;
/**
 * 字典列表的包装
 * @author liuc
 *
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Integer id = (Integer) map.get("id");
        List<SysDict> dicts = ConstantFactory.me().findInDict(id);
        if(dicts != null){
            for (SysDict dict : dicts) {
                detail.append(dict.getNum() + ":" +dict.getName() + ",");
            }
            map.put("detail", StringUtils.removeSuffix(detail.toString(),","));
        }
    }

}
