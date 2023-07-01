package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.AccountsIncome;

public interface AccountIncomeRepository extends JpaRepository<AccountsIncome,Integer>{

	
}
