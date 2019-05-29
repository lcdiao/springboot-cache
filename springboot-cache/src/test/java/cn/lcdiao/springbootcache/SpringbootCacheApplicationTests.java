package cn.lcdiao.springbootcache;

import cn.lcdiao.springbootcache.dao.EmployeeMapper;
import cn.lcdiao.springbootcache.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() {
        Employee employee = employeeMapper.selectByPrimaryKey(1);
        System.out.println(employee);
    }

}
