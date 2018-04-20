package com.springboot.ssmybt.common.dictmap.factory;



import java.lang.reflect.Method;

import com.springboot.ssmybt.common.factory.ConstantFactory;
import com.springboot.ssmybt.common.factory.IConstantFactory;
import com.springboot.ssmybt.core.exception.BizExceptionEnum;
import com.springboot.ssmybt.core.exception.SystemException;

/**
 * 字段的包装创建工厂
 *
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object field, String methodName) {
        IConstantFactory me = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, field.getClass());
            Object result = method.invoke(me, field);
            return result;
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                Object result = method.invoke(me, Integer.parseInt(field.toString()));
                return result;
            } catch (Exception e1) {
                throw new SystemException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }

}
