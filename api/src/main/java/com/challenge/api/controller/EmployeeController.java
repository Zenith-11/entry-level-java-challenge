package com.challenge.api.controller;

import com.challenge.api.dto.EmployeeCreateRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees in the system.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees")})
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get employee by UUID", description = "Retrieves a single employee by their unique UUID.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the employee"),
                @ApiResponse(responseCode = "404", description = "Employee not found")
            })
    @GetMapping("/{uuid}")
    public Employee getEmployeeByUuid(@PathVariable UUID uuid) {
        return employeeService.getEmployeeByUuid(uuid);
    }

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee and returns the created employee data including their UUID.")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "201", description = "Employee successfully created"),
                @ApiResponse(responseCode = "400", description = "Invalid request payload (validation failed)")
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody EmployeeCreateRequest requestBody) {
        return employeeService.createEmployee(requestBody);
    }
}
