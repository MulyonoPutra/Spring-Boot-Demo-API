package com.labs.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ResponseData<T> {

    private boolean status;
    private List<String> message = new ArrayList<String>();
    private T data;
    private Object payload;

}
