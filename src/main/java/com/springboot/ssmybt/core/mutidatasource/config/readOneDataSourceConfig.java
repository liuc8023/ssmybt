
/**
* 文件名：read1DataSourceConfig.java
*
* 版本信息：
* 日期：2018年2月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.core.mutidatasource.config;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

/**
*
* 项目名称：ssmybt
* 类名称：read1DataSourceConfig
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月9日 上午11:00:17
* 修改人：liuc
* 修改时间：2018年2月9日 上午11:00:17
* 修改备注：
* @version
*
*/
@Configuration
public class readOneDataSourceConfig {
	private static Logger log = LoggerFactory.getLogger(readOneDataSourceConfig.class);
	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Value("${spring.datasource.read1.url}")
	private String dbUrl;

	@Value("${spring.datasource.read1.username}")
	private String username;

	@Value("${spring.datasource.read1.password}")
	private String password;

	@Value("${spring.datasource.read1.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.read1.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.read1.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.read1.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.read1.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.read1.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.read1.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.read1.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.read1.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.read1.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.read1.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.read1.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.read1.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.read1.filters}")
	private String filters;

	@Value("${spring.datasource.read1.connectionProperties}")
	private String connectionProperties;

	/**
	 * 读库 数据源配置
	 * @return
	 */
	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "spring.datasource.read1")
	public DataSource read1OneDataSource() {
		log.info("-------------------- read1 DataSourceOne init ---------------------");
		DruidDataSource datasource = new DruidDataSource();
		datasource.setUrl(this.dbUrl);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setDriverClassName(driverClassName);
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return datasource;
	}
}
