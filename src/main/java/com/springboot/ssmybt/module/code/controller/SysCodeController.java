package com.springboot.ssmybt.module.code.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.ssmybt.config.properties.DruidProperties;
import com.springboot.ssmybt.core.base.controller.BaseController;
import com.springboot.ssmybt.generator.action.config.WebGeneratorConfig;
import com.springboot.ssmybt.generator.action.model.GenQo;
import com.springboot.ssmybt.module.code.factory.DefaultTemplateFactory;
import com.springboot.ssmybt.module.code.service.TableService;

/**
 * 代码生成控制器
 * @author liuc
 *
 */
@RestController
@RequestMapping("/code")
public class SysCodeController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SysCodeController.class);

    private static String PREFIX = "code/";

    @Autowired
    private TableService tableService;

    @Autowired
    private DruidProperties druidProperties;
    
	@Value("${spring.datasource.db-name}")
	private String dbName;

    /**
     * 跳转到代码生成主页
     */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView blackboard(Model model) {
		logger.info("跳转到代码生成主页");
		model.addAttribute("tables", tableService.selectAllTables(dbName));
	    model.addAttribute("params", DefaultTemplateFactory.getDefaultParams());
	    model.addAttribute("templates", DefaultTemplateFactory.getDefaultTemplates());
		ModelAndView mv = new ModelAndView(PREFIX+"code.html");
		return mv;
	}

    /**
     * 生成代码
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Object generate(GenQo genQo) {
        genQo.setUrl(druidProperties.getUrl());
        genQo.setUserName(druidProperties.getUsername());
        genQo.setPassword(druidProperties.getPassword());
        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        webGeneratorConfig.doMpGeneration();
        webGeneratorConfig.doAdiGeneration();
        return SUCCESS_TIP;
    }
}
