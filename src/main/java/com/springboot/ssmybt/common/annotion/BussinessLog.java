package com.springboot.ssmybt.common.annotion;


import java.lang.annotation.*;

import com.springboot.ssmybt.common.dictmap.base.AbstractDictMap;
import com.springboot.ssmybt.common.dictmap.base.SystemDict;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BussinessLog {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";

    /**
     * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
     */
    String key() default "id";

    /**
     * 字典(用于查找key的中文名称和字段的中文名称)
     */
    Class<? extends AbstractDictMap> dict() default SystemDict.class;
}
