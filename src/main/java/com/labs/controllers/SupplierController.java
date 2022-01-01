package com.labs.controllers;

import javax.validation.Valid;

import com.labs.dto.ResponseData;
import com.labs.dto.SearchDTO;
import com.labs.dto.SupplierDTO;
import com.labs.entities.Supplier;
import com.labs.services.SupplierService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Iterable<Supplier>> findAll() {
        Iterable<Supplier> categories = supplierService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Supplier findById(@PathVariable("id") Long id) {
        return supplierService.findOneById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> create(@Valid @RequestBody SupplierDTO supplierDTO, Errors errors) {

        ResponseData<Supplier> response = new ResponseData<>();

        try {
            Supplier supplier = mapper.map(supplierDTO, Supplier.class);
            response.setStatus(true);
            response.setData(supplierService.save(supplier));

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
    public ResponseEntity<ResponseData<Supplier>> update(@Valid @RequestBody SupplierDTO supplierDTO, Errors errors) {

        ResponseData<Supplier> response = new ResponseData<>();

        try {
            Supplier supplier = mapper.map(supplierDTO, Supplier.class);
            response.setStatus(true);
            response.setData(supplierService.save(supplier));

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
        supplierService.remove(id);
    }

    @PostMapping("search/email")
    public Supplier findByEmail(@RequestBody SearchDTO searchDTO) {
        return supplierService.findByEmail(searchDTO.getSearchKeyword());
    }

    @PostMapping("search/email-like")
    public Supplier findByEmailContains(@RequestBody SearchDTO searchDTO) {
        return supplierService.findByEmailContains(searchDTO.getSearchKeyword());
    }

    @PostMapping("search/name")
    public Supplier findByName(@RequestBody SearchDTO searchDTO) {
        return supplierService.findByName(searchDTO.getSearchKeyword());
    }
}
