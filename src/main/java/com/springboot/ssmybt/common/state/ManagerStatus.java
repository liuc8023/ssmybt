
/**
* 文件名：ManagerStatus.java
*
* 版本信息：
* 日期：2018年4月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common.state;


/**
*
* 项目名称：ssmybt
* 类名称：ManagerStatus
* 类描述：管理员的状态
* 创建人：liuc
* 创建时间：2018年4月9日 上午10:29:15
* 修改人：liuc
* 修改时间：2018年4月9日 上午10:29:15
* 修改备注：
* @version
*
*/
public enum ManagerStatus {

    OK(1, "启用"), FREEZED(2, "冻结"), DELETED(3, "被删除");

    int code;
    String message;

    ManagerStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (ManagerStatus ms : ManagerStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
