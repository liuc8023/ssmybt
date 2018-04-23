
/**
* 文件名：RedisConfig.java
*
* 版本信息：
* 日期：2018年2月9日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.config;

import java.lang.reflect.Method;
import org.springframework.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
*
* 项目名称：ssmybt
* 类名称：RedisConfig
* 类描述：
* 创建人：liuc
* 创建时间：2018年2月9日 下午4:34:34
* 修改人：liuc
* 修改时间：2018年2月9日 下午4:34:34
* 修改备注：
* @version
*
*/
@Configuration
@EnableCaching
//继承CachingConfigurerSupport并重写方法,配合该注解实现spring缓存框架的启用
public class RedisConfig extends CachingConfigurerSupport{
	private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private int timeout;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWaitMillis;
    
    @SuppressWarnings("deprecation")
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
    	logger.info("JedisConnectionFactory注入成功！！");
    	logger.info("redis地址：" + host + ":" + port);
    	  JedisConnectionFactory factory = new JedisConnectionFactory();
          factory.setHostName(host);
          factory.setPort(port);
          factory.setTimeout(timeout); //设置连接超时时间
          return factory;
    }
    
    /**
	 * 重写缓存的key生成策略,可根据自身业务需要进行自己的配置生成条件
	 * 
	 * @see org.springframework.cache.annotation.CachingConfigurerSupport#
	 * keyGenerator()
	 */
 	@Bean
     public KeyGenerator keyGenerator() {
 		logger.info("RedisCacheConfig.keyGenerator init");
         return new KeyGenerator() {
             @Override
             public Object generate(Object target, Method method, Object... params) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
 				String[] value = new String[1];
 				Cacheable cacheable = method.getAnnotation(Cacheable.class);
 				if (cacheable != null) {
 					value = cacheable.value();
 				}
 				CachePut cachePut = method.getAnnotation(CachePut.class);
 				if (cachePut != null) {
 					value = cachePut.value();
 				}
 				CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
 				if (cacheEvict != null) {
 					value = cacheEvict.value();
 				}
 				sb.append(value[0]);
 				//遍历参数并且追加
 				for (Object obj : params) {
 					sb.append(":" + obj.toString());
 				}
 				logger.info("调用Redis缓存Key: " + sb.toString());
                return sb.toString();
             }

         };
     }

     
     /**
 	 * 重写Redis序列化方式，使用Json方式:
 	 * 当我们的数据存储到Redis的时候，我们的键（key）和值（value）都是通过Spring提供的Serializer序列化到数据库的。RedisTemplate默认使用的是JdkSerializationRedisSerializer，StringRedisTemplate默认使用的是StringRedisSerializer。
 	 * Spring Data JPA为我们提供了下面的Serializer：
 	 * GenericToStringSerializer、Jackson2JsonRedisSerializer、JacksonJsonRedisSerializer、JdkSerializationRedisSerializer、OxmSerializer、StringRedisSerializer。
 	 * 在此我们将自己配置RedisTemplate并定义Serializer。
 	 * @param redisConnectionFactory
 	 * @return
 	 */
     @Bean
     public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
     {
    	 logger.info("RedisCacheConfig.redisTemplate init");
         Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
         ObjectMapper om = new ObjectMapper();
         om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
         jackson2JsonRedisSerializer.setObjectMapper(om);
         RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
         template.setConnectionFactory(redisConnectionFactory);
         // 设置键（key）的序列化采用Jackson2JsonRedisSerializer。
         template.setKeySerializer(jackson2JsonRedisSerializer);
         template.setHashKeySerializer(jackson2JsonRedisSerializer);
         // 设置值（value）的序列化采用Jackson2JsonRedisSerializer。
         template.setValueSerializer(jackson2JsonRedisSerializer);
         template.setHashValueSerializer(jackson2JsonRedisSerializer);
         template.afterPropertiesSet();
         return template;
     }
     
     @Bean  
     public CacheErrorHandler errorHandler() {
    	 // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
         CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {  
             @Override  
             public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {  
                 System.out.println(key);  
             }  
   
             @Override  
             public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {  
                 System.out.println(value);  
             }  
   
             @Override  
             public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {  
   
             }  
   
             @Override  
             public void handleCacheClearError(RuntimeException e, Cache cache) {  
   
             }  
         };  
         return cacheErrorHandler;  
     } 
}
