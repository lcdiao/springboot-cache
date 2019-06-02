package cn.lcdiao.springbootcache.controller;

import cn.lcdiao.springbootcache.entity.Employee;
import cn.lcdiao.springbootcache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer  id){
        Employee employee = employeeService.getEmp(id);

        return employee;
    }


    @GetMapping("/emp")
    public Employee updateEmp(Employee employee) {
        return employeeService.updateEmp(employee);
    }

    @GetMapping("/deleteEmp")
    public String deleteEmp(Integer id) {
        employeeService.deleteEmp(id);
        return "delete success";
    }

    @GetMapping("/getEmpByLastName")
    public Employee getEmpByLastName(String lastName) {
        return employeeService.getEmpByLastName(lastName);
    }
}
