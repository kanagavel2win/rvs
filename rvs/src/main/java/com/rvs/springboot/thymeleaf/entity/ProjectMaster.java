package com.rvs.springboot.thymeleaf.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "projectmaster")
public class ProjectMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//@Column
	//private String ContactPerson;
	
	@Column
	private String projectID;
	
	@Column
	private String board;
	
	@Column
	private String Organization;
	@Column
	private String Title;
	@Column
	private String Source;
	@Column
	private String Reference;
	@Column
	private String purpose;
	@Column
	private String startdate;
	
	@Column
	private String expectedclosingdate;
	@Column
	private String pipeline;
	@Column
	private String label;
	@Column
	private String notes;
	
	@Column
	private String status;	
	@Column
	private String tdate;
	@Column
	private String Location;
	@Column
	private String UNITS;
	@Column
	private String Area;
	@Column
	private String NatureofWork;
	
	@Column
	int branch;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<ProjectContact> projectContact;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private List<ProjectFollowers> projectFollowers;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	@OrderBy("Fileid ASC")
	private List<ProjectFiles> projectFiles;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectid")
	@OrderBy("orderID ASC")
	private List<ProjectPhases> projectPhases;
	

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name =  "projectid")
	@OrderBy("invoiceid ASC")
	List<InvoiceMaster> invoiceList;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name =  "projectid")
	@OrderBy("recepitid ASC")
	List<InvoiceReceiptMaster> receiptList;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name =  "projectid")
	@OrderBy("projectpurchaseid ASC")
	List<ProjectpurchaseMaster> ProjectpurchaseMasterList;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name =  "projectid")
	@OrderBy("recepitid ASC")
	List<ProjectpurchasePaymentMaster> purchasePaymentMasterList;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectId")
	@OrderBy("prjExpenseid ASC")
	private List<ProjectExpense> projectExpenseList;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "projectId")
	@OrderBy("projectitemid ASC")
	private List<ProjectItemMaster> projectItemMaster;
	
	
	@Column
	private String addressline1;
	@Column 
	private String addressline2;
	@Column
	private String lankmark;
	@Column
	private String taluk;
	@Column
	private String district;
	@Column
	private String state;
	@Column
	private String pincode;	
	@Column
	private String lanlong;
	
	@Column
	private String createddate;
	@Column
	private String lossbacktoleadreason;
	@Column
	private int sourceid;
	@Column
	private String sourcefrom;
	@Column
	private int projectvalue;
	
	
	
	
	
	@Transient
	private String nextactivity;
	
	
	@Transient
	private String OrganizationName;

	@Transient
	private String ContactPersonName;
	
	@Transient
	private String ReferenceName;
	@Transient
	private String createddateMMddYYY;
	
	@Transient
	private String expectedclosingdateMMddYYY;
	
	@Transient
	private String expectedstartdateMMddYYY;
	
	@Transient
	private String tdateMMddYYY;
	
	@Transient
	private String projecttotalvaluefromItem;
	
	@Transient
	private String projecttotalvaluereceipt;

	@Transient
	private String projecttotalvaluebilled;

	@Transient
	private String projecttotalvalueexpense;
	
	@Transient
	private String projecttotalvaluepurchase;
	
	@Transient
	private String branchname;
	
	@Transient
	private String projectfollowerids;
	
	@Transient
	private String boardName;

	
	@Transient
	private String noofdaysRemaining;
	
	@Transient
	private String noofdaysRemainingPercentage;
	
}
