package com.ankit.webservice.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder 
@Data
public class User {
	private Integer id;
	@Size(min = 2, message = "Name must have some value")
	private String name;
	@Past(message = "Birthdate must be past")
	private LocalDate birthDate;
}
