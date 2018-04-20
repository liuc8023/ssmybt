package com.springboot.ssmybt.core.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.springboot.ssmybt.core.base.tips.ErrorTip;
import com.springboot.ssmybt.core.exception.SystemException;
import com.springboot.ssmybt.core.exception.SystemExceptionEnum;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 * @author liuc
 *
 */

public class BaseControllerExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(BaseControllerExceptionHandler.class);

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(SystemException e) {
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return new ErrorTip(SystemExceptionEnum.SERVER_ERROR.getCode(), SystemExceptionEnum.SERVER_ERROR.getMessage());
    }

}
