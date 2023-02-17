package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ContactPerson;

public interface ContactPersonRepository extends JpaRepository<ContactPerson,Integer>{

	List<ContactPerson> findByOrganization(String organizationname);

}
