package com.microservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exception.handler.RecordNotFoundException;
import com.microservice.model.Employee;
import com.microservice.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Value("${server.port}")
	private int portNo;

	public Employee getEmployeeById(long id) {
		LOG.info("Inside getEmployeeById() method");
		Employee emp = employeeRepository.findById(id).orElse(null);
		if (emp != null) {
			LOG.debug("Retrieved Employee data for EmpId :" + id + " ::" + emp);
			LOG.info("Exit from getEmployeeById method");
			return emp;
		} else {
			LOG.error("No Record found for EmpId :" + id);
			throw new RecordNotFoundException("Record not found for EmpID:" + id);
		}
	}

	public List<Employee> getAllEmp() {
		LOG.info("Inside getAllEmp() method");
		List<Employee> listEmp = new ArrayList<>();
		Iterator<Employee> empIterator = employeeRepository.findAll().iterator();
		while (empIterator.hasNext()) {
			listEmp.add(empIterator.next());
		}
		LOG.debug("Retrieved Employees data : " + listEmp);
		LOG.info("Exit from getAllEmp() method");
		return listEmp;
	}

	public Employee deleteEmployee(long id) {
		LOG.info("Inside deleteEmployee() method");
		Employee emp = employeeRepository.findById(id).orElse(null);
		if (emp != null) {
			LOG.debug("Deleting Record for EmpId :" + id);
			employeeRepository.deleteById(id);
			LOG.debug("Deleted Record :" + emp);
			LOG.info("Exit from deleteEmployee() method");
			return emp;
		} else {
			LOG.error("No Record found to delete for EmpId  : " + id);
			throw new RecordNotFoundException("Record not found for EmpID:" + id);
		}
	}

	public ResponseEntity<Employee> saveEmployee(Employee emp) {
		LOG.info("Inside saveEmployee() method");
		ResponseEntity<Employee> response = null;
		try {
			Employee savedEmployee = employeeRepository.save(emp);
			response = new ResponseEntity<Employee>(savedEmployee, HttpStatus.CREATED);
			LOG.info("Employee saved status : " + response.getStatusCode());
			LOG.debug("Saved Employee Details :" + savedEmployee);
		} catch (DataAccessException ex) {
			LOG.error("Exception Occured while saving data : " + ex);
		}
		LOG.info("Exit from saveEmployee() method");
		return response;
	}

	public String getEmployeeServicePort() {
		LOG.info("getEmployeeServicePort() called");
		return "Employee Service port No : " + portNo;
	}

}
