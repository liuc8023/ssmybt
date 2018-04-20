package com.springboot.ssmybt.core.log.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springboot.ssmybt.common.constant.LogSucceed;
import com.springboot.ssmybt.common.constant.LogType;
import com.springboot.ssmybt.core.db.Db;
import com.springboot.ssmybt.core.utils.StringUtils;
import com.springboot.ssmybt.module.system.dao.SysLoginLogMapper;
import com.springboot.ssmybt.module.system.dao.SysOperationLogMapper;
import com.springboot.ssmybt.module.system.entity.SysLoginLog;
import com.springboot.ssmybt.module.system.entity.SysOperationLog;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 */
public class LogTaskFactory {
    private static Logger logger = LoggerFactory.getLogger(LogTaskFactory.class);
    
    private static SysLoginLogMapper loginLogMapper = (SysLoginLogMapper) Db.getMapper(SysLoginLogMapper.class);
    private static SysOperationLogMapper operationLogMapper = (SysOperationLogMapper) Db.getMapper(SysOperationLogMapper.class);

    public static TimerTask loginLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    SysLoginLog loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                SysLoginLog loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Integer userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
            	SysLoginLog loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null,ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Integer userId, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                SysOperationLog operationLog = LogFactory.createOperationLog(
                        LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = StringUtils.getExceptionMsg(exception);
                SysOperationLog operationLog = LogFactory.createOperationLog(
                        LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
