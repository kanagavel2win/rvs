package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;

public interface ContactOrganizationRepository extends JpaRepository<OrganizationContacts,Integer>{

	List<OrganizationContacts> findByOrgname(String str); 

}
