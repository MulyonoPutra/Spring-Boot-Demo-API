package com.labs.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import com.labs.entities.Category;
import com.labs.repositories.CategoryRepository;
import com.labs.utils.CSVUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findOneById(Long id) {
        Optional<Category> categoryId = categoryRepository.findById(id);
        if (!categoryId.isPresent()) {
            return null;
        }
        return categoryId.get();
    }

    // @Cacheable("categories")
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }

    public Iterable<Category> findByNameContains(String name, Pageable pageable) {
        return categoryRepository.findByNameContains(name, pageable);
    }

    public Iterable<Category> saveBatch(Iterable<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

    public List<Category> uploadCSVFile(MultipartFile uploadFile) {
        try {
            List<Category> categories = CSVUpload.csvToCategory(uploadFile.getInputStream());
            return categoryRepository.saveAll(categories);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV file: " + e.getMessage());
        }
    }

}
