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
import com.microservice.service.EmployeeFeignClient;

@RestController
@RequestMapping(value = "/consumer")
public class EmployeeClientController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeClientController.class);

	@Autowired
	private EmployeeFeignClient client;

	@RequestMapping(value = "/getEmployeeById/{empId}")
	public Employee getEmployeeById(@PathVariable long empId) {
		LOG.debug("Getting Employee Details for EmpId : " + empId);
		Employee emp = client.getEmployeeById(empId);
		return emp;
	}

	@RequestMapping(value = "/getAllEmpList")
	public List<Employee> getAllEmpList() {
		LOG.info("Getting All Employee Details");
		List<Employee> empList = client.getAllEmpList();
		return empList;
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
		LOG.debug("Saving Employee Details");
		return client.saveEmployee(employee);
	}

	@RequestMapping(value = "/deleteEmployeeById/{empId}")
	public Employee deleteEmployeeById(@PathVariable long empId) {
		LOG.debug("Calling Employee Service to Delete Employee Details for EmpId : " + empId);
		return client.deleteEmployeeById(empId);
	}

	@GetMapping(value = "/getPort")
	public String getPort() {
		LOG.info("Getting EmployeeService Port no");
		String port = client.getEmployeeServicePort();
		LOG.debug("Retrieved Port Info :" + port);
		return port;
	}

}
