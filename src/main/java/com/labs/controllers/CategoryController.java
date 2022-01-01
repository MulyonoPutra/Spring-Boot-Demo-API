package com.labs.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import com.labs.dto.CategoryDTO;
import com.labs.dto.ResponseData;
import com.labs.dto.SearchDTO;
import com.labs.entities.Category;
import com.labs.services.CategoryService;
import com.labs.utils.CSVUpload;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll() {
        Iterable<Category> category = categoryService.findAll();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable("id") Long id) {
        return categoryService.findOneById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors) {

        ResponseData<Category> response = new ResponseData<>();
        Category category = mapper.map(categoryDTO, Category.class);

        try {
            response.setStatus(true);
            response.setData(categoryService.save(category));

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
    public ResponseEntity<ResponseData<Category>> update(@Valid @RequestBody CategoryDTO categoryDTO, Errors errors) {

        ResponseData<Category> response = new ResponseData<>();

        try {
            Category category = mapper.map(categoryDTO, Category.class);
            response.setStatus(true);
            response.setData(categoryService.save(category));

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
        categoryService.remove(id);
    }

    @PostMapping("/search/{size}/{page}")
    public ResponseEntity<Iterable<Category>> findByNameContains(
            @RequestBody SearchDTO searchDTO,
            @PathVariable("size") int size,
            @PathVariable("page") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Iterable<Category> category = categoryService.findByNameContains(searchDTO.getSearchKeyword(), pageable);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public ResponseEntity<Iterable<Category>> findByNameContains(
            @RequestBody SearchDTO searchDTO,
            @PathVariable("size") int size,
            @PathVariable("page") int page,
            @PathVariable("page") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("id").descending());
        }

        Iterable<Category> category = categoryService.findByNameContains(searchDTO.getSearchKeyword(), pageable);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseData<Iterable<Category>>> saveBatch(@Valid @RequestBody Category[] categories,
            Errors errors) {

        ResponseData<Iterable<Category>> response = new ResponseData<>();

        try {
            response.setStatus(true);
            response.setData(categoryService.saveBatch(Arrays.asList(categories)));

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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseData<Object> response = new ResponseData<>();

        if (!CSVUpload.hasCSVFormat(file)) {
            response.setStatus(false);
            response.getMessage().add("Uploaded the file successfully!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            List<Category> categories = categoryService.uploadCSVFile(file);
            List<Object> objectCategory = new ArrayList<Object>(categories);

            response.setStatus(true);
            response.getMessage().add("Uploaded the file successfully! " + file.getOriginalFilename());
            response.setData(objectCategory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(false);
            response.getMessage().add("Could not upload the file: " + file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
