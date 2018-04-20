
/**
* 文件名：WriteDataSourceConfig.java
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
import org.springframework.context.annotation.Primary;
import com.alibaba.druid.pool.DruidDataSource;

/**
*
* 项目名称：ssmybt
* 类名称：WriteDataSourceConfig
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
public class WriteDataSourceConfig {
	private static Logger log = LoggerFactory.getLogger(WriteDataSourceConfig.class);
	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Value("${spring.datasource.write.url}")
	private String dbUrl;

	@Value("${spring.datasource.write.username}")
	private String username;

	@Value("${spring.datasource.write.password}")
	private String password;

	@Value("${spring.datasource.write.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.write.initialSize}")
	private int initialSize;

	@Value("${spring.datasource.write.minIdle}")
	private int minIdle;

	@Value("${spring.datasource.write.maxActive}")
	private int maxActive;

	@Value("${spring.datasource.write.maxWait}")
	private int maxWait;

	@Value("${spring.datasource.write.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.write.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;

	@Value("${spring.datasource.write.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.write.testWhileIdle}")
	private boolean testWhileIdle;

	@Value("${spring.datasource.write.testOnBorrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.write.testOnReturn}")
	private boolean testOnReturn;

	@Value("${spring.datasource.write.poolPreparedStatements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.write.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Value("${spring.datasource.write.filters}")
	private String filters;

	@Value("${spring.datasource.write.connectionProperties}")
	private String connectionProperties;


	/**
	 * 写库 数据源配置
	 * @return
	 */
	@Bean(name = "writeDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.write")
	public DataSource writeDataSource() {
		log.info("-------------------- writeDataSource init ---------------------");
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
