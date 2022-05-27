package com.rvs.springboot.thymeleaf.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.LoginRepository;
import com.rvs.springboot.thymeleaf.dao.LoginRoleRepository;
import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.LoginRegistrationDto;
import com.rvs.springboot.thymeleaf.entity.LoginRole;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    private LoginRoleRepository userRoleRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Login findByEmpid(String empid){
        return userRepository.findByEmpid(empid);
    }

    

    
    public Login save(LoginRegistrationDto registration, String privilege) {
    	
    	ArrayList<LoginRole> ls =new ArrayList<LoginRole>();
    	//ls.add(new LoginRole(null,privilege));
    	ls.add(userRoleRepository.findByRole(privilege));
    	
        Login user = new Login();
        user.setEmpid(registration.getEmpid());
        user.setPassword(passwordEncoder.encode(registration.getEmpid()));
        user.setRoles(ls);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String empid) throws UsernameNotFoundException {
        Login user = userRepository.findByEmpid(empid);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }else
        {
        
        	
    		 
    		
        }
       
        return new org.springframework.security.core.userdetails.User(user.getEmpid(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<LoginRole> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }


	

		
}
