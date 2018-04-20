
/**
* 文件名：MybatisPlusConfig.java
*
* 版本信息：
* 日期：2018年2月7日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.io.IOException;
import java.util.*;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageInterceptor;
import com.springboot.ssmybt.core.mutidatasource.MyAbstractRoutingDataSource;
import com.springboot.ssmybt.core.mutidatasource.Interceptor.SqlPrintInterceptor;
import com.springboot.ssmybt.core.mutidatasource.constant.DataSourceType;
import com.springboot.ssmybt.core.utils.SpringContextUtil;

/**
*
* 项目名称：ssmybt
* 类名称：MybatisPlusConfig
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月7日 上午11:02:10
* 修改人：liuc
* 修改时间：2018年2月7日 上午11:02:10
* 修改备注：
* @version
*
*/
@Configuration
@EnableTransactionManagement(order = 2) // 由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = { "com.springboot.ssmybt.module.*.dao" })
public class MybatisPlusConfig {
	private static Logger log = LoggerFactory.getLogger(MybatisPlusConfig.class);
	@Value("${spring.datasource.readSize}")
	private String dataSourceSize;
	@Autowired
	@Qualifier("writeDataSource")
	private DataSource writeDataSource;

	@Autowired
	@Qualifier("readDataSource1")
	private DataSource readDataSource1;

	@Autowired(required = false)
	private Interceptor[] interceptors;

	@Autowired(required = false)
	private DatabaseIdProvider databaseIdProvider;

	@Autowired
	private MybatisProperties properties;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactorys() throws Exception {
		log.info("--------------------  重载父类sqlSessionFactory init ---------------------");
		try {
			SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
			sessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
			if (this.databaseIdProvider != null) {
				sessionFactoryBean.setDatabaseIdProvider(this.databaseIdProvider);
			}
			// 读取配置
			if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
				sessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
			}
			if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
				sessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
			}
			// 设置mapper.xml文件所在位置
			if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
				sessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
			}
			// 设置mybatis-config.xml配置文件位置
			// sessionFactoryBean.setConfigLocation(new
			// DefaultResourceLoader().getResource(configLocation));
			sessionFactoryBean.setVfs(SpringBootVFS.class);
			// 添加分页插件、打印sql插件
			interceptors = new Interceptor[] { pageHelper(), new SqlPrintInterceptor() };
			sessionFactoryBean.setPlugins(interceptors);

			return sessionFactoryBean.getObject();
		} catch (IOException e) {
			log.error("mybatis resolver mapper*xml is error", e);
			return null;
		} catch (Exception e) {
			log.error("mybatis sqlSessionFactoryBean create error", e);
			return null;
		}
	}

	/**
	   * 
	  * @author liuc
	  * @date 2018年1月27日 下午11:09:11
	  * @Description: 配置mybatis的分页插件pageHelper
	  * @return PageInterceptor    返回类型
	  * @param @return(参数)
	  * @throws
	  * @since CodingExample　Ver(编码范例查看) 1.1
	   */
	@Bean
	public PageInterceptor pageHelper() {
		log.info("MyBatisConfiguration.pageHelper()");
		/**
		 * 5.0 是用这个类 com.github.pagehelper.PageInterceptor
		 */
		PageInterceptor pageHelper = new PageInterceptor();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		// p.setProperty("dialect","mysql");
		// //配置mysql数据库的方言(新版本能自动识别底层数据库,可以不用配置)
		pageHelper.setProperties(p);
		return pageHelper;
	}

	/**
	 * 把所有数据库都放在路由中
	 * @return
	 */
	@Bean(name = "roundRobinDataSouceProxy")
	public AbstractRoutingDataSource roundRobinDataSouceProxy() {
		int size = Integer.parseInt(dataSourceSize);
		MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
		// 否则切换数据源时找不到正确的数据源
		targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
		targetDataSources.put(DataSourceType.read.getType() + "1", readDataSource1);

		proxy.setDefaultTargetDataSource(writeDataSource);
		proxy.setTargetDataSources(targetDataSources);
		return proxy;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	// 事务管理
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager((DataSource) SpringContextUtil.getBean("roundRobinDataSouceProxy"));
	}

}
