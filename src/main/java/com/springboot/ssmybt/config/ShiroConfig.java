
/**
* 文件名：ShiroConfig.java
*
* 版本信息：
* 日期：2018年2月28日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springboot.ssmybt.core.shiro.MyShiroRealm;
import com.springboot.ssmybt.core.shiro.filter.KickoutSessionControlFilter;
import com.springboot.ssmybt.core.shiro.listener.ShiroSessionListener;
import com.springboot.ssmybt.module.system.entity.SysPermissionInit;
import com.springboot.ssmybt.module.system.service.SysPermissionInitService;

/**
*
* 项目名称：ssmybt
* 类名称：ShiroConfig
* 类描述：shiro权限管理的配置
* 创建人：liuc
* 创建时间：2018年2月28日 下午2:08:04
* 修改人：liuc
* 修改时间：2018年2月28日 下午2:08:04
* 修改备注：
* @version
*
*/
@Configuration
public class ShiroConfig {
	private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	@Autowired(required = false)
	SysPermissionInitService sysPermissionInitService;

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.lettuce.shutdown-timeout}")
	private int timeout;

	@Value("${spring.shiro.md5.hashIterations}")
	private int hashIterations;

	@Value("${ssmybt.session-invalidate-time}")
	private long sessionTimeout;

	@Value("${ssmybt.session-validation-interval}")
	private long sessionValidationInterval;

	@Value("${spring.shiro.rememberMeCookieTimeout}")
	private int rememberMeCookieTimeout;

	@Bean
	public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		// 自定义拦截器
		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
//		filtersMap.put("sessionexp", new SessionExpiredFilter());
		// 限制同一帐号同时在线的个数。
		filtersMap.put("kickout", kickoutSessionControlFilter());
		shiroFilterFactoryBean.setFilters(filtersMap);

		// 权限控制map.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		// 从数据库获取动态的权限
		// filterChainDefinitionMap.put("/add", "perms[权限添加]");
		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		// logout这个拦截器是shiro已经实现好了的。
		// 从数据库获取
		List<SysPermissionInit> list = sysPermissionInitService.selectAll();

		for (SysPermissionInit sysPermissionInit : list) {
			filterChainDefinitionMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
		}

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	* @author liuc
	* @date 2018年3月2日 下午3:20:22
	* @Description: 限制同一账号登录同时登录人数控制
	* @return Filter    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/

	public Filter kickoutSessionControlFilter() {
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		// 使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
		// 这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
		// 也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
		kickoutSessionControlFilter.setCacheManager(cacheManager());
		// 用于根据会话ID，获取会话进行踢出操作的；
		kickoutSessionControlFilter.setSessionManager(sessionManager());
		// 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
		kickoutSessionControlFilter.setKickoutAfter(false);
		// 同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
		kickoutSessionControlFilter.setMaxSession(1);
		// 被踢出后重定向到的地址；
		kickoutSessionControlFilter.setKickoutUrl("/kickout");
		return kickoutSessionControlFilter;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午4:56:56
	* @Description: cookie管理对象;记住我功能
	* @return RememberMeManager    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午4:58:03
	* @Description: cookie对象;
	* @return Cookie    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(rememberMeCookieTimeout);
		return simpleCookie;
	}

	/**
	 * 
	* @author liuc
	* @date 2018年2月28日 下午2:47:57
	* @Description: spring session管理器（多机环境）
	* @return ServletContainerSessionManager    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ssmybt", name = "spring-session-open", havingValue = "true")
	public ServletContainerSessionManager servletContainerSessionManager() {
		return new ServletContainerSessionManager();
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午2:29:23
	* @Description: Session Manager使用的是shiro-redis开源插件
	* @return SessionManager    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	@Bean
	@ConditionalOnProperty(prefix = "ssmybt", name = "spring-session-open", havingValue = "false")
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 会话超时时间，单位：毫秒
		sessionManager.setGlobalSessionTimeout(sessionTimeout);// 设置全局session超时时间
		// 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
		sessionManager.setSessionValidationInterval(sessionValidationInterval);
		sessionManager.setSessionIdUrlRewritingEnabled(false); // 去掉
																// JSESSIONID,shiro在1.3.2版本已经解决了这个问题，只需配置一下参数即可
		sessionManager.setSessionDAO(redisSessionDAO());
		sessionManager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setDeleteInvalidSessions(true);// 删除过期的session
		Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
		cookie.setName("shiroCookie");
		cookie.setHttpOnly(true);
		sessionManager.setSessionIdCookie(cookie);
		 Collection<SessionListener> listeners = new ArrayList<SessionListener>();
	     listeners.add(new ShiroSessionListener());
	     sessionManager.setSessionListeners(listeners);
		return sessionManager;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午5:03:58
	* @Description: RedisSessionDAO shiro sessionDao层的实现 通过redis使用的是shiro-redis开源插件
	* @return SessionDAO    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	@Bean
	public SessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午2:26:05
	* @Description: cacheManager 缓存 redis实现使用的是shiro-redis开源插件
	* @return CacheManager    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	public CacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午2:28:19
	* @Description: 配置shiro redisManager使用的是shiro-redis开源插件
	* @return RedisManager    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setExpire(timeout);// 配置缓存过期时间
		redisManager.setTimeout(timeout);
		// redisManager.setPassword(password);
		return redisManager;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午2:13:51
	* @Description: 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	* @return Realm    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	@Bean
	public Realm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	/**
	* @author liuc
	* @date 2018年2月28日 下午2:14:15
	* @Description: 凭证匹配器
	* 				（由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	*  				所以我们需要修改下doGetAuthenticationInfo中的代码;）
	* @return CredentialsMatcher    返回类型
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	*/
	@Bean
	public CredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		// 散列的次数，比如散列三次，相当于 md5(md5(""));
		hashedCredentialsMatcher.setHashIterations(hashIterations);
		return hashedCredentialsMatcher;
	}

	/**
	 * 
	* @author liuc
	* @date 2018年3月2日 下午3:13:00
	* @Description: 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;<br/>
	* 				拦截@ RequiresPermissions注释的方法
	* @return AuthorizationAttributeSourceAdvisor    返回类型
	* @param @param securityManager
	* @param @return(参数)
	* @throws
	* @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		logger.info("ShiroConfiguration.authorizaionAttributeSourceAdvisor : 实例化");
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
