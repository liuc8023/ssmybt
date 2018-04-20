package com.springboot.ssmybt.common.dictmap;

import com.springboot.ssmybt.common.dictmap.base.AbstractDictMap;

/**
 * 用户的字典
 */
public class UserDict extends AbstractDictMap {

    @Override
    public void init() {
        put("userId","账号");
        put("avatar","头像");
        put("account","账号");
        put("name","名字");
        put("birthday","生日");
        put("sex","性别");
        put("email","电子邮件");
        put("phone","电话");
        put("roleid","角色名称");
        put("deptid","部门名称");
        put("roleIds","角色名称集合");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("sex","getSexName");
        putFieldWrapperMethodName("deptid","getDeptName");
        putFieldWrapperMethodName("roleid","getSingleRoleName");
        putFieldWrapperMethodName("userId","getUserAccountById");
        putFieldWrapperMethodName("roleIds","getRoleName");
    }
}
