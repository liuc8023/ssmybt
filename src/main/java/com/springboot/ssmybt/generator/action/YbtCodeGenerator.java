package com.springboot.ssmybt.generator.action;

import com.springboot.ssmybt.generator.action.config.YbtGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 * @author liuc
 *
 */
public class YbtCodeGenerator {
    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        YbtGeneratorConfig gunsGeneratorConfig = new YbtGeneratorConfig();
        gunsGeneratorConfig.doMpGeneration();

        /**
         * adi的生成器:
         *      adi的代码生成器可以生成controller,html页面,页面对应的js
         */
        gunsGeneratorConfig.doAdiGeneration();
    }

}