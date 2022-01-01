package com.labs.repositories;

import com.labs.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Supplier findByEmail(String email);

    Supplier findByEmailContains(String email);

    Supplier findByNameContains(String name);
}
    
/**
 * Derived methods JPA references: 
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
 */