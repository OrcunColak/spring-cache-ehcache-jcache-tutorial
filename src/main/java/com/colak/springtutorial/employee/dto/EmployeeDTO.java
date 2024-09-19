package com.colak.springtutorial.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

// Serializable since ehcache config uses offheap configuration
@Getter
@Setter
public class EmployeeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;
}
