package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
public class Packages extends BaseEntity {

	@Column(length = 50, name = "pkg_name")
	private String name;
	@Column(length = 400, name = "pkg_desc")
	private String description;
	@Column(length = 5, name = "pkg_price")
	private Double price;

    
    @OneToMany(mappedBy = "packagee")
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy = "packagee")
    private List<AppointmentRequest> appointmentrequests = new ArrayList<>();

}
