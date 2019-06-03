package cn.lcdiao.springbootcache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 一、搭建基本环境：
 * 1、在数据库创建出department和employee表
 * 2、整合mybatis操作数据库（创建好javabean和对应的映射）
 *      1.配置数据源信息
 *      2.使用@MapperScan指定需要扫描的mapper接口所在的包
 * 二、快速 体验缓存
 *    步骤：
 *      1、开启基于注解的缓存 @EnableCaching
 *      2、标注缓存注解即可
 *              @Cacheable      方法自治县之前先检查缓存中有没有这个数据，默认安装参数的值作为key去查询缓存，如果没有就运行方法并将结果放入缓存
 *              @CacheEvict     缓存清除，调用方法后把之前的缓存清除掉
 *              @CachePut       既调用方法，又更新缓存数据（修改了数据库的某个数据，同时更新缓存）
 * 默认使用的是ConcurrentMapCacheManager == ConcurrentMapCache；将数据保存在ConcurrentMap<Object, Object>
 * 三、整合redis作为缓存
 *      1、安装redis：使用docker
 *      2、引入redis的starter
 *      3、配置redis
 *      4、测试缓存
 *          原理：CacheManager====Cache 缓存组件来实际给缓存中存取数据
 *          1）、引入redis的starter，容器中保存的是 RedisCacheManager
 *          2）、RedisCacheManager 帮我们创建 RedisCache 来作为缓存组件，RedisCache通过操作redis缓存数据的
 *          3）、默认保存数据 k-v 都是Object；利用序列化保存；
 *                  如何保存为json
 *                      1、引入了redis的starter、cacheManager变为RedisCacheManager
 *                      2、默认创建的RedisCacheManager 操作redis的时候使用的是RedisTemplate<Object,Object>
 *                      3、RedisTemplate<Object,Object> 是默认使用jdk的序列化机制
 *          4）、自定义CacheManager
 *
 */
@SpringBootApplication
@MapperScan("cn.lcdiao.springbootcache.dao")
@EnableCaching
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }

}
