package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rvs.springboot.thymeleaf.entity.CheckOutMaster;

public interface CheckOutMasterRepository extends JpaRepository<CheckOutMaster, Integer> {
    
}
