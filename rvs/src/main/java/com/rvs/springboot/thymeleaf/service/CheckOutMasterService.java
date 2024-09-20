package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rvs.springboot.thymeleaf.entity.CheckOutMaster;
import com.rvs.springboot.thymeleaf.dao.CheckOutMasterRepository;

@Service
public class CheckOutMasterService {

    @Autowired
    private CheckOutMasterRepository checkOutMasterRepository;

    public List<CheckOutMaster> findAll() {
        return checkOutMasterRepository.findAll();
    }

    public CheckOutMaster save(CheckOutMaster checkOutMaster) {
        return checkOutMasterRepository.save(checkOutMaster);
    }
    
  
}
