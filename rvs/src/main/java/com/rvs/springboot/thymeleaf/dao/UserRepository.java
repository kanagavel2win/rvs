package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvs.springboot.thymeleaf.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	User findByMemberID(String memberID);
}
