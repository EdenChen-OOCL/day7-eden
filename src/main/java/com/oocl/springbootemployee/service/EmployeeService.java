package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.EmployeeAgeNotValidException;
import com.oocl.springbootemployee.exception.EmployeeAgeSalaryNotMatchedException;
import com.oocl.springbootemployee.exception.EmployeeInactiveException;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeInMemoryRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class EmployeeService {
    private final EmployeeInMemoryRepository employeeInMemoryRepository;
    private final EmployeeRepository employeeJpaRepository;

    public EmployeeService(EmployeeInMemoryRepository employeeInMemoryRepository, EmployeeRepository employeeJpaRepository) {
        this.employeeInMemoryRepository = employeeInMemoryRepository;
        this.employeeJpaRepository = employeeJpaRepository;
    }

    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    public List<Employee> findAll(Gender gender) {
        return employeeJpaRepository.getAllByGender(gender);
    }

    public List<Employee> findAll(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Employee> all = employeeJpaRepository.findAll(pageRequest);
        return all.getContent();
//        return employeeJpaRepository.findAll(pageRequest).getContent();
    }

    public Employee findById(Integer employeeId) {
        return employeeJpaRepository.findById(employeeId)
                .orElse(null);
    }

    public Employee create(Employee employee) {
        if(employee.getAge() < 18 || employee.getAge() > 65)
            throw new EmployeeAgeNotValidException();
        if(employee.getAge() >= 30 && employee.getSalary() < 20000.0)
            throw new EmployeeAgeSalaryNotMatchedException();

        employee.setActive(true);
        return employeeJpaRepository.save(employee);
    }

    public Employee update(Integer employeeId , Employee employee) {
        Employee employeeExisted = employeeJpaRepository.findById(employeeId).orElse(null);
        if(Objects.isNull(employeeExisted) || !employeeExisted.getActive())
            throw new EmployeeInactiveException();

        employee.setId(employeeId);
        return employeeJpaRepository.save(employee);
    }

    public void delete(Integer employeeId) {
        employeeJpaRepository.deleteById(employeeId);
    }
}
