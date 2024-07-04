package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Garagedto {
//	@NotEmpty
	private String name;
//	@NotEmpty
	private String address;
//	@NotEmpty
	private String mobile;
	@Email(message = "email is not valid")
	private String email;
	@Size(min = 8, max = 20, message = "password must be more than 8 and less than 20 characters")
	private String password;

}
