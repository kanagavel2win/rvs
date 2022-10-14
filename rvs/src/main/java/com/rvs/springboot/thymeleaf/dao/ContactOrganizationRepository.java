package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ContactOrganization;

public interface ContactOrganizationRepository extends JpaRepository<ContactOrganization,Integer>{

}
