package com.app.pojos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
@ToString

@Entity
@JsonIgnoreProperties({"customer"})
public class Appointment extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "garage_id")
	private Garage garage;
	@Enumerated(EnumType.STRING)
	private ServiceType type;
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Column(name = "Date")
	private String date;
	@Column(name = "Time")
	private String time;

	private boolean onSpotMechanic;

	@ManyToMany(mappedBy = "appointments", cascade = { CascadeType.ALL })
	private Set<Services> services = new HashSet<Services>();

	// an Appointment can have only one Package assigned to it, and a Package can be
	// assigned to multiple Appointments.
	@ManyToOne
	@JoinColumn(name = "package_id")
	private Packages packagee;
	@OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
	private Payment payment;
}
