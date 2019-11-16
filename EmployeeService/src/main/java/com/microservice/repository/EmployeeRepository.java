package com.microservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
