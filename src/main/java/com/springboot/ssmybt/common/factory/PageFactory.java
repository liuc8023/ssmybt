package com.springboot.ssmybt.common.factory;

import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.ssmybt.common.constant.Order;
import com.springboot.ssmybt.core.utils.HttpUtils;
import com.springboot.ssmybt.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpUtils.getRequest();
        int limit = Integer.valueOf(request.getParameter("limit"));     //每页多少条数据
        int offset = Integer.valueOf(request.getParameter("offset"));   //每页的偏移量(本页当前有多少条)
        String sort = request.getParameter("sort");         //排序字段名称
        String order = request.getParameter("order");       //asc或desc(升序或降序)
        if (StringUtils.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}
