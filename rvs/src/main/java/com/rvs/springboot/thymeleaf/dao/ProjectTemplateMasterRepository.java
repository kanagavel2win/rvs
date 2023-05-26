package com.rvs.springboot.thymeleaf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvs.springboot.thymeleaf.entity.ProjectTemplatePhase;

public interface ProjectTemplateMasterRepository extends JpaRepository<ProjectTemplatePhase,Integer>{

}
