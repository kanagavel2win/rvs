package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rvs.springboot.thymeleaf.entity.CheckInMaster;
import com.rvs.springboot.thymeleaf.dao.CheckInMasterRepository;

@Service
public class CheckInMasterService {

    @Autowired
    private CheckInMasterRepository checkOutMasterRepository;

    public List<CheckInMaster> findAll() {
        return checkOutMasterRepository.findAll();
    }

    public CheckInMaster save(CheckInMaster CheckInMaster) {
        return checkOutMasterRepository.save(CheckInMaster);
    }
    
  
}
