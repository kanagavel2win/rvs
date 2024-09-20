package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rvs.springboot.thymeleaf.entity.CheckInMaster;

public interface CheckInMasterRepository extends JpaRepository<CheckInMaster, Integer> {
    
}
