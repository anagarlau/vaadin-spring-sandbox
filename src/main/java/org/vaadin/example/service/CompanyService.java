package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.repository.CompanyRepo;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    public List<Company> findAll(){
        return this.companyRepo.findAll();
    }
}
