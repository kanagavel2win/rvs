package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AccountTransfer;

public interface AccountTransferRepository extends JpaRepository<AccountTransfer,Integer>{

	
}
