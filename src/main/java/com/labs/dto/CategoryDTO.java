package com.labs.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDTO {
    
    @NotEmpty(message = "Category name is required")
    private String name;

    private String description;
    
}
