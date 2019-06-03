package cn.lcdiao.springbootcache.service;

import cn.lcdiao.springbootcache.dao.DepartmentMapper;
import cn.lcdiao.springbootcache.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/6/3 11:59
 */
@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    CacheManager cacheManager;
/*
    @Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id) {
        System.out.println("查询的部门id:" + id);
        Department department = departmentMapper.selectByPrimaryKey(id);
        return department;
    }*/

    public Department getDeptById(Integer id) {
        System.out.println("查询的部门id:" + id);
        Department department = departmentMapper.selectByPrimaryKey(id);
        //获取某个缓存
        Cache dept = cacheManager.getCache("dept");
        dept.put("dept:" + id,department);
        return department;
    }
}
