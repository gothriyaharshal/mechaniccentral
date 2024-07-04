package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.app.dto.AppointmentRequestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
@Entity

public class Garage extends BaseEntity {
	@Column(length = 50, name = "g_name")
	private String name;
	@Column(length = 200, name = "g_address")
	private String address;
	@Column(length = 12, name = "g_mobile")
	private String mobile;
	@Column(length = 30, name = "g_email", unique = true, nullable = false)
	private String email;
	@Column(length = 30, name = "g_password")
	private String password;

	@OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Appointment> appointments;

	@OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<AppointmentRequest> appointmentRequests = new ArrayList<AppointmentRequest>();

}
