package com.microservice.fallback;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.exception.handler.RecordNotFoundException;
import com.microservice.model.Employee;
import com.microservice.service.EmployeeFeignClient;

import feign.hystrix.FallbackFactory;

@Configuration
public class EmployeeClientFallbackFactory implements FallbackFactory<EmployeeFeignClient> {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeClientFallbackFactory.class);

	@Override
	public EmployeeFeignClient create(Throwable cause) {
		return new EmployeeFeignClient() {

			@Override
			public ResponseEntity<Employee> saveEmployee(@Valid Employee employee) {
				LOG.info("Handling error for saveEmployee () method");
				LOG.error("Failure Reason : " + cause.getCause().getLocalizedMessage());
				throw new RuntimeException(cause);
			}

			@Override
			public String getEmployeeServicePort() {
				LOG.info("Handling error for getEmployeeServicePort () method");
				LOG.error("Failure Reason : " + cause.getCause().getLocalizedMessage());
				throw new RuntimeException(cause.getLocalizedMessage());
			}

			@Override
			public Employee getEmployeeById(long empId) {
				LOG.info("Handling error for getEmployeeById () method");
				LOG.error("Failure Reason : " + cause.getMessage());
				throw new RecordNotFoundException("Record not found for EmpID:" + empId);
			}

			@Override
			public List<Employee> getAllEmpList() {
				LOG.info("Handling error for getAllEmpList () method");
				LOG.error("Failure Reason : " + cause.getMessage());
				throw new RuntimeException(cause.getLocalizedMessage());
			}

			@Override
			public Employee deleteEmployeeById(long empId) {
				LOG.info("Handling error for deleteEmployeeById () method");
				LOG.error("Failure Reason : " + cause.getMessage());
				throw new RuntimeException(cause.getLocalizedMessage());
			}
		};
	}

}
