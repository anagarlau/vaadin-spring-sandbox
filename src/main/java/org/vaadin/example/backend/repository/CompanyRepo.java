package org.vaadin.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.backend.entity.Company;
@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
}
