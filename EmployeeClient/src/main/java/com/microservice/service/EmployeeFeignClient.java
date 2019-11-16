package com.microservice.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservice.fallback.EmployeeClientFallbackFactory;
import com.microservice.model.Employee;

@FeignClient(value="EMPLOYEESERVICE",fallbackFactory=EmployeeClientFallbackFactory.class)
public interface EmployeeFeignClient {

	@GetMapping(value = "/employee/getEmployeeById/{empId}")
	public Employee getEmployeeById(@PathVariable long empId);

	@GetMapping(value = "/employee/getAllEmpList")
	public List<Employee> getAllEmpList();

	@PostMapping(value = "/employee/saveEmployee")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee);

	@GetMapping(value = "/employee/deleteEmployeeById/{empId}")
	public Employee deleteEmployeeById(@PathVariable long empId);

	@GetMapping(value = "/employee/getPort")
	public String getEmployeeServicePort();

}
