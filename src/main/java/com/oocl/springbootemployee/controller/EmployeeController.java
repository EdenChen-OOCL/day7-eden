package com.oocl.springbootemployee.controller;


import java.util.List;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployeeList() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam Gender gender) {
        return employeeService.findAll(gender);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable Integer id) {
       employeeService.delete(id);
    }

    @GetMapping(params = {"pageIndex", "pageSize"})
    public List<Employee> getAllByPageSize(@RequestParam Integer pageIndex, @RequestParam Integer pageSize){
        List<Employee> all = employeeService.findAll(pageIndex, pageSize);
        return all;
    }
}
