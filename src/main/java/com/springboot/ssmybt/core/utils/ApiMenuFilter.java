
/**
* 文件名：ApiMenuFilter.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.springboot.ssmybt.common.constant.Const;
import com.springboot.ssmybt.common.node.MenuNode;
import com.springboot.ssmybt.config.properties.SsmybtProperties;


/**
*
* 项目名称：ssmybt
* 类名称：ApiMenuFilter
* 类描述：api接口文档显示过滤
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:11:29
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:11:29
* 修改备注：
* @version
*
*/
public class ApiMenuFilter extends MenuNode {


    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
    	SsmybtProperties ssmybtProperties = SpringContextUtil.getBean(SsmybtProperties.class);
        if (!ssmybtProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
