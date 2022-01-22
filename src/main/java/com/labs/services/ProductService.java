package com.labs.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.labs.constants.ProductConstant;
import com.labs.entities.Product;
import com.labs.entities.Supplier;
import com.labs.exceptions.SupplierNotFoundException;
import com.labs.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findOneById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return null;
        }
        return optionalProduct.get();
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContains(name);
    }

    public void addSupplier(Supplier supplier, Long productId) {
        Product product = findOneById(productId);
        if (product == null) {
            throw new RuntimeException("Product with ID: " + productId + " not found");
        }

        product.getSuppliers().add(supplier);
        save(product);

    }

    public Product findByProductName(String name) {
        return productRepository.findProductByName(name);
    }

    public List<Product> findByProductNameLike(String name) {
        return productRepository.findProductByNameLike("%" + name + "%");
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findProductByCategory(categoryId);
    }

    public List<Product> findBySupplier(Long supplierId) throws SupplierNotFoundException {
        Supplier supplier = supplierService.findOneById(supplierId);
        if (supplier == null) {
            throw new SupplierNotFoundException(ProductConstant.SUPPLIER_NOT_FOUND, null, false, false);
        }

        return productRepository.findProductBySupplier(supplier);
    }



    
}
