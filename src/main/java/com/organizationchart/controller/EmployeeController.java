package com.organizationchart.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.organizationchart.entity.Employee;
import com.organizationchart.exceptions.EmployeeNotFoundException;
import com.organizationchart.service.EmployeeService;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://server-front-c6dvcfb9c4h5hxe4.canadacentral-01.azurewebsites.net")
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        // Call the service method to create the employee
        Employee savedEmployee = employeeService.createEmployee(employee);

        // Return the created employee with a 201 Created status
        return ResponseEntity.status(201).body(savedEmployee);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

 // Method to find the origin employee by their empId
    @GetMapping("/origin/{empId}")
    public ResponseEntity<List<Employee>> getOriginEmployee(@PathVariable Long empId) {
        List<Employee> reportingChain = employeeService.findOrigin(empId);

        if (reportingChain.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if no employee or chain found
        }

        return ResponseEntity.ok(reportingChain);  // Return the list of employees in the reporting chain
    }

 // Get employees reporting to a specific empId
    @GetMapping("/reporting-to/{empId}")
    public ResponseEntity<List<Employee>> getEmployeesReportingTo(@PathVariable Long empId) {
        List<Employee> reportingEmployees = employeeService.reportingToList(empId);

        if (reportingEmployees.isEmpty()) {
            // Throw an exception if no employees are found
            throw new EmployeeNotFoundException("No employees found reporting to ID: " + empId);
        }

        return ResponseEntity.ok(reportingEmployees); // Return the list with 200 OK
    }

    // Get employees by country
    @GetMapping("/country/{country}")
    public ResponseEntity<List<Employee>> getByCountry(@PathVariable String country) {
        List<Employee> employees = employeeService.findByCountry(country);

        if (employees.isEmpty()) {
            // Throw an exception if no employees are found
            throw new EmployeeNotFoundException("No employees found in country: " + country);
        }

        return ResponseEntity.ok(employees); // Return the list with 200 OK
    }
}

