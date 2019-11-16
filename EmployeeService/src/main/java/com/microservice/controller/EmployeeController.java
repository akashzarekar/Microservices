package com.microservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.Employee;
import com.microservice.service.EmployeeService;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/getEmployeeById/{empId}")
	public Employee getEmployeeById(@PathVariable long empId) {
		LOG.debug("Retrieving Employee Record for EmpID :" + empId);
		return employeeService.getEmployeeById(empId);
	}

	@RequestMapping(value = "/getAllEmpList")
	public List<Employee> getAllEmpList() {
		LOG.info("Retrieving All Employee Records");
		return employeeService.getAllEmp();
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
		LOG.info("Saving Employee Record");
		return employeeService.saveEmployee(employee);
	}

	@RequestMapping(value = "/deleteEmployeeById/{empId}")
	public Employee deleteEmployeeById(@PathVariable long empId) {
		LOG.debug("Deleting Employee Record for EmpID :" + empId);
		return employeeService.deleteEmployee(empId);
	}

	@GetMapping(value = "/getPort")
	public String getEmployeeServicePort() {
		LOG.info("Retrieving Employee service port no to check load balancing");
		return employeeService.getEmployeeServicePort();
	}
}
