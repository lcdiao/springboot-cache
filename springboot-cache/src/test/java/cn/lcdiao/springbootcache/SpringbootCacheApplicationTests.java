package cn.lcdiao.springbootcache;

import cn.lcdiao.springbootcache.dao.EmployeeMapper;
import cn.lcdiao.springbootcache.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;

    //操作k-v都是字符串的
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //k-v都是对象的
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;


    /**
     * Redis常见五大数据类型
     * String(字符串)、 List(列表) 、Set(集合) 、 Hash(散列) 、 ZSet(有序散列)
     *
     * stringRedisTemplate.opsForValue()[String(字符串)]
     * stringRedisTemplate.opsForList()[List(列表)]
     * stringRedisTemplate.opsForSet()[Set(集合)]
     * stringRedisTemplate.opsForHash()[Hash(散列)]
     * stringRedisTemplate.opsForZSet()[ZSet(有序散列)]
     */
    @Test
    public void test01(){
        //给redis中保存数据
        //stringRedisTemplate.opsForValue().append("msg","hello");
        //从redis中查询数据
        //System.out.println(stringRedisTemplate.opsForValue().get("msg"));

        stringRedisTemplate.opsForList().leftPush("mylist","1");
        stringRedisTemplate.opsForList().leftPush("mylist","2");
        stringRedisTemplate.opsForList().leftPush("mylist","3");
        stringRedisTemplate.opsForList().leftPush("mylist","4");
        stringRedisTemplate.opsForList().leftPush("mylist","5");
    }

    @Test
    public void test02(){
        Employee employee = employeeMapper.selectByPrimaryKey(1);
        //默认如果保存对象，使用jdk序列号机制，序列化后的数据保存到redis中
        //redisTemplate.opsForValue().set("emp-01",employee);
        /*
        1、将数据以json的方式保存
            （1）自己将对象转为json
            （2）redisTemplate默认的序列化规则:改变默认的序列化规则
         */
        empRedisTemplate.opsForValue().set("emp02",employee);
    }

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.selectByPrimaryKey(1);
        System.out.println(employee);
    }

}
