
/**
* 文件名：BaseController.java
*
* 版本信息：
* 日期：2018年3月5日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.base.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.core.base.tips.SuccessTip;
import com.springboot.ssmybt.core.base.warpper.BaseControllerWarpper;
import com.springboot.ssmybt.core.page.PageInfoBT;
import com.springboot.ssmybt.core.utils.HttpUtils;

/**
*
* 项目名称：ssmybt
* 类名称：BaseController
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月5日 下午3:06:24
* 修改人：liuc
* 修改时间：2018年3月5日 下午3:06:24
* 修改备注：
* @version
*
*/
public class BaseController {
	protected static String SUCCESS = "SUCCESS";
    protected static String ERROR = "ERROR";

    protected static String REDIRECT = "redirect:";
    protected static String FORWARD = "forward:";
    
    protected SuccessTip SUCCESS_TIP = new SuccessTip();
    
    protected HttpServletRequest getHttpServletRequest() {
        return HttpUtils.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpUtils.getResponse();
    }

    protected HttpSession getSession() {
        return HttpUtils.getRequest().getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return HttpUtils.getRequest().getSession(flag);
    }
    
    protected String getPara(String name) {
        return HttpUtils.getRequest().getParameter(name);
    }
    
    protected void setAttr(String name, Object value) {
        HttpUtils.getRequest().setAttribute(name, value);
    }
    
    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfoBT<T> packForBT(Page<T> page) {
        return new PageInfoBT<T>(page);
    }
    
    /**
     * 包装一个list，让list增加额外属性
     */
    protected Object warpObject(BaseControllerWarpper warpper) {
        return warpper.warp();
    }


    /**
     * 返回前台文件流
     *
     * @author liuc
     */
    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
        String dfileName = null;
        try {
            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
    }
}
