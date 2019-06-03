package cn.lcdiao.springbootcache.controller;

import cn.lcdiao.springbootcache.entity.Department;
import cn.lcdiao.springbootcache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: diao
 * @Description:
 * @Date: 2019/6/3 12:06
 */
@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @GetMapping("/dept/{id}")
    public Department getDeptById(@PathVariable("id") Integer  id) {
        return deptService.getDeptById(id);
    }
}
