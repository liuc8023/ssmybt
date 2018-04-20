
/**
* 文件名：BeetlConfig.java
*
* 版本信息：
* 日期：2018年3月10日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.io.IOException;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.springboot.ssmybt.core.shiro.util.ShiroUtil;
import com.springboot.ssmybt.core.utils.StringUtils;



/**
*
* 项目名称：ssmybt
* 类名称：BeetlConfig
* 类描述：web 配置类
* 创建人：liuc
* 创建时间：2018年3月10日 上午12:08:38
* 修改人：liuc
* 修改时间：2018年3月10日 上午12:08:38
* 修改备注：
* @version
*
*/
@Configuration
public class BeetlConfig {
//	@Autowired
//    private BeetlProperties beetlProperties;

	@Bean(initMethod = "init")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        try {
            // WebAppResourceLoader 配置root路径是关键
            WebAppResourceLoader webAppResourceLoader =
                    new WebAppResourceLoader(patternResolver.getResource("classpath:/templates/view").getFile().getPath());
            beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取配置文件信息
        return beetlGroupUtilConfiguration;
    }

    @Bean
    public BeetlSpringViewResolver getBeetlSpringViewResolver() {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(getBeetlGroupUtilConfiguration());
        return beetlSpringViewResolver;
    }
    
    @Bean
    public GroupTemplate getGroupTemplate() {
    	BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = getBeetlGroupUtilConfiguration();
    	GroupTemplate groupTemplate = beetlGroupUtilConfiguration.getGroupTemplate();
    	groupTemplate.registerFunctionPackage("tool", new StringUtils());
    	groupTemplate.registerFunctionPackage("shiro", new ShiroUtil());
        return groupTemplate;

    }



}