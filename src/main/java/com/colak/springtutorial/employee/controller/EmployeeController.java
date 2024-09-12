package com.colak.springtutorial.employee.controller;


import com.colak.springtutorial.employee.dto.EmployeeDTO;
import com.colak.springtutorial.employee.mapstruct.EmployeeMapper;
import com.colak.springtutorial.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
@CacheConfig(cacheNames = "employees")

@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    // http://localhost:8080/api/employee/findById/1
    @GetMapping(path = "/findById/{id}")
    @Cacheable(key = "#id", condition = "#id != null", sync = true)
    public EmployeeDTO findById(@PathVariable Long id) {
        log.info("findById is called with : {}", id);
        return employeeService.findById(id)
                .map(EmployeeMapper.INSTANCE::employeeToDto)
                // throws NoSuchElementException
                .orElseThrow();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception) {
        // Return 404
        return ResponseEntity.notFound().build();
    }
}
