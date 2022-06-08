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
@ToString @NoArgsConstructor @Setter @Getter @AllArgsConstructor
public class payslip implements Comparable<payslip> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int payslipid;
	@Column
	int paymonth;
	@Column
	String employeeid;
	@Column
	String staff_name;
	@Column
	String payperiod;
	@Column
	String locationstate;
	@Column
	String bankName;
	@Column
	String accountNo;
	@Column
	String ctc;
	@Column
	String totalWorkingDays;
	@Column
	String absent;
	@Column
	String workingDays;
	@Column
	String extraWorkingDays;
	@Column
	String basicSalary;
	@Column
	String da;
	@Column
	String hra;
	@Column
	String totalgross;
	@Column
	String esi;
	@Column
	String epf;
	@Column
	String advance;
	@Column
	String totaldeduction;
	@Column
	String monthlyincentives;
	@Column
	String net;
	@Override
	public int compareTo(payslip o) {
		
		if(this.getPaymonth() ==o.getPaymonth() )
		{
			return 0;
		}else if(this.getPaymonth() > o.getPaymonth() )
		{
			return 1;
		}else
		{
			return -1;
		}
	}
}
