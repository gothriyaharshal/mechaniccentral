package com.app.dto;

import java.util.List;

import com.app.pojos.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppointmentRequestdto {
	private List<Long> serviceIds;
	private Long packageId;
	private boolean onSpotMechanic;
	private ServiceType servicetype;
	

	// getters and setters
}