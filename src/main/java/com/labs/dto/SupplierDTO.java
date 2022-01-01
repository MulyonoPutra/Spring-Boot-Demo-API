package com.labs.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SupplierDTO {

    @NotEmpty(message = "Supplier name is required")
    private String name;

    @NotEmpty(message = "Supplier address is required")
    private String address;
    
    @NotEmpty(message = "Supplier email is required")
    @Email(message = "Supplier email is not valid")
    private String email;
    
    @NotEmpty(message = "Supplier phone is required")
    private String phone;

}
