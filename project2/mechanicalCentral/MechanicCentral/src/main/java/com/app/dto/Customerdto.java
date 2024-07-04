package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customerdto {
	private Long id;

	@NotEmpty
	@Size(min = 4, message = "Username must be of 4 characters!!!!")
	private String name;
	@Email(message = "email address is not valid")
	private String email;
	@NotBlank
	private String mobile;
	@Size(min = 8, max = 25, message = "password must be minimum of 8 chars and maximum of 25 chars")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\]).{8,32}$"
//, message = "password must contain at least one lowercase, one uppercase, one special char, min length of 8 and max of 32")
	private String password;
}
