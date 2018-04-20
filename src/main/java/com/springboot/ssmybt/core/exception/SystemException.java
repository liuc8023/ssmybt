package com.springboot.ssmybt.core.exception;

/**
 * 封装系统的异常
 *
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;

    private String message;

    public SystemException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
