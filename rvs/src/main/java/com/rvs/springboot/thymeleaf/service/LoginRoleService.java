package com.rvs.springboot.thymeleaf.service;

import com.rvs.springboot.thymeleaf.entity.LoginRole;


public interface LoginRoleService {

    LoginRole findByRole(String role);
   	
}
