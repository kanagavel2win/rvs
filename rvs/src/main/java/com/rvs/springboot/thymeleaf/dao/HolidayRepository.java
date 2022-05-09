package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

}
