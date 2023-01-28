package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;

public interface BranchMasterService {
	
	public BranchMaster save(BranchMaster obj);
	public BranchMaster findById(Integer id);
	public List<BranchMaster> findAll();
	public int insertbranchContact(String dep,String phonenumber , String email, int branchid);
	public int updatebranchContact(int id, String dep,String phonenumber , String email);
	public int deletebranchContact(int id);
	
	public int insertbranchAccountdetails(int acid, String acno,String acname , String bankname,String branchname,String ifsccode, int branchid);
	public int insertbranchFiles(String DocumentType,String DocumentNo , String FilePath, int branchid);
	public int deletebranchFiles(int id);
}
