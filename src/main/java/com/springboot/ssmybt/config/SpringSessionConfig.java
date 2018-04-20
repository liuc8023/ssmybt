
/**
* 文件名：SpringSessionConfig.java
*
* 版本信息：
* 日期：2018年3月5日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
*
* 项目名称：ssmybt
* 类名称：SpringSessionConfig
* 类描述：
* 创建人：liuc
* 创建时间：2018年3月5日 下午2:47:49
* 修改人：liuc
* 修改时间：2018年3月5日 下午2:47:49
* 修改备注：
* @version
*
*/
@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) //session过期时间  如果部署多机环境,需要打开注释
@ConditionalOnProperty(prefix = "ssmybt", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
