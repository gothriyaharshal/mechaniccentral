package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin extends BaseEntity {
	@Column(length = 50, name = "cust_name", nullable = false)
	private String name;
	@Column(length = 50, unique = true, name = "cust_email", nullable = false)
	private String email;
	@Column(length = 12, name = "cust_mob", nullable = false)
	private String mobile;
	@Column(length = 50, name = "cust_pass", nullable = false)
	private String password;


}
