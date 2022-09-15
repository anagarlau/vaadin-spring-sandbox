package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.repository.CompanyRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    public List<Company> findAll(){
        return this.companyRepo.findAll();
    }

    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        List<Company> allCompanies = this.findAll();
        //loop
        return allCompanies.stream().collect(Collectors.toMap(company -> company.getName(), company -> company.getEmployees().size()));

    }
}
