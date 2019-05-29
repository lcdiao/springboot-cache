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
 *              @CacheEvict
 *              @CachePut       既调用方法，又更新缓存数据（修改了数据库的某个数据，同时更新缓存）
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
