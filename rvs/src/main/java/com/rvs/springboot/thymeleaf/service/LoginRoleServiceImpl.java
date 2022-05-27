package com.rvs.springboot.thymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LoginRoleRepository;
import com.rvs.springboot.thymeleaf.entity.LoginRole;

@Service
public class LoginRoleServiceImpl implements LoginRoleService {

    @Autowired
    private LoginRoleRepository userRoleRepository;

	
	@Override
	public LoginRole findByRole(String role) {
		return userRoleRepository.findByRole(role);
	}

	
		
}
