package com.app.dto;

import java.util.HashSet;
import java.util.Set;

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
public class AppointmentResponsedto {
	private Long id;
	private Long customerId;
	private String customername;
	private Long garageId;
	private String garagename;
	private String garagemob;
	private String garageaddress;
	private String serviceType;
	private String appointmentstatus;
	private String date;
	private String time;
	private boolean onSpotMechanic;
	private String package_name;
	private Set<String> services = new HashSet<>();
	private Double totalPrice;
}
