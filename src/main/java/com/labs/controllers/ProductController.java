package com.labs.controllers;

import java.util.List;

import javax.validation.Valid;

import com.labs.dto.ResponseData;
import com.labs.dto.SearchDTO;
import com.labs.entities.Product;
import com.labs.entities.Supplier;
import com.labs.exceptions.SupplierNotFoundException;
import com.labs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        Iterable<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findOneById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Product>> createProduct(@Valid @RequestBody Product product, Errors errors) {

        ResponseData<Product> response = new ResponseData<>();

        try {
            response.setStatus(true);
            response.setData(productService.save(product));

        } catch (Exception e) {
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    response.getMessage().add(error.getDefaultMessage());
                }
                response.setStatus(false);
                response.setData(null);
                return ResponseEntity.badRequest().body(response);
            }
        }

        return ResponseEntity.ok(response);

    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> updateProduct(@Valid @RequestBody Product product, Errors errors) {

        ResponseData<Product> response = new ResponseData<>();

        try {
            response.setStatus(true);
            response.setData(productService.save(product));

        } catch (Exception e) {
            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    response.getMessage().add(error.getDefaultMessage());
                }
                response.setStatus(false);
                response.setData(null);
                return ResponseEntity.badRequest().body(response);
            }
        }

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") Long id) {
        productService.remove(id);
    }

    @PostMapping("/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productId) {
        productService.addSupplier(supplier, productId);
    }

    @PostMapping("/search/name")
    public Product findProductByName(@RequestBody SearchDTO searchDTO) {
        return productService.findByProductName(searchDTO.getSearchKeyword());
    }

    @PostMapping("/search/name-like")
    public List<Product> findProductByNameLike(@RequestBody SearchDTO searchDTO) {
        return productService.findByProductNameLike(searchDTO.getSearchKeyword());
    }

    @GetMapping("/search/category/{categoryId}")
    public List<Product> findProductByCategory(@PathVariable("categoryId") Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/search/supplier/{supplierId}")
    public List<Product> findProductBySupplier(@PathVariable("supplierId") Long supplierId)
            throws SupplierNotFoundException {
        return productService.findBySupplier(supplierId);
    }

}
