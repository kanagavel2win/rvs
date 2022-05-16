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
@Table
@Setter @Getter @ToString @NoArgsConstructor @AllArgsConstructor


public class HireMasterQuestions implements Comparable<HireMasterQuestions> {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int questionid;
	@Column
	String qtype;
	@Column
	String 	questiontitle;
	@Column
	String option1;
	@Column
	String option2;
	@Column
	String option3;
	@Override
	public int compareTo(HireMasterQuestions o) {
		if(o.getQuestionid() == this.questionid)
		{
			return -1;
		}else if(o.getQuestionid() < this.questionid)
		{
			return 1;
		}else
		{
			return -1;
		}
	}
	
}
