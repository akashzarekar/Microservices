package com.microservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

	private long empID;

	@NotEmpty
	@NotNull
	private String empFirstName;

	@NotEmpty
	@NotNull
	private String empLastName;

	@NotEmpty
	@NotNull
	@Email
	private String email;

}
