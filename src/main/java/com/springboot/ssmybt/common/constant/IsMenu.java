
/**
* 文件名：IsMenu.java
*
* 版本信息：
* 日期：2018年3月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common.constant;


/**
*
* 项目名称：ssmybt
* 类名称：IsMenu
* 类描述：是否是菜单的枚举
* 创建人：liuc
* 创建时间：2018年3月9日 上午11:10:41
* 修改人：liuc
* 修改时间：2018年3月9日 上午11:10:41
* 修改备注：
* @version
*
*/
public enum IsMenu {

    YES(1, "是"),
    NO(0, "不是");//不是菜单的是按钮

    int code;
    String message;

    IsMenu(int code, String message) {
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

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (IsMenu s : IsMenu.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
