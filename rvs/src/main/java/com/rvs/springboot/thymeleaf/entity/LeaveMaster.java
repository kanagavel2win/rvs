package com.rvs.springboot.thymeleaf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.Empty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "leavemaster")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaveMaster implements Comparable<LeaveMaster> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column
	int empid;
	@Column
	String fromadate;
	@Column
	String todate;
	@Column
	String leavetype;
	@Column
	boolean halfday;
	@Column
	String permissionstarttime;
	@Column
	String permissionendtime;

	@Column
	String notes;
	@Column
	String status;
	
	@Column
	String approver;
	@Column
	String approvercomments;
	@Column
	String approverejectdate;

	@Override
	public int compareTo(LeaveMaster o) {
		if (o.getId() == this.getId()) {
			return 0;
		} else if (o.getId() < this.getId()) {
			return 1;
		} else {
			return -1;
		}

	}

}
