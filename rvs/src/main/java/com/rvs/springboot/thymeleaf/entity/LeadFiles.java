package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="leadfiles")

public class LeadFiles {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Fileid;

	@Column
	private String DocumentType;
	@Column
	private String DocumentNo;
	@Column
	private String FilePath;
	@Column
	private String createddate;
	@Column
	private String docgroup;
	
	
	

}
