package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyInMemoryRepository;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyInMemoryRepository companyInMemoryRepository;
    private final CompanyRepository companyJpaRepository;
    private final JpaContext jpaContext;

    public CompanyService(CompanyInMemoryRepository companyInMemoryRepository, CompanyRepository companyJpaRepository, JpaContext jpaContext) {
        this.companyInMemoryRepository = companyInMemoryRepository;
        this.companyJpaRepository = companyJpaRepository;
        this.jpaContext = jpaContext;
    }

    public List<Company> findAll(){
        return companyJpaRepository.findAll();
    }

    public Page<Company> findAll(int pageIndex, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        return companyJpaRepository.findAll(pageRequest);
    }

    public Company findById(Integer id) {
        return companyJpaRepository.findById(id).orElse(null);
    }


    public List<Employee> getEmployeesByCompanyId(Integer id) {
        Company company = companyJpaRepository.findById(id).orElse(null);
        return company.getEmployees();
    }

    public Company create(Company company) {
        return companyInMemoryRepository.addCompany(company);
    }

    public Company update(Integer id, Company company) {
        final var companyNeedToUpdate = companyInMemoryRepository
                .findById(id);

        var nameToUpdate = company.getName() == null ? companyNeedToUpdate.getName() : company.getName();
        var employeesToUpdate = company.getEmployees() == null ? companyNeedToUpdate.getEmployees() : company.getEmployees();

        final var companyToUpdate = new Company(id,nameToUpdate,employeesToUpdate);
        return companyInMemoryRepository.updateCompany(id, companyToUpdate);
    }
}
