
/**
* 文件名：Swagger2Config.java
*
* 版本信息：
* 日期：2018年3月28日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
*
* 项目名称：ssmybt
* 类名称：Swagger2Config
* 类描述：swagger配置类
* 创建人：liuc
* 创建时间：2018年3月28日 下午4:08:10
* 修改人：liuc
* 修改时间：2018年3月28日 下午4:08:10
* 修改备注：
* @version
*
*/
@Configuration
@EnableSwagger2//加上注解@EnableSwagger2 表示开启Swagger
@ConditionalOnProperty(prefix = "ssmybt", name = "swagger-open", havingValue = "true")
public class Swagger2Config {
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))                         //这里采用包含注解的方式来确定要显示的接口
//                .apis(RequestHandlerSelectors.basePackage("com.springboot.ssmybt"))    //这里采用包扫描的方式来确定要显示的接口
                .paths(PathSelectors.any())
                .build();
    }


	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("银保通系统数据接口文档")
                .description("银保通 Api文档")
                .termsOfServiceUrl("http://git.oschina.net/naan1993/guns")
                .contact(new Contact("卡布奇诺的味道", "https://blog.csdn.net/lc1010078424", "1010078424@qq.com"))//创建人
                .version("1.0")//版本号
                .build();
    }
    

}
