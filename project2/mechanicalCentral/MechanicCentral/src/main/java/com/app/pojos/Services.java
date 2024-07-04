package com.app.pojos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Services extends BaseEntity {
	@Column(length = 60, name = "service_name")
	private String name;
	@Column(length = 400, name = "service_desc")
	private String description;
	@Column(length = 6, name = "service_price")
	private Double price;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "service_appointment", joinColumns = { @JoinColumn(name = "service_id") }, inverseJoinColumns = {
			@JoinColumn(name = "appointment_id") })
	Set<Appointment> appointments = new HashSet<Appointment>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "service_appointment", joinColumns = { @JoinColumn(name = "service_id") }, inverseJoinColumns = {
			@JoinColumn(name = "appointment_id") })
	Set<AppointmentRequest> appointmentrequests = new HashSet<AppointmentRequest>();
}
