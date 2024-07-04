package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = "password")
@Table(name = "Customer")
@JsonIgnoreProperties({"appointments"})
public class Customer extends BaseEntity {

	@Column(length = 50, name = "cust_name", nullable = false)
	private String name;
	@Column(length = 50, unique = true, name = "cust_email", nullable = false)
	private String email;
	@Column(length = 12, name = "cust_mob", nullable = false)
	private String mobile;
	@Column(length = 50, name = "cust_pass", nullable = false)
	private String password;


	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointments = new ArrayList<>();
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AppointmentRequest> appointmentrequests = new ArrayList<>();

}
