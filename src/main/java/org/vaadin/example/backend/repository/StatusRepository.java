package org.vaadin.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.example.backend.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
