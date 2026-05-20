package com.challenge.api.service;

import com.challenge.api.dto.EmployeeCreateRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    public EmployeeServiceImpl() {
        // Pre-populate some mock data
        EmployeeImpl emp1 = new EmployeeImpl();
        emp1.setUuid(UUID.randomUUID());
        emp1.setFirstName("Jane");
        emp1.setLastName("Doe");
        emp1.setSalary(80000);
        emp1.setAge(28);
        emp1.setJobTitle("Software Engineer");
        emp1.setEmail("jane.doe@example.com");
        emp1.setContractHireDate(Instant.now());
        employeeStore.put(emp1.getUuid(), emp1);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employeeStore.get(uuid);
        if (employee == null) {
            throw new com.challenge.api.exception.EmployeeNotFoundException("Employee with UUID " + uuid + " not found");
        }
        return employee;
    }

    @Override
    public Employee createEmployee(EmployeeCreateRequest request) {
        EmployeeImpl newEmployee = new EmployeeImpl();
        newEmployee.setUuid(UUID.randomUUID());
        newEmployee.setFirstName(request.getFirstName());
        newEmployee.setLastName(request.getLastName());
        newEmployee.setSalary(request.getSalary());
        newEmployee.setAge(request.getAge());
        newEmployee.setJobTitle(request.getJobTitle());
        newEmployee.setEmail(request.getEmail());
        newEmployee.setContractHireDate(Instant.now());

        employeeStore.put(newEmployee.getUuid(), newEmployee);
        return newEmployee;
    }
}
