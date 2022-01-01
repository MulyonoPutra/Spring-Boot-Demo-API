package com.labs.services;

import java.util.Optional;
import javax.transaction.Transactional;
import com.labs.entities.Supplier;
import com.labs.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier findOneById(Long id) {
        Optional<Supplier> supplierId = supplierRepository.findById(id);
        if (!supplierId.isPresent()) {
            return null;
        }
        return supplierId.get();
    }

    public Iterable<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public void remove(Long id) {
        supplierRepository.deleteById(id);
    }

    public Supplier findByEmail(String email) {
        return supplierRepository.findByEmail(email);
    }

    public Supplier findByEmailContains(String email) {
        return supplierRepository.findByEmailContains(email);
    }

    public Supplier findByName(String name) {
        return supplierRepository.findByNameContains(name);
    }
    
}
