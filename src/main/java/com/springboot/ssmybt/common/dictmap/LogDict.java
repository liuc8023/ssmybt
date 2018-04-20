package com.springboot.ssmybt.common.dictmap;

import com.springboot.ssmybt.common.dictmap.base.AbstractDictMap;

/**
 * 日志的字典
 
 */
public class LogDict extends AbstractDictMap {

    @Override
    public void init() {
        put("tips","备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}