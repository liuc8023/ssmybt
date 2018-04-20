package com.springboot.ssmybt.module.code.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.ssmybt.Application;
import com.springboot.ssmybt.core.CoreFlag;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.generator.action.model.GenQo;
/**
 * 模板种类构建器
 * @author liuc
 *
 */
public class DefaultTemplateFactory {
	/**
     * 获取所有的模板种类
     */
    public static List<Map<String,Object>> getDefaultTemplates(){
        ArrayList<Map<String, Object>> templates = new ArrayList<>();
        templates.add(create("controllerSwitch","controller-控制器模板"));
        templates.add(create("entitySwitch","entity-实体模板"));
        templates.add(create("serviceSwitch","service-service模板"));
        templates.add(create("daoSwitch","dao-dao模板"));
        templates.add(create("indexPageSwitch","indexPage-首页模板"));
        templates.add(create("addPageSwitch","addPage-添加页面模板"));
        templates.add(create("editPageSwitch","editPage-编辑页面模板"));
        templates.add(create("jsSwitch","indexJs-主页js模板"));
        templates.add(create("infoJsSwitch","infoJs-详情页js模板"));
        templates.add(create("sqlSwitch","sql-sql语句模板"));
        return templates;
    }

    /**
     * 获取默认的参数
     */
    public static GenQo getDefaultParams(){
        GenQo genQo = new GenQo();
        genQo.setProjectPath(StringUtils.getWebRootPath(null));
        genQo.setAuthor("liuc");
        genQo.setProjectPackage(Application.class.getPackage().getName());
        genQo.setCorePackage(CoreFlag.class.getPackage().getName());
        genQo.setIgnoreTabelPrefix("sys_");
        genQo.setModuleName("system");
        genQo.setParentMenuName("系统管理");
        return genQo;
    }

    private static Map<String,Object> create(String key,String desc){
        HashMap<String, Object> template = new HashMap<>();
        template.put("key",key);
        template.put("desc",desc);
        return template;
    }
}
