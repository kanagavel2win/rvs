package com.rvs.springboot.thymeleaf.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.LoginRegistrationDto;


public interface LoginService extends UserDetailsService {

    Login findByEmpid(String email);
    Login save(LoginRegistrationDto registration, String privilege);
    Login savePasswordchange(Login obj);
   
	UserDetails loadUserByUsername(String empid) throws UsernameNotFoundException;
}
