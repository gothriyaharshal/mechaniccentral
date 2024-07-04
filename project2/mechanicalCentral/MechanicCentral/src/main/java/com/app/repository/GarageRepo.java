package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Garage;

public interface GarageRepo extends JpaRepository<Garage, Long> {

}
