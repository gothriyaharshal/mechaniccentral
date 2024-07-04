package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.AppointmentRequest;

public interface AppointmentRequestrepo extends JpaRepository<AppointmentRequest, Long>  {

}
