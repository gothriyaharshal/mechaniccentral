package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Services;

public interface ServiceRepo extends JpaRepository<Services, Long> {

}
