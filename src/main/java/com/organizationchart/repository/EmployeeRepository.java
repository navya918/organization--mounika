package com.organizationchart.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.organizationchart.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmpId(Long empId); // Find employee by employee ID

	List<Employee> findByReportingTo(Long reportingTo);

    List<Employee> findByCountry(String country);

}

