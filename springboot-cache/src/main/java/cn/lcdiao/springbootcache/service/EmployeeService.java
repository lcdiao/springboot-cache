package cn.lcdiao.springbootcache.service;

import cn.lcdiao.springbootcache.dao.EmployeeMapper;
import cn.lcdiao.springbootcache.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

//指定缓存组件的名字
@CacheConfig(cacheNames = "emp")    //抽取缓存的公共配置
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用方法
     *
     * CacheManager管理多个Cache组件，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字
     *
     * 原理：
     *  1、自动配置类：CacheAutoConfiguration
     *  2、缓存的配置类：
     *      org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     *      org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration   【默认】
     *      org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     *  3、哪个配置类默认生效?
     *      在yml配置文件中配置debug为true，查看配置信息，ctrl+f --》CacheConfiguration
     *      可查看到SimpleCacheConfiguration默认生效
     *  4、SimpleCacheConfiguration给容器中注册了一个CacheManager：ConcurrentMapCacheManager
     *  5、可以获取和创建ConcurrentMapCache类型的缓存组件；他的作用是将数据保存在ConcurrentMap中；
     *
     *  运行的流程：
     *  @Cacheable：
     *  1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取
     *      （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建
     *  2、去Cache中去查找缓存的内容，使用一个key，默认就是方法的参数
     *      key是按照某种策略生成的，默认是使用keyGenerator生成，默认使用SimpleKeyGenerator生成key
     *          SimpleKeyGenerator生成key的默认策略：
     *              如果没有参数，key=new SimpleKey();
     *              如果有一个参数：key=参数的值
     *              如果有多个参数：key=new SimpleKey(params)
     *  3、没有查到缓存就调用目标方法
     *  4、将目标方法的返回结果放进缓存中
     *
     * 核心：
     *  1）、使用 CacheManager【ConcurrentMapCacheManager】，按照名字得到Cache组件【ConcurrentMapCache】
     *  2）、key使用KeyGenerator生成的，默认是SimpleKeyGenerator
     *
     *
     * 几个属性：
     *      cacheNames/value：指定缓存组件的名字；将方法的返回结果放在哪个缓存中， 是数组的形式，可以指定多个缓存
     *      key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值，  1-方法的返回值
     *              可编写SpEL表达式;     #id:参数id的值     #a0   #p0  #root.args[0]
     *      keyGenerator:key的生成器；可以自己指定key的生成器的组件id     （key/keyGenerator：二选一使用）
     *      cacheManager：指定缓存管理器；或者cacheResolver指定缓存解析器（二选一）
     *      condition：指定符合条件的情况下才缓存
     *      unless：否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断，
     *          unless="#result == null"
     *          unless="#a0==1":如果第一个参数的值是1，结果不缓存
     *      sync:是否使用异步模式
     * @param id
     * @return
     */
    //@Cacheable(cacheNames = "emp",condition = "#id>0",key = "#root.methodName+'['+#id+']'")
    //@Cacheable(cacheNames = "emp",condition = "#id>0",keyGenerator = "myKeyGenerator",unless="#a0==1")
    @Cacheable(/*cacheNames = "emp",*/key = "#id")
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        return employeeMapper.selectByPrimaryKey(id) ;
    }


    /**
     * 先调用目标方法，在将目标方法的结果缓存起来
     * 注意查询的时候缓存的key为id值，虽然是同个生成策略(myKeyGenerator)，但生成的key不同，缓存的也不同
     * 改为用返回值的id为key
     * @param employee
     * @return
     */
    //@CachePut(cacheNames = "emp",keyGenerator = "myKeyGenerator")
    @CachePut(/*cacheNames = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新:"+employee);
        employeeMapper.updateByPrimaryKeySelective(employee);
        return employee;
    }


    //allEntries : 删除缓存中的所有数据
    //beforeInvocation: 缓存的清除是否在方法执行之前，默认代表在方法执行之后执行
    @CacheEvict(/*value = "emp",*/key = "#id",allEntries = true,beforeInvocation = false)
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
        employeeMapper.deleteByPrimaryKey(id);
    }


    /*
    以lastName为key缓存数据并更新以id、email为key的缓存
     */
    @Caching(
            cacheable = {
                @Cacheable(/*value = "emp",*/key = "#lastName")
            },
            put = {
                @CachePut(/*value = "emp",*/key = "#result.id"),
                @CachePut(/*value = "emp",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
