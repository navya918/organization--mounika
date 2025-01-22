package com.organizationchart.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.organizationchart.entity.Employee;
import com.organizationchart.exceptions.EmployeeNotFoundException;
import com.organizationchart.repository.EmployeeRepository;


@Service
public class EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

 // Method to create a new employee
    public Employee createEmployee(Employee employee) {
       return employeeRepository.save(employee); // Save the employee to the database
    }

    // Method to get all employees
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();  // Use the JPA repository method to fetch all employees
    }

    // Method to find the origin employee by their empId
    public List<Employee> findOrigin(Long empId) {
        System.out.println("Employee Id: "+empId);
        List<Employee> reportingEmployees = new ArrayList<>();

        // Fetch the initial employee
        Employee emp = employeeRepository.findByEmpId(empId);

        // Check if the employee exists
        if (emp == null) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + empId);
        }

        reportingEmployees.add(emp);

        Long reportingEmpId = emp.getReportingTo();// Assuming this returns the empId of the superior

        // Loop until there are no more employees to fetch
        while (reportingEmpId != null) {
            Employee superior = employeeRepository.findByEmpId(reportingEmpId);

            // If superior is found, add it to the list
            if (superior != null) {
                reportingEmployees.add(superior);
                reportingEmpId = superior.getReportingTo(); // Update reportingEmpId to the next manager
            } else {
                break; // Exit the loop if no more employees are found
            }
        }

        return reportingEmployees; // Return the list of employees
    }

    // Method to find employees who report to a specific empId
    public List<Employee> reportingToList(Long empId) {
        Employee emp = employeeRepository.findByEmpId(empId);
        if (emp == null) {
            return Collections.emptyList(); // Return empty list if employee does not exist
        }
        return employeeRepository.findByReportingTo(empId);
    }

    public List<Employee> findByCountry(String country) {
        List<Employee> employees = employeeRepository.findByCountry(country);
        return employees != null ? employees : Collections.emptyList(); // Return empty list if null
    }
    
    

}


