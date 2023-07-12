package com.rvs.springboot.thymeleaf.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rvs.springboot.thymeleaf.entity.AccountTransfer;
import com.rvs.springboot.thymeleaf.entity.AccountsIncome;
import com.rvs.springboot.thymeleaf.entity.Accountsheads;
import com.rvs.springboot.thymeleaf.entity.ActivityMaster;
import com.rvs.springboot.thymeleaf.entity.ActivityMasterFiles;
import com.rvs.springboot.thymeleaf.entity.ActivityMasterGuest;
import com.rvs.springboot.thymeleaf.entity.AssetAudit;
import com.rvs.springboot.thymeleaf.entity.AssetAuditFiles;
import com.rvs.springboot.thymeleaf.entity.AssetMaster;
import com.rvs.springboot.thymeleaf.entity.AssetMasterFiles;
import com.rvs.springboot.thymeleaf.entity.AssetService;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;
import com.rvs.springboot.thymeleaf.entity.BranchAccNo;
import com.rvs.springboot.thymeleaf.entity.BranchContact;
import com.rvs.springboot.thymeleaf.entity.BranchEffective;
import com.rvs.springboot.thymeleaf.entity.BranchFiles;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;
import com.rvs.springboot.thymeleaf.entity.BranchexpenseItemMaster;
import com.rvs.springboot.thymeleaf.entity.BranchexpenseMaster;
import com.rvs.springboot.thymeleaf.entity.BranchpurchaseItemMaster;
import com.rvs.springboot.thymeleaf.entity.BranchpurchaseMaster;
import com.rvs.springboot.thymeleaf.entity.BranchpurchasePaymentMaster;
import com.rvs.springboot.thymeleaf.entity.CheckIn;
import com.rvs.springboot.thymeleaf.entity.CheckInFiles;
import com.rvs.springboot.thymeleaf.entity.CheckOut;
import com.rvs.springboot.thymeleaf.entity.CheckOutFiles;
import com.rvs.springboot.thymeleaf.entity.ContactPerson;
import com.rvs.springboot.thymeleaf.entity.ContactPersonAccNo;
import com.rvs.springboot.thymeleaf.entity.ContactPersonContact;
import com.rvs.springboot.thymeleaf.entity.ContactPersonFiles;
import com.rvs.springboot.thymeleaf.entity.DealContact;
import com.rvs.springboot.thymeleaf.entity.DealFiles;
import com.rvs.springboot.thymeleaf.entity.DealFollowers;
import com.rvs.springboot.thymeleaf.entity.DealMaster;
import com.rvs.springboot.thymeleaf.entity.DealProjectMaster;
import com.rvs.springboot.thymeleaf.entity.EmployeeAccNo;
import com.rvs.springboot.thymeleaf.entity.EmployeeContact;
import com.rvs.springboot.thymeleaf.entity.EmployeeEducation;
import com.rvs.springboot.thymeleaf.entity.EmployeeEmgContact;
import com.rvs.springboot.thymeleaf.entity.EmployeeExperience;
import com.rvs.springboot.thymeleaf.entity.EmployeeFiles;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobHire;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobcompensation;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobempstatus;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobinfo;
import com.rvs.springboot.thymeleaf.entity.EmployeeLanguage;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;
import com.rvs.springboot.thymeleaf.entity.HireMaster;
import com.rvs.springboot.thymeleaf.entity.HireMasterQuestions;
import com.rvs.springboot.thymeleaf.entity.Holiday;
import com.rvs.springboot.thymeleaf.entity.HolidayextendedProps;
import com.rvs.springboot.thymeleaf.entity.InsuranceClaimHistory;
import com.rvs.springboot.thymeleaf.entity.InsuranceDetails;
import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;
import com.rvs.springboot.thymeleaf.entity.InvoiceItemMaster;
import com.rvs.springboot.thymeleaf.entity.InvoiceMaster;
import com.rvs.springboot.thymeleaf.entity.InvoiceReceiptMaster;
import com.rvs.springboot.thymeleaf.entity.LeadContact;
import com.rvs.springboot.thymeleaf.entity.LeadFiles;
import com.rvs.springboot.thymeleaf.entity.LeadFollowers;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;
import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.LoginRegistrationDto;
import com.rvs.springboot.thymeleaf.entity.OrganizationAccNo;
import com.rvs.springboot.thymeleaf.entity.OrganizationContact;
import com.rvs.springboot.thymeleaf.entity.OrganizationContacts;
import com.rvs.springboot.thymeleaf.entity.OrganizationFiles;
import com.rvs.springboot.thymeleaf.entity.ProjectContact;
import com.rvs.springboot.thymeleaf.entity.ProjectExpense;
import com.rvs.springboot.thymeleaf.entity.ProjectFiles;
import com.rvs.springboot.thymeleaf.entity.ProjectFollowers;
import com.rvs.springboot.thymeleaf.entity.ProjectItemMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectPhases;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateActivityMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateBoard;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplatePhase;
import com.rvs.springboot.thymeleaf.entity.ProjectpurchaseItemMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectpurchaseMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectpurchasePaymentMaster;
import com.rvs.springboot.thymeleaf.entity.VendorEmgContact;
import com.rvs.springboot.thymeleaf.entity.VendorFiles;
import com.rvs.springboot.thymeleaf.entity.VendorMaster;
import com.rvs.springboot.thymeleaf.entity.payslip;
import com.rvs.springboot.thymeleaf.pojo.CalenderFormat;
import com.rvs.springboot.thymeleaf.pojo.menuactivelist;
import com.rvs.springboot.thymeleaf.pojo.springtest;
import com.rvs.springboot.thymeleaf.pojo.tagify;
import com.rvs.springboot.thymeleaf.service.AccountIncomeService;
import com.rvs.springboot.thymeleaf.service.AccountTransferService;
import com.rvs.springboot.thymeleaf.service.AccountheadsService;
import com.rvs.springboot.thymeleaf.service.ActivityMasterService;
import com.rvs.springboot.thymeleaf.service.AssetAuditService;
import com.rvs.springboot.thymeleaf.service.AssetMasterService;
import com.rvs.springboot.thymeleaf.service.AssetServiceService;
import com.rvs.springboot.thymeleaf.service.AttendanceMasterService;
import com.rvs.springboot.thymeleaf.service.BranchMasterService;
import com.rvs.springboot.thymeleaf.service.CheckInService;
import com.rvs.springboot.thymeleaf.service.CheckOutService;
import com.rvs.springboot.thymeleaf.service.ContactOrganizationService;
import com.rvs.springboot.thymeleaf.service.ContactPersonService;
import com.rvs.springboot.thymeleaf.service.DealMasterService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobHireService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobcompensationService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobempstatusService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobinfoService;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;
import com.rvs.springboot.thymeleaf.service.HireMasterService;
import com.rvs.springboot.thymeleaf.service.HolidayService;
import com.rvs.springboot.thymeleaf.service.InsuranceMasterService;
import com.rvs.springboot.thymeleaf.service.ItemListService;
import com.rvs.springboot.thymeleaf.service.LeadMasterService;
import com.rvs.springboot.thymeleaf.service.LeaveMasterService;
import com.rvs.springboot.thymeleaf.service.LoginService;
import com.rvs.springboot.thymeleaf.service.PaySlipService;
import com.rvs.springboot.thymeleaf.service.ProjectMasterService;
import com.rvs.springboot.thymeleaf.service.ProjectTemplateBoardService;
import com.rvs.springboot.thymeleaf.service.ProjectTemplateMasterService;
import com.rvs.springboot.thymeleaf.service.VendorMasterService;

@Controller

public class HomeController {

	@Autowired
	springtest springtestobj;
	@Autowired
	menuactivelist menuactivelistobj;
	@Autowired
	BranchMasterService branchMasterService;
	@Autowired
	EmployeeMasterService employeeMasterService;
	@Autowired
	EmployeeJobcompensationService employeeJobcompensationService;
	@Autowired
	EmployeeJobempstatusService employeeJobempstatusService;
	@Autowired
	EmployeeJobHireService employeeJobHireService;
	@Autowired
	EmployeeJobinfoService employeeJobinfoService;
	@Autowired
	AttendanceMasterService attendanceMasterService;
	@Autowired
	HolidayService holidayService;
	@Autowired
	LeaveMasterService leaveMasterService;
	@Autowired
	HireMasterService hireMasterService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private PaySlipService payslipserive;

	@Autowired
	VendorMasterService vendorMasterService;
	@Autowired
	AssetMasterService assetMasterService;
	@Autowired
	AssetServiceService assetserviceService;
	@Autowired
	CheckOutService checkoutService;
	@Autowired
	CheckInService checkinService;
	@Autowired
	AssetAuditService assetauditService;
	@Autowired
	ItemListService itemlistService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	InsuranceMasterService insuranceMasterService;
	@Autowired
	ContactPersonService contactPersonService;
	@Autowired
	ContactOrganizationService contactOrganizationService;
	@Autowired
	LeadMasterService leadMasterService;
	@Autowired
	ActivityMasterService activityMasterService;
	@Autowired
	DealMasterService dealMasterService;

	@Autowired
	ProjectMasterService projectMasterService;

	@Autowired
	ProjectTemplateMasterService projectTemplateMasterService;

	@Autowired
	AccountTransferService accountTransferService;

	@Autowired
	AccountIncomeService accountIncomeService;

	@Autowired
	ProjectTemplateBoardService projectTemplateBoardService;

	@Autowired
	AccountheadsService accountheadsService;

	DateFormat displaydateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat displaydateFormatrev = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat displaydateFormatyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	DateFormat displaydatetimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	DateFormat displaydatetimeFormatHHmm = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	DateFormat displaydateFormatFirstMMMddYYY = new SimpleDateFormat("MMM-dd-yyyy");
	DateFormat displaydateFormatFirstMMMddYYYAMPM = new SimpleDateFormat("MMM-dd-yyyy hh:mm a");
	DateFormat displaydateFormatAMPM = new SimpleDateFormat("hh:mm a");
	DateFormat displaydateFormathhmm = new SimpleDateFormat("hh:mm");

	@ModelAttribute
	public void addAttributes(Model themodel, HttpSession session, HttpServletRequest request) {

		String dataLoginEmpID = "";
		String dataLoginEmpName = "";
		String dataLoginrole = "";
		String dataLoginEmpprofiileimg = "";
		try {

			try {
				if (request.getSession().getAttribute("dataLoginEmpID").toString().equals(null)) {
					request.getSession().setAttribute("dataLoginEmpID", getLoginempID());
				}
				if (request.getSession().getAttribute("dataLoginEmpName").toString().equals(null)) {
					request.getSession().setAttribute("dataLoginEmpName", getLoginEmpName());
				}
				if (request.getSession().getAttribute("dataLoginrole").toString().equals(null)) {
					request.getSession().setAttribute("dataLoginrole", getdataLoginrole());
				}
				if (request.getSession().getAttribute("dataLoginEmpprofiileimg").toString().equals(null)) {
					request.getSession().setAttribute("dataLoginEmpprofiileimg", getdataLoginEmpprofiileimg());
				}
			} catch (NullPointerException e) {
				request.getSession().setAttribute("dataLoginEmpID", getLoginempID());
				request.getSession().setAttribute("dataLoginEmpName", getLoginEmpName());
				request.getSession().setAttribute("dataLoginrole", getdataLoginrole());
				request.getSession().setAttribute("dataLoginEmpprofiileimg", getdataLoginEmpprofiileimg());
			}

			dataLoginEmpID = request.getSession().getAttribute("dataLoginEmpID").toString();
			dataLoginEmpName = request.getSession().getAttribute("dataLoginEmpName").toString();
			dataLoginrole = request.getSession().getAttribute("dataLoginrole").toString();
			dataLoginEmpprofiileimg = request.getSession().getAttribute("dataLoginEmpprofiileimg").toString();
		} catch (Exception e) {

		} finally {
			themodel.addAttribute("dataLoginEmpID", dataLoginEmpID);
			themodel.addAttribute("dataLoginEmpName", dataLoginEmpName);
			themodel.addAttribute("dataLoginrole", dataLoginrole);
			themodel.addAttribute("dataLoginEmpprofiileimg", dataLoginEmpprofiileimg);

		}

	}

	@GetMapping("/")
	public String home(Model theModel, HttpSession session, HttpServletRequest request) {
		if (logintype("ROLE_EMPLOYEE")) {
			return "redirect:rvsemp/";
		} else if (logintype("ROLE_ADMIN")) {
			theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("index"));
			return "index";
		} else {
			return "login";
		}

		// return "index";
	}

	@GetMapping("/index")
	public String index(Model theModel) {

		if (logintype("ROLE_EMPLOYEE")) {
			return "redirect:rvsemp/";
		} else if (logintype("ROLE_ADMIN")) {
			theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("index"));
			return "index";
		} else {
			// return "redirect:logout";
			return "login";
		}

	}

	@GetMapping("/maintenance")
	public String maintenance(Model theModel) {

		return "maintaince";

	}

	private boolean logintype(String expectedrole) {

		@SuppressWarnings("unchecked")
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();

		boolean RoleStatus = false;

		for (SimpleGrantedAuthority simpleGrantedAuthority : authorities) {

			if (simpleGrantedAuthority.getAuthority().toString().contains(expectedrole)) {
				RoleStatus = true;
			}
		}

		return RoleStatus;

	}

	public String getdataLoginrole() {
		if (logintype("ROLE_EMPLOYEE")) {
			return "ROLE_EMPLOYEE";
		} else if (logintype("ROLE_ADMIN")) {
			return "ROLE_ADMIN";
		} else {
			return "NA";
		}
	}

	public String getLoginempID() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public String getLoginEmpName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EmployeeMaster obj = employeeMasterService.findById(Integer.parseInt(authentication.getName()));
		return obj.getStaffName();
	}

	public String getdataLoginEmpprofiileimg() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EmployeeMaster obj = employeeMasterService.findById(Integer.parseInt(authentication.getName()));
		String profilephoto = "";
		if (obj.getEmployeeFiles().size() > 0) {
			List<EmployeeFiles> empfile = obj.getEmployeeFiles().stream()
					.filter(C -> C.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (empfile.size() > 0) {
				profilephoto = empfile.get(0).getFilePath();
			}
		}

		return profilephoto;
	}

	@GetMapping("login")
	public String login(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("dataLoginEmpID", null);
		request.getSession().setAttribute("dataLoginEmpName", null);
		request.getSession().setAttribute("dataLoginrole", null);
		request.getSession().setAttribute("dataLoginEmpprofiileimg", null);
		return "login";
	}

	@GetMapping("403")
	public String accessDenied(Model model) {

		return "error/403";
	}

	@GetMapping("createpwd")
	public String createpwd(Model themodel, @RequestParam(name = "id", required = false) Long id) {

		if (!(id == null)) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(String.valueOf(id)));

			Login obj = new Login();
			obj.setId(Long.valueOf(empobj.getEmpMasterid()));

			themodel.addAttribute("login", obj);
			themodel.addAttribute("empname", empobj.getStaffName());
		}
		return "credentialreg";
	}

	@PostMapping("createpwd")
	public String registerloginAccount(@ModelAttribute("login") Login login,
			@RequestParam(name = "privilege", defaultValue = "ROLE_EMPLOYEE") String privilege) {

		Login existing = loginService.findByEmpid(String.valueOf(login.getId()));
		if (existing != null) {
			return "redirect:/createpwd?error";
		} else {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(String.valueOf(login.getId())));

			LoginRegistrationDto loginDto = new LoginRegistrationDto();
			loginDto.setEmpid(String.valueOf(empobj.getEmpMasterid()));
			loginDto.setConfirmPassword(String.valueOf(empobj.getEmpMasterid()));
			loginDto.setPassword(String.valueOf(empobj.getEmpMasterid()));

			loginService.save(loginDto, privilege);

		}
		return "redirect:/createpwd?success";
	}

	@GetMapping("changerole")
	public String changeroleget(Model themodel, @RequestParam(name = "id", required = false) Long id) {

		if (!(id == null)) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(String.valueOf(id)));
			Login obj = loginService.findByEmpid(String.valueOf(empobj.getEmpMasterid()));

			if (obj == null) {
				return "redirect:/credentialrolechange?error";
			} else {
				themodel.addAttribute("login", obj);
				themodel.addAttribute("empname", empobj.getStaffName());
			}
		}
		return "credentialrolechange";
	}

	@PostMapping("changerole")
	public String changerole(@RequestParam(name = "id") int id, @RequestParam(name = "privilege") String privilege,
			@RequestParam(name = "checkboxresetpwd", defaultValue = "false") boolean checkboxresetpwd) {

		Login existing = loginService.findByEmpid(String.valueOf(id));
		if (existing != null) {

			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(String.valueOf(id)));
			LoginRegistrationDto loginDto = new LoginRegistrationDto();
			loginDto.setEmpid(String.valueOf(empobj.getEmpMasterid()));
			if (checkboxresetpwd) {
				loginDto.setPassword(passwordEncoder.encode(String.valueOf(empobj.getEmpMasterid())));
			} else {
				loginDto.setPassword(existing.getPassword());
			}

			loginService.resetall(loginDto, privilege, existing.getId());

		} else {

			return "redirect:/changerole?error";
		}
		return "redirect:/changerole?success";
	}

	@GetMapping("addnewbranch")
	public String addnewbranch(Model theModel) {

		List<BranchMaster> bmlist = branchMasterService.findAll().stream()
				.filter(c -> c.getB_TYPE().equalsIgnoreCase("Head Office")).collect(Collectors.toList());
		theModel.addAttribute("Headofficelist", bmlist);
		BranchMaster obj_bm = new BranchMaster();
		theModel.addAttribute("BranchMaster", obj_bm);
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		theModel.addAttribute("supplierlist",
				corglis.stream().filter(C -> nullremover(C.getCustomer_supplier()).equalsIgnoreCase("Supplier"))
						.collect(Collectors.toList()));

		return "branchadd";

	}

	@PostMapping("branchsave")
	public String branchsave(HttpServletRequest req, @ModelAttribute("BranchMaster") BranchMaster bmobj,
			Model theModel) {
		branchMasterService.save(bmobj);

		List<BranchMaster> bmlist = branchMasterService.findAll().stream()
				.filter(c -> c.getB_TYPE().equalsIgnoreCase("Head Office")).collect(Collectors.toList());
		theModel.addAttribute("Headofficelist", bmlist);

		theModel.addAttribute("BranchMaster", bmobj);
		theModel.addAttribute("save", true);
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		theModel.addAttribute("supplierlist",
				corglis.stream().filter(C -> nullremover(C.getCustomer_supplier()).equalsIgnoreCase("Supplier"))
						.collect(Collectors.toList()));

		return "branchadd";
	}

	@GetMapping("branchlist")
	public String branchList(Model themodel) {
		List<BranchMaster> bmList = branchMasterService.findAll();

		// System.out.println("<---------List of Branch------------->");
		// System.out.println(bmList);
		themodel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("branchlist", bmList);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_branchlist"));
		return "branchlist";

	}

	@GetMapping("checkHeadofficeisPresant")
	@ResponseBody
	public boolean checkHeadofficeisPresant(@RequestParam Map<String, String> params) {

		List<BranchMaster> bmList = branchMasterService.findAll().stream()
				.filter(C -> C.getB_TYPE().equalsIgnoreCase("Head-Office")).collect(Collectors.toList());
		if (bmList.size() > 0) {

			if (params.get("branchid") != null) {
				if (bmList.get(0).getId() == Integer.parseInt(params.get("branchid"))) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	@GetMapping("checkPrimaryContactisPresant")
	@ResponseBody
	public boolean checkPrimaryContactisPresant(@RequestParam Map<String, String> params) {
		if (params.get("src").equalsIgnoreCase("Branch")) {

			List<BranchContact> bmList = branchMasterService.findById(Integer.parseInt(params.get("branchid")))
					.getBranchContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (bmList.size() > 0) {

				if (params.get("modalcontactid") != null
						&& (!params.get("modalcontactid").toString().equalsIgnoreCase("-"))) {
					if (bmList.get(0).getBranchcontactid() == Integer.parseInt(params.get("modalcontactid"))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}

			} else {
				return false;
			}

		} else if (params.get("src").equalsIgnoreCase("Employee")) {

			List<EmployeeContact> empList = employeeMasterService.findById(Integer.parseInt(params.get("empMasterid")))
					.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (empList.size() > 0) {

				if (params.get("modalcontactid") != null
						&& (!params.get("modalcontactid").toString().equalsIgnoreCase("-"))) {
					if (empList.get(0).getEmployeecontactid() == Integer.parseInt(params.get("modalcontactid"))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}

			} else {
				return false;
			}

		} else if (params.get("src").equalsIgnoreCase("ContactPerson")) {

			List<ContactPersonContact> cpList = contactPersonService
					.findById(Integer.parseInt(params.get("contactPersonid"))).getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (cpList.size() > 0) {

				if (params.get("modalcontactid") != null
						&& (!params.get("modalcontactid").toString().equalsIgnoreCase("-"))) {
					if (cpList.get(0).getContactid() == Integer.parseInt(params.get("modalcontactid"))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}

			} else {
				return false;
			}

		} else if (params.get("src").equalsIgnoreCase("OrganizationContacts")) {

			List<OrganizationContact> cpList = contactOrganizationService
					.findById(Integer.parseInt(params.get("organizationid"))).getOrganizationContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (cpList.size() > 0) {

				if (params.get("modalcontactid") != null
						&& (!params.get("modalcontactid").toString().equalsIgnoreCase("-"))) {
					if (cpList.get(0).getContactid() == Integer.parseInt(params.get("modalcontactid"))) {
						return false;
					} else {
						return true;
					}
				} else {
					return true;
				}

			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	@ResponseBody
	@PostMapping("branchsavejson")
	public int branchsavejson(@RequestParam Map<String, String> params) {
		BranchMaster bm = new BranchMaster();
		bm.setB_TYPE(params.get("branch_type"));
		bm.setBRANCH_NAME(params.get("branchName"));
		bm.setBRANCH_IN_CHARGE(params.get("branch_incharge"));
		bm.setBRANCH_OFFICE_EMAIL_ID(params.get("branchemail"));
		bm.setSTATED_DATE(params.get("branch_startdate"));
		bm.setCOMES_UNDER(params.get("branchHierarchy"));
		bm.setCURRENT_STATUS(params.get("branchstatus"));
		bm.setOFFICE_PHONE_NUMBER(params.get("branchOfficeContact"));
		bm.setBranchCode(params.get("branchcode"));

		List<BranchEffective> bfls = new ArrayList<BranchEffective>();
		bfls.add(new BranchEffective(0, params.get("branch_startdate"), params.get("branchstatus")));
		bm.setBranchEffective(bfls);
		bm = branchMasterService.save(bm);
		return bm.getId();
	}

	@ResponseBody
	@PostMapping("branchupdatejson")
	public BranchMaster branchupdatejson(@RequestParam Map<String, String> params) {
		int branchid = Integer.parseInt(params.get("BranchID"));
		BranchMaster bm = branchMasterService.findById(branchid);

		if (!bm.getCURRENT_STATUS().equalsIgnoreCase(params.get("branchstatus"))) {
			List<BranchEffective> bfls = bm.getBranchEffective();
			bfls.add(new BranchEffective(0, params.get("effectiveon"), params.get("branchstatus")));
			bm.setBranchEffective(bfls);
		}
		bm.setB_TYPE(params.get("branch_type"));
		bm.setBRANCH_NAME(params.get("branchName"));
		bm.setBRANCH_IN_CHARGE(params.get("branch_incharge"));
		bm.setSTATED_DATE(params.get("branch_startdate"));
		bm.setCOMES_UNDER(params.get("branchHierarchy"));
		bm.setCURRENT_STATUS(params.get("branchstatus"));
		bm.setBranchCode(params.get("branchCode"));

		bm = branchMasterService.save(bm);
		return branchListresponsebody(bm);

	}

	@ResponseBody
	@PostMapping("fileuploadjson")
	public Object fileuploadjson(@RequestParam Map<String, String> params,
			@RequestParam(name = "File_Attach", required = false) MultipartFile Files_Attach,
			HttpServletRequest request) {

		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("branchfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("branchfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			BranchFiles bfiles = new BranchFiles();

			int branchid = Integer.parseInt(params.get("BranchID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			int id = branchMasterService.insertbranchFiles(Documenttype, DocNo, filename.toString(), branchid);

			bfiles.setBranchfilesid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("contactpersonfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("contactpersonfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			ContactPersonFiles bfiles = new ContactPersonFiles();

			int ContactPersonid = Integer.parseInt(params.get("ContactPersonID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			int id = contactPersonService.insertFiles(Documenttype, DocNo, filename.toString(), ContactPersonid);

			bfiles.setFilesid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("OrganizationContacts")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext()
						.getRealPath("organizationcontactsfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("organizationcontactsfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			OrganizationFiles bfiles = new OrganizationFiles();

			int Organizationid = Integer.parseInt(params.get("OrganizationContactsID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			int id = contactOrganizationService.insertFiles(Documenttype, DocNo, filename.toString(), Organizationid);

			bfiles.setFilesid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("employeefiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("employeefiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			EmployeeFiles bfiles = new EmployeeFiles();

			int employeeid = Integer.parseInt(params.get("empMasterid"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");

			itemlistService.savesingletxt(Documenttype, "Documenttype");

			int id = employeeMasterService.insertemployeeFiles(Documenttype, DocNo, filename.toString(), employeeid);

			bfiles.setEmpFileid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("Asset")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("assetfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("assetfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			AssetMasterFiles bfiles = new AssetMasterFiles();

			int AssetId = Integer.parseInt(params.get("AssetId"));

			int id = assetMasterService.insertassetFiles(filename.toString(), AssetId);

			bfiles.setAssetFileid(id);
			bfiles.setFiles_Attach(filename.toString());
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("Lead")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("leadfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("leadfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			LeadFiles bfiles = new LeadFiles();

			int ContactPersonid = Integer.parseInt(params.get("LeadID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			String createdate = displaydateFormatFirstMMMddYYYAMPM.format(new Date());
			int id = leadMasterService.insertFiles(Documenttype, DocNo, filename.toString(), ContactPersonid,
					createdate);

			bfiles.setFileid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			bfiles.setCreateddate(createdate);
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("Deal")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("dealfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("dealfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			DealFiles bfiles = new DealFiles();

			int ContactPersonid = Integer.parseInt(params.get("DealID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			String createdate = displaydateFormatFirstMMMddYYYAMPM.format(new Date());
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			int id = dealMasterService.insertFiles(Documenttype, DocNo, filename.toString(), ContactPersonid,
					createdate);

			bfiles.setFileid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			bfiles.setCreateddate(createdate);
			return bfiles;
		} else if (params.get("functiontype").equalsIgnoreCase("Project")) {
			StringBuilder filename = new StringBuilder();
			if (Files_Attach != null) {
				// File Uploading
				String profilephotouploadRootPath = request.getServletContext().getRealPath("projectfiles");
				// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

				File uploadRootDir = new File(profilephotouploadRootPath);
				// Create directory if it not exists.
				if (!uploadRootDir.exists()) {
					uploadRootDir.mkdirs();
				}

				if (Files_Attach.getOriginalFilename().toString().length() > 0) {

					String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append("projectfiles/" + tempfilename);

					try {
						Files.write(fileNameandPath, Files_Attach.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			ProjectFiles bfiles = new ProjectFiles();

			int ContactPersonid = Integer.parseInt(params.get("ProjectID"));
			String Documenttype = params.get("Documenttype");
			String DocNo = params.get("DocNo");
			String createdate = displaydateFormatFirstMMMddYYYAMPM.format(new Date());
			itemlistService.savesingletxt(Documenttype, "Documenttype");
			int id = projectMasterService.insertFiles(Documenttype, DocNo, filename.toString(), ContactPersonid,
					createdate);

			bfiles.setFileid(id);
			bfiles.setDocumentNo(DocNo);
			bfiles.setDocumentType(Documenttype);
			bfiles.setFilePath(filename.toString());
			bfiles.setCreateddate(createdate);
			return bfiles;
		} else {
			throw new RuntimeException("functiontype is invalid");
		}

	}

	@ResponseBody
	@PostMapping("Addressupdatejson")
	public Object Addressupdatejson(@RequestParam Map<String, String> params) {

		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			int branchid = Integer.parseInt(params.get("BranchID"));
			BranchMaster bm = branchMasterService.findById(branchid);
			bm.setCOUNTRY(params.get("AddressCountry"));
			bm.setBRANCH_ADDRESSLINE1(params.get("AddressAddress1"));
			bm.setBRANCH_ADDRESSLINE2(params.get("AddressAddress2"));
			bm.setBRANCH_ADDRESSLandMark(params.get("AddressLandmark"));
			bm.setBRANCH_ADDRESSVillage(params.get("AddressVillage"));
			bm.setCITY(params.get("AddressCity"));
			bm.setSTATE(params.get("AddressState"));
			bm.setZIP_CODE(params.get("AddressZipCode"));
			bm = branchMasterService.save(bm);
			return branchListresponsebody(bm);
		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {

			int ContactPersonID = Integer.parseInt(params.get("ContactPersonID"));
			ContactPerson cp = contactPersonService.findById(ContactPersonID);
			cp.setAddressCountry(params.get("AddressCountry"));
			cp.setAddressStreet1(params.get("AddressAddress1"));
			cp.setAddressStreet2(params.get("AddressAddress2"));
			cp.setAddressLankmark(params.get("AddressLandmark"));
			cp.setAddressVillage(params.get("AddressVillage"));
			cp.setAddressCity(params.get("AddressCity"));
			cp.setAddressState(params.get("AddressState"));
			cp.setAddressZIP(params.get("AddressZipCode"));
			cp = contactPersonService.save(cp);
			return ContactPersonobjectfiller(cp);
		} else if (params.get("functiontype").equalsIgnoreCase("OrganizationContacts")) {

			int OrganizationContactsID = Integer.parseInt(params.get("OrganizationContactsID"));
			OrganizationContacts corg = contactOrganizationService.findById(OrganizationContactsID);
			corg.setAddressCountry(params.get("AddressCountry"));
			corg.setAddressStreet1(params.get("AddressAddress1"));
			corg.setAddressStreet2(params.get("AddressAddress2"));
			corg.setAddressLankmark(params.get("AddressLandmark"));
			corg.setAddressVillage(params.get("AddressVillage"));
			corg.setAddressCity(params.get("AddressCity"));
			corg.setAddressState(params.get("AddressState"));
			corg.setAddressZIP(params.get("AddressZipCode"));
			corg = contactOrganizationService.save(corg);
			return OrganizationContactsobjectfiller(corg);
		}

		else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			int empMasterid = Integer.parseInt(params.get("empMasterid"));
			EmployeeMaster bm = employeeMasterService.findById(empMasterid);
			bm.setAddress_Country(params.get("AddressCountry"));
			bm.setAddress_Street1(params.get("AddressAddress1"));
			bm.setAddress_Street2(params.get("AddressAddress2"));
			bm.setAddress_Landmark(params.get("AddressLandmark"));
			bm.setAddress_Village(params.get("AddressVillage"));
			bm.setAddress_City(params.get("AddressCity"));
			bm.setAddress_State(params.get("AddressState"));
			bm.setAddress_ZIP(params.get("AddressZipCode"));
			bm = employeeMasterService.save(bm);
			return bm;
		} else if (params.get("functiontype").equalsIgnoreCase("Deal")) {
			int dealMasterID = Integer.parseInt(params.get("dealMasterID"));
			DealMaster bm = dealMasterService.findById(dealMasterID);
			bm.setAddressline1(params.get("AddressAddress1"));
			bm.setAddressline2(params.get("AddressAddress2"));
			bm.setLankmark(params.get("AddressLandmark"));
			bm.setTaluk(params.get("AddressTaluk"));
			bm.setDistrict(params.get("AddressDistrict"));
			bm.setState(params.get("AddressState"));
			bm.setPincode(params.get("AddressPinCode"));
			bm.setLanlong(params.get("AddressLanlong"));

			bm = dealMasterService.save(bm);
			return bm;
		} else if (params.get("functiontype").equalsIgnoreCase("Project")) {
			int projectMasterID = Integer.parseInt(params.get("projectMasterID"));
			ProjectMaster bm = projectMasterService.findById(projectMasterID);
			bm.setAddressline1(params.get("AddressAddress1"));
			bm.setAddressline2(params.get("AddressAddress2"));
			bm.setLankmark(params.get("AddressLandmark"));
			bm.setTaluk(params.get("AddressTaluk"));
			bm.setDistrict(params.get("AddressDistrict"));
			bm.setState(params.get("AddressState"));
			bm.setPincode(params.get("AddressPinCode"));
			bm.setLanlong(params.get("AddressLanlong"));

			bm = projectMasterService.save(bm);
			return bm;
		}

		else {
			throw new RuntimeException("functiontype is invalid");
		}
	}

	@ResponseBody
	@PostMapping("Contactupdatejson")
	public int Contactupdatejson(@RequestParam Map<String, String> params) {

		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			int branchid = Integer.parseInt(params.get("BranchID"));
			String contacttype = params.get("contacttype");
			String contactPhone = params.get("contactPhone");
			String contactemail = params.get("contactemail");
			String contactid = params.get("contactid");
			boolean primarycontact = Boolean.parseBoolean(params.get("primarycontact"));
			itemlistService.savesingletxt(contacttype, "CONTACTTYPE");

			if (contactid.equalsIgnoreCase("-")) {
				return branchMasterService.insertbranchContact(contacttype, contactPhone, contactemail, branchid,
						primarycontact);
			} else {
				return branchMasterService.updatebranchContact(Integer.parseInt(params.get("contactid")), contacttype,
						contactPhone, contactemail, primarycontact);
			}
		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {
			int ContactPersonID = Integer.parseInt(params.get("ContactPersonID"));
			String contacttype = params.get("contacttype");
			String contactPhone = params.get("contactPhone");
			String contactemail = params.get("contactemail");
			String contactid = params.get("contactid");
			boolean primarycontact = Boolean.parseBoolean(params.get("primarycontact"));
			itemlistService.savesingletxt(contacttype, "CONTACTTYPE");

			if (contactid.equalsIgnoreCase("-")) {
				return contactPersonService.insertContact(contacttype, contactPhone, contactemail, ContactPersonID,
						primarycontact);
			} else {
				return contactPersonService.updateContact(Integer.parseInt(params.get("contactid")), contacttype,
						contactPhone, contactemail, primarycontact);
			}
		} else if (params.get("functiontype").equalsIgnoreCase("OrganizationContacts")) {
			int OrganizationContactID = Integer.parseInt(params.get("OrganizationContactsID"));
			String contacttype = params.get("contacttype");
			String contactPhone = params.get("contactPhone");
			String contactemail = params.get("contactemail");
			String contactid = params.get("contactid");
			boolean primarycontact = Boolean.parseBoolean(params.get("primarycontact"));
			itemlistService.savesingletxt(contacttype, "CONTACTTYPE");

			if (contactid.equalsIgnoreCase("-")) {
				return contactOrganizationService.insertContact(contacttype, contactPhone, contactemail,
						OrganizationContactID, primarycontact);
			} else {
				return contactOrganizationService.updateContact(Integer.parseInt(params.get("contactid")), contacttype,
						contactPhone, contactemail, primarycontact);
			}
		} else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			int empMasterid = Integer.parseInt(params.get("empMasterid"));
			String contacttype = params.get("contacttype");
			String contactPhone = params.get("contactPhone");
			String contactemail = params.get("contactemail");
			String contactid = params.get("contactid");
			boolean primarycontact = Boolean.parseBoolean(params.get("primarycontact"));
			itemlistService.savesingletxt(contacttype, "CONTACTTYPE");

			if (contactid.equalsIgnoreCase("-")) {
				return employeeMasterService.insertemployeeContact(contacttype, contactPhone, contactemail, empMasterid,
						primarycontact);
			} else {
				return employeeMasterService.updateemployeeContact(Integer.parseInt(params.get("contactid")),
						contacttype, contactPhone, contactemail, primarycontact);
			}

		} else {
			throw new RuntimeException("functiontype is invalid");
		}

	}

	@ResponseBody
	@PostMapping("Bankaccupdatejson")
	public int Bankaccupdatejson(@RequestParam Map<String, String> params) {

		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			int branchid = Integer.parseInt(params.get("BranchID"));
			int acid = Integer.parseInt(params.get("acid"));
			String acno = params.get("acno");
			String acname = params.get("acname");
			String bankname = params.get("bankname");
			String branchname = params.get("branchname");
			String ifsccode = params.get("ifsccode");
			return branchMasterService.insertbranchAccountdetails(acid, acno, acname, bankname, branchname, ifsccode,
					branchid);

		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {
			int ContactPersonID = Integer.parseInt(params.get("ContactPersonID"));
			int acid = Integer.parseInt(params.get("acid"));
			String acno = params.get("acno");
			String acname = params.get("acname");
			String bankname = params.get("bankname");
			String branchname = params.get("branchname");
			String ifsccode = params.get("ifsccode");
			return contactPersonService.insertAccountdetails(acid, acno, acname, bankname, branchname, ifsccode,
					ContactPersonID);

		} else if (params.get("functiontype").equalsIgnoreCase("OrganizationContacts")) {
			int OrganizationContactsID = Integer.parseInt(params.get("OrganizationContactsID"));
			int acid = Integer.parseInt(params.get("acid"));
			String acno = params.get("acno");
			String acname = params.get("acname");
			String bankname = params.get("bankname");
			String branchname = params.get("branchname");
			String ifsccode = params.get("ifsccode");
			return contactOrganizationService.insertAccountdetails(acid, acno, acname, bankname, branchname, ifsccode,
					OrganizationContactsID);

		} else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			int empMasterid = Integer.parseInt(params.get("empMasterid"));
			int acid = Integer.parseInt(params.get("acid"));
			String acno = params.get("acno");
			String acname = params.get("acname");
			String bankname = params.get("bankname");
			String branchname = params.get("branchname");
			String ifsccode = params.get("ifsccode");
			return employeeMasterService.insertemployeeAccountdetails(acid, acno, acname, bankname, branchname,
					ifsccode, empMasterid);

		} else {
			throw new RuntimeException("functiontype is invalid");
		}
	}

	@ResponseBody
	@PostMapping("Contactdeletejson")
	public int Contactdeletejson1(@RequestParam Map<String, String> params) {

		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			int contactid = Integer.parseInt(params.get("contactid"));

			return branchMasterService.deletebranchContact(contactid);
		} else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			int contactid = Integer.parseInt(params.get("contactid"));
			return employeeMasterService.deleteemployeeContact(contactid);
		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {
			int contactid = Integer.parseInt(params.get("contactid"));
			return contactPersonService.deleteContact(contactid);
		} else if (params.get("functiontype").equalsIgnoreCase("OrganizationContacts")) {
			int contactid = Integer.parseInt(params.get("contactid"));
			return contactOrganizationService.deleteContact(contactid);
		} else {
			throw new RuntimeException("functiontype is invalid");
		}

	}

	@ResponseBody
	@PostMapping("Filedeletejson")
	public int Filedeletejson(@RequestParam Map<String, String> params) {
		if (params.get("functiontype").equalsIgnoreCase("Branch")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return branchMasterService.deletebranchFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("Employee")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return employeeMasterService.deleteemployeeFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("ContactPerson")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return contactPersonService.deleteFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("AssetFile")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return assetMasterService.deleteassetFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("Lead")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return leadMasterService.deleteFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("Deal")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return dealMasterService.deleteFiles(fileid);
		} else if (params.get("functiontype").equalsIgnoreCase("Project")) {
			int fileid = Integer.parseInt(params.get("fileid"));
			return projectMasterService.deleteFiles(fileid);
		} else {
			throw new RuntimeException("functiontype is invalid");
		}
	}

	public BranchMaster branchListresponsebody(BranchMaster bm) {

		Date todaydate = new Date();
		List<BranchMaster> bmList = branchMasterService.findAll();

		if (!bm.getCOMES_UNDER().equalsIgnoreCase("Self")) {
			List<BranchMaster> templist = bmList.stream()
					.filter(C -> C.getId() == Integer.parseInt(bm.getCOMES_UNDER())).collect(Collectors.toList());
			if (templist.size() > 0) {
				bm.setCOMES_UNDER_name(templist.get(0).getBRANCH_NAME());
			}
		} else {
			bm.setCOMES_UNDER_name("Self");
		}
		if (!bm.getB_TYPE().equalsIgnoreCase("")) {
			bm.setBRANCH_Type_2w(bm.getB_TYPE().substring(0, 1) + "O");
		}
		if (!nullremover(String.valueOf(bm.getBRANCH_IN_CHARGE())).equalsIgnoreCase("")) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()));
			bm.setBRANCH_IN_CHARGE_img(getemp_photo(empobj));
			bm.setBRANCH_IN_CHARGE_name(empobj.getStaffName());

		}
		if (!bm.getSTATED_DATE().equalsIgnoreCase("")) {
			try {
				bm.setStartdateMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(bm.getSTATED_DATE())).toString());
				bm.setStartdatatimeline(getTimeage(bm.getSTATED_DATE()));

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// Branch Effective
		List<BranchEffective> branchEffective = bm.getBranchEffective();

		if (branchEffective.size() > 0) {
			branchEffective.sort(Comparator.comparing(BranchEffective::getEffectivedate));
			bm.setEffectiveon(branchEffective.get(branchEffective.size() - 1).getEffectivedate());
			try {
				bm.setEffectiveonMMformat(
						displaydateFormatFirstMMMddYYY
								.format(displaydateFormatrev
										.parse(branchEffective.get(branchEffective.size() - 1).getEffectivedate()))
								.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------
		List<BranchContact> bcls = bm.getBranchContact().stream().filter(C -> C.getPrimarycontact() == true)
				.collect(Collectors.toList());
		if (bcls.size() > 0) {
			bm.setOFFICE_PHONE_NUMBER(bcls.get(0).getPhonenumber());
			bm.setBRANCH_OFFICE_EMAIL_ID(bcls.get(0).getEmail());
		}
		// -------------------------------------------
		List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()))
				.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
		if (ecls.size() > 0) {
			bm.setIN_CHARGE_CONTACT_DETAILS(ecls.get(0).getPhonenumber());

		}
		// -------------------------------------------

		return bm;
	}

	@ResponseBody
	@GetMapping("employeetagifylist")
	public ArrayList employeetagifylist(Model themodel) {
		List<EmployeeMaster> empobj = EffectiveEmployee(employeeMasterService.findAll());
		ArrayList<HashMap<String, Object>> arrlist = new ArrayList<>();

		for (EmployeeMaster obj : empobj) {
			HashMap<String, Object> hmp = new HashMap();
			hmp.put("value", obj.getEmpMasterid());
			hmp.put("name", obj.getStaffName());
			hmp.put("avatar", getemp_photo(obj));
			arrlist.add(hmp);
		}

		return arrlist;
	}

	@ResponseBody
	@GetMapping("branchlistjson")
	public List<BranchMaster> branchListresponsebody(Model themodel) {
		List<BranchMaster> bmList = branchMasterService.findAll();
		Date todaydate = new Date();

		for (BranchMaster bm : bmList) {

			if (!bm.getCOMES_UNDER().equalsIgnoreCase("Self")) {
				List<BranchMaster> templist = bmList.stream()
						.filter(C -> C.getId() == Integer.parseInt(bm.getCOMES_UNDER())).collect(Collectors.toList());
				if (templist.size() > 0) {
					bm.setCOMES_UNDER_name(templist.get(0).getBRANCH_NAME());
				}
			} else {
				bm.setCOMES_UNDER_name("Self");
			}
			if (!bm.getB_TYPE().equalsIgnoreCase("")) {
				bm.setBRANCH_Type_2w(bm.getB_TYPE().substring(0, 1) + "O");
			}
			if (!nullremover(String.valueOf(bm.getBRANCH_IN_CHARGE())).equalsIgnoreCase("")) {
				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()));
				bm.setBRANCH_IN_CHARGE_img(getemp_photo(empobj));
				bm.setBRANCH_IN_CHARGE_name(empobj.getStaffName());

			}
			if (!bm.getSTATED_DATE().equalsIgnoreCase("")) {
				try {
					bm.setStartdateMMformat(displaydateFormatFirstMMMddYYY
							.format(displaydateFormatrev.parse(bm.getSTATED_DATE())).toString());
					bm.setStartdatatimeline(getTimeage(bm.getSTATED_DATE()));

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// Set primary contact

			List<BranchContact> bcls = bm.getBranchContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (bcls.size() > 0) {
				bm.setOFFICE_PHONE_NUMBER(bcls.get(0).getPhonenumber());
				bm.setBRANCH_OFFICE_EMAIL_ID(bcls.get(0).getEmail());
			}
			// -------------------
			// Set primary contact
			List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()))
					.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (ecls.size() > 0) {
				bm.setIN_CHARGE_CONTACT_DETAILS(ecls.get(0).getPhonenumber());

			}
			// -------------------

		}

		return bmList;

	}

	@ResponseBody
	@GetMapping("contactpersonlistjson")
	public List<ContactPerson> contactpersonlistjson(Model themodel) {
		List<ContactPerson> cpList = contactPersonService.findAll().stream().filter(C -> C.getPeoplename() != null)
				.collect(Collectors.toList());

		for (ContactPerson cp : cpList) {

			if (!nullremover(String.valueOf(cp.getFollowers())).equalsIgnoreCase("")) {
				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()));
				cp.setFollowerimg(getemp_photo(empobj));
				cp.setFollowername(empobj.getStaffName());
			}

			// branch name
			BranchMaster bm = branchMasterService.findById(cp.getBranchid());
			cp.setBranchName(bm.getBRANCH_NAME());
			cp.setBranchCode(bm.getBranchCode());
			// Organization Name
			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				cp.setOrganizationname(
						contactOrganizationService.findById(Integer.parseInt(cp.getOrganization())).getOrgname());
			}

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			// -------------------
			// Set primary contact
			if (!nullremover(String.valueOf(cp.getFollowers())).equalsIgnoreCase("")) {

				List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()))
						.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
						.collect(Collectors.toList());
				if (ecls.size() > 0) {
					cp.setFollowerprimarymob(ecls.get(0).getPhonenumber());

				}
			}
			// -------------------

		}

		return cpList;

	}

	@ResponseBody
	@GetMapping("contactorganizationlistjson")
	public List<OrganizationContacts> contactorganizationlistjson(Model themodel) {
		List<OrganizationContacts> cpList = contactOrganizationService.findAll();
		Date todaydate = new Date();

		for (OrganizationContacts cp : cpList) {

			if (!nullremover(String.valueOf(cp.getFollowers())).equalsIgnoreCase("")) {
				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()));
				cp.setFollowerimg(getemp_photo(empobj));
				cp.setFollowername(empobj.getStaffName());

				// Set primary contact
				List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()))
						.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
						.collect(Collectors.toList());
				if (ecls.size() > 0) {
					cp.setFollowerprimarymob(ecls.get(0).getPhonenumber());

				}
			}
			// branch name
			BranchMaster bm = branchMasterService.findById(cp.getBranchid());
			cp.setBranchName(bm.getBRANCH_NAME());
			cp.setBranchCode(bm.getBranchCode());
			// -------------------
			cp.setCplist(contactPersonService.contactpersonlistbyorgname(String.valueOf(cp.getId())));

		}

		return cpList;

	}

	public String getTimeage(String date) {

		String[] arr = date.toString().split("-");
		LocalDate today = LocalDate.now();
		LocalDate starteddate = LocalDate.of(Integer.parseInt(arr[0]), Month.of(Integer.parseInt(arr[1])),
				Integer.parseInt(arr[2]));

		// LocalDate birthday = LocalDate.of(1960, Month.JANUARY, 1);

		Period period = Period.between(today, starteddate);
		int years = Math.abs(period.getYears());
		int months = Math.abs(period.getMonths());
		int days = Math.abs(period.getDays());

		String timeline = "";

		if (years > 0) {
			timeline += years + " Years ";
		}
		if (months > 0) {
			timeline += months + " Months ";
		}
		if (days > 0) {
			timeline += days + " Days ";
		}

		return timeline;
	}

	@GetMapping("editbranch")
	public String getBranchMassterDetails(Model theModel, @RequestParam("id") int branchid) {
		List<BranchMaster> bmlist = branchMasterService.findAll();

		BranchMaster bm = branchMasterService.findById(branchid);
		if (bm.getBranchAccNo().size() == 0) {
			List<BranchAccNo> BranchAccNols = new ArrayList();
			BranchAccNols.add(new BranchAccNo());
			bm.setBranchAccNo(BranchAccNols);
			bm = branchMasterService.save(bm);
		}
		// ---------------------------------------
		// Get Primary contact
		List<BranchContact> branchContactls = bm.getBranchContact().stream().filter(C -> C.getPrimarycontact() == true)
				.collect(Collectors.toList());
		if (branchContactls.size() == 0) {
			theModel.addAttribute("primaryContact", false);
		} else {
			theModel.addAttribute("primaryContact", true);
		}
		// ---------------------------------------
		if (!bm.getCOMES_UNDER().equalsIgnoreCase("Self")) {
			int comes_underint = Integer.parseInt(bm.getCOMES_UNDER());
			List<BranchMaster> templist = bmlist.stream().filter(C -> C.getId() == comes_underint)
					.collect(Collectors.toList());
			if (templist.size() > 0) {
				bm.setCOMES_UNDER_name(templist.get(0).getBRANCH_NAME());
			}
		} else {
			bm.setCOMES_UNDER_name("Self");
		}
		if (!bm.getB_TYPE().equalsIgnoreCase("")) {
			bm.setBRANCH_Type_2w(bm.getB_TYPE().substring(0, 1) + "O");
		}
		if (!nullremover(String.valueOf(bm.getBRANCH_IN_CHARGE())).equalsIgnoreCase("")) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()));
			bm.setBRANCH_IN_CHARGE_img(getemp_photo(empobj));
			bm.setBRANCH_IN_CHARGE_name(empobj.getStaffName());

		}
		if (!bm.getSTATED_DATE().equalsIgnoreCase("")) {

			try {
				bm.setStartdateMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(bm.getSTATED_DATE())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bm.setStartdatatimeline(getTimeage(bm.getSTATED_DATE()));
		}

		// -------------------------------------------
		// Branch Effective
		List<BranchEffective> branchEffective = new ArrayList<>();
		branchEffective = branchMasterService.findById(branchid).getBranchEffective();
		if (branchEffective.size() > 0) {
			branchEffective.sort(Comparator.comparing(BranchEffective::getEffectivedate));
			bm.setEffectiveon(branchEffective.get(branchEffective.size() - 1).getEffectivedate());
			try {
				bm.setEffectiveonMMformat(
						displaydateFormatFirstMMMddYYY
								.format(displaydateFormatrev
										.parse(branchEffective.get(branchEffective.size() - 1).getEffectivedate()))
								.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------
		// -------------------------------------------
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		theModel.addAttribute("BranchMaster", bm);
		theModel.addAttribute("BranchList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_branchlist"));

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		theModel.addAttribute("supplierlist",
				corglis.stream().filter(C -> nullremover(C.getCustomer_supplier()).equalsIgnoreCase("Supplier"))
						.collect(Collectors.toList()));
		theModel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		theModel.addAttribute("expenselist", getaaccountsHeads_Expenses());
		return "branchadd";
	}

	@GetMapping("branchexpense")
	public String branchexpense(Model theModel, @RequestParam("id") int branchid) {
		List<BranchMaster> bmlist = branchMasterService.findAll();

		BranchMaster bm = branchMasterService.findById(branchid);
		if (bm.getBranchAccNo().size() == 0) {
			List<BranchAccNo> BranchAccNols = new ArrayList();
			BranchAccNols.add(new BranchAccNo());
			bm.setBranchAccNo(BranchAccNols);
			bm = branchMasterService.save(bm);
		}
		// ---------------------------------------
		// Get Primary contact
		List<BranchContact> branchContactls = bm.getBranchContact().stream().filter(C -> C.getPrimarycontact() == true)
				.collect(Collectors.toList());
		if (branchContactls.size() == 0) {
			theModel.addAttribute("primaryContact", false);
		} else {
			theModel.addAttribute("primaryContact", true);
		}
		// ---------------------------------------
		if (!bm.getCOMES_UNDER().equalsIgnoreCase("Self")) {
			int comes_underint = Integer.parseInt(bm.getCOMES_UNDER());
			List<BranchMaster> templist = bmlist.stream().filter(C -> C.getId() == comes_underint)
					.collect(Collectors.toList());
			if (templist.size() > 0) {
				bm.setCOMES_UNDER_name(templist.get(0).getBRANCH_NAME());
			}
		} else {
			bm.setCOMES_UNDER_name("Self");
		}
		if (!bm.getB_TYPE().equalsIgnoreCase("")) {
			bm.setBRANCH_Type_2w(bm.getB_TYPE().substring(0, 1) + "O");
		}
		if (!nullremover(String.valueOf(bm.getBRANCH_IN_CHARGE())).equalsIgnoreCase("")) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(bm.getBRANCH_IN_CHARGE()));
			bm.setBRANCH_IN_CHARGE_img(getemp_photo(empobj));
			bm.setBRANCH_IN_CHARGE_name(empobj.getStaffName());

		}
		if (!bm.getSTATED_DATE().equalsIgnoreCase("")) {

			try {
				bm.setStartdateMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(bm.getSTATED_DATE())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bm.setStartdatatimeline(getTimeage(bm.getSTATED_DATE()));
		}

		// -------------------------------------------
		// Branch Effective
		List<BranchEffective> branchEffective = new ArrayList<>();
		branchEffective = branchMasterService.findById(branchid).getBranchEffective();
		if (branchEffective.size() > 0) {
			branchEffective.sort(Comparator.comparing(BranchEffective::getEffectivedate));
			bm.setEffectiveon(branchEffective.get(branchEffective.size() - 1).getEffectivedate());
			try {
				bm.setEffectiveonMMformat(
						displaydateFormatFirstMMMddYYY
								.format(displaydateFormatrev
										.parse(branchEffective.get(branchEffective.size() - 1).getEffectivedate()))
								.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------
		// -------------------------------------------
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		theModel.addAttribute("BranchMaster", bm);
		theModel.addAttribute("BranchList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_branchlist"));

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		theModel.addAttribute("supplierlist",
				corglis.stream().filter(C -> nullremover(C.getCustomer_supplier()).equalsIgnoreCase("Supplier"))
						.collect(Collectors.toList()));
		theModel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		theModel.addAttribute("expenselist", getaaccountsHeads_Expenses());
		return "branchexpense";
	}

	public String getemp_photo(EmployeeMaster obj) {
		String str = "";
		List<EmployeeFiles> validProfilephoto = obj.getEmployeeFiles().stream()
				.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
		if (validProfilephoto.size() > 0) {
			str += validProfilephoto.get(0).getFilePath();
		}
		return str;
	}

	@GetMapping("emplist")
	public String employeelist(Model theModel) {
		List<String> JobTitle = itemlistService.findByFieldName("JobTitle");
		theModel.addAttribute("JobTitle", JobTitle);
		theModel.addAttribute("BranchList", branchMasterService.findAll());

		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_employeelist"));

		return "emplist";

	}

	@ResponseBody
	@GetMapping("employeelistjson")
	public List<EmployeeMaster> employeelistjson(Model themodel) {
		List<EmployeeMaster> empList = employeeMasterService.findAll();
		List<BranchMaster> bmList = branchMasterService.findAll();

		Date todaydate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		for (EmployeeMaster em : empList) {

			// Emp photo
			em.setT_emp_img(getemp_photo(em));
			// -------------------------------------------
			// Contact
			List<EmployeeContact> conlist = em.getEmployeeContact();
			em.setT_primary_contactno("");
			em.setT_primary_email("");
			if (conlist.size() > 0) {
				List<EmployeeContact> contactobjjobgreen = conlist.stream()
						.filter(c -> c.getDepartment().equalsIgnoreCase("Personal")).collect(Collectors.toList());
				if (contactobjjobgreen.size() > 0) {
					em.setT_primary_contactno(contactobjjobgreen.get(0).getPhonenumber());
					em.setT_primary_email(contactobjjobgreen.get(0).getEmail());
				}
			}
			// -------------------------------------------
			// Emp Location and Branch
			List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
			infoobjjob = employeeJobinfoService.findByEmployeeid(em.getEmpMasterid());
			if (infoobjjob.size() > 0) {
				List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
						.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
				if (infoobjjobgreen.size() > 0) {
					em.setT_position(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJobtitle());
					em.setT_branch_name(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation());
				}
			}
			// -------------------------------------------
			// Hiring date and timeline
			List<EmployeeJobHire> hireobj = new ArrayList<>();
			hireobj = employeeJobHireService.findByEmployeeid(em.getEmpMasterid());

			if (hireobj.size() > 0) {
				try {
					em.setT_joindateMMformat(displaydateFormatFirstMMMddYYY
							.format(displaydateFormatrev.parse(hireobj.get(0).getEmployeehiredate())).toString());
					em.setT_joindatetimeline(getTimeage(hireobj.get(0).getEmployeehiredate()));
				} catch (ParseException e) {
					// e.printStackTrace();
				}
			}
			// -------------------------------------------
			// Salary
			List<EmployeeJobcompensation> compenobj = new ArrayList<>();
			compenobj = employeeJobcompensationService.findByEmployeeid(em.getEmpMasterid());

			if (compenobj.size() > 0) {
				List<EmployeeJobcompensation> Compenobjjobgreen = compenobj.stream()
						.filter(c -> dateFormat.format(date).compareTo(c.getComeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				Compenobjjobgreen.sort(Comparator.comparing(EmployeeJobcompensation::getComeffectivedate));
				if (Compenobjjobgreen.size() > 0) {
					em.setT_salary(Compenobjjobgreen.get(Compenobjjobgreen.size() - 1).getCompayrate());

				}
			}
			// -------------------------------------------
			// CurrentStaus
			List<EmployeeJobempstatus> jobobj = new ArrayList<>();
			jobobj = employeeJobempstatusService.findByEmployeeid(em.getEmpMasterid());

			if (jobobj.size() > 0) {
				List<EmployeeJobempstatus> jobobjjobgreen = jobobj.stream()
						.filter(c -> dateFormat.format(date).compareTo(c.getEmpstatus_effectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				jobobjjobgreen.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate));
				if (jobobjjobgreen.size() > 0) {
					em.setT_currentStatus(
							jobobjjobgreen.get(jobobjjobgreen.size() - 1).getEmpstatus_employmentstatus());

				}
			}
			// -------------------------------------------
		}

		return empList;
	}

	@ResponseBody
	@PostMapping("employeesavejson")
	public int employeesavejson(@RequestParam Map<String, String> params) {
		EmployeeMaster emp = new EmployeeMaster();
		emp.setStaffName(params.get("firstName"));
		emp.setFather_HusbandName(params.get("lastName"));

		EmployeeContact contact = new EmployeeContact();
		contact.setDepartment("Personal");
		contact.setEmail(params.get("email"));
		contact.setPhonenumber(params.get("phonenumber"));
		List<EmployeeContact> scontact = new ArrayList<>();
		scontact.add(contact);
		emp.setEmployeeContact(scontact);

		emp = employeeMasterService.save(emp);
		int empid = emp.getEmpMasterid();

		EmployeeJobempstatus empstatus = new EmployeeJobempstatus();
		empstatus.setEmpstatus_effectivedate(params.get("joiningDate"));
		empstatus.setEmpstatus_employmentstatus("Full-Time");
		empstatus.setEmployeeid(empid);
		employeeJobempstatusService.save(empstatus);

		EmployeeJobinfo job = new EmployeeJobinfo();
		job.setJobeffectivedate(params.get("joiningDate"));
		job.setJoblocation(params.get("branch_name"));
		job.setJobtitle(params.get("position"));
		job.setEmployeeid(empid);
		employeeJobinfoService.save(job);

		EmployeeJobHire hire = new EmployeeJobHire();
		hire.setEmployeehiredate(params.get("joiningDate"));
		hire.setEmployeeid(empid);
		employeeJobHireService.save(hire);

		EmployeeJobcompensation comp = new EmployeeJobcompensation();
		comp.setComeffectivedate(params.get("joiningDate"));
		comp.setComchangereason("-");
		comp.setComcomments("");
		comp.setCompayrate(params.get("salary"));
		comp.setCompayratetype("Month");
		comp.setCompaytype("Salary");
		comp.setComPayschedule("Monthly");
		comp.setEmployeeid(empid);
		employeeJobcompensationService.save(comp);

		return empid;
	}

	@ResponseBody
	@PostMapping("employeep1json")
	public EmployeeMaster employeeuseredit(@RequestParam Map<String, String> params) {

		int empMasterid = Integer.parseInt(params.get("empMasterid"));
		EmployeeMaster emp = employeeMasterService.findById(empMasterid);
		emp.setStaffName(params.get("StaffName"));
		emp.setFather_HusbandName(params.get("Father_HusbandName"));
		emp.setGender(params.get("Gender"));
		emp.setMaritalStatus(params.get("MaritalStatus"));
		emp.setDateofBirth(params.get("DateofBirth"));
		emp.setBloodGroup(params.get("BloodGroup"));
		emp.setHeight(params.get("Height"));
		emp.setWeight(params.get("Weight"));
		emp.setShirtSize(params.get("ShirtSize"));
		emp.setShoeSize(params.get("ShoeSize"));
		emp.setPantSize(params.get("PantSize"));
		emp.setAccommodation(params.get("Accommodation"));

		// params.entrySet().forEach(el -> { System.out.println(el.getKey() + " " +
		// el.getValue());} );
		try {
			emp.setDobMMformat(displaydateFormatFirstMMMddYYY
					.format(displaydateFormatrev.parse(params.get("DateofBirth"))).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return employeeMasterService.save(emp);
	}

	@GetMapping("empnew")
	public String employeeadd(Model themodel) {

		EmployeeMaster empobj = new EmployeeMaster();

		EmployeeEducation empedu = new EmployeeEducation();
		ArrayList<EmployeeEducation> eduls = new ArrayList<EmployeeEducation>();
		empedu.setDegree("-");
		eduls.add(empedu);

		EmployeeEmgContact empcont = new EmployeeEmgContact();
		ArrayList<EmployeeEmgContact> emgls = new ArrayList<EmployeeEmgContact>();
		empcont.setEmg_Relation("-");
		emgls.add(empcont);

		EmployeeExperience empexper = new EmployeeExperience();
		ArrayList<EmployeeExperience> exptrls = new ArrayList<EmployeeExperience>();
		exptrls.add(empexper);

		EmployeeLanguage emplang = new EmployeeLanguage();
		ArrayList<EmployeeLanguage> langls = new ArrayList<EmployeeLanguage>();
		emplang.setLanguage("-");
		langls.add(emplang);

		EmployeeFiles empfiles = new EmployeeFiles();
		ArrayList<EmployeeFiles> filels = new ArrayList<EmployeeFiles>();
		filels.add(empfiles);

		List<String> MARITALSTATUS = itemlistService.findByFieldName("MARITAL STATUS");
		themodel.addAttribute("MARITALSTATUS", MARITALSTATUS);
		List<String> DEGREE = itemlistService.findByFieldName("DEGREE");
		themodel.addAttribute("DEGREE", DEGREE);
		List<String> RELATION = itemlistService.findByFieldName("RELATION");
		themodel.addAttribute("RELATION", RELATION);
		List<String> LANGUAGE = itemlistService.findByFieldName("LANGUAGE");
		themodel.addAttribute("LANGUAGE", LANGUAGE);

		themodel.addAttribute("employeeEducation", eduls);
		themodel.addAttribute("employeeEmgContact", emgls);
		themodel.addAttribute("employeeExperience", exptrls);
		themodel.addAttribute("employeeLanguage", langls);
		themodel.addAttribute("employeeFiles", empfiles);

		themodel.addAttribute("employeemaster", empobj);
		return "empadd";
	}

	@ResponseBody
	@PostMapping("employeeemgaddressjson")
	public int employeeemgaddressjson(@RequestParam Map<String, String> params) {

		EmployeeEmgContact emg = new EmployeeEmgContact();

		emg.setEmg_Country(params.get("emgcontact[Emg_Country]"));
		// emg.setEmg_EmailID(params.get("emgcontact[]"));
		if (String.valueOf(params.get("emgcontact[Emg_InsuranceNominee]")).equalsIgnoreCase("true")) {
			emg.setEmg_InsuranceNominee(true);
		} else {
			emg.setEmg_InsuranceNominee(false);
		}

		emg.setEmg_Landmark(params.get("emgcontact[Emg_Landmark]"));
		emg.setEmg_Name(params.get("emgcontact[Emg_Name]"));
		// emg.setEmg_OtherPhone(params.get("emgcontact[]"));
		emg.setEmg_PersonalPhone(params.get("emgcontact[Emg_PersonalPhone]"));
		emg.setEmg_State(params.get("emgcontact[Emg_State]"));
		emg.setEmg_Street1(params.get("emgcontact[Emg_Street1]"));
		emg.setEmg_Street2(params.get("emgcontact[Emg_Street2]"));
		emg.setEmg_Village(params.get("emgcontact[Emg_Village]"));
		emg.setEmg_ZIP(params.get("emgcontact[Emg_ZIP]"));
		emg.setEmg_City(params.get("emgcontact[Emg_City]"));
		emg.setEmg_Relation(params.get("emgcontact[Emg_Relation]"));

		int empMasterid = Integer.parseInt(params.get("emgcontact[empMasterid]"));
		EmployeeMaster emp = employeeMasterService.findById(empMasterid);

		if (params.get("emgcontact[empEmgContactid]").equalsIgnoreCase("")) {
			return employeeMasterService.insertemployeeEmgContact(empMasterid, emg);
		} else {
			emg.setEmpEmgContactid(Integer.parseInt(params.get("emgcontact[empEmgContactid]")));
			return employeeMasterService.updateemployeeEmgContact(emg);
		}

	}

	@ResponseBody
	@PostMapping("employeeQualificationjson")
	public int employeeQualificationjson(@RequestParam Map<String, String> params) {

		EmployeeEducation edu = new EmployeeEducation();

		edu.setCollege_Institution(params.get("edu[Institution]"));
		edu.setDegree(params.get("edu[Degree]"));
		edu.setFromYear(params.get("edu[Qualificationfrom]"));
		edu.setMajorSpecialization(params.get("edu[Major]"));
		edu.setPercentage_GPA(params.get("edu[Percentage]"));
		edu.setToYear(params.get("edu[Qualificationto]"));

		int empMasterid = Integer.parseInt(params.get("edu[empMasterid]"));

		if (params.get("edu[Qualid]").equalsIgnoreCase("")) {
			return employeeMasterService.insertemployeeQualification(empMasterid, edu);
		} else {
			edu.setEmpEduid(Integer.parseInt(params.get("edu[Qualid]")));
			return employeeMasterService.updateemployeeQualification(edu);
		}

	}

	@ResponseBody
	@PostMapping("employeeExperiencejson")
	public int employeeExperiencejson(@RequestParam Map<String, String> params) {

		EmployeeExperience exp = new EmployeeExperience();

		exp.setCompany(params.get("exp[Company]"));
		exp.setExpFromyear(params.get("exp[Experiencefrom]"));
		exp.setExpToyear(params.get("exp[Experiencento]"));
		exp.setJobTitle(params.get("exp[Role]"));
		exp.setLocation(params.get("exp[Location]"));

		int empMasterid = Integer.parseInt(params.get("exp[empMasterid]"));

		if (params.get("exp[expid]").equalsIgnoreCase("")) {
			return employeeMasterService.insertemployeeExperience(empMasterid, exp);
		} else {
			exp.setEmpExperienceid(Integer.parseInt(params.get("exp[expid]")));
			return employeeMasterService.updateemployeeExperience(exp);
		}

	}

	@ResponseBody
	@PostMapping("employeelanguagejson")
	public int employeelanguagejson(@RequestParam Map<String, String> params) {

		EmployeeLanguage langu = new EmployeeLanguage();

		if (String.valueOf(params.get("langu[Read]")).equalsIgnoreCase("on")) {
			langu.setLan_read(true);
		} else {
			langu.setLan_read(false);
		}
		if (String.valueOf(params.get("langu[Write]")).equalsIgnoreCase("on")) {
			langu.setLan_write(true);
		} else {
			langu.setLan_write(false);
		}
		if (String.valueOf(params.get("langu[Speak]")).equalsIgnoreCase("on")) {
			langu.setLan_speak(true);
		} else {
			langu.setLan_speak(false);
		}

		langu.setLanguage(params.get("langu[Language]"));

		int empMasterid = Integer.parseInt(params.get("langu[empMasterid]"));

		if (params.get("langu[lanid]").equalsIgnoreCase("")) {
			return employeeMasterService.insertemployeeLanguag(empMasterid, langu);
		} else {
			langu.setEmpLanguid(Integer.parseInt(params.get("langu[lanid]")));
			return employeeMasterService.updateemployeeLanguag(langu);
		}

	}

	@PostMapping("employeesave")
	public String employeesave(HttpServletRequest req, @ModelAttribute("employeemaster") EmployeeMaster employeemaster,
			@RequestParam(name = "empEduid", required = false) String[] empEduid,
			@RequestParam(name = "College_Institution", required = false) String[] College_Institution,
			@RequestParam(name = "Degree", required = false) String[] Degree,
			@RequestParam(name = "MajorSpecialization", required = false) String[] MajorSpecialization,
			@RequestParam(name = "Percentage_GPA", required = false) String[] Percentage_GPA,
			@RequestParam(name = "FromYear", required = false) String[] FromYear,
			@RequestParam(name = "ToYear", required = false) String[] ToYear,

			@RequestParam(name = "empLanguid", required = false) String[] empLanguid,
			@RequestParam(name = "language", required = false) String[] language,
			@RequestParam(name = "languageid", required = false) String[] languageid,
			@RequestParam(name = "lan_write", required = false) Boolean[] lan_write,
			@RequestParam(name = "lan_read", required = false) Boolean[] lan_read,
			@RequestParam(name = "lan_speak", required = false) Boolean[] lan_speak,

			@RequestParam(name = "photoempFileid", required = false) String[] photoempFileid,
			@RequestParam(name = "resumeempFileid", required = false) String[] resumeempFileid,
			@RequestParam(name = "certificateempFileid", required = false) String[] certificateempFileid,
			@RequestParam(name = "photoempFileidstr", required = false) String[] photoempFileidstr,
			@RequestParam(name = "resumeempFileidstr", required = false) String[] resumeempFileidstr,
			@RequestParam(name = "certificateempFileidstr", required = false) String[] certificateempFileidstr,

			@RequestParam(name = "Photo_Attach", required = false) MultipartFile Photo_Attach,
			@RequestParam(name = "Resume_Attach", required = false) MultipartFile Resume_Attach,
			@RequestParam(name = "Certificates_Attach", required = false) MultipartFile Certificates_Attach,

			@RequestParam(name = "empExperienceid", required = false) String[] empExperienceid,
			@RequestParam(name = "Company", required = false) String[] Company,
			@RequestParam(name = "Location", required = false) String[] Location,
			@RequestParam(name = "expFromyear", required = false) String[] expFromyear,
			@RequestParam(name = "expToyear", required = false) String[] expToyear,
			@RequestParam(name = "JobTitle", required = false) String[] JobTitle,

			@RequestParam(name = "empEmgContactid", required = false) String[] empEmgContactid,
			@RequestParam(name = "emgid", required = false) String[] emgid,
			@RequestParam(name = "Emg_primarycontact", required = false) Boolean[] Emg_primarycontact,
			@RequestParam(name = "Emg_InsuranceNominee", required = false) Boolean[] Emg_InsuranceNominee,
			@RequestParam(name = "Emg_Name", required = false) String[] Emg_Name,
			@RequestParam(name = "Emg_Relation", required = false) String[] Emg_Relation,
			@RequestParam(name = "Emg_PersonalPhone", required = false) String[] Emg_PersonalPhone,
			@RequestParam(name = "Emg_OtherPhone", required = false) String[] Emg_OtherPhone,
			@RequestParam(name = "Emg_EmailID", required = false) String[] Emg_EmailID,
			@RequestParam(name = "Emg_Street1", required = false) String[] Emg_Street1,
			@RequestParam(name = "Emg_Street2", required = false) String[] Emg_Street2,
			@RequestParam(name = "Emg_Village", required = false) String[] Emg_Village,
			@RequestParam(name = "Emg_Taluk", required = false) String[] Emg_Taluk,
			@RequestParam(name = "Emg_City", required = false) String[] Emg_City,
			@RequestParam(name = "Emg_State", required = false) String[] Emg_State,
			@RequestParam(name = "Emg_ZIP", required = false) String[] Emg_ZIP,
			@RequestParam(name = "Emg_Country", required = false) String[] Emg_Country,

			@RequestParam Map<String, String> params, HttpServletRequest request, Model themodel) {

		employeemaster.setEmpid("RVS");

		// System.out.println("------------------------------------");

		for (int farr = 0; farr < languageid.length; farr++) {
			String templangurow = languageid[farr];
		}
		// System.out.println("------------------------------------");

		for (int farr = 0; farr < emgid.length; farr++) {
			String templangurow = emgid[farr];
			// System.out.println(params.get("Emg_primarycontact" + templangurow));
			// System.out.println(params.get("Emg_InsuranceNominee" + templangurow));

		}

		// System.out.println("------------------------------------");
		List<EmployeeEducation> eduls = new ArrayList<EmployeeEducation>();
		for (int farr = 0; farr < College_Institution.length; farr++) {
			EmployeeEducation empedu = new EmployeeEducation();

			empedu.setCollege_Institution(College_Institution[farr]);
			empedu.setDegree(Degree[farr]);

			if (!empEduid[farr].isEmpty()) {
				empedu.setEmpEduid(Integer.parseInt(empEduid[farr]));
			}

			if (FromYear.length > 0)
				empedu.setFromYear(FromYear[farr]);
			if (MajorSpecialization.length > 0)
				empedu.setMajorSpecialization(MajorSpecialization[farr]);
			if (Percentage_GPA.length > 0)
				empedu.setPercentage_GPA(Percentage_GPA[farr]);
			if (ToYear.length > 0)
				empedu.setToYear(ToYear[farr]);
			itemlistService.savesingletxt(empedu.getDegree(), "DEGREE");
			eduls.add(empedu);
		}
		employeemaster.setEmployeeEducation(eduls);
		// System.out.println("--------------Step 1 end----------------------");

		List<EmployeeEmgContact> emgls = new ArrayList<EmployeeEmgContact>();
		for (int farr = 0; farr < Emg_Name.length; farr++) {
			EmployeeEmgContact empcont = new EmployeeEmgContact();

			String templangurow = emgid[farr];
			if (params.get("Emg_primarycontact" + templangurow) != null) {
				empcont.setEmg_primarycontact(true);
			} else {
				empcont.setEmg_primarycontact(false);
			}
			if (params.get("Emg_InsuranceNominee" + templangurow) != null) {
				empcont.setEmg_InsuranceNominee(true);
			} else {
				empcont.setEmg_InsuranceNominee(false);
			}

			if (!empEmgContactid[farr].isEmpty()) {
				empcont.setEmpEmgContactid(Integer.parseInt(empEmgContactid[farr]));
			}

			if (Emg_City.length > 0)
				empcont.setEmg_City(Emg_City[farr]);
			if (Emg_Country.length > 0)
				empcont.setEmg_Country(Emg_Country[farr]);
			if (Emg_EmailID.length > 0)
				empcont.setEmg_EmailID(Emg_EmailID[farr]);
			if (Emg_Name.length > 0)
				empcont.setEmg_Name(Emg_Name[farr]);
			if (Emg_OtherPhone.length > 0)
				empcont.setEmg_OtherPhone(Emg_OtherPhone[farr]);
			if (Emg_PersonalPhone.length > 0)
				empcont.setEmg_PersonalPhone(Emg_PersonalPhone[farr]);
			if (Emg_Relation.length > 0) {
				empcont.setEmg_Relation(Emg_Relation[farr]);
				itemlistService.savesingletxt(empcont.getEmg_Relation(), "RELATION");
			}
			if (Emg_State.length > 0)
				empcont.setEmg_State(Emg_State[farr]);
			if (Emg_Street1.length > 0)
				empcont.setEmg_Street1(Emg_Street1[farr]);
			if (Emg_Street2.length > 0)
				empcont.setEmg_Street2(Emg_Street2[farr]);

			if (Emg_Village.length > 0)
				empcont.setEmg_Village(Emg_Village[farr]);
			if (Emg_ZIP.length > 0)
				empcont.setEmg_ZIP(Emg_ZIP[farr]);
			emgls.add(empcont);
		}
		employeemaster.setEmployeeEmgContact(emgls);
		// System.out.println("--------------Step 2 End----------------------");

		List<EmployeeExperience> exptrls = new ArrayList<EmployeeExperience>();
		if (!Objects.isNull(Company)) {

			for (int farr = 0; farr < Company.length; farr++) {
				EmployeeExperience empexper = new EmployeeExperience();
				empexper.setCompany(Company[farr]);

				if (!empExperienceid[farr].isEmpty()) {
					empexper.setEmpExperienceid(Integer.parseInt(empExperienceid[farr]));
				}
				if (expFromyear.length > 0)
					empexper.setExpFromyear(expFromyear[farr]);
				if (expToyear.length > 0)
					empexper.setExpToyear(expToyear[farr]);
				if (JobTitle.length > 0)
					empexper.setJobTitle(JobTitle[farr]);
				if (Location.length > 0)
					empexper.setLocation(Location[farr]);
				exptrls.add(empexper);
			}
		}
		employeemaster.setEmployeeExperience(exptrls);

		// System.out.println("--------------Step 3 end----------------------");
		List<EmployeeLanguage> langls = new ArrayList<EmployeeLanguage>();
		for (int farr = 0; farr < language.length; farr++) {
			EmployeeLanguage emplang = new EmployeeLanguage();
			String templangurow = languageid[farr];
			if (params.get("lan_read" + templangurow) != null) {
				emplang.setLan_read(true);
			} else {
				emplang.setLan_read(false);
			}
			if (params.get("lan_write" + templangurow) != null) {
				emplang.setLan_write(true);
			} else {
				emplang.setLan_write(false);
			}
			if (params.get("lan_speak" + templangurow) != null) {
				emplang.setLan_speak(true);
			} else {
				emplang.setLan_speak(false);
			}

			if (!empLanguid[farr].isEmpty()) {
				emplang.setEmpLanguid((Integer) Integer.parseInt(empLanguid[farr]));
			}
			if (language.length > 0) {
				emplang.setLanguage(language[farr]);
				itemlistService.savesingletxt(emplang.getLanguage(), "LANGUAGE");
			}
			langls.add(emplang);

		}
		employeemaster.setEmployeeLanguage(langls);
		// System.out.println("--------------Step 4 end----------------------");

		List<EmployeeFiles> filels = new ArrayList<EmployeeFiles>();

		if (photoempFileid != null)
			for (int farr = 0; farr < photoempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (photoempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(photoempFileid[farr]));
				}
				obj.setFilePath(photoempFileidstr[farr]);
				filels.add(obj);
			}

		if (resumeempFileid != null)
			for (int farr = 0; farr < resumeempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (resumeempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(resumeempFileid[farr]));
				}
				obj.setFilePath(resumeempFileidstr[farr]);
				filels.add(obj);
			}

		if (certificateempFileid != null)
			for (int farr = 0; farr < certificateempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (certificateempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(certificateempFileid[farr]));
				}
				obj.setFilePath(certificateempFileidstr[farr]);
				filels.add(obj);
			}

		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("employeeprofilephoto");
		// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String resumeuploadRootPath = request.getServletContext().getRealPath("employeeresume");
		// System.out.println("uploadRootPath=" + resumeuploadRootPath);

		File uploadRootDirresume = new File(resumeuploadRootPath);
		if (!uploadRootDirresume.exists()) {
			uploadRootDirresume.mkdirs();
		}

		String certificateuploadRootPath = request.getServletContext().getRealPath("employeecertification");
		// System.out.println("uploadRootPath=" + certificateuploadRootPath);

		File uploadRootDircertificate = new File(certificateuploadRootPath);
		if (!uploadRootDircertificate.exists()) {
			uploadRootDircertificate.mkdirs();
		}

		if (Photo_Attach.getOriginalFilename().toString().length() > 0) {
			EmployeeFiles empfiles = new EmployeeFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + Photo_Attach.getOriginalFilename();
			Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
			filename.append(tempfilename);
			empfiles.setFilePath("employeeprofilephoto/" + filename);
			try {
				Files.write(fileNameandPath, Photo_Attach.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			filels.add(empfiles);
		}

		if (Resume_Attach.getOriginalFilename().toString().length() > 0) {
			EmployeeFiles empfiles = new EmployeeFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + Resume_Attach.getOriginalFilename();
			Path fileNameandPath = Paths.get(resumeuploadRootPath, tempfilename);
			filename.append(tempfilename);
			empfiles.setFilePath("employeeresume/" + filename);
			try {
				Files.write(fileNameandPath, Resume_Attach.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			filels.add(empfiles);
		}

		if (Certificates_Attach.getOriginalFilename().toString().length() > 0) {
			EmployeeFiles empfiles = new EmployeeFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + Certificates_Attach.getOriginalFilename();
			Path fileNameandPath = Paths.get(certificateuploadRootPath, tempfilename);
			filename.append(tempfilename);
			empfiles.setFilePath("employeecertification/" + filename);
			try {
				Files.write(fileNameandPath, Certificates_Attach.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			filels.add(empfiles);
		}

		employeemaster.setEmployeeFiles(filels);
		// System.out.println("--------------Step 5 end----------------------");

		// System.out.println("--------------Step 6 end----------------------");
		// System.out.println(employeemaster);

		EmployeeMaster employeemasternew = new EmployeeMaster();

		itemlistService.savesingletxt(employeemaster.getMaritalStatus(), "MARITAL STATUS");
		employeemasternew = employeeMasterService.save(employeemaster);
		// System.out.println(employeemasternew);

		List<EmployeeEducation> edulsnew = new ArrayList<EmployeeEducation>();
		List<EmployeeEmgContact> emglsnew = new ArrayList<EmployeeEmgContact>();
		List<EmployeeExperience> exptrlsnew = new ArrayList<EmployeeExperience>();
		List<EmployeeLanguage> langlsnew = new ArrayList<EmployeeLanguage>();
		List<EmployeeFiles> filelsnew = new ArrayList<EmployeeFiles>();

		// System.out.println(employeemasternew.getEmployeeEducation().size());
		if (employeemasternew.getEmployeeEducation().size() > 0) {
			edulsnew.addAll(employeemasternew.getEmployeeEducation());
		} else {
			EmployeeEducation empedu = new EmployeeEducation();
			empedu.setDegree("-");
			edulsnew.add(empedu);

		}

		// System.out.println(employeemasternew.getEmployeeEmgContact().size());
		if (employeemasternew.getEmployeeEmgContact().size() > 0) {
			emglsnew.addAll(employeemasternew.getEmployeeEmgContact());
		} else {
			EmployeeEmgContact empcont = new EmployeeEmgContact();
			empcont.setEmg_Relation("-");
			emglsnew.add(empcont);

		}
		// System.out.println(employeemasternew.getEmployeeExperience().size());
		if (employeemasternew.getEmployeeExperience().size() > 0) {
			exptrlsnew.addAll(employeemasternew.getEmployeeExperience());
		} else {
			EmployeeExperience empexper = new EmployeeExperience();
			exptrlsnew.add(empexper);

		}
		if (employeemasternew.getEmployeeLanguage().size() > 0) {
			langlsnew.addAll(employeemasternew.getEmployeeLanguage());
		} else {
			EmployeeLanguage emplang = new EmployeeLanguage();
			emplang.setLanguage("-");
			langlsnew.add(emplang);

		}
		if (employeemasternew.getEmployeeFiles().size() > 0) {
			filelsnew.addAll(employeemasternew.getEmployeeFiles());
		} else {
			EmployeeFiles empfiles1 = new EmployeeFiles();
			filelsnew.add(empfiles1);
		}

		List<String> MARITALSTATUS = itemlistService.findByFieldName("MARITAL STATUS");
		themodel.addAttribute("MARITALSTATUS", MARITALSTATUS);
		List<String> DEGREE = itemlistService.findByFieldName("DEGREE");
		themodel.addAttribute("DEGREE", DEGREE);
		List<String> RELATION = itemlistService.findByFieldName("RELATION");
		themodel.addAttribute("RELATION", RELATION);
		List<String> LANGUAGE = itemlistService.findByFieldName("LANGUAGE");
		themodel.addAttribute("LANGUAGE", LANGUAGE);

		themodel.addAttribute("employeeEducation", edulsnew);
		themodel.addAttribute("employeeEmgContact", emglsnew);
		themodel.addAttribute("employeeExperience", exptrlsnew);
		themodel.addAttribute("employeeLanguage", langlsnew);
		themodel.addAttribute("employeeFiles", filelsnew);
		themodel.addAttribute("employeemaster", employeemasternew);
		themodel.addAttribute("save", true);
		return "empadd";

	}

	@GetMapping("emp")
	public String employeedetails(Model themodel, @RequestParam("id") int id) {
		Date todaydate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		EmployeeMaster employeemasternew = new EmployeeMaster();
		employeemasternew = employeeMasterService.findById(id);
		// DOB MM DD format
		if (!nullremover(String.valueOf(employeemasternew.getDateofBirth())).equalsIgnoreCase("")) {
			try {
				employeemasternew.setDobMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(employeemasternew.getDateofBirth())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (employeemasternew.getEmployeeAccNo().size() == 0) {
			List<EmployeeAccNo> empAccNols = new ArrayList();
			empAccNols.add(new EmployeeAccNo());
			employeemasternew.setEmployeeAccNo(empAccNols);
			employeemasternew = employeeMasterService.save(employeemasternew);
		}
		// -------------------------------------------
		List<EmployeeEducation> edulsnew = new ArrayList<EmployeeEducation>();
		List<EmployeeEmgContact> emglsnew = new ArrayList<EmployeeEmgContact>();
		List<EmployeeExperience> exptrlsnew = new ArrayList<EmployeeExperience>();
		List<EmployeeLanguage> langlsnew = new ArrayList<EmployeeLanguage>();
		List<EmployeeFiles> filelsnew = new ArrayList<EmployeeFiles>();

		// System.out.println(employeemasternew.getEmployeeEducation().size());
		if (employeemasternew.getEmployeeEducation().size() > 0) {
			edulsnew.addAll(employeemasternew.getEmployeeEducation());

		} else {
			EmployeeEducation empedu = new EmployeeEducation();
			empedu.setDegree("-");
			edulsnew.add(empedu);

		}

		// System.out.println(employeemasternew.getEmployeeEmgContact().size());
		if (employeemasternew.getEmployeeEmgContact().size() > 0) {
			List<EmployeeEmgContact> emglsnewtemp = employeemasternew.getEmployeeEmgContact().stream()
					.sorted(Comparator.comparing(EmployeeEmgContact::getEmpEmgContactid)).collect(Collectors.toList());
			emglsnew.addAll(emglsnewtemp);
			// System.out.println(emglsnewtemp);
			// employeemasternew.setEmployeeEmgContact(emglsnewtemp);
		} else {
			EmployeeEmgContact empcont = new EmployeeEmgContact();
			empcont.setEmg_Relation("-");
			emglsnew.add(empcont);

		}
		// System.out.println(employeemasternew.getEmployeeExperience().size());
		if (employeemasternew.getEmployeeExperience().size() > 0) {
			exptrlsnew.addAll(employeemasternew.getEmployeeExperience());
		} else {
			EmployeeExperience empexper = new EmployeeExperience();
			exptrlsnew.add(empexper);

		}
		if (employeemasternew.getEmployeeLanguage().size() > 0) {
			langlsnew.addAll(employeemasternew.getEmployeeLanguage());
		} else {
			EmployeeLanguage emplang = new EmployeeLanguage();
			emplang.setLanguage("-");
			langlsnew.add(emplang);

		}
		if (employeemasternew.getEmployeeFiles().size() > 0) {
			filelsnew.addAll(employeemasternew.getEmployeeFiles());
		} else {
			EmployeeFiles empfiles1 = new EmployeeFiles();
			filelsnew.add(empfiles1);
		}

		// -------------------------------------------
		// Emp photo
		employeemasternew.setT_emp_img(getemp_photo(employeemasternew));
		// -------------------------------------------

		// -------------------------------------------
		// Emp Location and Branch
		List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
		infoobjjob = employeeJobinfoService.findByEmployeeid(employeemasternew.getEmpMasterid());
		if (infoobjjob.size() > 0) {
			List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjjobgreen.size() > 0) {
				employeemasternew.setT_position(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJobtitle());
				employeemasternew.setT_branch_name(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation());
			}
		}
		// -------------------------------------------
		// Hiring date and timeline
		List<EmployeeJobHire> hireobj = new ArrayList<>();
		hireobj = employeeJobHireService.findByEmployeeid(employeemasternew.getEmpMasterid());

		if (hireobj.size() > 0) {
			try {
				employeemasternew.setT_joindateMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(hireobj.get(0).getEmployeehiredate())).toString());
				employeemasternew.setT_joindatetimeline(getTimeage(hireobj.get(0).getEmployeehiredate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------

		List<String> MARITALSTATUS = itemlistService.findByFieldName("MARITAL STATUS");
		themodel.addAttribute("MARITALSTATUS", MARITALSTATUS);
		List<String> DEGREE = itemlistService.findByFieldName("DEGREE");
		themodel.addAttribute("DEGREE", DEGREE);
		List<String> RELATION = itemlistService.findByFieldName("RELATION");
		themodel.addAttribute("RELATION", RELATION);
		List<String> LANGUAGE = itemlistService.findByFieldName("LANGUAGE");
		themodel.addAttribute("LANGUAGE", LANGUAGE);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		themodel.addAttribute("CONTACTTYPE", CONTACTTYPE);
		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		themodel.addAttribute("Documenttype", Documenttype);

		themodel.addAttribute("employeeEmgAddress", new EmployeeEmgContact());
		themodel.addAttribute("employeeEducation", edulsnew);
		themodel.addAttribute("employeeEmgContact", emglsnew);
		themodel.addAttribute("employeeExperience", exptrlsnew);
		themodel.addAttribute("employeeLanguage", langlsnew);
		themodel.addAttribute("employeeFiles", filelsnew);

		themodel.addAttribute("employeemaster", employeemasternew);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_employeelist"));

		return "empadd";
	}

	public String stringdatetime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public EmployeeMaster fillemployeeobject(int empid) {

		Date todaydate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		EmployeeMaster employeemasternew = new EmployeeMaster();
		employeemasternew = employeeMasterService.findById(empid);
		// DOB MM DD format
		try {
			employeemasternew.setDobMMformat(displaydateFormatFirstMMMddYYY
					.format(displaydateFormatrev.parse(employeemasternew.getDateofBirth())).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (employeemasternew.getEmployeeAccNo().size() == 0) {
			List<EmployeeAccNo> empAccNols = new ArrayList();
			empAccNols.add(new EmployeeAccNo());
			employeemasternew.setEmployeeAccNo(empAccNols);
			employeemasternew = employeeMasterService.save(employeemasternew);
		}
		// -------------------------------------------
		List<EmployeeEducation> edulsnew = new ArrayList<EmployeeEducation>();
		List<EmployeeEmgContact> emglsnew = new ArrayList<EmployeeEmgContact>();
		List<EmployeeExperience> exptrlsnew = new ArrayList<EmployeeExperience>();
		List<EmployeeLanguage> langlsnew = new ArrayList<EmployeeLanguage>();
		List<EmployeeFiles> filelsnew = new ArrayList<EmployeeFiles>();

		// System.out.println(employeemasternew.getEmployeeEducation().size());
		if (employeemasternew.getEmployeeEducation().size() > 0) {
			edulsnew.addAll(employeemasternew.getEmployeeEducation());
		} else {
			EmployeeEducation empedu = new EmployeeEducation();
			empedu.setDegree("-");
			edulsnew.add(empedu);

		}

		// System.out.println(employeemasternew.getEmployeeEmgContact().size());
		if (employeemasternew.getEmployeeEmgContact().size() > 0) {
			emglsnew.addAll(employeemasternew.getEmployeeEmgContact());
		} else {
			EmployeeEmgContact empcont = new EmployeeEmgContact();
			empcont.setEmg_Relation("-");
			emglsnew.add(empcont);

		}
		// System.out.println(employeemasternew.getEmployeeExperience().size());
		if (employeemasternew.getEmployeeExperience().size() > 0) {
			exptrlsnew.addAll(employeemasternew.getEmployeeExperience());
		} else {
			EmployeeExperience empexper = new EmployeeExperience();
			exptrlsnew.add(empexper);

		}
		if (employeemasternew.getEmployeeLanguage().size() > 0) {
			langlsnew.addAll(employeemasternew.getEmployeeLanguage());
		} else {
			EmployeeLanguage emplang = new EmployeeLanguage();
			emplang.setLanguage("-");
			langlsnew.add(emplang);

		}
		if (employeemasternew.getEmployeeFiles().size() > 0) {
			filelsnew.addAll(employeemasternew.getEmployeeFiles());
		} else {
			EmployeeFiles empfiles1 = new EmployeeFiles();
			filelsnew.add(empfiles1);
		}

		// -------------------------------------------
		// Emp photo
		employeemasternew.setT_emp_img(getemp_photo(employeemasternew));
		// -------------------------------------------

		// -------------------------------------------
		// Emp Location and Branch
		List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
		infoobjjob = employeeJobinfoService.findByEmployeeid(employeemasternew.getEmpMasterid());
		if (infoobjjob.size() > 0) {
			List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjjobgreen.size() > 0) {
				employeemasternew.setT_position(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJobtitle());
				employeemasternew.setT_branch_name(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation());
			}
		}
		// -------------------------------------------
		// Hiring date and timeline
		List<EmployeeJobHire> hireobj = new ArrayList<>();
		hireobj = employeeJobHireService.findByEmployeeid(employeemasternew.getEmpMasterid());

		if (hireobj.size() > 0) {
			try {
				employeemasternew.setT_joindateMMformat(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(hireobj.get(0).getEmployeehiredate())).toString());
				employeemasternew.setT_joindatetimeline(getTimeage(hireobj.get(0).getEmployeehiredate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// -------------------------------------------
		return employeemasternew;
	}

	@GetMapping("empjob")
	public String employeejob(Model theModel, @RequestParam("id") int empid) {

		EmployeeMaster emobj = fillemployeeobject(empid);

		int greenpointemployementstatus = 0;
		int greenpointjobstatus = 0;
		int greenpointcompensationstatus = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		List<EmployeeJobempstatus> obj = new ArrayList<>();
		obj = employeeJobempstatusService.findByEmployeeid(empid);
		if (obj.size() > 0) {
			List<EmployeeJobempstatus> objgreen = obj.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getEmpstatus_effectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			objgreen.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate));
			if (objgreen.size() > 0) {
				greenpointemployementstatus = objgreen.get(objgreen.size() - 1).getEmployeejobempstatusid();
			}
		}

		List<EmployeeJobinfo> infoobj = new ArrayList<>();

		for (EmployeeJobinfo stmojb : employeeJobinfoService.findByEmployeeid(empid)) {

			if (!stmojb.getJobreportsto().equalsIgnoreCase("")) {
				stmojb.setReportstoname(
						employeeMasterService.findById(Integer.parseInt(stmojb.getJobreportsto())).getStaffName());
			}
			infoobj.add(stmojb);
		}

		if (infoobj.size() > 0) {
			List<EmployeeJobinfo> infoobjgreen = infoobj.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjgreen.size() > 0) {
				greenpointjobstatus = infoobjgreen.get(infoobjgreen.size() - 1).getEmployeejobinfoid();
			}
		}

		List<EmployeeJobcompensation> comoobj = new ArrayList<>();
		comoobj = employeeJobcompensationService.findByEmployeeid(empid);
		if (comoobj.size() > 0) {
			List<EmployeeJobcompensation> comoobjgreen = comoobj.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getComeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			comoobjgreen.sort(Comparator.comparing(EmployeeJobcompensation::getComeffectivedate));
			if (comoobjgreen.size() > 0) {
				greenpointcompensationstatus = comoobjgreen.get(comoobjgreen.size() - 1).getEmployeejobcompensationid();
			}
		}

		List<EmployeeJobHire> hireobj = new ArrayList<>();
		hireobj = employeeJobHireService.findByEmployeeid(empid);
		String stringhiredate = "";
		if (hireobj.size() > 0) {
			stringhiredate = hireobj.get(0).getEmployeehiredate();
		}

		// Get all branch info
		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();

		List<String> EmploymentStatus = itemlistService.findByFieldName("EmploymentStatus");
		theModel.addAttribute("EmploymentStatus", EmploymentStatus);
		List<String> TerminationType = itemlistService.findByFieldName("TerminationType");
		theModel.addAttribute("TerminationType", TerminationType);
		List<String> TerminationReason = itemlistService.findByFieldName("TerminationReason");
		theModel.addAttribute("TerminationReason", TerminationReason);
		List<String> Department = itemlistService.findByFieldName("Department");
		theModel.addAttribute("Department", Department);
		List<String> JobTitle = itemlistService.findByFieldName("JobTitle");
		theModel.addAttribute("JobTitle", JobTitle);
		List<String> PAYCHANGEREASON = itemlistService.findByFieldName("PAYCHANGEREASON");
		theModel.addAttribute("PAYCHANGEREASON", PAYCHANGEREASON);

		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		theModel.addAttribute("greenpointemployementstatus", greenpointemployementstatus);
		theModel.addAttribute("greenpointjobstatus", greenpointjobstatus);
		theModel.addAttribute("greenpointcompensationstatus", greenpointcompensationstatus);
		theModel.addAttribute("branchls", branchls);
		theModel.addAttribute("employeeJobemphiredate", stringhiredate);

		obj.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate).reversed());
		infoobj.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate).reversed());
		comoobj.sort(Comparator.comparing(EmployeeJobcompensation::getComeffectivedate).reversed());

		theModel.addAttribute("employeeJobempstatus", obj);
		theModel.addAttribute("employeeJobinfomation", infoobj);
		theModel.addAttribute("employeecompensation", comoobj);
		theModel.addAttribute("empid", empid);
		theModel.addAttribute("employeemaster", emobj);

		theModel.addAttribute("emptitle",
				emobj.getEmpid() + "000" + emobj.getEmpMasterid() + " - " + emobj.getStaffName());
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_employeelist"));
		return "empjob";
	}

	@PostMapping("employeehiredate")
	@ResponseBody
	public String employeehiredate(@RequestParam Map<String, String> params) {
		EmployeeJobHire obj = new EmployeeJobHire();

		obj.setEmployeehiredate(params.get("hiredate"));
		obj.setEmployeeid(Integer.valueOf(params.get("empid")));

		if (params.get("hiredateid") != null && params.get("hiredateid") != "") {
			obj.setEmployeehireid(Integer.valueOf(params.get("hiredateid")));
		}
		// System.out.println(obj);
		employeeJobHireService.save(obj);
		return "Success" + obj.getEmployeehireid();
	}

	@PostMapping("employeeemploymentupdate")
	@ResponseBody
	public String employeeemploymentupdate(@RequestParam Map<String, String> params) {
		EmployeeJobempstatus obj = new EmployeeJobempstatus();

		obj.setEmployeeid(Integer.parseInt(params.get("empid")));

		obj.setEmpstatus_effectivedate(params.get("empstatus_effectivedate"));
		obj.setEmpstatus_employmentstatus(params.get("empstatus_employmentstatus"));
		obj.setEmpstatus_rehire(params.get("empstatus_rehire"));
		obj.setEmpstatus_remarks(params.get("empstatus_remarks"));
		obj.setEmpstatus_terminationreason(params.get("empstatus_terminationreason"));
		obj.setEmpstatus_terminationtype(params.get("empstatus_terminationtype"));

		if (params.get("employeeJobempstatusid") != null && params.get("employeeJobempstatusid") != "") {
			obj.setEmployeejobempstatusid(Integer.parseInt(params.get("employeeJobempstatusid")));
		}

		itemlistService.savesingletxt(obj.getEmpstatus_employmentstatus(), "EmploymentStatus");
		itemlistService.savesingletxt(obj.getEmpstatus_terminationreason(), "TerminationReason");
		itemlistService.savesingletxt(obj.getEmpstatus_terminationtype(), "TerminationType");

		employeeJobempstatusService.save(obj);
		return "Success" + obj.getEmployeejobempstatusid();
	}

	@PostMapping("employeejobinformationupdate")
	@ResponseBody
	public EmployeeJobinfo employeejobinformationupdate(@RequestParam Map<String, String> params) {
		EmployeeJobinfo obj = new EmployeeJobinfo();

		obj.setEmployeeid(Integer.parseInt(params.get("empid")));
		obj.setJobdeparment(params.get("jobdeparment"));
		obj.setJobeffectivedate(params.get("jobeffectivedate"));
		obj.setJoblocation(params.get("joblocation"));
		obj.setJobreportsto(params.get("jobreportsto"));
		obj.setJobtitle(params.get("jobtitle"));

		if (params.get("employeejobinfoid") != null && params.get("employeejobinfoid") != "") {
			obj.setEmployeejobinfoid(Integer.parseInt(params.get("employeejobinfoid")));
		}
		// System.out.println(obj);
		itemlistService.savesingletxt(obj.getJobdeparment(), "Department");
		itemlistService.savesingletxt(obj.getJobtitle(), "JobTitle");
		employeeJobinfoService.save(obj);

		if (!params.get("jobreportsto").equalsIgnoreCase("")) {
			obj.setReportstoname(
					employeeMasterService.findById(Integer.parseInt(params.get("jobreportsto"))).getStaffName());
		}

		return obj;
	}

	@PostMapping("employeecompensationupdate")
	@ResponseBody
	public String employeecompensationupdate(@RequestParam Map<String, String> params) {
		EmployeeJobcompensation obj = new EmployeeJobcompensation();

		obj.setEmployeeid(Integer.parseInt(params.get("empid")));
		obj.setComchangereason(params.get("comchangereason"));
		obj.setComcomments(params.get("comcomments"));
		obj.setComeffectivedate(params.get("comeffectivedate"));
		obj.setCompayrate(params.get("compayrate"));
		obj.setCompayratetype(params.get("compayratetype"));
		obj.setComPayschedule(params.get("comPayschedule"));
		obj.setCompaytype(params.get("compaytype"));

		if (params.get("employeejobcompensationid") != null && params.get("employeejobcompensationid") != "") {
			obj.setEmployeejobcompensationid(Integer.parseInt(params.get("employeejobcompensationid")));
		}
		itemlistService.savesingletxt(obj.getComchangereason(), "PAYCHANGEREASON");

		employeeJobcompensationService.save(obj);
		return "Success" + obj.getEmployeejobcompensationid();
	}

	@PostMapping("employeedelete")
	@ResponseBody
	public String employeedelete(@RequestParam Map<String, String> params) {

		if (params.get("deletetype").equalsIgnoreCase("employmentstatus")) {
			employeeJobempstatusService.deleteById(Integer.parseInt(params.get("deleteid")));
		} else if (params.get("deletetype").equalsIgnoreCase("jobinformation")) {
			employeeJobinfoService.deleteById(Integer.parseInt(params.get("deleteid")));
		} else if (params.get("deletetype").equalsIgnoreCase("compensation")) {
			employeeJobcompensationService.deleteById(Integer.parseInt(params.get("deleteid")));
		}

		return "Success";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("empattendance")
	public String empattendance(Model theModel,
			@RequestParam(value = "date", defaultValue = "", required = false) String attdate,
			@RequestParam(value = "branch", defaultValue = "", required = false) String branch) {
		SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdd = new SimpleDateFormat("dd");
		SimpleDateFormat formattermonname = new SimpleDateFormat("MMMM");
		DecimalFormat decformatter = new DecimalFormat("00");
		String jsdate = "";
		Date date = new Date();
		Date temppredate = new Date();
		Date tempnxtdate = new Date();
		String tempcurrentdate = formatterdate.format(new Date()).toString();
		if (attdate.equalsIgnoreCase("")) {
			attdate = tempcurrentdate;
		}

		if (!attdate.equalsIgnoreCase("")) {
			try {
				date = formatterdate.parse(attdate);
				temppredate = formatterdate.parse(attdate);
				tempnxtdate = formatterdate.parse(attdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// -----------------------------------------------------------
		String temptargetedbranchName = "Coimbatore";
		if (!branch.equalsIgnoreCase("")) {
			temptargetedbranchName = branch;
		}
		String targetedbranchName = temptargetedbranchName;

		List<BranchMaster> getallBranchList = branchMasterService.findAll();
		List<BranchMaster> getallBranchList1 = getallBranchList.stream()
				.filter(c -> c.getBRANCH_NAME().equalsIgnoreCase(targetedbranchName)).collect(Collectors.toList());

		String branchidtemp = "";
		if (getallBranchList1.size() > 0) {
			branchidtemp = String.valueOf(getallBranchList1.get(0).getId());
		}
		String branchid = branchidtemp;
		// -----------------------------------------------------------

		int currentyear = Integer.parseInt(formatteryear.format(date).toString());
		int currentmonth = Integer.parseInt(formattermonth.format(date).toString());
		int currentdd = Integer.parseInt(formatterdd.format(date).toString());

		Calendar nxtcal = Calendar.getInstance();
		nxtcal.set(currentyear, currentmonth - 1, 1);
		nxtcal.add(Calendar.MONTH, 1);

		Calendar precal = Calendar.getInstance();
		precal.set(currentyear, currentmonth - 1, 1);
		precal.add(Calendar.MONTH, -1);
		String preDate = formatterdate.format(precal.getTime()).toString();
		String nxtDate = formatterdate.format(nxtcal.getTime()).toString();

		String currentmonname = formattermonname.format(date).toString();
		Calendar calendar = Calendar.getInstance();
		calendar.set(currentyear, currentmonth - 1, 1);
		String stringDay = calendar.getTime().toString().substring(0, 3);
		int currentmonthnumDays = calendar.getActualMaximum(Calendar.DATE);
		int firstdayofmonth = 1;
		switch (stringDay) {
		case "Sun":
			firstdayofmonth = 1;
			break;
		case "Mon":
			firstdayofmonth = 2;
			break;
		case "Tue":
			firstdayofmonth = 3;
			break;
		case "Wed":
			firstdayofmonth = 4;
			break;
		case "Thu":
			firstdayofmonth = 5;
			break;
		case "Fri":
			firstdayofmonth = 6;
			break;
		case "Sat":
			firstdayofmonth = 7;
			break;
		}

		String calhtml = "<tr>";

		for (int i = 1; i < firstdayofmonth; i++) {
			calhtml = calhtml + "<td></td>";
		}

		for (int i = 1; i <= currentmonthnumDays; i++) {
			if ((firstdayofmonth + i - 2) % 7 == 0) {
				calhtml = calhtml + "</tr><tr>";
			}

			if (currentdd == i) {
				calhtml = calhtml + "<td class='selectdate' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear)
						+ "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right_today'>" + i + "</div>";
				jsdate = (i) + "/" + (currentmonth) + "/" + (currentyear);
			} else {
				calhtml = calhtml + "<td class='td' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear) + "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right'>" + i + "</div>";
			}

			List<AttendanceMaster> attls = new ArrayList<AttendanceMaster>();

			String currentmmstr = decformatter.format(currentmonth);
			String istr = decformatter.format(i);

			attls = attendanceMasterService.findByattendanceDate((currentyear) + "-" + (currentmmstr) + "-" + (istr));

			long p = 0;
			long a = 0;
			long t = 0;
			long hl = 0;

			p = attls.stream().filter(c -> c.getAttstatus().equalsIgnoreCase("P")
					&& String.valueOf(c.getBranchMasterid()).equalsIgnoreCase(branchid)).count();
			a = attls.stream().filter(c -> c.getAttstatus().equalsIgnoreCase("A")
					&& String.valueOf(c.getBranchMasterid()).equalsIgnoreCase(branchid)).count();
			t = attls.stream().filter(c -> c.getAttstatus().equalsIgnoreCase("T")
					&& String.valueOf(c.getBranchMasterid()).equalsIgnoreCase(branchid)).count();
			hl = attls.stream().filter(c -> c.getAttstatus().equalsIgnoreCase("HL")
					&& String.valueOf(c.getBranchMasterid()).equalsIgnoreCase(branchid)).count();

			calhtml = calhtml + "<div class='cal_inner_holder' id='" + i + "_div1'>" + "<a class='cal_inner cal_innerp"
					+ i + "' style='background:#8FBC8F;color:#fff'>" + p + "</a>" + "<a class='cal_inner cal_innera" + i
					+ "' style='background:#FF8C69;color:#fff'>" + a + "</a>" + "<a class='cal_inner cal_innert" + i
					+ "' style='background:#FFEC8B;color:#fff'>" + t + "</a>" + "<a class='cal_inner cal_innerhl" + i
					+ "' style='background:#DEDEDE;color:#fff'>" + hl + "</a><div></td>";

		}
		calhtml = calhtml + "</tr>";
		// ------------------------------------------------------------------------------------
		// ------------------------------------------------------------------------------------

		// Getting Branch List
		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();

		// Getting All employee
		List<EmployeeMaster> employeeMasterls = new ArrayList<EmployeeMaster>();
		List<EmployeeMaster> employeeMasterlswitheffectivelocation = new ArrayList<EmployeeMaster>();
		employeeMasterls = employeeMasterService.findAll();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateforeffectemp = date;

		// ------------------------------------------------------------------------------------
		// Find out Effective location Employee filter with selected branch
		for (EmployeeMaster obj : employeeMasterls) {
			List<EmployeeJobinfo> infoobj = new ArrayList<>();
			infoobj = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());

			if (infoobj.size() > 0) {
				List<EmployeeJobinfo> infoobjgreen = infoobj.stream().filter(
						c -> dateFormat.format(dateforeffectemp).compareTo(c.getJobeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				infoobjgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));

				if (infoobjgreen.size() > 0) {
					if (infoobjgreen.get(infoobjgreen.size() - 1).getJoblocation()
							.equalsIgnoreCase(targetedbranchName)) {

						if (!calculateTerminatedstatus(obj.getEmpMasterid(), date)) {
							employeeMasterlswitheffectivelocation.add(obj);
						}
					}
				}
			}
		}

		// ------------------------------------------------------------------------------------
		// Get Current date employee attendance details
		List<AttendanceMaster> selecteddateattls = new ArrayList<AttendanceMaster>();

		String[] datesplit = attdate.split("/");
		String strmm = decformatter.format(Integer.parseInt(datesplit[1]));
		String strdd = decformatter.format(Integer.parseInt(datesplit[0]));
		selecteddateattls = attendanceMasterService.findByattendanceDate(datesplit[2] + "-" + strmm + "-" + strdd);
		List<String> empattendancedetailslist = new ArrayList<String>();
		for (EmployeeMaster aempobj : employeeMasterlswitheffectivelocation) {
			List<AttendanceMaster> attendanceMasterobj = selecteddateattls.stream()
					.filter(c -> String.valueOf(c.getEmployeeid()).equals(String.valueOf(aempobj.getEmpMasterid())))
					.collect(Collectors.toList());
			String attstr = "";

			if (attendanceMasterobj.size() > 0) {
				attstr = aempobj.getEmpMasterid() + "||" + aempobj.getStaffName() + "||"
						+ attendanceMasterobj.get(0).getAttendancemasterid() + "||"
						+ attendanceMasterobj.get(0).getAttstatus() + "||" + attendanceMasterobj.get(0).getNotes()
						+ "||";
				empattendancedetailslist.add(attstr);
			} else {
				attstr = aempobj.getEmpMasterid() + "||" + aempobj.getStaffName() + "||0|| || ||";
				empattendancedetailslist.add(attstr);
			}

		}
		// ------------------------------------------------------------------------------------

		int targetedbranchid = 0;
		if (branchls.stream().filter(c -> c.getBRANCH_NAME().equalsIgnoreCase(targetedbranchName))
				.collect(Collectors.toList()).size() > 0) {
			targetedbranchid = branchls.stream().filter(c -> c.getBRANCH_NAME().equalsIgnoreCase(targetedbranchName))
					.collect(Collectors.toList()).get(0).getId();
		}
		// ------------------------------------------------------------------------------------
		theModel.addAttribute("targetedbranchName", targetedbranchName);
		theModel.addAttribute("selecteddateattls", selecteddateattls);
		theModel.addAttribute("targetedbranchid", targetedbranchid);
		theModel.addAttribute("empattendancedetailslist", empattendancedetailslist);
		theModel.addAttribute("branchls", branchls);
		theModel.addAttribute("preDate", preDate);
		theModel.addAttribute("nxtDate", nxtDate);
		theModel.addAttribute("tempcurrentdate", tempcurrentdate);
		theModel.addAttribute("currentdd", currentdd);
		theModel.addAttribute("currentyear", currentyear);
		theModel.addAttribute("currentmonname", currentmonname);
		theModel.addAttribute("calhtml", calhtml);
		theModel.addAttribute("jsdate", jsdate);
		theModel.addAttribute("menuactivelist",
				menuactivelistobj.getactivemenulist("admin_hr_Attendance_Daily Attendance"));
		return "empattendance";

	}

	private boolean calculateTerminatedstatus(int EmpMasterid, Date date) {
		// --------------------------------------------
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateforeffectemp = date;

		Boolean returnstatus = false;
		// --------------------------------------------
		// Start Termination Details calculate

		List<EmployeeJobempstatus> empstatusobj = new ArrayList<>();
		empstatusobj = employeeJobempstatusService.findByEmployeeid(EmpMasterid);

		if (empstatusobj.size() > 0) {
			List<EmployeeJobempstatus> empstatusobjgreen = empstatusobj.stream().filter(
					c -> dateFormat.format(dateforeffectemp).compareTo(c.getEmpstatus_effectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			empstatusobjgreen.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate));
			if (empstatusobjgreen.size() > 0) {
				if (empstatusobjgreen.get(empstatusobjgreen.size() - 1).getEmpstatus_employmentstatus()
						.equalsIgnoreCase("Terminated")) {
					returnstatus = true;
				}
			}
		}

		return returnstatus;
	}

	@ResponseBody
	@PostMapping("employeeattendancesave")
	public String employeeattendancesave(@RequestParam("attendancestr") String attendancestr) {

		String[] temparr1 = attendancestr.split("~");

		for (String str : temparr1) {

			AttendanceMaster attdmast = new AttendanceMaster();
			String[] temparr2 = str.split("\\|");

			String[] datesplit = temparr2[1].split("/");
			attdmast.setAttendanceDate(datesplit[2] + "-" + datesplit[1] + "-" + datesplit[0]);
			attdmast.setAttstatus(temparr2[4]);
			attdmast.setBranchMasterid(Integer.parseInt(temparr2[0]));
			attdmast.setEmployeeid(Integer.parseInt(temparr2[2]));
			attdmast.setNotes(temparr2[3]);
			attdmast.setAttendancemasterid(Integer.parseInt(temparr2[5]));

			// System.out.println(attdmast);
			attendanceMasterService.save(attdmast);
		}
		return attendancestr;
	}

	@ResponseBody
	@PostMapping("employeeattendancedelete")
	public String employeeattendancedelete(@RequestParam("attendid") int attendid) {

		attendanceMasterService.deleteById(attendid);
		return "";

	}

	@ResponseBody
	@PostMapping("holidaysave")
	public String holidaysave(@RequestParam Map<String, String> param) {

		// System.out.println(param);
		Holiday obj = new Holiday();
		if (param.get("calid") != null && (!param.get("calid").equalsIgnoreCase(""))) {
			obj.setId(Integer.parseInt(param.get("calid").toString()));
		}
		obj.setTitle(param.get("title").toString());
		obj.setStart(param.get("fromdate").toString());
		obj.setEnd(param.get("").toString());
		obj.setAllDay(Boolean.valueOf(param.get("allDay")));

		HolidayextendedProps holiextra = new HolidayextendedProps();
		holiextra.setBranch(param.get("branch").toString());
		holiextra.setCalendar(param.get("calendar").toString());
		holiextra.setDescription(param.get("description").toString());
		holiextra.setType(param.get("type").toString());

		obj.setExtendedProps(holiextra);
		holidayService.save(obj);
		return "Save";

	}

	@GetMapping("holidaydefine")
	public String holidaydefine(Model theModel) {
		List<BranchMaster> bmList = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmList);
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_Attendance_Holiday"));
		return "holidaydefine";
	}

	@GetMapping("hire")
	public String hire(Model theModel) {
		List<HireMaster> hmlist = hireMasterService.findAll();
		List<String> hmlistemp = new ArrayList<String>();
		List<EmployeeMaster> emplist = employeeMasterService.findAll();
		for (HireMaster hm : hmlist) {
			hmlistemp.add(emplist.stream()
					.filter(c -> String.valueOf(c.getEmpMasterid()).equalsIgnoreCase(hm.getHiring_lead()))
					.collect(Collectors.toList()).get(0).getStaffName());

		}
		// System.out.println(hmlistemp);
		theModel.addAttribute("hmlist", hmlist);
		theModel.addAttribute("emp", hmlistemp);
		return "hiring";
	}

	@GetMapping("hireaddjob")
	public String hirejobadd(Model theModel,
			@RequestParam(name = "id", required = false, defaultValue = "0") String id) {
		List<EmployeeMaster> emplist = employeeMasterService.findAll();
		List<BranchMaster> bmlist = branchMasterService.findAll();
		HireMaster obj = new HireMaster();
		if (!id.equalsIgnoreCase("0")) {
			obj = hireMasterService.findById(Integer.parseInt(id));
			/*
			 * List<HireMasterQuestions> list = new
			 * ArrayList<HireMasterQuestions>(obj.getHireMasterQuestions());
			 * Collections.sort(list); obj.setHireMasterQuestions(new
			 * HashList<HireMasterQuestions>(list));
			 */
		}

		theModel.addAttribute("bmlist", bmlist);
		theModel.addAttribute("emplist", emplist);
		theModel.addAttribute("HireMaster", obj);
		return "hirejobadd";
	}

	@PostMapping("hireaddjob")
	public String hirejobaddsave(Model theModel, @ModelAttribute HireMaster hireMaster,
			@RequestParam(name = "shortans", required = false) String[] shortans,
			@RequestParam(name = "shortansid", required = false) String[] shortansid,
			@RequestParam(name = "longans", required = false) String[] longans,
			@RequestParam(name = "longansid", required = false) String[] longansid,
			@RequestParam(name = "yesnoans", required = false) String[] yesnoans,
			@RequestParam(name = "yesnoansid", required = false) String[] yesnoansid,
			@RequestParam(name = "multipleans", required = false) String[] multipleans,
			@RequestParam(name = "multipleansid", required = false) String[] multipleansid,
			@RequestParam(name = "option1", required = false) String[] option1,
			@RequestParam(name = "option2", required = false) String[] option2,
			@RequestParam(name = "option3", required = false) String[] option3,
			@RequestParam(name = "checkans", required = false) String[] checkans,
			@RequestParam(name = "checkansid", required = false) String[] checkansid,
			@RequestParam(name = "fileuploadans", required = false) String[] fileuploadans,
			@RequestParam(name = "fileuploadansid", required = false) String[] fileuploadansid) {
		// --------------------------------------------------------------
		/*
		 * System.out.println("shortans" + Arrays.toString(shortans));
		 * System.out.println("shortansid" + Arrays.toString(shortansid));
		 * System.out.println("longans" + Arrays.toString(longans));
		 * System.out.println("longansid" + Arrays.toString(longansid));
		 * System.out.println("yesnoans" + Arrays.toString(yesnoans));
		 * System.out.println("yesnoansid" + Arrays.toString(yesnoansid));
		 * System.out.println("multipleans" + Arrays.toString(multipleans));
		 * System.out.println("multipleansid" + Arrays.toString(multipleansid));
		 * System.out.println("option1" + Arrays.toString(option1));
		 * System.out.println("option2" + Arrays.toString(option2));
		 * System.out.println("option3" + Arrays.toString(option3));
		 * System.out.println("checkans" + Arrays.toString(checkans));
		 * System.out.println("checkansid" + Arrays.toString(checkansid));
		 * System.out.println("fileuploadans" + Arrays.toString(fileuploadans));
		 * System.out.println("fileuploadansid" + Arrays.toString(fileuploadansid));
		 */
		// --------------------------------------------------------------
		Date currentdate = new Date();
		SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		if (hireMaster.getCreateddate().toString().equalsIgnoreCase("")) {
			hireMaster.setCreateddate(formatterdate.format(currentdate));
		}

		List<HireMasterQuestions> setHireMasterQuestions = new ArrayList<HireMasterQuestions>();

		if (shortans != null)
			for (int i = 0; i < shortans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("Short Answer");
				hmq.setQuestiontitle(shortans[i]);
				if (!shortansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(shortansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}

		if (longans != null)
			for (int i = 0; i < longans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("Long Answer");
				hmq.setQuestiontitle(longans[i]);
				if (!longansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(longansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}

		if (yesnoans != null)
			for (int i = 0; i < yesnoans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("Yes / No");
				hmq.setQuestiontitle(yesnoans[i]);
				if (!yesnoansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(yesnoansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}

		if (multipleans != null)
			for (int i = 0; i < multipleans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("Multiple Choice");
				hmq.setQuestiontitle(multipleans[i]);
				hmq.setOption1(option1[i]);
				hmq.setOption2(option2[i]);
				hmq.setOption3(option3[i]);

				if (!multipleansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(multipleansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}

		if (checkans != null)
			for (int i = 0; i < checkans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("Check box");
				hmq.setQuestiontitle(checkans[i]);
				if (!checkansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(checkansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}
		if (fileuploadans != null)
			for (int i = 0; i < fileuploadans.length; i++) {
				HireMasterQuestions hmq = new HireMasterQuestions();
				hmq.setQtype("File Upload");
				hmq.setQuestiontitle(fileuploadans[i]);
				if (!fileuploadansid[i].isEmpty()) {
					hmq.setQuestionid(Integer.parseInt(fileuploadansid[i]));
				}
				setHireMasterQuestions.add(hmq);
			}

		hireMaster.setHireMasterQuestions(setHireMasterQuestions);
		hireMaster = hireMasterService.save(hireMaster);

		List<EmployeeMaster> emplist = employeeMasterService.findAll();
		List<BranchMaster> bmlist = branchMasterService.findAll();

		theModel.addAttribute("bmlist", bmlist);
		theModel.addAttribute("emplist", emplist);
		theModel.addAttribute("HireMaster", hireMaster);
		theModel.addAttribute("save", "save");
		return "hirejobadd";
	}

	@GetMapping("calendar")
	public String calendarMaster(Model themodel) {
		return "calendarMaster";
	}

	@GetMapping("save")
	@ResponseBody
	public String saveBranchMaster() {
		BranchMaster objBM = new BranchMaster();
		branchMasterService.save(objBM);
		return "Success";
	}

	@ResponseBody
	@PostMapping("holidaydelete")
	public String holidaydelete(@RequestParam Map<String, String> param) {
		holidayService.deleteByid(Integer.parseInt(param.get("calid")));
		return "deleted";
	}

	@GetMapping("leaveapprove")
	public String leaveapprove(Model theModel) {
		theModel.addAttribute("menuactivelist",
				menuactivelistobj.getactivemenulist("admin_hr_Attendance_Leave Approval"));
		return "leaveapprove";
	}

	@GetMapping("leavereview")
	public String leavereview(Model theModel, @RequestParam("id") int id) {
		LeaveMaster leaveMaster = leaveMasterService.findById(id);
		EmployeeMaster emp = employeeMasterService.findById(leaveMaster.getEmpid());
		theModel.addAttribute("emmap", emp);
		theModel.addAttribute("leavemaster", leaveMaster);
		return "leavereview";
	}

	@PostMapping("leavereview")
	public String leaveresave(Model theModel, @ModelAttribute("leavemaster") LeaveMaster obj,
			HttpServletRequest request) {

		obj.setApproverejectdate(String.valueOf(new Date()));
		obj.setApprover(request.getSession().getAttribute("dataLoginEmpID").toString());
		LeaveMaster leaveMaster = leaveMasterService.save(obj);
		EmployeeMaster emp = employeeMasterService.findById(leaveMaster.getEmpid());
		theModel.addAttribute("emmap", emp);
		theModel.addAttribute("leavemaster", leaveMaster);
		theModel.addAttribute("save", "save");
		return "leavereview";
	}

	@ResponseBody
	@GetMapping("leavehistoryjsoncalendar")
	public Object leavehistoryjsoncalendar(Model theModel,
			@RequestParam(name = "startdate", required = false) String startdate,
			@RequestParam(name = "enddate", required = false) String enddate) {
		List<Map<String, Object>> lmhistory = leaveMasterService.findByDates(startdate, enddate);

		List<CalenderFormat> calformate = new ArrayList<CalenderFormat>();

		lmhistory.forEach(rowMap -> {
			CalenderFormat cf = new CalenderFormat();
			cf.setId((int) rowMap.get("id"));
			cf.setAllDay(Boolean.parseBoolean(String.valueOf(rowMap.get("halfday"))));
			cf.setTitle(String.valueOf(rowMap.get("empname")));
			cf.setStart(String.valueOf(rowMap.get("start")));
			cf.setEnd(String.valueOf(rowMap.get("end")));

			HashMap<String, String> exextendedProps = new HashMap<>();
			exextendedProps.put("approvercomments", String.valueOf(rowMap.get("approvercomments")));
			exextendedProps.put("approverejectdate", String.valueOf(rowMap.get("approverejectdate")));
			exextendedProps.put("leavetype", String.valueOf(rowMap.get("leavetype")));
			exextendedProps.put("notes", String.valueOf(rowMap.get("notes")));
			exextendedProps.put("permissionendtime", String.valueOf(rowMap.get("permissionendtime")));
			exextendedProps.put("permissionstarttime", String.valueOf(rowMap.get("permissionstarttime")));
			exextendedProps.put("calendar", String.valueOf(rowMap.get("status")));
			exextendedProps.put("approver", String.valueOf(rowMap.get("approver")));
			exextendedProps.put("approvername", String.valueOf(rowMap.get("approvername")));
			cf.setExtendedProps(exextendedProps);
			calformate.add(cf);

		});

		return calformate;
	}

	@ResponseBody
	@GetMapping("leavehistoryjson")
	public Object leavehistoryjson(Model theModel, @RequestParam(name = "startdate", required = false) String startdate,
			@RequestParam(name = "enddate", required = false) String enddate) {
		List<Map<String, Object>> lmhistory = leaveMasterService.findByDates(startdate, enddate);

		return lmhistory;
	}

	@GetMapping("leavehistory")
	public String leavehistory(Model theModel) {
		theModel.addAttribute("menuactivelist",
				menuactivelistobj.getactivemenulist("admin_hr_Attendance_Leave History"));

		return "leavehistory";
	}

	@ResponseBody
	@PostMapping("leavereject")
	public String leavereject(@RequestParam Map<String, String> param) {

		LeaveMaster obj = leaveMasterService.findById(Integer.parseInt(param.get("calid")));
		obj.setStatus("Rejected");
		leaveMasterService.save(obj);
		return "Rejected";

	}

	@ResponseBody
	@PostMapping("leaveapprove")
	public String leaveapprove(@RequestParam Map<String, String> param) {

		LeaveMaster obj = leaveMasterService.findById(Integer.parseInt(param.get("calid")));
		obj.setStatus("Approved");
		leaveMasterService.save(obj);
		return "Approved";

	}

	@GetMapping("payroll")
	public String payrollget(Model themodel) {
		List<BranchMaster> branchls = branchMasterService.findAll();
		themodel.addAttribute("branchlist", branchls);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_Payroll"));
		return "payroll";
	}

	@PostMapping("payroll")
	public String payrollpost(@RequestParam(name = "month") String selectedmonth,
			@RequestParam(name = "branch") int branch_masterid, Model themodel,
			@RequestParam(value = "save", defaultValue = "", required = false) String save) {

		LocalDate lastDayOfMonth = LocalDate.parse(selectedmonth + "-01", DateTimeFormatter.ofPattern("yyyy-M-dd"))
				.with(TemporalAdjusters.lastDayOfMonth());

		String prd[] = lastDayOfMonth.toString().split("-");
		String prdenddate = prd[2] + "." + prd[1] + "." + prd[0];
		String prdStartdate = "01." + prd[1] + "." + prd[0];

		String Payperiod = prdStartdate + " - " + prdenddate;
		if (!save.equalsIgnoreCase("")) {
			for (EmployeeMaster emoobj : EffectiveEmployee(employeeMasterService.findAll())) {
				payslipserive.deleteByPayperiod(Payperiod, String.valueOf(branch_masterid));
			}
		}

		// -------------------------------------------------------
		// Get all Sunday dates
		// -------------------------------------------------------
		int year = Integer.parseInt(prd[0]);
		int month1 = Integer.parseInt(prd[1]);
		ArrayList<String> sundays = GetallSundaydates(year, month1);

		String Sundaysql = "";
		String Sundaysqlstr = "";
		if (sundays.size() > 0) {
			for (String str : sundays) {
				Sundaysql += "'" + selectedmonth + "-" + String.format("%02d", Integer.parseInt(str.replace("|", "")))
						+ " 00:00:00',";
			}
			Sundaysql = Sundaysql.substring(0, Sundaysql.length() - 1);
			// System.out.println("Sundaysql" + Sundaysql);
		}
		// --------------------------------------------------------
		if (Sundaysql.length() > 0) {
			Sundaysqlstr = ", sum(CASE WHEN (attendance_date in (" + Sundaysql
					+ ") and attstatus in('T','P')) THEN 1 ELSE 0 END)AS 'SUNDAYP' ,"
					+ "sum(CASE WHEN (attendance_date in (" + Sundaysql
					+ ") and attstatus in('HL')) THEN 1 ELSE 0 END)AS 'SUNDAYHL' ";

		} else {
			Sundaysqlstr = ",0 AS 'SUNDAYP' ,0 AS 'SUNDAYHL' ,0 AS 'SUNDAYT' ";
		}

		// -------------------------------------------------------
		// Get Holiday details between start and end dates
		// exclude the Sundays
		// -------------------------------------------------------
		ArrayList<String> holidaylist = GetHolidaydetailsbetweenstartandenddates(selectedmonth, sundays);

		String Holidaysql = "";
		String Holidaysqlstr = "";
		if (holidaylist.size() > 0) {
			for (String str : holidaylist) {
				Holidaysql += "'" + selectedmonth + "-" + String.format("%02d", Integer.parseInt(str.replace("|", "")))
						+ " 00:00:00',";
			}
			Holidaysql = Holidaysql.substring(0, Holidaysql.length() - 1);
			// System.out.println("Holidaysql" + Holidaysql);
		}
		// --------------------------------------------------------
		if (Holidaysql.length() > 0) {
			Holidaysqlstr = ", sum(CASE WHEN (attendance_date in (" + Holidaysql
					+ ") and attstatus in('T','P')) THEN 1 ELSE 0 END)AS 'HOLIDAYP' ,"
					+ "sum(CASE WHEN (attendance_date in (" + Holidaysql
					+ ") and attstatus in('HL')) THEN 1 ELSE 0 END)AS 'HOLIDAYHL' , "
					+ "sum(CASE WHEN (attendance_date in (" + Holidaysql
					+ ") and attstatus in('A')) THEN 1 ELSE 0 END)AS 'HOLIDAYA'  ";
		} else {
			Holidaysqlstr = ",0 AS 'HOLIDAYP' ,0 AS 'HOLIDAYHL' ,0 AS 'HOLIDAYA' ";
		}

		Holidaysqlstr = Sundaysqlstr + Holidaysqlstr;
		ArrayList<String> report = new ArrayList<String>();
		List<Map<String, Object>> atm = attendanceMasterService.getpayrolldetails(selectedmonth, Holidaysqlstr,
				branch_masterid);

		ArrayList<Double> totalnet = new ArrayList<Double>();
		totalnet.add(0, 0.0);
		atm.forEach(rowMap -> {

			int employeeid = (int) rowMap.get("employeeid");
			double P = Double.parseDouble(rowMap.get("P").toString());
			double A = Double.parseDouble(rowMap.get("A").toString());
			double T = Double.parseDouble(rowMap.get("T").toString());
			double HL0 = Double.parseDouble(rowMap.get("HL").toString());
			double HOLIDAYP = Double.parseDouble(rowMap.get("HOLIDAYP").toString());
			double HOLIDAYHL0 = Double.parseDouble(rowMap.get("HOLIDAYHL").toString());
			double SUNDAYP = Double.parseDouble(rowMap.get("SUNDAYP").toString());
			double SUNDAYHL0 = Double.parseDouble(rowMap.get("SUNDAYHL").toString());
			double HOLIDAYA = Double.parseDouble(rowMap.get("HOLIDAYA").toString());

			double HL = HL0 / 2;
			double HOLIDAYHL = HOLIDAYHL0 / 2;
			double SUNDAYHL = SUNDAYHL0 / 2;

			String staff_name = (String) rowMap.get("staff_name");
			String AccountNo = (String) rowMap.get("bankacno");
			String BankName = (String) rowMap.get("bank_name");
			String Locationstate = (String) rowMap.get("joblocation");
			double compayrate = Integer.parseInt(rowMap.get("compayrate").toString());

			double ctc = compayrate;
			double TotalWWorkingDays = 0.00;
			double TotalCompanywrkdays = 0.00;
			double Totalholidaywrkdays = 0.00;
			double Totalsundaywrkdays = 0.00;
			double Totalholidays = 0.00;

			double Absent = 0.00;
			double WorkingDays = 0.00;
			double ExtraWorkingDays = 0.00;
			double BasicSalary = 0.00;
			double DA = 0.00;
			double HRA = 0.00;
			double TOTALGROSS = 0.00;
			double ESI = 0.00;
			double EPF = 0.00;
			double Advance = 0.00;
			double TOTALDeduction = 0.00;
			double Monthlyincentives = 0.00;
			double net = 0.00;
			// ----------------------------------------------------
			/*
			 * System.out.println("HOLIDAYHL0-" + HOLIDAYHL0+ " SUNDAYHL0-" + SUNDAYHL0+
			 * " HL0-" + HL0); System.out.println("P-" + P+ " A-" + A+ " T-" + T +" HL-" +
			 * HL); System.out.println("HOLIDAYP-" + HOLIDAYP+ " HOLIDAYHL-" + HOLIDAYHL+
			 * " HOLIDAYA-" + HOLIDAYA); System.out.println("SUNDAYP-" + SUNDAYP+
			 * " SUNDAYHL-" + SUNDAYHL); System.out.println("0.5" + (P-HOLIDAYP-SUNDAYP) + T
			 * + (HL-HOLIDAYHL-SUNDAYHL) );
			 */
			Totalholidays = holidaylist.size();
			TotalWWorkingDays = (P - HOLIDAYP - SUNDAYP) + T + (HL - HOLIDAYHL - SUNDAYHL);
			Totalsundaywrkdays = SUNDAYP + SUNDAYHL;
			Totalholidaywrkdays = HOLIDAYP + HOLIDAYHL;
			ExtraWorkingDays = Totalsundaywrkdays + Totalholidaywrkdays;

			Absent = A;
			WorkingDays = TotalWWorkingDays + Totalholidays - HOLIDAYA;

			/*
			 * System.out.println("Totalholidays-" + Totalholidays+ " TotalWWorkingDays-" +
			 * TotalWWorkingDays+ " Totalsundaywrkdays-" +
			 * Totalsundaywrkdays+" Totalholidaywrkdays-" + Totalholidaywrkdays);
			 * System.out.println("ExtraWorkingDays-" + ExtraWorkingDays+ " WorkingDays-" +
			 * WorkingDays);
			 */

			BasicSalary = Math.round((ctc / 26) * WorkingDays * 0.40);
			DA = Math.round((ctc / 26) * WorkingDays * 0.35);
			HRA = Math.round((ctc / 26) * WorkingDays * 0.25);
			TOTALGROSS = Math.round(BasicSalary + DA + HRA);
			ESI = Math.round(BasicSalary * (0.01) * 0);
			EPF = Math.round(BasicSalary * (0.12) * 0);
			TOTALDeduction = ESI + EPF + Advance;
			Monthlyincentives = Math.round(ExtraWorkingDays * (ctc / 26));
			net = Math.round((TOTALGROSS - TOTALDeduction) + Monthlyincentives);

			String str = employeeid + "-" + staff_name + "-" + ctc + "-" + (WorkingDays + ExtraWorkingDays) + "-"
					+ Absent + "-" + WorkingDays + "-" + ExtraWorkingDays;
			str += "-" + BasicSalary + "-" + DA + "-" + HRA + "-" + TOTALGROSS + "-" + ESI + "-" + EPF + "-" + Advance
					+ "-" + TOTALDeduction + "-" + Monthlyincentives + "-" + net;

			report.add(str);
			totalnet.set(0, totalnet.get(0) + net);

			if (!save.equalsIgnoreCase("")) {

				payslip payslipboj = new payslip();
				payslipboj.setPaymonth(Integer.parseInt(selectedmonth.replace("-", "")));
				payslipboj.setAbsent(String.valueOf(Absent));
				payslipboj.setAccountNo(AccountNo);
				payslipboj.setAdvance(String.valueOf(Advance));
				payslipboj.setBankName(String.valueOf(BankName));
				payslipboj.setBasicSalary(String.valueOf(BasicSalary));
				payslipboj.setCtc(String.valueOf(ctc));
				payslipboj.setDa(String.valueOf(DA));
				payslipboj.setEmployeeid(String.valueOf(employeeid));
				payslipboj.setEpf(String.valueOf(EPF));
				payslipboj.setEsi(String.valueOf(ESI));
				payslipboj.setExtraWorkingDays(String.valueOf(ExtraWorkingDays));
				payslipboj.setHra(String.valueOf(HRA));
				payslipboj.setLocationstate(String.valueOf(Locationstate));
				payslipboj.setMonthlyincentives(String.valueOf(Monthlyincentives));
				payslipboj.setNet(String.valueOf(net));
				payslipboj.setPayperiod(String.valueOf(Payperiod));
				payslipboj.setStaff_name(String.valueOf(staff_name));
				payslipboj.setTotaldeduction(String.valueOf(TOTALDeduction));
				payslipboj.setTotalgross(String.valueOf(TOTALGROSS));
				payslipboj.setTotalWorkingDays(String.valueOf(WorkingDays + ExtraWorkingDays));
				payslipboj.setWorkingDays(String.valueOf(WorkingDays));
				payslipboj.setBranchid(String.valueOf(branch_masterid));

				payslipserive.save(payslipboj);
				themodel.addAttribute("save", "save");

			}
		});

		if (!save.equalsIgnoreCase("")) {
			themodel.addAttribute("save", "save");
		}
		themodel.addAttribute("report", report);
		themodel.addAttribute("selectedmonth", selectedmonth);
		themodel.addAttribute("totalnet", totalnet.get(0));

		BranchMaster bm = branchMasterService.findById(branch_masterid);
		themodel.addAttribute("branchobj", bm);

		List<BranchMaster> branchls = branchMasterService.findAll();
		themodel.addAttribute("branchlist", branchls);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_hr_Payroll"));

		return "payroll";
	}

	@PostMapping("payrollpdf")
	public String payrollpdf(@RequestParam(name = "month") String selectedmonth, Model themodel,
			@RequestParam(value = "report") String report, @RequestParam(value = "branchname") String branchname) {
		// System.out.println(selectedmonth);
		String Str = this.theMonth(Integer.parseInt(String.valueOf(selectedmonth).substring(5, 7)) - 1).toUpperCase()
				+ " " + String.valueOf(selectedmonth).substring(0, 4);
		themodel.addAttribute("report", report.replace("]", ""));
		themodel.addAttribute("monthtext", Str);
		themodel.addAttribute("branchname", branchname);
		themodel.addAttribute("selectedmonth", selectedmonth);

		return "payslippdf";
	}

	@GetMapping("attendancereport")
	public String empattendancereport(Model themodel,
			@RequestParam(name = "month", required = false, defaultValue = "") String selectedmonth,
			@RequestParam(name = "branch", required = false, defaultValue = "1") String branchid) {

		int prdenddate;
		String monthstr = "";
		int yearstr = 0;
		int month = 0;
		if (!selectedmonth.equalsIgnoreCase("")) {
			LocalDate lastDayOfMonth = LocalDate.parse(selectedmonth + "-01", DateTimeFormatter.ofPattern("yyyy-M-dd"))
					.with(TemporalAdjusters.lastDayOfMonth());

			String prd[] = lastDayOfMonth.toString().split("-");
			prdenddate = Integer.parseInt(prd[2]);
			yearstr = Integer.parseInt(prd[0]);
			month = Integer.parseInt(prd[1]);

		} else {
			Date currentdate = new Date();
			SimpleDateFormat formatterdate = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate lastDayOfMonth = LocalDate.parse(formatterdate.format(currentdate))
					.with(TemporalAdjusters.lastDayOfMonth());

			String prd[] = lastDayOfMonth.toString().split("-");
			prdenddate = Integer.parseInt(prd[2]);
			monthstr = prd[0] + "-" + prd[1];
			yearstr = Integer.parseInt(prd[0]);
			month = Integer.parseInt(prd[1]);
		}

		monthstr = yearstr + "-" + month;

		// -------------------------------------------------------
		// Get Attendance details for particular month
		// -------------------------------------------------------
		List<Map<String, Object>> atm = attendanceMasterService.getatttendancereport(monthstr, prdenddate,
				Integer.parseInt(branchid));

		ArrayList<String> reportarr = new ArrayList<String>();

		atm.forEach(rowMap -> {
			String reportstr = "";
			int P = 0;
			int A = 0;
			int T = 0;
			int HL = 0;

			reportstr += rowMap.get("staff_name").toString() + " ~";
			reportstr += rowMap.get("employeeid").toString() + " ~";

			for (int i = 1; i < 10; i++) {
				String temp = rowMap.get("0" + i).toString().trim();
				reportstr += temp + " ~";

				if (temp.equalsIgnoreCase("A")) {
					A++;
				}
				if (temp.equalsIgnoreCase("P")) {
					P++;
				}
				if (temp.equalsIgnoreCase("HL")) {
					HL++;
				}
				if (temp.equalsIgnoreCase("T")) {
					T++;
				}
			}
			for (int j = 10; j <= prdenddate; j++) {
				String temp = rowMap.get(String.valueOf(j)).toString().trim();
				reportstr += temp + " ~";

				if (temp.equalsIgnoreCase("A")) {
					A++;
				}
				if (temp.equalsIgnoreCase("P")) {
					P++;
				}
				if (temp.equalsIgnoreCase("HL")) {
					HL++;
				}
				if (temp.equalsIgnoreCase("T")) {
					T++;
				}
			}

			reportstr += P + " ~" + A + " ~" + HL + " ~" + T + " ~";
			reportarr.add(reportstr);

		});

		// -------------------------------------------------------
		// Get all Sunday dates
		// -------------------------------------------------------
		int year = yearstr;
		int month1 = month;
		ArrayList<String> sundays = GetallSundaydates(year, month1);

		// -------------------------------------------------------
		// Get Holiday details between start and end dates
		// exclude the Sundays
		// -------------------------------------------------------
		ArrayList<String> holidaylist = GetHolidaydetailsbetweenstartandenddates(monthstr, sundays);

		// --------------------------------------------------------
		// Add Models data
		themodel.addAttribute("holidaylist", holidaylist);
		themodel.addAttribute("monthstr", monthstr);
		themodel.addAttribute("sundays", sundays);
		themodel.addAttribute("reportarr", reportarr);
		themodel.addAttribute("prdenddate", prdenddate);

		List<BranchMaster> bmList = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmList);
		themodel.addAttribute("branchid", branchid);
		themodel.addAttribute("menuactivelist",
				menuactivelistobj.getactivemenulist("admin_hr_Attendance_Attendance Report"));
		return "empattendancereport";
	}

	public ArrayList<String> GetallSundaydates(int year, int month1) {
		// -------------------------------------------------------
		// Get all Sunday dates
		// -------------------------------------------------------

		ArrayList<String> sundays = new ArrayList<String>();

		IntStream.rangeClosed(1, YearMonth.of(year, month1).lengthOfMonth())
				.mapToObj(day -> LocalDate.of(year, month1, day))
				.filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
				.forEach(date -> sundays.add("|" + date.getDayOfMonth() + "|"));
		return sundays;
	}

	public ArrayList<String> GetHolidaydetailsbetweenstartandenddates(String monthstr, ArrayList<String> sundays) {
		// -------------------------------------------------------
		// Get Holiday details between start and end dates
		// exclude the Sundays
		// -------------------------------------------------------
		ArrayList<String> holidaylist = new ArrayList<String>();
		List<Map<String, Object>> holidaylistmap = holidayService.getHolidaysforMonth(monthstr + "-01",
				monthstr + "-31");
		holidaylistmap.forEach(rowMap -> {

			String hstartdate = rowMap.get("start").toString().substring(0, 10);
			String henddate = rowMap.get("end").toString().substring(0, 10);
			int hdiff = Integer.parseInt(rowMap.get("diff").toString());

			// System.out.println(hstartdate + " , " + henddate + " , " + hdiff);

			LocalDate lastDayOfMonth = LocalDate.parse(hstartdate, DateTimeFormatter.ofPattern("yyyy-M-dd"))
					.with(TemporalAdjusters.lastDayOfMonth());

			String prd[] = lastDayOfMonth.toString().split("-");
			int monthenddate = Integer.parseInt(prd[2]);

			String startddprd[] = hstartdate.toString().split("-");
			String endddprd[] = henddate.toString().split("-");

			int startdd = Integer.parseInt(startddprd[2]);
			int enddd = Integer.parseInt(endddprd[2]);

			if (hdiff > 1) {
				for (int h = startdd; h < enddd; h++) {
					if (h <= monthenddate) {
						if (!sundays.contains("|" + h + "|"))

							holidaylist.add("|" + h + "|");
					}
				}

			} else {
				if (!sundays.contains("|" + startdd + "|"))
					holidaylist.add("|" + startdd + "|");
			}

		});
		// --------------------------------------------------------
		return holidaylist;
	}

	public String theMonth(int month) {
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[month];
	}

	@GetMapping("vendorlist")
	public String vendorlist(Model theModel) {
		List<String> data = new ArrayList<String>();

		List<VendorMaster> ls = new ArrayList<VendorMaster>();
		ls = vendorMasterService.findAll();

		for (VendorMaster obj : ls) {
			String str = "";

			str += obj.getName() + " |";

			str += " |";

			str += obj.getVendormasterid() + "|";
			str += obj.getAssetType() + " |";

			if (obj.getVendorEmgContact().size() > 0) {
				List<VendorEmgContact> venec = obj.getVendorEmgContact().stream()
						.filter(C -> !String.valueOf(C.getEmg_PersonalPhone()).equalsIgnoreCase(""))
						.collect(Collectors.toList());

				if (venec.size() > 0) {
					str += venec.get(0).getEmg_PersonalPhone() + " |";
				} else {
					str += "- |";
				}

			} else {
				str += "- |";
			}
			str += obj.getAddress_City() + " |";

			data.add(str);
		}

		theModel.addAttribute("venlist", data);
		return "vendorlist";

	}

	@GetMapping("vendor")
	public String vendornew(Model themodel) {

		VendorMaster venobj = new VendorMaster();

		VendorEmgContact vencont = new VendorEmgContact();
		ArrayList<VendorEmgContact> emgls = new ArrayList<VendorEmgContact>();
		emgls.add(vencont);

		VendorFiles venfiles = new VendorFiles();
		ArrayList<VendorFiles> filels = new ArrayList<VendorFiles>();
		filels.add(venfiles);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		themodel.addAttribute("vendorEmgContact", emgls);
		themodel.addAttribute("vendorFiles", venfiles);
		themodel.addAttribute("vendormaster", venobj);
		return "vendoradd";
	}

	@GetMapping("ven")
	public String vendordetails(Model themodel, @RequestParam("id") int id) {

		VendorMaster vendormasternew = new VendorMaster();
		vendormasternew = vendorMasterService.findById(id);

		List<VendorEmgContact> emglsnew = new ArrayList<VendorEmgContact>();
		List<VendorFiles> filelsnew = new ArrayList<VendorFiles>();

		if (vendormasternew.getVendorEmgContact().size() > 0) {
			emglsnew.addAll(vendormasternew.getVendorEmgContact());
		} else {
			VendorEmgContact empcont = new VendorEmgContact();
			emglsnew.add(empcont);

		}

		if (vendormasternew.getVendorFiles().size() > 0) {
			filelsnew.addAll(vendormasternew.getVendorFiles());
		} else {
			VendorFiles empfiles1 = new VendorFiles();
			filelsnew.add(empfiles1);
		}

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		themodel.addAttribute("vendorEmgContact", emglsnew);
		themodel.addAttribute("vendorFiles", filelsnew);
		themodel.addAttribute("vendormaster", vendormasternew);
		return "vendoradd";
	}

	@PostMapping("Vendorsave")
	public String Vendorsave(HttpServletRequest req, Model themodel,
			@ModelAttribute("vendormaster") VendorMaster vendormaster,
			@RequestParam(name = "filesempFileid", required = false) String[] filesempFileid,
			@RequestParam(name = "filesempFileidstr", required = false) String[] filesempFileidstr,
			@RequestParam(name = "Files_Attach", required = false) MultipartFile Files_Attach,
			@RequestParam(name = "venEmgContactid", required = false) String[] venEmgContactid,
			@RequestParam(name = "emgid", required = false) String[] emgid,
			@RequestParam(name = "Emg_Name", required = false) String[] Emg_Name,
			@RequestParam(name = "Designation", required = false) String[] Designation,
			@RequestParam(name = "Emg_PersonalPhone", required = false) String[] Emg_PersonalPhone,
			@RequestParam(name = "Emg_OtherPhone", required = false) String[] Emg_OtherPhone,
			@RequestParam(name = "Emg_EmailID", required = false) String[] Emg_EmailID,
			@RequestParam(name = "Landlineno", required = false) String[] Landlineno,
			@RequestParam Map<String, String> params, HttpServletRequest request) {

		// Systven.out.println("--------------Step 1 end----------------------");

		List<VendorEmgContact> vengls = new ArrayList<VendorEmgContact>();
		for (int farr = 0; farr < Emg_Name.length; farr++) {
			VendorEmgContact venpcont = new VendorEmgContact();

			String tvenplangurow = emgid[farr];
			if (params.get("Emg_primarycontact" + tvenplangurow) != null) {
				venpcont.setEmg_primarycontact(true);
			} else {
				venpcont.setEmg_primarycontact(false);
			}

			if (!venEmgContactid[farr].isEmpty()) {
				venpcont.setVenEmgContactid(Integer.parseInt(venEmgContactid[farr]));
			}
			if (Emg_Name.length > 0)
				venpcont.setEmg_Name(Emg_Name[farr]);
			if (Designation.length > 0)
				venpcont.setDesignation(Designation[farr]);
			if (Emg_PersonalPhone.length > 0)
				venpcont.setEmg_PersonalPhone(Emg_PersonalPhone[farr]);
			if (Emg_OtherPhone.length > 0)
				venpcont.setEmg_OtherPhone(Emg_OtherPhone[farr]);
			if (Emg_EmailID.length > 0)
				venpcont.setEmg_EmailID(Emg_EmailID[farr]);

			if (Landlineno.length > 0)
				venpcont.setLandlineno(Landlineno[farr]);

			vengls.add(venpcont);
		}
		vendormaster.setVendorEmgContact(vengls);
		// Systven.out.println("--------------Step 2 End------v----------------");

		List<VendorFiles> filels = new ArrayList<VendorFiles>();

		if (filesempFileid != null)
			for (int farr = 0; farr < filesempFileid.length; farr++) {
				VendorFiles obj = new VendorFiles();

				if (filesempFileid[farr] != null) {
					obj.setVenFileid(Integer.parseInt(filesempFileid[farr]));
				}
				obj.setFiles_Attach(filesempFileidstr[farr]);
				filels.add(obj);
			}

		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("vendorprofilephoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String certificateuploadRootPath = request.getServletContext().getRealPath("vendorfiles");
		// Systven.out.println("uploadRootPath=" + certificateuploadRootPath);

		File uploadRootDircertificate = new File(certificateuploadRootPath);
		if (!uploadRootDircertificate.exists()) {
			uploadRootDircertificate.mkdirs();
		}

		if (Files_Attach.getOriginalFilename().toString().length() > 0) {
			VendorFiles venpfiles = new VendorFiles();
			StringBuilder filename = new StringBuilder();
			String tvenpfilename = stringdatetime() + Files_Attach.getOriginalFilename();
			Path fileNameandPath = Paths.get(certificateuploadRootPath, tvenpfilename);
			filename.append(tvenpfilename);
			venpfiles.setFiles_Attach("vendorfiles/" + filename);
			try {
				Files.write(fileNameandPath, Files_Attach.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			filels.add(venpfiles);
		}
		vendormaster.setVendorFiles(filels);
		// Systven.out.println("--------------Step 5 end----------------------");
		VendorMaster vendormasternew = new VendorMaster();
		itemlistService.savesingletxt(vendormaster.getAssetType(), "ASSETTYPE");
		vendormasternew = vendorMasterService.save(vendormaster);

		List<VendorEmgContact> venglsnew = new ArrayList<VendorEmgContact>();
		List<VendorFiles> filelsnew = new ArrayList<VendorFiles>();

		// Systven.out.println(vendormasternew.getVendorEmgContact().size());
		if (vendormasternew.getVendorEmgContact().size() > 0) {
			venglsnew.addAll(vendormasternew.getVendorEmgContact());
		} else {
			VendorEmgContact venpcont = new VendorEmgContact();
			venglsnew.add(venpcont);

		}

		if (vendormasternew.getVendorFiles().size() > 0) {
			filelsnew.addAll(vendormasternew.getVendorFiles());
		} else {
			VendorFiles venpfiles1 = new VendorFiles();
			filelsnew.add(venpfiles1);
		}

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		themodel.addAttribute("vendorEmgContact", venglsnew);
		themodel.addAttribute("vendorFiles", filelsnew);
		themodel.addAttribute("vendormaster", vendormaster);
		return "vendoradd";
	}

	@GetMapping("assetlist")
	public String assetlist(Model theModel) {
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_AssetManagement"));

		theModel.addAttribute("branchls", branchMasterService.findAll());

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		theModel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		theModel.addAttribute("ASSETTYPE", ASSETTYPE);

		return "assetlist";
	}

	@ResponseBody
	@GetMapping("assetlistbyids")
	public List<AssetMaster> assetlistbyids(@RequestParam Map<String, String> params) {

		List<Integer> assetids = Arrays.asList(params.get("assetids").split(",")).stream()
				.map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		List<AssetMaster> ls = assetMasterService.findByManyassetIds(assetids);
		return ls;
	}

	@ResponseBody
	@GetMapping("assetlistjson")
	public List<AssetMaster> assetlistjson() {

		List<AssetMaster> ls = new ArrayList<AssetMaster>();

		List<BranchMaster> bm = branchMasterService.findAll();
		String custodian = "";
		for (AssetMaster obj : assetMasterService.findAll()) {

			if (obj.getBranch() != null) {
				obj.setBranchname(bm.stream().filter(C -> C.getId() == Integer.parseInt(obj.getBranch()))
						.collect(Collectors.toList()).get(0).getBRANCH_NAME());
			}

			if ((obj.getStaffID() != null)) {
				if (!nullremover(String.valueOf(obj.getStaffID())).equalsIgnoreCase("")) {
					obj.setCustodian(employeeMasterService.findById(Integer.parseInt(obj.getStaffID())).getStaffName());
				}
			}

			ls.add(obj);
		}

		return ls;
	}

	@GetMapping("assetnew")
	public String assetnew(Model themodel) {

		AssetMaster assetobj = new AssetMaster();
		assetobj.setStatus("In Stock");

		List<AssetService> setassetSevice = new ArrayList();
		AssetService objassetservice = new AssetService();
		objassetservice.setOptionradiobtn("RepeatEvery");
		setassetSevice.add(objassetservice);
		assetobj.setAssetService(setassetSevice);
		AssetMasterFiles assetfiles = new AssetMasterFiles();
		ArrayList<AssetMasterFiles> filels = new ArrayList<AssetMasterFiles>();
		filels.add(assetfiles);

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		themodel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		List<String> ServiceItem = itemlistService.findByFieldName("ServiceItem");
		themodel.addAttribute("ServiceItem", ServiceItem);
		// ---------------------------------------
		themodel.addAttribute("assetFiles", assetfiles);
		themodel.addAttribute("assetmaster", assetobj);
		themodel.addAttribute("maxid", assetMasterService.getmaxid());

		themodel.addAttribute("newasset", true);

		return "asset";
	}

	@GetMapping("asset")
	public String assetdetails(Model themodel, @RequestParam("id") int id) {

		AssetMaster assetmaster = new AssetMaster();
		assetmaster = assetMasterService.findById(id);

		if (assetmaster.getAssetService().size() == 0) {
			List<AssetService> setassetSevice = new ArrayList();
			setassetSevice.add(new AssetService());
			assetmaster.setAssetService(setassetSevice);
		}
		List<AssetMasterFiles> filelsnew = new ArrayList<AssetMasterFiles>();

		if (assetmaster.getAssetMasterFiles().size() > 0) {
			filelsnew.addAll(assetmaster.getAssetMasterFiles());
		} else {
			AssetMasterFiles empfiles1 = new AssetMasterFiles();
			filelsnew.add(empfiles1);
		}

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		themodel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		List<String> ServiceItem = itemlistService.findByFieldName("ServiceItem");
		themodel.addAttribute("ServiceItem", ServiceItem);

		themodel.addAttribute("assetFiles", filelsnew);
		themodel.addAttribute("assetmaster", assetmaster);

		return "asset";
	}

	@PostMapping("assetsave")
	public String Assetsave(HttpServletRequest req, Model themodel,
			@ModelAttribute("assetmaster") AssetMaster assetmaster,
			@RequestParam(name = "filesempFileid", required = false) String[] filesempFileid,
			@RequestParam(name = "filesempFileidstr", required = false) String[] filesempFileidstr,

			@RequestParam(name = "Files_Attach", required = false) MultipartFile Files_Attach,

			HttpServletRequest request) {

		// Systasset.out.println("--------------Step 1 end----------------------");

		List<AssetMasterFiles> filels = new ArrayList<AssetMasterFiles>();

		if (filesempFileid != null)
			for (int farr = 0; farr < filesempFileid.length; farr++) {
				AssetMasterFiles obj = new AssetMasterFiles();

				if (filesempFileid[farr] != null) {
					obj.setAssetFileid(Integer.parseInt(filesempFileid[farr]));
				}
				obj.setFiles_Attach(filesempFileidstr[farr]);
				filels.add(obj);
			}

		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("assetprofilephoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String certificateuploadRootPath = request.getServletContext().getRealPath("assetfiles");
		// Systasset.out.println("uploadRootPath=" + certificateuploadRootPath);

		File uploadRootDircertificate = new File(certificateuploadRootPath);
		if (!uploadRootDircertificate.exists()) {
			uploadRootDircertificate.mkdirs();
		}

		if (Files_Attach.getOriginalFilename().toString().length() > 0) {
			AssetMasterFiles assetpfiles = new AssetMasterFiles();
			StringBuilder filename = new StringBuilder();
			String tassetpfilename = stringdatetime() + Files_Attach.getOriginalFilename();
			Path fileNameandPath = Paths.get(certificateuploadRootPath, tassetpfilename);
			filename.append(tassetpfilename);
			assetpfiles.setFiles_Attach("assetfiles/" + filename);
			try {
				Files.write(fileNameandPath, Files_Attach.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			filels.add(assetpfiles);
		}
		assetmaster.setAssetMasterFiles(filels);
		// Systasset.out.println("--------------Step 5 end----------------------");
		AssetMaster assetmasternew = new AssetMaster();
		itemlistService.savesingletxt(assetmaster.getAssetType(), "ASSETTYPE");

		List<AssetService> setassetSevicetemp = new ArrayList();

		for (AssetService obj : assetmaster.getAssetService()) {
			if (obj.getServiceItem() != null) {
				itemlistService.savesingletxt(obj.getServiceItem(), "ServiceItem");
				setassetSevicetemp.add(obj);
			}

		}

		assetmaster.setAssetService(setassetSevicetemp);
		assetmasternew = assetMasterService.save(assetmaster);

		List<AssetMasterFiles> filelsnew = new ArrayList<AssetMasterFiles>();

		if (assetmasternew.getAssetMasterFiles().size() > 0) {
			filelsnew.addAll(assetmasternew.getAssetMasterFiles());
		} else {
			AssetMasterFiles assetpfiles1 = new AssetMasterFiles();
			filelsnew.add(assetpfiles1);
		}

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		themodel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		List<String> ServiceItem = itemlistService.findByFieldName("ServiceItem");
		themodel.addAttribute("ServiceItem", ServiceItem);

		themodel.addAttribute("assetFiles", filelsnew);
		themodel.addAttribute("assetmaster", assetmasternew);
		themodel.addAttribute("save", "save");
		return "asset";
	}

	@ResponseBody
	@PostMapping("assetsavejson")
	public String Assetsave(HttpServletRequest req, Model themodel, HttpServletRequest request,
			@RequestParam Map<String, String> params) {

		// Systasset.out.println("--------------Step 1 end----------------------");
		// System.out.println(params);

		AssetMaster assetmasternew = new AssetMaster();
		assetmasternew.setACondition(params.get("CONDITION"));
		assetmasternew.setAssetName(params.get("ASSET"));
		assetmasternew.setAssetType(params.get("AssetType"));
		assetmasternew.setBranch(params.get("Branch"));
		assetmasternew.setBrand(params.get("BRAND"));
		assetmasternew.setCustodian("");
		assetmasternew.setManufacturer(params.get("MANUFACTURER"));
		assetmasternew.setModel(params.get("MODEL"));
		assetmasternew.setNotes(params.get("NOTES"));
		assetmasternew.setPurchased(params.get("PURCHASED"));
		assetmasternew.setPurchasedType(params.get("PurchasedType"));
		assetmasternew.setPurchaseOrderNo(params.get("PURCHASE_ORDER"));
		assetmasternew.setPurchasePrice(params.get("PurchasePrice"));
		assetmasternew.setSerialNumber(params.get("SERIAL_NUMBER"));
		assetmasternew.setStatus("In Stock");
		assetmasternew.setWarrantyEnd(params.get("WARRANTYEND"));
		assetmasternew.setVendor(params.get("Vendor"));

		itemlistService.savesingletxt(params.get("AssetType"), "ASSETTYPE");
		assetmasternew.setBranchname(
				branchMasterService.findAll().stream().filter(C -> C.getId() == Integer.parseInt(params.get("Branch")))
						.collect(Collectors.toList()).get(0).getBRANCH_NAME());

		assetmasternew = assetMasterService.save(assetmasternew);

		return "assetsaved";
	}

	@GetMapping("assetview")
	public String assetview(@RequestParam("id") int assetid, Model theModel) {

		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_AssetManagement"));

		theModel.addAttribute("branchls", branchMasterService.findAll());

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		theModel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		theModel.addAttribute("ASSETTYPE", ASSETTYPE);

		AssetMaster assetmasternew = assetMasterService.findById(assetid);

		try {
			assetmasternew.setVendorName(vendorMasterService.findAll().stream()
					.filter(C -> C.getVendormasterid() == Integer.parseInt(assetmasternew.getVendor()))
					.collect(Collectors.toList()).get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			assetmasternew.setBranchname(branchMasterService.findAll().stream()
					.filter(C -> C.getId() == Integer.parseInt(assetmasternew.getBranch())).collect(Collectors.toList())
					.get(0).getBRANCH_NAME());

		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		try {
			if (!nullremover(String.valueOf(assetmasternew.getWarrantyEnd())).equalsIgnoreCase("")) {
				assetmasternew.setWarrantyEndMMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(assetmasternew.getWarrantyEnd())).toString());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			if (!nullremover(String.valueOf(assetmasternew.getPurchased())).equalsIgnoreCase("")) {
				assetmasternew.setPurchasedMMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(assetmasternew.getPurchased())).toString());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		theModel.addAttribute("assetobj", assetMasterService.findById(assetid));

		return "assetview";
	}

	@ResponseBody
	@PostMapping("assetsave1json")
	public AssetMaster AssetMasteredit(@RequestParam Map<String, String> params) {
		AssetMaster assetmasternew = assetMasterService.findById(Integer.parseInt(params.get("AssetId")));

		assetmasternew.setACondition(params.get("ACondition"));
		assetmasternew.setAssetName(params.get("AssetName"));
		assetmasternew.setAssetType(params.get("AssetType"));
		assetmasternew.setBranch(params.get("Branch"));
		assetmasternew.setBrand(params.get("Brand"));
		assetmasternew.setManufacturer(params.get("Manufacturer"));
		assetmasternew.setModel(params.get("Model"));
		assetmasternew.setNotes(params.get("Notes"));
		assetmasternew.setPurchased(params.get("Purchased"));
		assetmasternew.setPurchasedType(params.get("PurchasedType"));
		assetmasternew.setPurchaseOrderNo(params.get("PurchaseOrderNo"));
		assetmasternew.setPurchasePrice(params.get("PurchasePrice"));
		assetmasternew.setSerialNumber(params.get("SerialNumber"));
		assetmasternew.setWarrantyEnd(params.get("WarrantyEnd"));
		assetmasternew.setVendor(params.get("Vendor"));
		itemlistService.savesingletxt(params.get("AssetType"), "ASSETTYPE");
		try {
			assetmasternew.setBranchname(branchMasterService.findAll().stream()
					.filter(C -> C.getId() == Integer.parseInt(params.get("Branch"))).collect(Collectors.toList())
					.get(0).getBRANCH_NAME());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			assetmasternew.setVendorName(vendorMasterService.findAll().stream()
					.filter(C -> C.getVendormasterid() == Integer.parseInt(assetmasternew.getVendor()))
					.collect(Collectors.toList()).get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (!nullremover(String.valueOf(params.get("WarrantyEnd"))).equalsIgnoreCase("")) {
				assetmasternew.setWarrantyEndMMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(params.get("WarrantyEnd"))).toString());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			if (!nullremover(String.valueOf(params.get("Purchased"))).equalsIgnoreCase("")) {
				assetmasternew.setPurchasedMMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(params.get("Purchased"))).toString());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		assetMasterService.save(assetmasternew);
		return assetmasternew;
	}

	@PostMapping("deleteassetService")
	@ResponseBody
	public String deleteassetService(@RequestParam("deleteid") int deleteid) {
		assetserviceService.deleteByid(deleteid);
		return "deleted";
	}

	public List<EmployeeMaster> EffectiveEmployee(List<EmployeeMaster> employeeMasterls) {

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateforeffectemp = date;
		List<EmployeeMaster> output = new ArrayList<EmployeeMaster>();
		// ------------------------------------------------------------------------------------
		// Find out Effective location Employee filter with selected branch
		for (EmployeeMaster obj : employeeMasterls) {
			List<EmployeeJobempstatus> empstatusobj = new ArrayList<>();
			empstatusobj = employeeJobempstatusService.findByEmployeeid(obj.getEmpMasterid());

			if (empstatusobj.size() > 0) {

				List<EmployeeJobempstatus> empstatusobjgreen = empstatusobj.stream()
						.filter(c -> dateFormat.format(dateforeffectemp)
								.compareTo(c.getEmpstatus_effectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				empstatusobjgreen.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate));

				if (empstatusobjgreen.size() > 0 && !(empstatusobjgreen.get(empstatusobjgreen.size() - 1)
						.getEmpstatus_employmentstatus().equalsIgnoreCase("Terminated"))) {

					List<EmployeeJobinfo> infoobj = new ArrayList<>();
					infoobj = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());

					if (infoobj.size() > 0) {
						List<EmployeeJobinfo> infoobjgreen = infoobj.stream()
								.filter(c -> dateFormat.format(dateforeffectemp)
										.compareTo(c.getJobeffectivedate().toString()) >= 0)
								.collect(Collectors.toList());
						infoobjgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));

						if (infoobjgreen.size() > 0) {

							output.add(obj);

							/*
							 * if (infoobjgreen.get(infoobjgreen.size() - 1).getJoblocation()
							 * .equalsIgnoreCase(targetedbranchName)) {
							 * 
							 * if (!calculateTerminatedstatus(obj.getEmpMasterid(), date)) {
							 * employeeMasterlswitheffectivelocation.add(obj); } }
							 */
						}
					}
				}
			}
		}
		return output;

		// ------------------------------------------------------------------------------------
	}

	@GetMapping("checkout")
	public String checkout(Model themodel, @RequestParam(name = "id", required = false, defaultValue = "") String ids) {
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("admin_AssetManagement"));
		List<AssetMaster> assetMaster = assetMasterService.findAll();

		List<AssetMaster> AssetMasterobj = assetMaster.stream()
				.filter(C -> String.valueOf(C.getStatus()).equalsIgnoreCase("In Stock")).collect(Collectors.toList());

		List<AssetMaster> selectedasset = new ArrayList<AssetMaster>();
		if (!ids.equalsIgnoreCase("")) {
			String[] strarr = ids.split(",");

			for (String ai : strarr) {
				selectedasset.add(assetMaster.stream().filter(C -> C.getAssetId() == Integer.parseInt(ai))
						.collect(Collectors.toList()).get(0));
			}
		}

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		List<VendorMaster> vendorls = new ArrayList<VendorMaster>();
		vendorls = vendorMasterService.findAll();
		themodel.addAttribute("vendorls", vendorls);

		List<String> ASSETTYPE = itemlistService.findByFieldName("ASSETTYPE");
		themodel.addAttribute("ASSETTYPE", ASSETTYPE);

		List<EmployeeMaster> EmployeeMasterobj = EffectiveEmployee(employeeMasterService.findAll());

		themodel.addAttribute("AssetMasterobj", AssetMasterobj);
		themodel.addAttribute("selectedasset", selectedasset);
		themodel.addAttribute("EmployeeMasterobj", EmployeeMasterobj);
		themodel.addAttribute("today", displaydateFormatrev.format(new Date()));
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Check Out"));
		return "checkout";
	}

	@PostMapping("getassetlistbasedonbranch")
	@ResponseBody
	public String getassetlistbasedonbranch(@RequestParam("branchid") String branch) {
		String result = "";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll().stream()
				.filter(C -> String.valueOf(C.getStatus()).equalsIgnoreCase("In Stock")
						&& String.valueOf(C.getBranch()).equalsIgnoreCase(branch))
				.collect(Collectors.toList());
		// ------------------------------------------------------------------------------------
		for (AssetMaster obj : AssetMasterobj) {
			result += obj.getAssetId() + "|~|" + obj.getAssetType() + "|~|" + obj.getAssetName() + "-"
					+ obj.getACondition() + "~|~";
		}
		return result;
	}

	@PostMapping("getassetlistbasedonbranchselect")
	@ResponseBody
	public String getassetlistbasedonbranchselect(@RequestParam("branchid") String branch) {
		String result = "<option value=''>-</option>";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll().stream()
				.filter(C -> String.valueOf(C.getBranch()).equalsIgnoreCase(branch)).collect(Collectors.toList());
		// ------------------------------------------------------------------------------------
		for (AssetMaster obj : AssetMasterobj) {
			result += "<option value='" + obj.getAssetId() + "'>" + obj.getAssetType() + "-" + obj.getAssetName() + "-"
					+ obj.getACondition() + "</option>";
		}
		return result;
	}

	@PostMapping("getassetlistbasedonbranchall")
	@ResponseBody
	public String getassetlistbasedonbranchall(@RequestParam("branchid") String branch) {
		String result = "";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		// ------------------------------------------------------------------------------------
		for (AssetMaster obj : AssetMasterobj) {
			result += obj.getAssetId() + "|~|" + obj.getAssetType() + "|~|" + obj.getAssetName() + "-"
					+ obj.getACondition() + "-" + obj.getStatus() + "~|~";
		}
		return result;
	}

	@PostMapping("getassetlistbasedonbranchallinstock")
	@ResponseBody
	public String getassetlistbasedonbranchallinstock(@RequestParam("branchid") String branch) {
		String result = "";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll().stream()
				.filter(C -> nullremover(String.valueOf(C.getStatus())).equalsIgnoreCase("In Stock"))
				.collect(Collectors.toList());
		// ------------------------------------------------------------------------------------
		for (AssetMaster obj : AssetMasterobj) {
			result += obj.getAssetId() + "|~|" + obj.getAssetType() + "|~|" + obj.getAssetName() + "-"
					+ obj.getACondition() + "-" + obj.getStatus() + "~|~";
		}
		return result;
	}

	@PostMapping("getassetlistbasedonbranchallstaff")
	@ResponseBody
	public String getassetlistbasedonbranchallstaff(@RequestParam("StaffID") String StaffID) {
		String result = "";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll().stream()
				.filter(C -> String.valueOf(C.getStaffID()).equalsIgnoreCase(StaffID)).collect(Collectors.toList());
		// ------------------------------------------------------------------------------------
		for (AssetMaster obj : AssetMasterobj) {
			result += obj.getAssetId() + "|~|" + obj.getAssetType() + "|~|" + obj.getAssetName() + "-"
					+ obj.getACondition() + "-" + obj.getStatus() + "~|~";
		}
		return result;
	}

	@PostMapping("getEmployeelistbasedonbranch")
	@ResponseBody
	public String getEmployeelistbasedonbranch(@RequestParam("branchname") String branch) {
		List<EmployeeMaster> emls = employeeMasterService.findAll();
		String result = "<option value=''>-</option>";

		// ------------------------------------------------------------------------------------
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateforeffectemp = new Date();
		// Find out Effective location Employee filter with selected branch
		for (EmployeeMaster obj : emls) {
			List<EmployeeJobinfo> infoobj = new ArrayList<>();
			infoobj = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());

			if (infoobj.size() > 0) {
				List<EmployeeJobinfo> infoobjgreen = infoobj.stream().filter(
						c -> dateFormat.format(dateforeffectemp).compareTo(c.getJobeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				infoobjgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));

				if (infoobjgreen.size() > 0) {
					if (infoobjgreen.get(infoobjgreen.size() - 1).getJoblocation().equalsIgnoreCase(branch)) {

						if (!calculateTerminatedstatus(obj.getEmpMasterid(), dateforeffectemp)) {
							result += "<option value='" + obj.getEmpMasterid() + "'>" + obj.getStaffName() + " (RVS000"
									+ obj.getEmpMasterid() + ")</option>";
						}
					}
				}
			}
		}

		// ------------------------------------------------------------------------------------

		return result;
	}

	public List<EmployeeMaster> getEmployeelistbasedonbranchbyid(@RequestParam("branchname") String branch) {
		List<EmployeeMaster> emls = employeeMasterService.findAll();

		List<EmployeeMaster> result = new ArrayList();

		// ------------------------------------------------------------------------------------
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateforeffectemp = new Date();
		// Find out Effective location Employee filter with selected branch
		for (EmployeeMaster obj : emls) {
			List<EmployeeJobinfo> infoobj = new ArrayList<>();
			infoobj = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());

			if (infoobj.size() > 0) {
				List<EmployeeJobinfo> infoobjgreen = infoobj.stream().filter(
						c -> dateFormat.format(dateforeffectemp).compareTo(c.getJobeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				infoobjgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));

				if (infoobjgreen.size() > 0) {
					if (infoobjgreen.get(infoobjgreen.size() - 1).getJoblocation().equalsIgnoreCase(branch)) {

						if (!calculateTerminatedstatus(obj.getEmpMasterid(), dateforeffectemp)) {
							result.add(obj);
						}
					}
				}
			}
		}

		// ------------------------------------------------------------------------------------

		return result;
	}

	@PostMapping("checkoutsave")
	public String checkoutsave(Model themodel, @RequestParam(name = "Branch") String BranchID,
			@RequestParam(name = "StaffID") String StaffID, @RequestParam(name = "CheckOutDate") String CheckOutDate,
			@RequestParam(name = "vendor") String vendor, @RequestParam(name = "Status") String Status,
			@RequestParam(name = "WhichLocation") String WhichLocation,

			@RequestParam(name = "ACondition") String[] ACondition,
			@RequestParam(name = "AssetName") String[] assetypeinstcokitem,
			@RequestParam(name = "Comments") String[] Comments,
			@RequestParam(name = "Photo_Attach") MultipartFile[] Photo_Attach, HttpSession session,
			HttpServletRequest request) {

		// -----------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("checkoutphoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		// -----------------------------------------
		List<CheckOut> objList = new ArrayList<CheckOut>();
		String sysdate = displaydatetimeFormat.format(new Date());
		for (int i = 0; i < assetypeinstcokitem.length; i++) {
			CheckOut obj = new CheckOut();
			obj.setBranchID(BranchID);
			obj.setStaffID(StaffID);
			obj.setCheckOutDate(CheckOutDate);
			obj.setVendor(vendor);
			obj.setStatus(Status);
			obj.setWhichLocation(WhichLocation);

			if (ACondition.length > 0) {
				obj.setAcondition(ACondition[i]);
			}
			obj.setAssetId(assetypeinstcokitem[i]);
			obj.setSysdate(sysdate);

			if (Comments.length > 0) {
				obj.setComments(Comments[i]);
			}

			// -----------------------------------------------------
			if (Photo_Attach.length > 0) {
				if (Photo_Attach[i].getOriginalFilename().toString().length() > 0) {
					List<CheckOutFiles> checkMasterfiles = new ArrayList<CheckOutFiles>();
					CheckOutFiles chekinfiles = new CheckOutFiles();
					StringBuilder filename = new StringBuilder();
					String tempfilename = stringdatetime() + Photo_Attach[i].getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append(tempfilename);
					chekinfiles.setPhoto_Attach("checkoutphoto/" + filename);
					try {
						Files.write(fileNameandPath, Photo_Attach[i].getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					checkMasterfiles.add(chekinfiles);
					obj.setCheckoutFiles(checkMasterfiles);
				}
			}
			// -----------------------------------------------------

			objList.add(obj);
			assetMasterService.updatetheAssetStatus(Status, Integer.parseInt(assetypeinstcokitem[i]), sysdate, StaffID);
		}
		ArrayList<String> printstr = new ArrayList<String>();

		List<CheckOut> CheckOutobj = checkoutService.saveall(objList);
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		List<EmployeeMaster> EmployeeMasterobj = employeeMasterService.findAll();

		for (CheckOut obj : CheckOutobj) {
			String str = "";

			int staffid = Integer.parseInt(obj.getStaffID());
			int assetid = Integer.parseInt(obj.getAssetId());
			AssetMaster assobj = AssetMasterobj.stream().filter(C -> C.getAssetId() == assetid)
					.collect(Collectors.toList()).get(0);

			EmployeeMaster empobj = EmployeeMasterobj.stream().filter(C -> C.getEmpMasterid() == staffid)
					.collect(Collectors.toList()).get(0);

			String tempstr = obj.getComments();
			if (obj.getComments() == null) {
				tempstr = "";
			}

			str += empobj.getStaffName() + " |";
			str += assobj.getAssetName() + " |";
			str += assobj.getBrand() + " |";
			str += assobj.getModel() + " |";
			str += assobj.getSerialNumber() + " |";
			str += assobj.getACondition() + " |";
			str += tempstr + " |";

			printstr.add(str);
		}

		List<AssetMaster> AssetMasterobj1 = assetMasterService.findAll().stream()
				.filter(C -> nullremover(String.valueOf(C.getStatus())).equalsIgnoreCase("In Stock"))
				.collect(Collectors.toList());

		themodel.addAttribute("printstr", printstr);
		request.getSession().setAttribute("printcheckoutstr", printstr);
		themodel.addAttribute("AssetMasterobj", AssetMasterobj1);

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Check Out"));

		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		return "checkout";
	}

	@PostMapping("checkoutprint")
	public String checkoutprint(Model themodel, HttpSession session, HttpServletRequest request) {

		ArrayList<String> printstr = (ArrayList<String>) request.getSession().getAttribute("printcheckoutstr");
		String temp[] = String.valueOf(printstr.get(0)).split("\\|");

		themodel.addAttribute("staffname", temp[0]);
		themodel.addAttribute("printstr", printstr);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Check Out"));
		return "checkoutprint";
	}

	@GetMapping("checkin")
	public String checkin(Model themodel, @RequestParam(name = "id", required = false, defaultValue = "") String ids) {

		List<AssetMaster> assetMaster = assetMasterService.findAll();
		List<AssetMaster> selectedasset = new ArrayList<AssetMaster>();
		if (!ids.equalsIgnoreCase("")) {
			String[] strarr = ids.split(",");

			for (String ai : strarr) {
				selectedasset.add(assetMaster.stream().filter(C -> C.getAssetId() == Integer.parseInt(ai))
						.collect(Collectors.toList()).get(0));
			}
		}

		EmployeeMaster emp = new EmployeeMaster();

		if (selectedasset.size() > 0) {
			emp = employeeMasterService.findById(Integer.parseInt(selectedasset.get(0).getStaffID()));

		}
		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("emp", emp);
		themodel.addAttribute("selectedasset", selectedasset);
		themodel.addAttribute("today", displaydateFormatrev.format(new Date()));
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Check In"));
		return "checkin";
	}

	@ResponseBody
	@PostMapping("getCheckoutlistfromassetMaster")
	public List<Map<String, Object>> getCheckoutlistfromassetMaster(@RequestParam("StaffID") String StaffID) {
		List<AssetMaster> amls = assetMasterService.findbyStaffID(StaffID);
		List<Map<String, Object>> response = new ArrayList<>();

		for (AssetMaster am : amls) {
			Map<String, Object> item = new HashMap<>();
			item.put("assetId", am.getAssetId());
			item.put("assetName", am.getAssetName());
			item.put("aCondition", am.getACondition());
			item.put("status", "In Stock");
			response.add(item);
		}

		return response;
	}

	@PostMapping("checkinsave")
	public String checkinsave(Model themodel, @RequestParam(name = "StaffID") String StaffID,
			@RequestParam(name = "CheckInDate") String CheckInDate,
			@RequestParam(name = "checkbox", required = false) boolean[] checkbox,
			@RequestParam(name = "AssetName") String[] AssetId, @RequestParam(name = "Status") String[] Status,
			@RequestParam(name = "ACondition") String[] Condition, @RequestParam(name = "Comments") String[] Comments,
			@RequestParam(name = "Photo_Attach") MultipartFile[] Photo_Attach, HttpSession session,
			HttpServletRequest request) {

		List<CheckIn> objList = new ArrayList<CheckIn>();

		// -----------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("checkinphoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		// -----------------------------------------
		String sysdate = displaydatetimeFormat.format(new Date());
		for (int i = 0; i < AssetId.length; i++) {
			if (checkbox[i] == true) {

				CheckIn obj = new CheckIn();
				obj.setAssetId(AssetId[i]);
				obj.setStaffID(StaffID);
				obj.setCheckInDate(CheckInDate);
				obj.setStatus(Status[i]);
				obj.setACondition(Condition[i]);

				if (Comments.length > 0) {
					obj.setComments(Comments[i]);
				}
				assetMasterService.updatetheAssetStatus(Status[i], Integer.parseInt(AssetId[i]), sysdate, "");

				if (Photo_Attach.length > 0) {
					if (Photo_Attach[i].getOriginalFilename().toString().length() > 0) {
						List<CheckInFiles> checkMasterfiles = new ArrayList<CheckInFiles>();
						CheckInFiles chekinfiles = new CheckInFiles();
						StringBuilder filename = new StringBuilder();
						String tempfilename = stringdatetime() + Photo_Attach[i].getOriginalFilename();
						Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
						filename.append(tempfilename);
						chekinfiles.setPhoto_Attach("checkinphoto/" + filename);
						try {
							Files.write(fileNameandPath, Photo_Attach[i].getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
						checkMasterfiles.add(chekinfiles);
						obj.setCheckInFiles(checkMasterfiles);
					}
				}

				objList.add(obj);
			}
		}
		ArrayList<String> printstr = new ArrayList<String>();

		List<CheckIn> CheckInobj = checkinService.saveall(objList);
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		List<EmployeeMaster> EmployeeMasterobj = employeeMasterService.findAll();
		String Empname = "";
		for (CheckIn obj : CheckInobj) {
			String str = "";

			int staffid = Integer.parseInt(obj.getStaffID());
			int assetid = Integer.parseInt(obj.getAssetId());
			AssetMaster assobj = AssetMasterobj.stream().filter(C -> C.getAssetId() == assetid)
					.collect(Collectors.toList()).get(0);

			EmployeeMaster empobj = EmployeeMasterobj.stream().filter(C -> C.getEmpMasterid() == staffid)
					.collect(Collectors.toList()).get(0);

			String tempstr = obj.getComments();
			if (obj.getComments() == null) {
				tempstr = "";
			}

			str += assobj.getAssetName() + " |";
			str += assobj.getBrand() + " |";
			str += assobj.getModel() + " |";
			str += assobj.getSerialNumber() + " |";
			str += obj.getACondition() + " |";
			str += tempstr + " |";

			printstr.add(str);
			Empname = empobj.getStaffName();
		}

		List<AssetMaster> AssetMasterobj1 = assetMasterService.findAll().stream()
				.filter(C -> !(nullremover(String.valueOf(C.getStatus())).equalsIgnoreCase("In Stock")))
				.collect(Collectors.toList());

		themodel.addAttribute("printstr", printstr);
		request.getSession().setAttribute("printcheckinstrEmpname", Empname);
		request.getSession().setAttribute("printcheckinstr", printstr);

		themodel.addAttribute("AssetMasterobj", AssetMasterobj1);
		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Check In"));
		return "checkin";
	}

	@PostMapping("checkinprint")
	public String checkinprint(Model themodel, HttpSession session, HttpServletRequest request) {

		ArrayList<String> printstr = (ArrayList<String>) request.getSession().getAttribute("printcheckinstr");
		String printcheckinstrEmpname = (String) request.getSession().getAttribute("printcheckinstrEmpname");

		themodel.addAttribute("printstr", printstr);
		themodel.addAttribute("printcheckinstrEmpname", printcheckinstrEmpname);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("asset"));
		return "checkinprint";
	}

	@GetMapping("assetaudit")
	public String assetaudit(Model themodel) {

		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		List<EmployeeMaster> EmployeeMasterobj = EffectiveEmployee(employeeMasterService.findAll());

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("asset"));
		themodel.addAttribute("AssetMasterobj", AssetMasterobj);
		themodel.addAttribute("EmployeeMasterobj", EmployeeMasterobj);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("assetaudit"));
		return "assetaudit";
	}

	@PostMapping("assetauditsave")
	public String assetauditsave(Model themodel, @RequestParam(name = "StaffID") String[] StaffID,
			@RequestParam(name = "AssetAuditDate") String[] AssetAuditDate,
			@RequestParam(name = "AssetId") String[] AssetId, @RequestParam(name = "Statusx") String[] Status,
			@RequestParam(name = "aCondition") String[] Condition, @RequestParam(name = "Comments") String[] Comments,
			@RequestParam(name = "Photo_Attach") MultipartFile[] Photo_Attach, HttpSession session,
			HttpServletRequest request) {

		List<AssetAudit> objList = new ArrayList<AssetAudit>();

		// -----------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("assetauditphoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		// -----------------------------------------
		String sysdate = displaydatetimeFormat.format(new Date());
		for (int i = 0; i < StaffID.length; i++) {
			AssetAudit obj = new AssetAudit();
			obj.setAssetId(AssetId[i]);
			obj.setStaffID(StaffID[i]);
			obj.setAssetAuditDate(AssetAuditDate[i]);
			obj.setStatus(Status[i]);
			obj.setACondition(Condition[i]);
			if (Comments.length > 0) {
				obj.setComments(Comments[i]);
			}
			assetMasterService.updatetheAssetStatus(Status[i], Integer.parseInt(AssetId[i]), sysdate, StaffID[i],
					Condition[i]);

			if (Photo_Attach.length > 0) {
				if (Photo_Attach[i].getOriginalFilename().toString().length() > 0) {
					List<AssetAuditFiles> assetauditMasterfiles = new ArrayList<AssetAuditFiles>();
					AssetAuditFiles assetauditfiles = new AssetAuditFiles();
					StringBuilder filename = new StringBuilder();
					String tempfilename = stringdatetime() + Photo_Attach[i].getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append(tempfilename);
					assetauditfiles.setPhoto_Attach("assetauditphoto/" + filename);
					try {
						Files.write(fileNameandPath, Photo_Attach[i].getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					assetauditMasterfiles.add(assetauditfiles);
					obj.setAssetauditFiles(assetauditMasterfiles);
				}
			}

			objList.add(obj);

		}
		ArrayList<String> printstr = new ArrayList<String>();

		List<AssetAudit> AssetAuditobj = assetauditService.saveall(objList);
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		List<EmployeeMaster> EmployeeMasterobj = employeeMasterService.findAll();

		for (AssetAudit obj : AssetAuditobj) {
			String str = "";

			int assetid = Integer.parseInt(obj.getAssetId());
			AssetMaster assobj = AssetMasterobj.stream().filter(C -> C.getAssetId() == assetid)
					.collect(Collectors.toList()).get(0);

			if (!obj.getStaffID().equalsIgnoreCase("null")) {
				int staffid = Integer.parseInt(obj.getStaffID());
				EmployeeMaster empobj = EmployeeMasterobj.stream().filter(C -> C.getEmpMasterid() == staffid)
						.collect(Collectors.toList()).get(0);
				str += empobj.getStaffName() + " |";
			} else {
				str += " |";
			}

			str += assobj.getAssetName() + " |";
			str += assobj.getBrand() + " |";
			str += assobj.getModel() + " |";
			str += assobj.getSerialNumber() + " |";
			str += obj.getACondition() + " |";
			str += obj.getComments() + " |";

			printstr.add(str);
		}
		themodel.addAttribute("printstr", printstr);

		request.getSession().setAttribute("printassetauditstr", printstr);

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("AssetMasterobj", AssetMasterobj);
		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("assetaudit"));
		return "assetaudit";
	}

	@GetMapping("insurancelist")
	public String insurancelist(Model theModel) {

		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Insurance"));

		return "insurancelist";

	}

	@ResponseBody
	@GetMapping("insurancelistjson")
	public List<InsuranceMaster> insurancelistjson() {

		List<InsuranceMaster> lnsurancels = insuranceMasterService.findAll();

		List<InsuranceMaster> lnsurancels_temp = new ArrayList();

		for (InsuranceMaster obj : lnsurancels) {

			if (obj.getInsuranceTo().equalsIgnoreCase("Asset")) {

				AssetMaster asset = assetMasterService.findById(Integer.parseInt(obj.getAssetNameID()));
				obj.setStaffassetname(asset.getAssetName().toString());

			} else {
				EmployeeMaster employee = employeeMasterService.findById(Integer.parseInt(obj.getStaffID()));
				obj.setStaffassetname(employee.getStaffName().toString());

			}

			for (InsuranceDetails objindetail : obj.getInsuranceDetails()) {

				VendorMaster vendor = vendorMasterService.findById(Integer.parseInt(objindetail.getVendorName()));
				objindetail.setVendorNamestr(vendor.getName());

				if (!String.valueOf(objindetail.getPTo()).equalsIgnoreCase("")) {
					try {
						objindetail.setDuedateformate(displaydateFormatFirstMMMddYYY
								.format(new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo())));

						long differ_in_time = new Date().getTime()
								- new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo()).getTime();

						objindetail.setDueindicatorcolor(insuranetimecolor((differ_in_time) / (1000 * 60 * 60 * 24)));

					} catch (ParseException e) {
					}
				} else {
					objindetail.setDuedateformate("");
					objindetail.setDueindicatorcolor("");

				}

			}

			lnsurancels_temp.add(obj);
		}

		return lnsurancels_temp;
	}

	public String insuranetimecolor(long timr) {
		String color = "";

		if (timr <= -45) {
			color = "G";
		} else if (timr < 0 && timr > -45) {
			color = "Y";
		} else if (timr >= 0) {
			color = "R";
		}

		return color;
	}

	@ResponseBody
	@PostMapping("insuranceclaimdetails")
	public List<InsuranceClaimHistory> insuranceclaimdetails(Model themodel, @RequestParam("id") int id) {

		List<InsuranceClaimHistory> ls = insuranceMasterService.findById(id).getInsuranceClaimHistory();

		for (InsuranceClaimHistory obj : ls) {
			try {
				obj.setClaimDateMMMddYYYFormate(displaydateFormatFirstMMMddYYY
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getClaimDate())));

			} catch (ParseException e) {

			}
		}

		return ls;

	}

	@GetMapping("insurance")
	public String insurancedetails(Model themodel, @RequestParam("id") int id) {

		InsuranceMaster insurancemasternew = new InsuranceMaster();
		insurancemasternew = insuranceMasterService.findById(id);

		List<VendorMaster> vm = vendorMasterService.findAll();

		if (!insurancemasternew.getStaffID().equalsIgnoreCase("")) {
			EmployeeMaster em = employeeMasterService.findById(Integer.parseInt(insurancemasternew.getStaffID()));
			themodel.addAttribute("em", em);
			themodel.addAttribute("ememgcontact", em.getEmployeeEmgContact());

		}
		if (!insurancemasternew.getAssetNameID().equalsIgnoreCase("")) {
			AssetMaster am = assetMasterService.findById(Integer.parseInt(insurancemasternew.getAssetNameID()));
			themodel.addAttribute("am", am);
		}

		themodel.addAttribute("insurancemaster", insurancemasternew);
		themodel.addAttribute("vm", vm);

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		List<String> PolicyCover = itemlistService.findByFieldName("PolicyCover");
		themodel.addAttribute("PolicyCover", PolicyCover);

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Insurance"));

		return "insurance";
	}

	@ResponseBody
	@PostMapping("getpolicydetailslist")
	public List<InsuranceDetails> getpolicydetailslist(@RequestParam("id") int id) {

		InsuranceMaster insurancemasternew = insuranceMasterService.findById(id);

		for (InsuranceDetails objindetail : insurancemasternew.getInsuranceDetails()) {

			VendorMaster vendor = vendorMasterService.findById(Integer.parseInt(objindetail.getVendorName()));
			objindetail.setVendorNamestr(vendor.getName());

			if (insurancemasternew.getInsuranceTo().equalsIgnoreCase("Staff")) {
				EmployeeMaster employee = employeeMasterService.findById(Integer.parseInt(objindetail.getNominee()));
				objindetail.setNominee_name_str(employee.getStaffName().toString());
			} else {
				objindetail.setNominee_name_str("");
			}
			try {
				objindetail.setPFrom_str(displaydateFormatFirstMMMddYYY
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPFrom())));
				objindetail.setPTo_str(displaydateFormatFirstMMMddYYY
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo())));

			} catch (ParseException e) {

			}

		}
		return insurancemasternew.getInsuranceDetails();

	}

	@ResponseBody
	@PostMapping("getpolicydetails")
	public InsuranceDetails getpolicydetails(@RequestParam("id") int id, @RequestParam("policyid") int policyid) {

		InsuranceMaster insurancemasternew = insuranceMasterService.findById(id);
		return insurancemasternew.getInsuranceDetails().stream().filter(C -> C.getInsuranceDetailsid() == policyid)
				.collect(Collectors.toList()).get(0);
	}

	@ResponseBody
	@PostMapping("getclaimpolicydetails")
	public InsuranceClaimHistory getclaimpolicydetails(@RequestParam("id") int id,
			@RequestParam("claimid") int claimid) {

		InsuranceMaster insurancemasternew = insuranceMasterService.findById(id);
		return insurancemasternew.getInsuranceClaimHistory().stream().filter(C -> C.getInsuranceClaimid() == claimid)
				.collect(Collectors.toList()).get(0);
	}

	@ResponseBody
	@PostMapping("policydetailsavejson")
	public InsuranceMaster policydetailsavejson(@RequestParam Map<String, String> params,
			@RequestParam(name = "File_Attach", required = false) MultipartFile Files_Attach,
			HttpServletRequest request) {

		// System.out.println(Files_Attach.getOriginalFilename().toString());
		// System.out.println(params);
		InsuranceMaster insurancemasternew = insuranceMasterService
				.findById(Integer.parseInt(params.get("Insuranceid")));

		StringBuilder filename = new StringBuilder();
		if (Files_Attach != null) {
			// File Uploading
			String profilephotouploadRootPath = request.getServletContext().getRealPath("insurancepolicy");
			// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

			File uploadRootDir = new File(profilephotouploadRootPath);
			// Create directory if it not exists.
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}

			if (Files_Attach.getOriginalFilename().toString().length() > 0) {

				String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
				Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
				filename.append("insurancepolicy/" + tempfilename);

				try {
					Files.write(fileNameandPath, Files_Attach.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (nullremover(String.valueOf(params.get("InsuranceDetailsid"))).equalsIgnoreCase("")) {

			InsuranceDetails insuDetails = new InsuranceDetails();

			insuDetails.setCover(params.get("Cover"));
			insuDetails.setCoverageAmount(params.get("CoverageAmount"));
			insuDetails.setDoc_Attach(filename.toString());
			insuDetails.setNominee(params.get("Nominee"));
			insuDetails.setNotes(params.get("Notes"));
			insuDetails.setPFrom(params.get("PFrom"));
			insuDetails.setPTo(params.get("PTo"));
			insuDetails.setStatus(params.get("status"));
			insuDetails.setVendorName(params.get("VendorName"));
			insuDetails.setPolicyName(params.get("PolicyName"));
			insuDetails.setPolicyNo(params.get("PolicyNo"));
			insuDetails.setPremium(params.get("Premium"));
			insurancemasternew.getInsuranceDetails().add(insuDetails);
		} else {
			for (InsuranceDetails insuDetails : insurancemasternew.getInsuranceDetails()) {
				if (insuDetails.getInsuranceDetailsid() == Integer.parseInt(params.get("InsuranceDetailsid"))) {

					insuDetails.setCover(params.get("Cover"));
					insuDetails.setCoverageAmount(params.get("CoverageAmount"));
					if (!nullremover(String.valueOf(filename.toString())).equalsIgnoreCase("")) {
						insuDetails.setDoc_Attach(filename.toString());
					}

					insuDetails.setNominee(params.get("Nominee"));
					insuDetails.setNotes(params.get("Notes"));
					insuDetails.setPFrom(params.get("PFrom"));
					insuDetails.setPTo(params.get("PTo"));
					insuDetails.setStatus(params.get("status"));
					insuDetails.setVendorName(params.get("VendorName"));
					insuDetails.setPolicyName(params.get("PolicyName"));
					insuDetails.setPolicyNo(params.get("PolicyNo"));
					insuDetails.setPremium(params.get("Premium"));
				}
			}
		}

		return insuranceMasterService.save(insurancemasternew);

	}

	@ResponseBody
	@PostMapping("policyClaimdetailsavejson")
	public InsuranceMaster policyClaimdetailsavejson(@RequestParam Map<String, String> params) {

		// System.out.println(params);

		InsuranceMaster insurancemasternew = insuranceMasterService
				.findById(Integer.parseInt(params.get("Insuranceid")));

		if (nullremover(String.valueOf(params.get("InsuranceClaimDetailsid"))).equalsIgnoreCase("")) {

			InsuranceClaimHistory insuDetails = new InsuranceClaimHistory();

			insuDetails.setClaimAmount(params.get("claimAmount"));
			insuDetails.setClaimDate(params.get("claimDate"));
			insuDetails.setPolicyNo(params.get("policyNo"));
			insuDetails.setReason(params.get("reason"));
			insurancemasternew.getInsuranceClaimHistory().add(insuDetails);

		} else {
			for (InsuranceClaimHistory insuDetails : insurancemasternew.getInsuranceClaimHistory()) {
				if (insuDetails.getInsuranceClaimid() == Integer.parseInt(params.get("InsuranceClaimDetailsid"))) {

					insuDetails.setClaimAmount(params.get("claimAmount"));
					insuDetails.setClaimDate(params.get("claimDate"));
					insuDetails.setPolicyNo(params.get("policyNo"));
					insuDetails.setReason(params.get("reason"));

				}
			}
		}

		return insuranceMasterService.save(insurancemasternew);

	}

	@PostMapping("insurancesave")
	public String Assetsave(HttpServletRequest req, Model themodel,
			@ModelAttribute("insurancemaster") InsuranceMaster insurancemaster, HttpServletRequest request,
			@RequestParam(name = "doc_Attach") MultipartFile[] doc_Attach) {

		List<InsuranceDetails> Insrls = insurancemaster.getInsuranceDetails().stream()
				.filter(C -> !String.valueOf(C.getPolicyName()).equalsIgnoreCase("null")).collect(Collectors.toList());

		for (InsuranceDetails o : Insrls) {
			itemlistService.savesingletxt(o.getCover(), "PolicyCover");
		}
		// -----------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("insurancephoto");

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		// -----------------------------------------

		if (doc_Attach.length > 0) {
			for (int i = 0; i < doc_Attach.length; i++) {
				if (doc_Attach[i].getOriginalFilename().toString().length() > 0) {

					StringBuilder filename = new StringBuilder();
					String tempfilename = stringdatetime() + doc_Attach[i].getOriginalFilename();
					Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
					filename.append(tempfilename);
					Insrls.get(i).setDoc_Attach("insurancephoto/" + filename);
					try {
						Files.write(fileNameandPath, doc_Attach[i].getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		insurancemaster.setInsuranceDetails(Insrls);
		InsuranceMaster insurancemasternew = new InsuranceMaster();
		insurancemasternew = insuranceMasterService.save(insurancemaster);
		List<VendorMaster> vm = vendorMasterService.findAll();

		if (!insurancemaster.getStaffID().equalsIgnoreCase("")) {
			EmployeeMaster em = employeeMasterService.findById(Integer.parseInt(insurancemaster.getStaffID()));
			themodel.addAttribute("em", em);
			themodel.addAttribute("ememgcontact", em.getEmployeeEmgContact());
		}
		if (!insurancemaster.getAssetNameID().equalsIgnoreCase("")) {
			AssetMaster am = assetMasterService.findById(Integer.parseInt(insurancemaster.getAssetNameID()));
			themodel.addAttribute("am", am);
		}

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("vm", vm);
		themodel.addAttribute("save", true);
		themodel.addAttribute("insurancemaster", insurancemasternew);

		List<String> PolicyCover = itemlistService.findByFieldName("PolicyCover");
		themodel.addAttribute("PolicyCover", PolicyCover);

		return "insurance";
	}

	@ResponseBody
	@PostMapping("employeenomineedetails")
	public String employeeattendancesave(@RequestParam("staffid") int staffid) {

		EmployeeMaster employee = employeeMasterService.findById(staffid);
		String output = "<option value=''>-</option>";
		for (EmployeeEmgContact emglsnew : employee.getEmployeeEmgContact()) {
			output = output + "<option value='" + emglsnew.getEmpEmgContactid() + "'>" + emglsnew.getEmg_Name() + " ("
					+ emglsnew.getEmg_Relation() + ")</option>";
		}

		return output;
	}

	@GetMapping("contactspersonlist")
	public String contactspersonlist(Model themodel) {

		List<BranchMaster> branchls = branchMasterService.findAll();
		themodel.addAttribute("branchlist", branchls);

		List<EmployeeMaster> empls = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("empls", empls);

		List<OrganizationContacts> orgList = contactOrganizationService.findAll();
		themodel.addAttribute("orgList", orgList);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("contact_People"));
		return "contactpersonlist";
	}

	@GetMapping("contactspersonadd")
	public String contactspersonadd(Model themodel) {

		ContactPerson contactperson = new ContactPerson();
		themodel.addAttribute("contactperson", contactperson);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("organizationlist", contactOrganizationService.findAll());
		List<String> TYPEOFINDUSTRY = itemlistService.findByFieldName("TYPEOFINDUSTRY");
		themodel.addAttribute("TYPEOFINDUSTRY", TYPEOFINDUSTRY);

		List<String> MEMBERIN = itemlistService.findByFieldName("MEMBERIN");
		themodel.addAttribute("MEMBERIN", MEMBERIN);
		return "contactpersonadd";
	}

	@GetMapping("contactpersonview")
	public String contactpersonview(Model theModel, @RequestParam("id") int id) {

		List<ContactPerson> cplist = contactPersonService.findAll();

		ContactPerson cp = ContactPersonobjectfiller(contactPersonService.findById(id));
		// Get Primary contact
		List<ContactPersonContact> branchContactls = cp.getContactPersonContact().stream()
				.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
		if (branchContactls.size() == 0) {
			theModel.addAttribute("primaryContact", false);
		} else {
			theModel.addAttribute("primaryContact", true);
		}

		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);
		List<String> MEMBERIN = itemlistService.findByFieldName("MEMBERIN");
		theModel.addAttribute("MEMBERIN", MEMBERIN);

		theModel.addAttribute("ContactPerson", cp);
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("contact_People"));
		// ---------------------------

		// ---------------------------
		return "contactpersonadd";
	}

	@ResponseBody
	@GetMapping("organizationNameList")
	public List<String> organizationList() {
		List<String> orglist = new ArrayList();
		for (OrganizationContacts o : contactOrganizationService.findAll()) {
			orglist.add(o.getOrgname());
		}
		return orglist;
	}

	@ResponseBody
	@GetMapping("organizationNameListtype1")
	public List<tagify> organizationNameListtype1() {
		List<tagify> taglist = new ArrayList<tagify>();

		for (OrganizationContacts o : contactOrganizationService.findAll()) {
			tagify tagobj = new tagify();
			tagobj.setValue(o.getOrgname());
			tagobj.setCode(String.valueOf(o.getId()));
			taglist.add(tagobj);
		}
		return taglist;
	}

	public ContactPerson ContactPersonobjectfiller(ContactPerson cp) {
		if (cp.getContactPersonAccNo().size() == 0) {
			List<ContactPersonAccNo> ContactPersonAccNols = new ArrayList();
			ContactPersonAccNols.add(new ContactPersonAccNo());
			cp.setContactPersonAccNo(ContactPersonAccNols);
			cp = contactPersonService.save(cp);
		}

		// ---------------------------------------
		// branch name
		cp.setBranchName(branchMasterService.findById(cp.getBranchid()).getBRANCH_NAME());

		if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
			// Organization Name
			cp.setOrganizationname(
					contactOrganizationService.findById(Integer.parseInt(cp.getOrganization())).getOrgname());
		}
		if (nullremover(String.valueOf(cp.getDesignation())).equalsIgnoreCase("")) {
			cp.setDesignation("");
		}
		// Set primary contact
		List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
				.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
		if (bcls.size() > 0) {
			cp.setPrimarymob(bcls.get(0).getPhonenumber());
			cp.setPrimaryemail(bcls.get(0).getEmail());
		}
		// -------------------------------------------
		if (!nullremover(String.valueOf(cp.getFollowers())).equalsIgnoreCase("")) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()));
			cp.setFollowerimg(getemp_photo(empobj));
			cp.setFollowername(empobj.getStaffName());
		}
		// Set primary contact
		if (!nullremover(String.valueOf(cp.getFollowers())).equalsIgnoreCase("")) {
			List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(cp.getFollowers()))
					.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (ecls.size() > 0) {
				cp.setFollowerprimarymob(ecls.get(0).getPhonenumber());

			}
		}
		// -------------------------------------------
		return cp;
	}

	@ResponseBody
	@PostMapping("contactPersonupdatejson")
	public ContactPerson contactPersonupdatejson(@RequestParam Map<String, String> params) {
		int cpid = Integer.parseInt(params.get("ContactPersonID"));
		ContactPerson cp = contactPersonService.findById(cpid);

		String organization = params.get("organization").replace("[{\"value\":\"", "").replace("\"}]", "");

		cp.setBranchid(Integer.parseInt(params.get("Model_branchid")));
		cp.setPeoplename(params.get("peoplename"));
		cp.setCustomer_supplier(params.get("customer_supplier"));
		cp.setFollowers(params.get("Relationmanger"));
		cp.setDesignation(params.get("designation"));
		cp.setMemberin(params.get("memberin"));

		if (organization != null && (!nullremover(String.valueOf(params.get("organization"))).equalsIgnoreCase(""))) {

			List<OrganizationContacts> conOrgls = contactOrganizationService.findbyOrgname(organization);
			if (conOrgls.size() > 0) {
				cp.setOrganization(String.valueOf(conOrgls.get(0).getId()));
			} else {
				OrganizationContacts contactOrganization = new OrganizationContacts();
				contactOrganization.setOrgname(organization);
				contactOrganization.setBranchid(Integer.parseInt(params.get("Model_branchid")));
				contactOrganization.setCustomer_supplier(params.get("customer_supplier"));
				contactOrganization.setFollowers(params.get("Relationmanger"));
				contactOrganization = contactOrganizationService.save(contactOrganization);
				cp.setOrganization(String.valueOf(contactOrganization.getId()));
			}
		} else {
			cp.setOrganization("");
		}
		cp = contactPersonService.save(cp);
		return ContactPersonobjectfiller(cp);

	}

	@ResponseBody
	@PostMapping("makeprimarycontactperson")
	public ContactPerson makeprimarycontactperson(@RequestParam Map<String, String> params) {

		ContactPerson cp = contactPersonService.findById(Integer.parseInt(params.get("personid")));
		cp.setPrimaryperson(Boolean.parseBoolean(params.get("primarystatus")));
		cp = contactPersonService.save(cp);
		return cp;
	}

	@ResponseBody
	@PostMapping("organizationupdatejson")
	public OrganizationContacts organizationupdatejson(@RequestParam Map<String, String> params) {
		int corgid = Integer.parseInt(params.get("OrganizationContactsID"));
		OrganizationContacts corg = contactOrganizationService.findById(corgid);
		corg.setBranchid(Integer.parseInt(params.get("Model_branchid")));
		corg.setOrgname(params.get("orgname"));
		corg.setCustomer_supplier(params.get("customer_supplier"));
		corg.setFollowers(params.get("Relationmanger"));
		corg.setWebsite(params.get("website"));
		corg.setIndustry_type(params.get("industry_type"));
		corg = contactOrganizationService.save(corg);

		itemlistService.savesingletxt(corg.getIndustry_type(), "industry_type");

		return OrganizationContactsobjectfiller(corg);

	}

	@ResponseBody
	@PostMapping("contactpersonsavejson")
	public int contactpersonsavejson(@RequestParam Map<String, String> params) {

		String organization = params.get("organization").replace("[{\"value\":\"", "").replace("\"}]", "");
		ContactPerson cp = new ContactPerson();
		cp.setBranchid(Integer.parseInt(params.get("Branch")));
		cp.setCustomer_supplier(params.get("customer_supplier"));
		cp.setFollowers(params.get("Relationmanger"));

		if (!nullremover(String.valueOf(params.get("peoplename"))).equalsIgnoreCase("")) {
			cp.setPeoplename(params.get("peoplename"));
			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(params.get("phonenumber"));
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
		}
		if (!nullremover(String.valueOf(organization)).equalsIgnoreCase("")) {
			List<OrganizationContacts> conOrgls = contactOrganizationService.findbyOrgname(organization);
			if (conOrgls.size() > 0) {
				cp.setOrganization(String.valueOf(conOrgls.get(0).getId()));
			} else {
				OrganizationContacts contactOrganization = new OrganizationContacts();
				contactOrganization.setOrgname(organization);
				contactOrganization.setBranchid(Integer.parseInt(params.get("Branch")));
				contactOrganization.setCustomer_supplier(params.get("customer_supplier"));
				contactOrganization.setFollowers(params.get("Relationmanger"));
				contactOrganization = contactOrganizationService.save(contactOrganization);

				cp.setOrganization(String.valueOf(contactOrganization.getId()));
			}
		} else {
			cp.setOrganization("");
		}
		return contactPersonService.save(cp).getId();

	}

	@ResponseBody
	@PostMapping("contactorgsavejson")
	public int contactorgsavejson(@RequestParam Map<String, String> params) {

		String organization = params.get("organization").replace("[{\"value\":\"", "").replace("\"}]", "");
		OrganizationContacts contactOrganization = new OrganizationContacts();
		contactOrganization.setOrgname(organization);
		contactOrganization.setBranchid(Integer.parseInt(params.get("Branch")));
		contactOrganization.setCustomer_supplier(params.get("customer_supplier"));
		contactOrganization.setFollowers(params.get("Relationmanger"));
		contactOrganization = contactOrganizationService.save(contactOrganization);
		return contactOrganizationService.save(contactOrganization).getId();

	}

	@ResponseBody
	@PostMapping("orgcontactpersonsavejson")
	public int orgcontactpersonsavejson(@RequestParam Map<String, String> params) {

		OrganizationContacts organization = contactOrganizationService
				.findById(Integer.parseInt(params.get("OrganizationContactsID")));

		ContactPerson cp = new ContactPerson();
		cp.setBranchid(organization.getBranchid());
		cp.setOrganization(String.valueOf(organization.getId()));
		cp.setPeoplename(params.get("peoplename"));
		cp.setCustomer_supplier(organization.getCustomer_supplier());
		cp.setFollowers(organization.getFollowers());

		ContactPersonContact cpc = new ContactPersonContact();
		cpc.setDepartment("Personal");
		cpc.setPhonenumber(params.get("phonenumber"));
		cpc.setPrimarycontact(true);
		List<ContactPersonContact> cpcls = new ArrayList();
		cpcls.add(cpc);
		cp.setContactPersonContact(cpcls);

		return contactPersonService.save(cp).getId();

	}

	@ResponseBody
	@PostMapping("workcontactpersonsavejson")
	public int workcontactpersonsavejson(@RequestParam Map<String, String> params) {

		int insertedkey = 0;
		if (params.get("category").equalsIgnoreCase("Lead")) {
			ContactPerson cp = new ContactPerson();
			cp.setBranchid(Integer.parseInt(params.get("branch")));
			cp.setOrganization(String.valueOf(params.get("organization")));
			cp.setPeoplename(params.get("peoplename"));
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(params.get("phonenumber"));
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			int cpkey = contactPersonService.save(cp).getId();
			leadMasterService.insertContact(cpkey, Integer.parseInt(params.get("id")));
			insertedkey = cpkey;
		} else if (params.get("category").equalsIgnoreCase("Deal")) {
			ContactPerson cp = new ContactPerson();
			cp.setBranchid(Integer.parseInt(params.get("branch")));
			cp.setOrganization(String.valueOf(params.get("organization")));
			cp.setPeoplename(params.get("peoplename"));
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(params.get("phonenumber"));
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			int cpkey = contactPersonService.save(cp).getId();
			dealMasterService.insertContact(cpkey, Integer.parseInt(params.get("id")));
			insertedkey = cpkey;
		} else if (params.get("category").equalsIgnoreCase("Project")) {
			ContactPerson cp = new ContactPerson();
			cp.setBranchid(Integer.parseInt(params.get("branch")));
			cp.setOrganization(String.valueOf(params.get("organization")));
			cp.setPeoplename(params.get("peoplename"));
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(params.get("phonenumber"));
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			int cpkey = contactPersonService.save(cp).getId();
			projectMasterService.insertContact(cpkey, Integer.parseInt(params.get("id")));
			insertedkey = cpkey;
		}

		return insertedkey;

	}

	@ResponseBody
	@PostMapping("workcontactlinkpersonsavejson")
	public ContactPerson workcontactlinkpersonsavejson(@RequestParam Map<String, String> params) {

		ContactPerson cp = new ContactPerson();
		if (params.get("category").equalsIgnoreCase("Lead")) {
			cp = contactPersonService.findById(Integer.parseInt(params.get("linkpeoplename")));
			leadMasterService.insertContact(cp.getId(), Integer.parseInt(params.get("id")));

			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
		} else if (params.get("category").equalsIgnoreCase("Deal")) {
			cp = contactPersonService.findById(Integer.parseInt(params.get("linkpeoplename")));
			dealMasterService.insertContact(cp.getId(), Integer.parseInt(params.get("id")));

			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
		} else if (params.get("category").equalsIgnoreCase("Project")) {
			cp = contactPersonService.findById(Integer.parseInt(params.get("linkpeoplename")));
			projectMasterService.insertContact(cp.getId(), Integer.parseInt(params.get("id")));

			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
		}
		return cp;

	}

	@ResponseBody
	@PostMapping("orgcontactlinkpersonsavejson")
	public ContactPerson orgcontactlinkpersonsavejson(@RequestParam Map<String, String> params) {

		OrganizationContacts organization = contactOrganizationService
				.findById(Integer.parseInt(params.get("OrganizationContactsID")));
		ContactPerson cp = contactPersonService.findById(Integer.parseInt(params.get("linkpeoplename")));
		cp.setOrganization(String.valueOf(organization.getId()));
		// Set primary contact
		List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
				.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
		if (bcls.size() > 0) {
			cp.setPrimarymob(bcls.get(0).getPhonenumber());
			cp.setPrimaryemail(bcls.get(0).getEmail());
		}
		return contactPersonService.save(cp);

	}

	@GetMapping("contactsorganizationslist")
	public String contactsorganizationslist(Model themodel) {

		List<BranchMaster> branchls = branchMasterService.findAll();
		themodel.addAttribute("branchlist", branchls);

		List<EmployeeMaster> empls = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("empls", empls);
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("contact_Organization"));

		return "contactorganizationlist";
	}

	public OrganizationContacts OrganizationContactsobjectfiller(OrganizationContacts corg) {
		if (!nullremover(String.valueOf(corg.getFollowers())).equalsIgnoreCase("")) {
			EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(corg.getFollowers()));
			corg.setFollowerimg(getemp_photo(empobj));
			corg.setFollowername(empobj.getStaffName());

			// Set primary contact
			List<EmployeeContact> ecls = employeeMasterService.findById(Integer.parseInt(corg.getFollowers()))
					.getEmployeeContact().stream().filter(C -> C.getPrimarycontact() == true)
					.collect(Collectors.toList());
			if (ecls.size() > 0) {
				corg.setFollowerprimarymob(ecls.get(0).getPhonenumber());

			}
		}

		// branch name
		BranchMaster bm = branchMasterService.findById(corg.getBranchid());
		corg.setBranchName(bm.getBRANCH_NAME());
		corg.setBranchCode(bm.getBranchCode());
		// -------------------
		corg.setCplist(contactPersonService.contactpersonlistbyorgname(corg.getOrgname()));
		return corg;
	}

	@GetMapping("contactorganizationview")
	public String contactOrganizationview(Model theModel, @RequestParam("id") int id) {

		OrganizationContacts corg = OrganizationContactsobjectfiller(contactOrganizationService.findById(id));
		if (corg.getOrganizationAccNo().size() == 0) {
			List<OrganizationAccNo> oanls = new ArrayList<OrganizationAccNo>();
			oanls.add(new OrganizationAccNo());
			corg.setOrganizationAccNo(oanls);
		}

		List<ContactPerson> cpls = new ArrayList();
		for (ContactPerson obj : contactPersonService.contactpersonlistbyorgname(String.valueOf(corg.getId()))) {
			cpls.add(ContactPersonobjectfiller(obj));
		}
		theModel.addAttribute("cpls", cpls);

		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList", contactPersonService.findAll());
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("contact_Organization"));
		return "contactorganizationadd";
	}

	@PostMapping("contactOrganizationsave")
	public String contactOrganizationsave(Model themodel,
			@ModelAttribute("contactOrganization") OrganizationContacts contactOrganization) {

		contactOrganization = contactOrganizationService.save(contactOrganization);

		themodel.addAttribute("contactOrganization", contactOrganization);
		themodel.addAttribute("save", true);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("personlist", contactPersonService.findAll());
		return "contactorganizationadd";
	}

	public void mappersonstoOrganization(String collectorgids, int personId) {

		/*
		 * final String perid = String.valueOf(personId); if
		 * (!collectorgids.equalsIgnoreCase("")) { for (String s :
		 * collectorgids.split(",")) { OrganizationContacts contactOrganization1 =
		 * contactOrganizationService.findById(Integer.parseInt(s)); String[] arr =
		 * String.valueOf(contactOrganization1.getPersonid()).split(",");
		 * 
		 * if (!Arrays.stream(arr).anyMatch(x -> x.equalsIgnoreCase(perid))) { String
		 * temp = String.valueOf(contactOrganization1.getPersonid()).replace("null",
		 * ""); if (temp.length() > 0) { contactOrganization1.setPersonid(temp + "," +
		 * personId); } else {
		 * contactOrganization1.setPersonid(String.valueOf(personId)); } }
		 * contactOrganizationService.save(contactOrganization1); } }
		 */
	}

	public void mapOrganizationtopersons(String collectpeopleids, int orgId) {

		// -------------------------------
		final String orgid = String.valueOf(orgId);
		if (!collectpeopleids.equalsIgnoreCase("")) {
			for (String s : collectpeopleids.split(",")) {
				ContactPerson contactPerson1 = contactPersonService.findById(Integer.parseInt(s));

				String[] arr = String.valueOf(contactPerson1.getOrganization()).split(",");

				if (!Arrays.stream(arr).anyMatch(x -> x.equalsIgnoreCase(orgid))) {
					String temp = String.valueOf(contactPerson1.getOrganization()).replace("null", "");
					if (temp.length() > 0) {
						contactPerson1.setOrganization(temp + "," + orgId);
					} else {
						contactPerson1.setOrganization(String.valueOf(orgId));
					}
				}
				contactPersonService.save(contactPerson1);
			}
		}
		// --------------------------------
	}

	@ResponseBody
	@GetMapping("leadlistjson")
	public List<LeadMaster> leadlistjson(Model themodel) {
		List<LeadMaster> leadmasterls = new ArrayList<>();
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		for (LeadMaster tmp1obj : leadMasterService.findAll()) {
			// --------------------------------------------------
			List<Map<String, Object>> ls = activityMasterService.nextactivity("Lead", String.valueOf(tmp1obj.getId()));
			if (ls.size() > 0) {
				ls.forEach(rowMap -> {
					String activitytitle = String.valueOf(rowMap.get("activitytitle"));
					String activitytype = String.valueOf(rowMap.get("activitytype"));
					tmp1obj.setNextactivity(activitytype + " - " + activitytitle);
				});
			} else {
				tmp1obj.setNextactivity("<span class='red'>No Activity</span>");
			}
			// --------------------------------------------------
			for (LeadFollowers leadfol : tmp1obj.getLeadFollowers()) {
				String followerstr = nullremover(String.valueOf(leadfol.getEmpid()));

				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(followerstr));

				leadfol.setFollowername(empobj.getStaffName());

				List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
						.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
				if (validProfilephoto.size() > 0) {

					leadfol.setFollowerimg(validProfilephoto.get(0).getFilePath());
				}
			}

			try {
				tmp1obj.setCreateddateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydatetimeFormat.parse(tmp1obj.getCreateddate())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tmp1obj.setBranchname(branchMasterService.findById(tmp1obj.getBranch()).getBRANCH_NAME());

			leadmasterls.add(tmp1obj);

		}
		return leadmasterls;
	}

	@ResponseBody
	@GetMapping("deallistjson")
	public List<DealMaster> deallistjson(Model themodel) {
		List<DealMaster> dealmasterls = new ArrayList<>();
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		for (DealMaster tmp1obj : dealMasterService.findAll()) {
			// --------------------------------------------------
			List<Map<String, Object>> ls = activityMasterService.nextactivity("Deal", String.valueOf(tmp1obj.getId()));
			if (ls.size() > 0) {
				ls.forEach(rowMap -> {
					String activitytitle = String.valueOf(rowMap.get("activitytitle"));
					String activitytype = String.valueOf(rowMap.get("activitytype"));
					tmp1obj.setNextactivity(activitytype + " - " + activitytitle);
				});
			} else {
				tmp1obj.setNextactivity("<span class='red'>No Activity</span>");
			}
			// --------------------------------------------------
			for (DealFollowers dealfol : tmp1obj.getDealFollowers()) {
				String followerstr = nullremover(String.valueOf(dealfol.getEmpid()));

				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(followerstr));

				dealfol.setFollowername(empobj.getStaffName());

				List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
						.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
				if (validProfilephoto.size() > 0) {

					dealfol.setFollowerimg(validProfilephoto.get(0).getFilePath());
				}
			}

			try {
				tmp1obj.setCreateddateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydatetimeFormat.parse(tmp1obj.getCreateddate())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tmp1obj.setBranchname(branchMasterService.findById(tmp1obj.getBranch()).getBRANCH_NAME());

			dealmasterls.add(tmp1obj);

		}
		return dealmasterls;
	}

	@ResponseBody
	@GetMapping("projectlistjson")
	public List<ProjectMaster> projectlistjson(Model themodel) {
		List<ProjectMaster> projectmasterls = new ArrayList<>();
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		for (ProjectMaster tmp1obj : projectMasterService.findAll()) {
			// --------------------------------------------------
			List<Map<String, Object>> ls = activityMasterService.nextactivity("Project",
					String.valueOf(tmp1obj.getId()));
			if (ls.size() > 0) {
				ls.forEach(rowMap -> {
					String activitytitle = String.valueOf(rowMap.get("activitytitle"));
					String activitytype = String.valueOf(rowMap.get("activitytype"));
					tmp1obj.setNextactivity(activitytype + " - " + activitytitle);
				});
			} else {
				tmp1obj.setNextactivity("<span class='red'>No Activity</span>");
			}
			// --------------------------------------------------
			for (ProjectFollowers projectfol : tmp1obj.getProjectFollowers()) {
				String followerstr = nullremover(String.valueOf(projectfol.getEmpid()));

				EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(followerstr));

				projectfol.setFollowername(empobj.getStaffName());

				List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
						.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
				if (validProfilephoto.size() > 0) {

					projectfol.setFollowerimg(validProfilephoto.get(0).getFilePath());
				}
			}

			try {
				tmp1obj.setCreateddateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydatetimeFormat.parse(tmp1obj.getCreateddate())).toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tmp1obj.setBranchname(branchMasterService.findById(tmp1obj.getBranch()).getBRANCH_NAME());

			projectmasterls.add(tmp1obj);

		}
		return projectmasterls;
	}

	@ResponseBody
	@GetMapping("leadcplist")
	public List<tagify> contactpersonlist() {

		List<tagify> taglist = new ArrayList<tagify>();

		for (ContactPerson cp : contactPersonService.findAll()) {
			tagify tagobj = new tagify();
			tagobj.setValue(cp.getPeoplename());
			tagobj.setCode(String.valueOf(cp.getId()));
			// -------------------------------------------

			if (cp.getContactPersonContact().size() > 0) {

				List<ContactPersonContact> cpList = cp.getContactPersonContact().stream()
						.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
				if (cpList.size() > 0) {
					tagobj.setValue(tagobj.getValue() + " [" + cpList.get(0).getPhonenumber() + "]");
				} else {
					tagobj.setValue(
							tagobj.getValue() + " [" + cp.getContactPersonContact().get(0).getPhonenumber() + "]");
				}
			}
			// -------------------------------------------

			taglist.add(tagobj);

		}

		return taglist;
	}

	@GetMapping("leadlist")
	public String leadlist(Model themodel) {

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("lead"));

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmlist);

		return "leadlist";
	}

	@GetMapping("leadview")
	public String leadview(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		LeadMaster leadMaster = new LeadMaster();
		leadMaster = leadMasterService.findById(id);

		if (!nullremover(String.valueOf(leadMaster.getOrganization())).equalsIgnoreCase("")) {
			leadMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(leadMaster.getOrganization())).getOrgname());
		}
		List<LeadFollowers> leadfolloersls = new ArrayList();
		String followerids = "";
		for (LeadFollowers lf : leadMaster.getLeadFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			leadfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		leadMaster.setLeadfollowerids(followerids);

		if (!nullremover(String.valueOf(leadMaster.getReference())).equalsIgnoreCase("")) {
			final String leadreference = leadMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(leadreference));
			leadMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(leadMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = leadMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			leadMaster.setBranchname(bm.getBRANCH_NAME());
		}

		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (LeadContact lc : leadMaster.getLeadContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("leadMaster", leadMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(leadMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("lead"));
		theModel.addAttribute("activityMaster", new ActivityMaster());
		return "leadview";
	}

	@GetMapping("dealview")
	public String dealview(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		DealMaster dealMaster = new DealMaster();
		dealMaster = dealMasterService.findById(id);

		if (!nullremover(String.valueOf(dealMaster.getOrganization())).equalsIgnoreCase("")) {
			dealMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(dealMaster.getOrganization())).getOrgname());
		}
		List<DealFollowers> dealfolloersls = new ArrayList();
		String followerids = "";
		for (DealFollowers lf : dealMaster.getDealFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			dealfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		dealMaster.setDealfollowerids(followerids);

		if (!nullremover(String.valueOf(dealMaster.getReference())).equalsIgnoreCase("")) {
			final String dealreference = dealMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(dealreference));
			dealMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(dealMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = dealMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			dealMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (DealContact lc : dealMaster.getDealContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(dealMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				dealMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(dealMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("dealMaster", dealMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(dealMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("deal"));
		theModel.addAttribute("activityMaster", new ActivityMaster());
		return "dealevents";
	}

	@GetMapping("dealattachment")
	public String dealattachment(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		DealMaster dealMaster = new DealMaster();
		dealMaster = dealMasterService.findById(id);

		if (!nullremover(String.valueOf(dealMaster.getOrganization())).equalsIgnoreCase("")) {
			dealMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(dealMaster.getOrganization())).getOrgname());
		}
		List<DealFollowers> dealfolloersls = new ArrayList();
		String followerids = "";
		for (DealFollowers lf : dealMaster.getDealFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			dealfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		dealMaster.setDealfollowerids(followerids);

		if (!nullremover(String.valueOf(dealMaster.getReference())).equalsIgnoreCase("")) {
			final String dealreference = dealMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(dealreference));
			dealMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(dealMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = dealMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			dealMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (DealContact lc : dealMaster.getDealContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(dealMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				dealMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(dealMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("dealMaster", dealMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(dealMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("deal"));

		return "dealattachment";
	}

	@GetMapping("dealprojectinfo")
	public String dealproject(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		DealMaster dealMaster = new DealMaster();
		dealMaster = dealMasterService.findById(id);

		if (!nullremover(String.valueOf(dealMaster.getOrganization())).equalsIgnoreCase("")) {
			dealMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(dealMaster.getOrganization())).getOrgname());
		}
		List<DealFollowers> dealfolloersls = new ArrayList();
		String followerids = "";
		for (DealFollowers lf : dealMaster.getDealFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			dealfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		dealMaster.setDealfollowerids(followerids);

		if (!nullremover(String.valueOf(dealMaster.getReference())).equalsIgnoreCase("")) {
			final String dealreference = dealMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(dealreference));
			dealMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(dealMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = dealMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			dealMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (DealContact lc : dealMaster.getDealContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(dealMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				dealMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(dealMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("dealMaster", dealMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		theModel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(dealMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("deal"));

		return "dealprojectinfo";
	}

	@PostMapping("getdealprojectlist")
	@ResponseBody
	public List<DealProjectMaster> getdealprojectlist(@RequestParam Map<String, String> params) {

		return dealMasterService.findById(Integer.parseInt(params.get("mastercategoryid"))).getDealProjectMaster();
	}

	@PostMapping("getprojectprojectlist")
	@ResponseBody
	public List<ProjectItemMaster> getprojectprojectlist(@RequestParam Map<String, String> params) {

		return projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid"))).getProjectItemMaster();
	}

	@PostMapping("getdealproject")
	@ResponseBody
	public DealProjectMaster getdealproject(@RequestParam Map<String, String> params) {

		DealMaster dm = dealMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));

		return dm.getDealProjectMaster().stream()
				.filter(C -> C.getDealprojectid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

	}

	@GetMapping("getprojectobj")
	@ResponseBody
	public ProjectMaster getproject(@RequestParam Map<String, String> params) {

		ProjectMaster dm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));
		for (ProjectPhases pm : dm.getProjectPhases()) {

			List<ActivityMaster> amls = activityMasterService.findByMastercategoryAndMastercategoryid("Project",
					String.valueOf(pm.getId()));

			for (ActivityMaster am : amls) {
				if (!nullremover(String.valueOf(am.getActivityfollowers())).equalsIgnoreCase("")) {
					EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(am.getActivityfollowers()));

					if (empobj != null) {
						String empphotos = "<button type='button' class='step-trigger' aria-selected='false' disabled='disabled'>  <span class='bs-stepper-circle'><i class='bx bx-user'></i></span></button>";

						List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
								.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo"))
								.collect(Collectors.toList());
						if (validProfilephoto.size() > 0) {

							empphotos = validProfilephoto.get(0).getFilePath();
						}

						am.setFollowerimg(
								"<span data-bs-toggle='tooltip' data-popup='tooltip-custom' data-bs-placement='top' class='avatar  pull-up tooltipx'>"
										+ " <img class='rounded-circle' src='" + empphotos
										+ " ' alt='Avatar'><span class='tooltiptextx'>" + empobj.getStaffName()
										+ "</span></span>");
					}
				}

				try {
					am.setStartdatestrformate(displaydateFormatFirstMMMddYYYAMPM
							.format(displaydateFormatyyyMMddHHmm.parse(am.getStartdate() + ' ' + am.getStarttime()))
							.toString().toUpperCase());

				} catch (ParseException e) {

					e.printStackTrace();
				}
			}

			pm.setActivityMaster(amls);
		}
		return dm;

	}

	@PostMapping("getproject")
	@ResponseBody
	public ProjectItemMaster getprojectitem(@RequestParam Map<String, String> params) {

		ProjectMaster dm = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));

		return dm.getProjectItemMaster().stream()
				.filter(C -> C.getProjectitemid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

	}

	@PostMapping("dealprojectsave")
	@ResponseBody
	public String dealprojectsave(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		DealMaster dm = dealMasterService.findById(Integer.parseInt(params.get("dealid")));

		List<DealProjectMaster> dealprojectList = new ArrayList();
		if (nullremover(String.valueOf(params.get("projectid"))).equalsIgnoreCase("")) {

			dealprojectList = dm.getDealProjectMaster();
			DealProjectMaster dpmobj = new DealProjectMaster();
			int amount = Integer.parseInt(params.get("modalPrice")) * Integer.parseInt(params.get("modalQuantity"));

			dpmobj.setAmount(String.valueOf(amount));
			dpmobj.setPrice(params.get("modalPrice"));
			dpmobj.setProjecttype(params.get("modalnatureofwork"));
			dpmobj.setQuantity(params.get("modalQuantity"));
			dpmobj.setUnit(params.get("modalunits"));

			dealprojectList.add(dpmobj);

		} else {
			for (DealProjectMaster tempdpmobj : dm.getDealProjectMaster()) {
				if (tempdpmobj.getDealprojectid() == Integer.parseInt(params.get("projectid"))) {

					int amount = Integer.parseInt(params.get("modalPrice"))
							* Integer.parseInt(params.get("modalQuantity"));

					tempdpmobj.setAmount(String.valueOf(amount));
					tempdpmobj.setPrice(params.get("modalPrice"));
					tempdpmobj.setProjecttype(params.get("modalnatureofwork"));
					tempdpmobj.setQuantity(params.get("modalQuantity"));
					tempdpmobj.setUnit(params.get("modalunits"));

				}
				dealprojectList.add(tempdpmobj);
			}

		}

		try {

			int dealvalue = dealprojectList.stream().mapToInt(o -> Integer.parseInt(o.getAmount())).sum();
			if (dealvalue > 0) {
				dm.setDealvalue(dealvalue);
			}

		} catch (Exception e) {

		}
		dm.setDealProjectMaster(dealprojectList);

		dealMasterService.save(dm);

		return "";
	}

	@PostMapping("projectitemsave")
	@ResponseBody
	public String projectprojectsave(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		ProjectMaster dm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));

		List<ProjectItemMaster> projectprojectList = new ArrayList();
		if (nullremover(String.valueOf(params.get("projectitemid"))).equalsIgnoreCase("")) {

			projectprojectList = dm.getProjectItemMaster();
			ProjectItemMaster dpmobj = new ProjectItemMaster();
			int amount = Integer.parseInt(params.get("modalPrice")) * Integer.parseInt(params.get("modalQuantity"));

			dpmobj.setAmount(String.valueOf(amount));
			dpmobj.setPrice(params.get("modalPrice"));
			dpmobj.setProjecttype(params.get("modalnatureofwork"));
			dpmobj.setQuantity(params.get("modalQuantity"));
			dpmobj.setUnit(params.get("modalunits"));

			projectprojectList.add(dpmobj);

		} else {
			for (ProjectItemMaster tempdpmobj : dm.getProjectItemMaster()) {
				if (tempdpmobj.getProjectitemid() == Integer.parseInt(params.get("projectitemid"))) {

					int amount = Integer.parseInt(params.get("modalPrice"))
							* Integer.parseInt(params.get("modalQuantity"));

					tempdpmobj.setAmount(String.valueOf(amount));
					tempdpmobj.setPrice(params.get("modalPrice"));
					tempdpmobj.setProjecttype(params.get("modalnatureofwork"));
					tempdpmobj.setQuantity(params.get("modalQuantity"));
					tempdpmobj.setUnit(params.get("modalunits"));

				}
				projectprojectList.add(tempdpmobj);
			}

		}

		try {

			int projectvalue = projectprojectList.stream().mapToInt(o -> Integer.parseInt(o.getAmount())).sum();
			if (projectvalue > 0) {
				dm.setProjectvalue(projectvalue);
			}

		} catch (Exception e) {

		}
		dm.setProjectItemMaster(projectprojectList);

		projectMasterService.save(dm);

		return "";
	}

	@GetMapping("leadattachment")
	public String leadattachment(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		LeadMaster leadMaster = new LeadMaster();
		leadMaster = leadMasterService.findById(id);

		if (!nullremover(String.valueOf(leadMaster.getOrganization())).equalsIgnoreCase("")) {
			leadMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(leadMaster.getOrganization())).getOrgname());
		}
		List<LeadFollowers> leadfolloersls = new ArrayList();
		String followerids = "";
		for (LeadFollowers lf : leadMaster.getLeadFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			leadfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		leadMaster.setLeadfollowerids(followerids);

		if (!nullremover(String.valueOf(leadMaster.getReference())).equalsIgnoreCase("")) {
			final String leadreference = leadMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(leadreference));
			leadMaster.setReferenceName(cp.getPeoplename());
		}
		if (!nullremover(String.valueOf(leadMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = leadMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			leadMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (LeadContact lc : leadMaster.getLeadContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("leadMaster", leadMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(leadMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("lead"));

		return "leadattachment";
	}

	@PostMapping("contactpersondetails")
	@ResponseBody
	public ContactPerson organizationlist(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		ContactPerson obj = new ContactPerson();
		String str = params.get("selectval").replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
				"");
		str = str.replace("\"", "");
		// System.out.println(str);
		String[] strarr = str.split(",");

		if (strarr.length > 1) {
			String strarr1 = strarr[1];
			obj = contactPersonService.findById(Integer.parseInt(strarr1));
			// Set primary contact
			List<ContactPersonContact> bcls = obj.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				obj.setPrimarymob(bcls.get(0).getPhonenumber());
				obj.setPrimaryemail(bcls.get(0).getEmail());

				if (!nullremover(String.valueOf(obj.getOrganization())).equalsIgnoreCase("")) {
					obj.setOrganizationname(
							contactOrganizationService.findById(Integer.parseInt(obj.getOrganization())).getOrgname());
				}
				;
			}
		}
		return obj;
	}

	@PostMapping("getActivityMaster")
	@ResponseBody
	public ActivityMaster getActivityMaster(@RequestParam("id") int id) {

		return activityMasterService.findById(id);

	}

	@PostMapping("getProjectTemplateActivityMaster")
	@ResponseBody
	public ProjectTemplateActivityMaster getProjectTemplateActivityMaster(@RequestParam("id") int id,
			@RequestParam("boardid") int boardid) {

		ProjectTemplateBoard proTemplateBoard = projectTemplateBoardService.findById(boardid);
		ProjectTemplateActivityMaster actobj = new ProjectTemplateActivityMaster();

		for (ProjectTemplatePhase phase : proTemplateBoard.getProjectTemplatePhase()) {
			if (phase.getProjecttemplateactivityMaster().stream().filter(C -> C.getProjectactivityid() == id)
					.collect(Collectors.toList()).size() > 0) {
				actobj = phase.getProjecttemplateactivityMaster().stream().filter(C -> C.getProjectactivityid() == id)
						.collect(Collectors.toList()).get(0);
				actobj.setTempphaseid(String.valueOf(phase.getId()));
			}

		}

		return actobj;

	}

	@PostMapping("activitysave")
	@ResponseBody
	public String activitysave(@RequestParam Map<String, String> param, @RequestParam("guestid") List<String> guestid,
			@RequestParam(name = "File_Attach", required = false) MultipartFile Files_Attach,
			HttpServletRequest request) {

		int activityId = Integer.parseInt(param.get("activityId"));
		ActivityMaster activityMaster = new ActivityMaster();
		if (activityId > 0) {
			activityMaster = activityMasterService.findById(activityId);
		} else {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}

		activityMaster.setActivitycategory(param.get("Activitycategory").toString());
		activityMaster.setActivityfollowers(param.get("activityfollowers").toString());
		activityMaster.setActivitytitle(param.get("activitytitle").toString());
		activityMaster.setActivitytype(param.get("activitytype").toString());

		activityMaster.setDescription("");

		activityMaster.setDuedate(param.get("duedate").toString());
		activityMaster.setEnddate(param.get("enddate").toString());
		activityMaster.setEndtime(param.get("endtime").toString());
		activityMaster.setHtmlnotes(param.get("htmlnotes").toString());
		activityMaster.setMastercategory(param.get("mastercategory").toString());
		activityMaster.setMastercategoryid(param.get("mastercategoryid").toString());
		activityMaster.setNotes(param.get("notes").toString());
		activityMaster.setStartdate(param.get("startdate").toString());
		activityMaster.setStarttime(param.get("starttime").toString());
		if (param.get("status") != null) {
			activityMaster.setStatus("Completed");
		} else {
			activityMaster.setStatus("");
		}

		// --------------------------------------------------
		// Guest

		/*
		 * List<ActivityMasterGuest> lsactivityMasterguest = new ArrayList();
		 * 
		 * for (String str : guestid) { ActivityMasterGuest guestobj = new
		 * ActivityMasterGuest(); guestobj.setGuestid(str);
		 * lsactivityMasterguest.add(guestobj); }
		 * activityMaster.setActivityMasterGuest(lsactivityMasterguest);
		 */
		// --------------------------------------------------
		// File Uploading
		List<ActivityMasterFiles> activityMasterFileslist = new ArrayList();
		if (Files_Attach != null) {

			StringBuilder filename = new StringBuilder();
			// File Uploading
			String profilephotouploadRootPath = request.getServletContext().getRealPath("activityfiles");
			// System.out.println("uploadRootPath=" + profilephotouploadRootPath);

			File uploadRootDir = new File(profilephotouploadRootPath);
			// Create directory if it not exists.
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}

			if (Files_Attach.getOriginalFilename().toString().length() > 0) {

				String tempfilename = stringdatetime() + Files_Attach.getOriginalFilename();
				Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
				filename.append("activityfiles/" + tempfilename);

				try {
					Files.write(fileNameandPath, Files_Attach.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ActivityMasterFiles activityMasterFiles = new ActivityMasterFiles();
			activityMasterFiles.setFiles_Attach(filename.toString());
			activityMasterFileslist.add(activityMasterFiles);

		}
		if (activityMasterFileslist.size() > 0) {
			activityMaster.setActivityMasterFiles(activityMasterFileslist);

		}

		activityMaster = activityMasterService.save(activityMaster);
		return "";
	}

	@PostMapping("projecttemplateactivitysave")
	@ResponseBody
	public String projecttemplateactivitysave(@RequestParam Map<String, String> param, HttpServletRequest request) {

		int boardid = Integer.parseInt(param.get("boardid"));
		int phaseid = Integer.parseInt(param.get("phaseid"));

		ProjectTemplateBoard proTemplateBoard = projectTemplateBoardService.findById(boardid);
		ProjectTemplatePhase projectTemplatePhase = proTemplateBoard.getProjectTemplatePhase().stream()
				.filter(C -> C.getId() == phaseid).collect(Collectors.toList()).get(0);

		int activityId = Integer.parseInt(param.get("projectactivityid"));
		ProjectTemplateActivityMaster activityMaster = new ProjectTemplateActivityMaster();

		if (activityId > 0) {
			activityMaster = projectTemplatePhase.getProjecttemplateactivityMaster().stream()
					.filter(C -> C.getProjectactivityid() == activityId).collect(Collectors.toList()).get(0);

		}

		activityMaster.setActivitytitle(param.get("activitytitle").toString());
		activityMaster.setActivitytype(param.get("activitytype").toString());
		activityMaster.setActivityorder(Integer.parseInt(param.get("activityorder")));
		activityMaster.setNotes(param.get("notes").toString());
		activityMaster.setDaysfromprojectstartdate(Integer.parseInt(param.get("daysfromprojectstartdate").toString()));
		activityMaster.setActivityfollowers(param.get("activityfollowers"));

		projectTemplateBoardService.ProjectTemplateActivityMastersave(activityMaster, phaseid);

		return "";
	}

	@PostMapping("leadsavestage2")
	@ResponseBody
	public LeadMaster leadsavestage2(@RequestParam Map<String, String> params) {

		LeadMaster leadMaster = leadMasterService.findById(Integer.parseInt(params.get("leadMasterID")));

		leadMaster.setTitle(params.get("Title"));
		leadMaster.setOrganization(params.get("Organization"));
		leadMaster.setSource(params.get("Source"));
		leadMaster.setPurpose(params.get("purpose"));
		leadMaster.setLeadvalue(Integer.parseInt(params.get("leadvalue")));
		leadMaster.setReference(params.get("Reference"));
		leadMaster.setLabel(params.get("Label"));
		leadMaster.setBranch(Integer.parseInt(params.get("branch")));
		leadMaster.setNotes(params.get("notes"));

		List<LeadFollowers> lfls = new ArrayList<>();

		for (String str : params.get("followers").split(",")) {

			int followerid = Integer.parseInt(str);

			if (leadMaster.getLeadFollowers().stream().filter(C -> C.getEmpid() == followerid)
					.collect(Collectors.toList()).size() > 0) {
				lfls.add(leadMaster.getLeadFollowers().stream().filter(C -> C.getEmpid() == followerid)
						.collect(Collectors.toList()).get(0));
			} else {
				lfls.add(new LeadFollowers(0, followerid, "", ""));
			}
		}
		leadMaster.setLeadFollowers(lfls);

		leadMaster = leadMasterService.save(leadMaster);
		for (LeadFollowers lf : leadMaster.getLeadFollowers()) {

			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

		}
		if (!nullremover(String.valueOf(leadMaster.getOrganization())).equalsIgnoreCase("")) {
			leadMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(leadMaster.getOrganization())).getOrgname());
		}
		if (!nullremover(String.valueOf(leadMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = leadMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			leadMaster.setBranchname(bm.getBRANCH_NAME());
		}

		return leadMaster;
	}

	@PostMapping("dealsavestage2")
	@ResponseBody
	public DealMaster dealsavestage2(@RequestParam Map<String, String> params) {

		DealMaster dealMaster = dealMasterService.findById(Integer.parseInt(params.get("dealMasterID")));

		dealMaster.setTitle(params.get("Title"));
		dealMaster.setOrganization(params.get("Organization"));
		dealMaster.setSource(params.get("Source"));
		dealMaster.setPurpose(params.get("purpose"));
		dealMaster.setDealvalue(Integer.parseInt(params.get("dealvalue")));
		dealMaster.setReference(params.get("Reference"));
		dealMaster.setPipeline(params.get("pipeline"));
		dealMaster.setBranch(Integer.parseInt(params.get("branch")));
		dealMaster.setNotes(params.get("notes"));
		dealMaster.setExpectedclosingdate(params.get("expectedclosingdate"));

		List<DealFollowers> lfls = new ArrayList<>();

		for (String str : params.get("followers").split(",")) {

			int followerid = Integer.parseInt(str);

			if (dealMaster.getDealFollowers().stream().filter(C -> C.getEmpid() == followerid)
					.collect(Collectors.toList()).size() > 0) {
				lfls.add(dealMaster.getDealFollowers().stream().filter(C -> C.getEmpid() == followerid)
						.collect(Collectors.toList()).get(0));
			} else {
				lfls.add(new DealFollowers(0, followerid, "", ""));
			}
		}
		dealMaster.setDealFollowers(lfls);

		dealMaster = dealMasterService.save(dealMaster);
		for (DealFollowers lf : dealMaster.getDealFollowers()) {

			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

		}
		if (!nullremover(String.valueOf(dealMaster.getOrganization())).equalsIgnoreCase("")) {
			dealMaster.setOrganizationName(
					contactOrganizationService.findById(Integer.parseInt(dealMaster.getOrganization())).getOrgname());
		}
		if (!nullremover(String.valueOf(dealMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = dealMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			dealMaster.setBranchname(bm.getBRANCH_NAME());
		}
		if (!nullremover(String.valueOf(dealMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				dealMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(dealMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return dealMaster;
	}

	@PostMapping("projectsavestage2")
	@ResponseBody
	public ProjectMaster projectsavestage2(@RequestParam Map<String, String> params) {

		ProjectMaster projectMaster = projectMasterService.findById(Integer.parseInt(params.get("projectMasterID")));

		projectMaster.setTitle(params.get("Title"));
		projectMaster.setOrganization(params.get("Organization"));
		projectMaster.setSource(params.get("Source"));
		projectMaster.setPurpose(params.get("purpose"));
		projectMaster.setProjectvalue(Integer.parseInt(params.get("projectvalue")));
		projectMaster.setReference(params.get("Reference"));
		projectMaster.setPipeline(params.get("pipeline"));
		projectMaster.setBranch(Integer.parseInt(params.get("branch")));
		projectMaster.setNotes(params.get("notes"));
		projectMaster.setExpectedclosingdate(params.get("expectedclosingdate"));
		projectMaster.setStartdate(params.get("startdate"));
		projectMaster.setLabel(params.get("label"));
		projectMaster.setBoard(params.get("board"));

		List<ProjectFollowers> lfls = new ArrayList<>();

		for (String str : params.get("followers").split(",")) {

			int followerid = Integer.parseInt(str);

			if (projectMaster.getProjectFollowers().stream().filter(C -> C.getEmpid() == followerid)
					.collect(Collectors.toList()).size() > 0) {
				lfls.add(projectMaster.getProjectFollowers().stream().filter(C -> C.getEmpid() == followerid)
						.collect(Collectors.toList()).get(0));
			} else {
				lfls.add(new ProjectFollowers(0, followerid, "", ""));
			}
		}
		projectMaster.setProjectFollowers(lfls);

		projectMaster = projectMasterService.save(projectMaster);
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

		}
		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}
		// ----------------------------------------------------------
		return projectMaster;
	}

	@PostMapping("leadsavestage1")
	@ResponseBody
	public String leadsavestage1(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String Label = params.get("Label");
		String notes = params.get("notes");
		String followers = params.get("followers");
		String phonenumber = params.get("phonenumber");
		String Purpose = params.get("Purpose");
		int leadValue = 0;
		if (!params.get("leadValue").equalsIgnoreCase("")) {
			leadValue = Integer.parseInt(params.get("leadValue"));
		}

		int branch = Integer.parseInt(params.get("branch"));
		// ---------------------------------------
		ContactPerson cp = new ContactPerson();
		String str = params.get("ContactPerson").replace("[{\"value\":\"", "").replace("\"code\":\"", "")
				.replace("\"}]", "");
		str = str.replace("\"", "");
		String[] strarr = str.split(",");

		if (strarr.length > 1) {
			String strarr1 = strarr[1];
			cp = contactPersonService.findById(Integer.parseInt(strarr1));
			// ----------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {

				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);

						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}
			} else {
				cp.setOrganization("");
			}
			// ----------------------------------------
		} else {
			cp.setBranchid(1);
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			cp.setPeoplename(strarr[0]);
			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(phonenumber);
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			// -------------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {
				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);
						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}
			} else {
				cp.setOrganization("");
			}
			// --------------------------------------------------------------

		}
		cp = contactPersonService.save(cp);

		// ----------------------------
		LeadMaster leadMaster = new LeadMaster();
		List<LeadContact> lclist = new ArrayList<LeadContact>();
		LeadContact lc = new LeadContact();
		lc.setContactPerson(cp.getId());
		lclist.add(lc);

		leadMaster.setLeadContact(lclist);
		leadMaster.setOrganization(cp.getOrganization());
		leadMaster.setTitle(Title);
		leadMaster.setSource(Source);
		leadMaster.setReference(Reference);
		leadMaster.setLabel(Label);
		leadMaster.setNotes(notes);
		leadMaster.setPurpose(Purpose);
		leadMaster.setBranch(branch);
		leadMaster.setLeadvalue(leadValue);
		List<LeadFollowers> lmlis = new ArrayList();
		LeadFollowers lfobj = new LeadFollowers();
		lmlis.add(new LeadFollowers(0, Integer.parseInt(followers), "", ""));

		leadMaster.setLeadFollowers(lmlis);
		leadMaster.setCreateddate(displaydatetimeFormat.format(new Date()));

		leadMasterService.save(leadMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		itemlistService.savesingletxt(Purpose, "PURPOSE");
		// ----------------------------
		return "";
	}

	@PostMapping("dealsavestage1")
	@ResponseBody
	public String dealsavestage1(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String Pipeline = params.get("Pipeline");
		String notes = params.get("notes");
		String followers = params.get("followers");
		String phonenumber = params.get("phonenumber");
		String Purpose = params.get("Purpose");
		int dealValue = 0;
		if (!params.get("dealValue").equalsIgnoreCase("")) {
			dealValue = Integer.parseInt(params.get("dealValue"));
		}

		int branch = Integer.parseInt(params.get("branch"));
		// ---------------------------------------
		ContactPerson cp = new ContactPerson();
		String str = params.get("ContactPerson").replace("[{\"value\":\"", "").replace("\"code\":\"", "")
				.replace("\"}]", "");
		str = str.replace("\"", "");
		String[] strarr = str.split(",");

		if (strarr.length > 1) {
			String strarr1 = strarr[1];
			cp = contactPersonService.findById(Integer.parseInt(strarr1));
			// ----------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {

				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);

						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}
			} else {
				cp.setOrganization("");
			}

			// ----------------------------------------
		} else {
			cp.setBranchid(1);
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			cp.setPeoplename(strarr[0]);
			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(phonenumber);
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			// -------------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {
				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);
						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}

			} else {
				cp.setOrganization("");
			}

			// --------------------------------------------------------------

		}
		cp = contactPersonService.save(cp);

		// ----------------------------
		DealMaster dealMaster = new DealMaster();
		List<DealContact> lclist = new ArrayList<DealContact>();
		DealContact lc = new DealContact();
		lc.setContactPerson(cp.getId());
		lclist.add(lc);

		dealMaster.setDealContact(lclist);
		dealMaster.setOrganization(cp.getOrganization());
		dealMaster.setTitle(Title);
		dealMaster.setSource(Source);
		dealMaster.setReference(Reference);
		dealMaster.setPipeline(Pipeline);
		dealMaster.setNotes(notes);
		dealMaster.setPurpose(Purpose);
		dealMaster.setBranch(branch);
		dealMaster.setDealvalue(dealValue);
		List<DealFollowers> lmlis = new ArrayList();
		DealFollowers lfobj = new DealFollowers();
		lmlis.add(new DealFollowers(0, Integer.parseInt(followers), "", ""));

		dealMaster.setDealFollowers(lmlis);
		dealMaster.setCreateddate(displaydatetimeFormat.format(new Date()));

		dealMasterService.save(dealMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		itemlistService.savesingletxt(Purpose, "PURPOSE");
		// ----------------------------
		return "";
	}

	@PostMapping("projectsavestage1")
	@ResponseBody
	public String projectsavestage1(@RequestParam Map<String, String> params) {

		// System.out.println(params);
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String Pipeline = params.get("Pipeline");
		String notes = params.get("notes");
		String followers = params.get("followers");
		String phonenumber = params.get("phonenumber");
		String Purpose = params.get("Purpose");
		String startDate = params.get("startDate");
		String board = params.get("board");

		int projectValue = 0;
		if (!params.get("projectValue").equalsIgnoreCase("")) {
			projectValue = Integer.parseInt(params.get("projectValue"));
		}

		int branch = Integer.parseInt(params.get("branch"));
		// ---------------------------------------
		ContactPerson cp = new ContactPerson();
		String str = params.get("ContactPerson").replace("[{\"value\":\"", "").replace("\"code\":\"", "")
				.replace("\"}]", "");
		str = str.replace("\"", "");
		String[] strarr = str.split(",");

		if (strarr.length > 1) {
			String strarr1 = strarr[1];
			cp = contactPersonService.findById(Integer.parseInt(strarr1));
			// ----------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {
				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);

						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}

			} else {
				cp.setOrganization("");
			}
			// ----------------------------------------
		} else {
			cp.setBranchid(1);
			cp.setCustomer_supplier("Customer");
			cp.setFollowers(params.get("followers"));

			cp.setPeoplename(strarr[0]);
			ContactPersonContact cpc = new ContactPersonContact();
			cpc.setDepartment("Personal");
			cpc.setPhonenumber(phonenumber);
			cpc.setPrimarycontact(true);
			List<ContactPersonContact> cpcls = new ArrayList();
			cpcls.add(cpc);
			cp.setContactPersonContact(cpcls);
			// -------------------------------------------------
			if (!nullremover(String.valueOf(Organization)).equalsIgnoreCase("")) {
				String strorg = Organization.replace("[{\"value\":\"", "").replace("\"code\":\"", "").replace("\"}]",
						"");
				strorg = strorg.replace("\"", "");
				String[] strarrorg = strorg.split(",");

				if (strarrorg.length > 1) {

					cp.setOrganization(String.valueOf(strarrorg[1]));

				} else {
					if (!nullremover(String.valueOf(strarrorg[0])).equalsIgnoreCase("")) {
						OrganizationContacts contactOrganization = new OrganizationContacts();
						contactOrganization.setOrgname(strarrorg[0]);
						contactOrganization.setBranchid(branch);
						contactOrganization.setCustomer_supplier("Customer");
						contactOrganization.setFollowers(params.get("followers"));
						contactOrganization = contactOrganizationService.save(contactOrganization);
						cp.setOrganization(String.valueOf(contactOrganization.getId()));
					}
				}

			} else {
				cp.setOrganization("");
			}
			// --------------------------------------------------------------

		}
		cp = contactPersonService.save(cp);

		// ------------------------------------------------------------------------------------
		List<ProjectPhases> prjphasels = new ArrayList();

		if (!nullremover(String.valueOf(board)).equalsIgnoreCase("")) {

			ProjectTemplateBoard ptboard = projectTemplateBoardService.findById(Integer.parseInt(board));

			for (ProjectTemplatePhase obj : ptboard.getProjectTemplatePhase()) {
				ProjectPhases tempProjectPhases = new ProjectPhases();

				tempProjectPhases.setPhaseName(obj.getPhaseName());
				tempProjectPhases.setOrderID(obj.getOrderID());

				List<ActivityMaster> actmls = new ArrayList();
				for (ProjectTemplateActivityMaster pactmobj : obj.getProjecttemplateactivityMaster()) {

					Calendar cal = Calendar.getInstance();
					String tempdate = "";
					try {
						cal.setTime(displaydateFormatrev.parse(startDate));
						cal.add(Calendar.DAY_OF_MONTH, pactmobj.getDaysfromprojectstartdate());
						tempdate = displaydateFormatrev.format(cal.getTime()).toString();

					} catch (ParseException e) {
						e.printStackTrace();
					}

					ActivityMaster am = new ActivityMaster();

					am.setActivitycategory("Activity");
					am.setActivitytitle(pactmobj.getActivitytitle());
					am.setActivityfollowers(pactmobj.getActivityfollowers());
					am.setActivitytype(pactmobj.getActivitytype());
					am.setCreatedtime(displaydatetimeFormat.format(new Date()));
					am.setStartdate(tempdate);
					am.setStarttime("09:00");
					am.setEnddate(tempdate);
					am.setEndtime("18:00");
					am.setMastercategory("Project");
					am.setMastercategoryid("");
					am.setNotes(pactmobj.getNotes());
					actmls.add(am);
				}

				tempProjectPhases.setActivityMaster(actmls);
				prjphasels.add(tempProjectPhases);

			}
		}
		// ------------------------------------------------------------------------------------
		ProjectMaster projectMaster = new ProjectMaster();
		List<ProjectContact> lclist = new ArrayList<ProjectContact>();
		ProjectContact lc = new ProjectContact();
		lc.setContactPerson(cp.getId());
		lclist.add(lc);

		projectMaster.setProjectContact(lclist);
		projectMaster.setOrganization(cp.getOrganization());
		projectMaster.setTitle(Title);
		projectMaster.setSource(Source);
		projectMaster.setReference(Reference);
		projectMaster.setPipeline(Pipeline);
		projectMaster.setNotes(notes);
		projectMaster.setPurpose(Purpose);
		projectMaster.setBranch(branch);
		projectMaster.setProjectvalue(projectValue);
		projectMaster.setStartdate(startDate);
		projectMaster.setBoard(board);
		List<ProjectFollowers> lmlis = new ArrayList();
		ProjectFollowers lfobj = new ProjectFollowers();
		lmlis.add(new ProjectFollowers(0, Integer.parseInt(followers), "", ""));

		projectMaster.setProjectFollowers(lmlis);
		projectMaster.setCreateddate(displaydatetimeFormat.format(new Date()));
		projectMaster.setProjectPhases(prjphasels);
		projectMaster = projectMasterService.save(projectMaster);
		// System.out.println(projectMaster);

		List<ActivityMaster> amtempls = new ArrayList();

		for (ProjectPhases ppobj : projectMaster.getProjectPhases()) {
			for (ActivityMaster amtempobj : ppobj.getActivityMaster()) {
				amtempobj.setMastercategoryid(String.valueOf(ppobj.getId()));
				amtempls.add(amtempobj);
			}

		}

		activityMasterService.saveall(amtempls);

		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		itemlistService.savesingletxt(Purpose, "PURPOSE");
		// ----------------------------
		return "";
	}

	@GetMapping("leadevents")
	public String leadevents(@RequestParam("id") int id, Model themodel) {
		LeadMaster leadMaster = leadMasterService.findById(id);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						OrganizationContacts obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
								.collect(Collectors.toList()).get(0);
						temp2 += cp.getId() + "|" + cp.getPeoplename() + " |" + obj.getId() + "|" + obj.getOrgname()
								+ " |";
						personorgls.add(temp2);
					} else {
						temp2 += cp.getId() + "|" + cp.getPeoplename() + " | | |";
						personorgls.add(temp2);
					}
				}
			} else {
				personorgls.add(cp.getId() + "|" + cp.getPeoplename() + " | | |");
			}

		}

		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("leadMaster", leadMaster);

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		return "leadevents";
	}

	@PostMapping("leadeventpart1save")
	public String leadeventpart1save(@ModelAttribute("leadMaster") LeadMaster leadMaster,
			@RequestParam Map<String, String> params, Model themodel) {

		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String Label = params.get("Label");
		String notes = params.get("notestemp");
		String phonework = params.get("phonework");
		String phonepersonal = params.get("phonepersonal");
		String phoneothers = params.get("phoneothers");
		String emailwork = params.get("emailwork");
		String emailpersonal = params.get("emailpersonal");
		String emailothers = params.get("emailothers");
		// ----------------------------

		String collectorgids = "";
		String srcOrg = String.valueOf(Organization).replace("null", "");
		if (srcOrg.length() > 0) {
			for (String str : srcOrg.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectorgids += str + ",";
				} else {
					OrganizationContacts contactOrganization = new OrganizationContacts();
					contactOrganization.setOrgname(str);

					collectorgids += contactOrganizationService.save(contactOrganization).getId() + ",";
				}
			}
			collectorgids = collectorgids.substring(0, collectorgids.length() - 1);
		}

		// ----------------------------
		String collectpeopleids = "";
		String srcPer = String.valueOf(ContactPerson).replace("null", "");

		if (srcPer.length() > 0) {
			for (String str : srcPer.split(",")) {
				if (str.contains("-")) {
					str = str.split("-")[0];
				}

				if (NumberUtils.isParsable(str)) {
					collectpeopleids += str + ",";
					ContactPerson contactperson = contactPersonService.findById(Integer.parseInt(str));
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);

					collectpeopleids += contactPersonService.save(contactperson).getId() + ",";
				}
			}
			collectpeopleids = collectpeopleids.substring(0, collectpeopleids.length() - 1);
		}

		// ----------------------------
		if (!collectpeopleids.equalsIgnoreCase("")) {
			for (String s : collectpeopleids.split(",")) {
				mappersonstoOrganization(collectorgids, Integer.parseInt(s));

			}
		}
		if (!collectorgids.equalsIgnoreCase("")) {
			for (String s : collectorgids.split(",")) {
				mapOrganizationtopersons(collectpeopleids, Integer.parseInt(s));
			}
		}
		// ----------------------------

		// leadMaster.setContactPerson(collectpeopleids);
		leadMaster.setOrganization(collectorgids);
		leadMasterService.save(leadMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		// ----------------------------

		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						OrganizationContacts obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
								.collect(Collectors.toList()).get(0);
						temp2 += cp.getId() + "|" + cp.getPeoplename() + " |" + obj.getId() + "|" + obj.getOrgname()
								+ " |";
						personorgls.add(temp2);
					} else {
						temp2 += cp.getId() + "|" + cp.getPeoplename() + " | | |";
						personorgls.add(temp2);
					}
				}
			} else {
				personorgls.add(cp.getId() + "|" + cp.getPeoplename() + " | | |");
			}

		}

		/*
		 * if
		 * (!nullremover(String.valueOf(leadMaster.getContactPerson())).equalsIgnoreCase
		 * ("")) { for (String str1 :
		 * (leadMaster.getContactPerson().toString()).split(",")) {
		 * 
		 * ContactPerson cplistemp =
		 * contactPersonService.findById(Integer.parseInt(str1)); contactPersonobj =
		 * cplistemp; break;
		 * 
		 * }
		 * 
		 * }
		 */
		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("leadMaster", leadMaster);

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);
		themodel.addAttribute("save", true);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		return "leadevents";
	}

	@ResponseBody
	@PostMapping("leadeventpart2save")
	public String leadeventpart2save(@RequestParam Map<String, String> params, Model themodel,

			HttpServletRequest request) {

		System.out.println(params);

		return "leadevents";

	}

	@ResponseBody
	@PostMapping("activitymarkascompleted")
	public String activitymarkascompleted(@RequestParam Map<String, String> params) {

		String activityid = params.get("activityid");
		ActivityMaster actimaster = activityMasterService.findById(Integer.parseInt(activityid));
		actimaster.setStatus("Completed");
		activityMasterService.save(actimaster);
		return "";
	}

	@ResponseBody
	@PostMapping("getboarddetails")
	public ProjectTemplateBoard getboarddetails(@RequestParam Map<String, String> params) {

		String boardid = params.get("boardid");
		return projectTemplateBoardService.findById(Integer.parseInt(boardid));

	}

	@ResponseBody
	@PostMapping("gettimelinelist")
	public String gettimelinelist(@RequestParam Map<String, String> params) {

		String mastercategoryid = params.get("mastercategoryid");
		String categoryType = params.get("categoryType");
		String statusweb = params.get("status");
		List<Map<String, Object>> ls = activityMasterService.gettimelinelist(categoryType, mastercategoryid, statusweb);

		String[] result = { "" };

		ls.forEach(rowMap -> {
			// -------------------------------------------------------------
			// Label BG Color
			Random rand = new Random();
			int stateNum = rand.nextInt(6);
			String[] states = { "success", "danger", "warning", "info", "dark", "primary", "secondary" };
			String state = states[stateNum];
			// -------------------------------------------------------------
			String activitytitle = String.valueOf(rowMap.get("activitytitle"));
			String activitytype = nullremover(String.valueOf(rowMap.get("activitytype"))).toUpperCase();
			String startdate = nullremover(String.valueOf(rowMap.get("startdate")));
			String starttime = nullremover(String.valueOf(rowMap.get("starttime")));
			String enddate = nullremover(String.valueOf(rowMap.get("enddate")));
			String endtime = nullremover(String.valueOf(rowMap.get("endtime")));
			String location = nullremover(String.valueOf(rowMap.get("location")));
			String description = nullremover(String.valueOf(rowMap.get("description")));
			String notes = String.valueOf(rowMap.get("notes"));
			String htmlnotes = String.valueOf(rowMap.get("htmlnotes"));
			String followers = nullremover(String.valueOf(rowMap.get("activityfollowers")));
			String status = nullremover(String.valueOf(rowMap.get("status")));
			ActivityMaster actimaster = activityMasterService
					.findById(Integer.parseInt(String.valueOf(rowMap.get("activity_id"))));
			// -------------------------------------------
			// guest Details
			String guestdetails = "<ul class='list-unstyled users-list d-flex align-items-center avatar-group m-0  me-2 guestdetailslist' >";
			List<ActivityMasterGuest> guestlist = actimaster.getActivityMasterGuest();
			for (ActivityMasterGuest gobj : guestlist) {
				if (gobj.getGuestid() != null) {
					EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(gobj.getGuestid()));

					if (empobj != null) {
						String empphotos = "<button type='button' class='step-trigger' aria-selected='false' disabled='disabled'>  <span class='bs-stepper-circle'><i class='bx bx-user'></i></span></button>";

						List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
								.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo"))
								.collect(Collectors.toList());
						if (validProfilephoto.size() > 0) {

							empphotos = validProfilephoto.get(0).getFilePath();
						}

						guestdetails += "<li data-bs-toggle='tooltip' data-popup='tooltip-custom' data-bs-placement='top' class='avatar  pull-up tooltipx'>"
								+ " <img class='rounded-circle' src='" + empphotos
								+ " ' alt='Avatar'><span class='tooltiptextx'>" + empobj.getStaffName()
								+ "</span></li>";
					}
				}
			}
			guestdetails += "</ul>";
			// -------------------------------------------
			// Activity File
			String filedetails = "";
			for (ActivityMasterFiles aobj : actimaster.getActivityMasterFiles()) {

				filedetails += "<a href='" + aobj.getFiles_Attach() + "' target='_blank' title='"
						+ (aobj.getFiles_Attach()).toString().substring(29, aobj.getFiles_Attach().length()) + "'>";

				String[] arrOfStr = String.valueOf(aobj.getFiles_Attach()).split("\\.");

				if (arrOfStr.length > 0) {
					String filevarpath = arrOfStr[1];

					if (filevarpath.equalsIgnoreCase("pdf") == true) {
						filedetails += "<img src='assets/img/icons/misc/pdf.png' alt='PDF image' width='20' class='me-2'>";
					} else {
						filedetails += "<img src='assets/img/icons/misc/jpg.png' alt='jp image' width='20' class='me-2'>";
					}
				}
				filedetails += (aobj.getFiles_Attach()).toString().substring(29, aobj.getFiles_Attach().length())
						+ "</a>";

			}
			// -------------------------------------------

			String followerdetails = "<ul class='list-unstyled users-list d-flex align-items-center avatar-group m-0  me-2 followerdetailslist' >";
			EmployeeMaster empobj = null;
			for (String str : followers.split(",")) {
				if (!str.equalsIgnoreCase("")) {
					empobj = employeeMasterService.findById(Integer.parseInt(str));

					if (empobj != null) {

						if (empobj != null) {
							String empphotos = "";

							List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
									.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo"))
									.collect(Collectors.toList());
							if (validProfilephoto.size() > 0) {

								empphotos = validProfilephoto.get(0).getFilePath();
								followerdetails += "<li data-bs-toggle='tooltip' data-popup='tooltip-custom' data-bs-placement='top' class='avatar  pull-up tooltipx' >"
										+ " <img class='rounded-circle' src='" + empphotos + "'"
										+ "  alt='Avatar'><span class='tooltiptextx'>" + empobj.getStaffName()
										+ "</span></li>";

							} else {

								String name = empobj.getStaffName();
								String[] initials = name.split("\\s+");

								empphotos = "<span class='avatar-initial rounded-circle bg-label-" + state + "'>"
										+ String.valueOf(
												initials[0].charAt(0) + "" + initials[initials.length - 1].charAt(0))
												.toUpperCase()
										+ "</span>";

								followerdetails += "<li data-bs-toggle='tooltip' data-popup='tooltip-custom' data-bs-placement='top' class='avatar  pull-up tooltipx' >"
										+ empphotos + "  <span class='tooltiptextx'>" + empobj.getStaffName()
										+ "</span></li>";
							}

						}

					}
				}

			}
			followerdetails += "</ul>";
			// -------------------------------------------
			if (status.equalsIgnoreCase("Completed")) {
				status = "";
			} else {
				status = "<button type='button' id='" + actimaster.getActivityId()
						+ "' class='btn rounded-pill btn-iconx btn-outline-success tooltipx markitascompleted'> <span class='tooltiptextx'>Mark as Completed</span></button>";

			}
			// -------------------------------------------
			// time calculator
			String timecalculator = "";
			/*
			 * LocalDateTime currentdatetime=
			 * LocalDateTime.parse(String.valueOf(rowMap.get("sorteddates")).replace(".0",
			 * "").trim().replace(" ", "T")); LocalDateTime sorteddatesordered=
			 * LocalDateTime.parse(String.valueOf(rowMap.get("sorteddates")).replace(".0",
			 * "").trim().replace(" ", "T")); Duration duration =
			 * Duration.between(sorteddatesordered, currentdatetime);
			 * 
			 * long differdays =ChronoUnit.DAYS.between(currentdatetime.toLocalDate(),
			 * sorteddatesordered.toLocalDate()); long differmins = duration.toMinutes();
			 * long differhr = ChronoUnit.HOURS.between(currentdatetime,
			 * sorteddatesordered);
			 */

			long differdays = Long.parseLong(String.valueOf(rowMap.get("differdays")));
			String differtime = String.valueOf(rowMap.get("differtime"));
			long differmins = 0;
			long differhr = 0;

			if (!nullremover(String.valueOf(rowMap.get("differmins"))).equalsIgnoreCase("")) {
				differmins = Long.parseLong(String.valueOf(rowMap.get("differmins")));
			}

			if (!nullremover(String.valueOf(rowMap.get("differhr"))).equalsIgnoreCase("")) {
				differhr = Long.parseLong(String.valueOf(rowMap.get("differhr")));
			}

			String sorteddates = (String) rowMap.get("newsorteddates");

			if (nullremover(String.valueOf(activitytitle)).equalsIgnoreCase("")) {
				try {
					timecalculator = displaydateFormatFirstMMMddYYYAMPM
							.format(displaydatetimeFormat.parse(String.valueOf(rowMap.get("createdtime")))).toString();
				} catch (Exception e) {
					// e.printStackTrace();
				}
			} else {
				if (differdays > 30) {

					try {
						timecalculator = displaydateFormatFirstMMMddYYYAMPM
								.format(displaydatetimeFormat.parse(sorteddates + " " + starttime)).toString() + " "
								+ displaydateFormatAMPM.format(displaydateFormathhmm.parse(starttime)).toString()
										.toUpperCase();

					} catch (Exception e) {
						// e.printStackTrace();
					}

				} else {
					String temp = "";
					try {
						temp = displaydateFormatAMPM.format(displaydateFormathhmm.parse(starttime)).toString()
								.toUpperCase();
					} catch (ParseException e) {
						// e.printStackTrace();
					}

					if (differdays == -1) {
						timecalculator = " Tomorrow " + temp;
					} else if (differdays < -1) {
						timecalculator = " Next Coming in  " + differdays + " days " + temp;
					} else if (differdays > 0) {
						timecalculator = differdays + " days ago " + temp;
					} else {
						timecalculator = differhr + "hrs " + differmins + "  mins ago " + temp;
					}
				}
			}
			// -------------------------------------------------
			String eventicon = "bx-note";
			if (activitytype.equalsIgnoreCase("Call")) {
				eventicon = "bx-phone-call";
			} else if (activitytype.equalsIgnoreCase("Task")) {
				eventicon = "bx-alarm-add";
			} else if (activitytype.equalsIgnoreCase("Meetings")) {
				eventicon = "bx-group";
			}

			if (nullremover(String.valueOf(activitytitle)).equalsIgnoreCase("")) {
				result[0] += " <li class='timeline-item timeline-item-transparent'>  <span class='timeline-indicator timeline-indicator-"
						+ state + "'><i class='bx " + eventicon
						+ "'></i></span> <div class='timeline-event'><div class='timeline-header'>";
				result[0] += "<h6 class='mb-0'><span  id='actid" + actimaster.getActivityId()
						+ "' class='editnoteactivity' title='Edit the event detials' onclick='editnoteactivity(this)'>"
						+ htmlnotes + "</span></h6>";
				result[0] += "</div><p class='text-muted'>" + timecalculator + "</p></div> </li>";
			} else {
				result[0] += " <li class='timeline-item timeline-item-transparent'> <span class='timeline-indicator timeline-indicator-"
						+ state + "'><i class='bx " + eventicon
						+ "'></i></span><div class='timeline-event'><div class='timeline-header'>";
				result[0] += "<h6 class='mb-0'>" + status + " <span  id='actid" + actimaster.getActivityId()
						+ "' class='editactivity' title='Edit the event detials' onclick='editactivity(this)'>"
						+ activitytitle + "</span></h6></div><p class='text-muted'>" + timecalculator
						+ "</p> <div class='d-flex justify-content-between flex-wrap mb-2'><div><span>"
						+ " </span><div class='timeline-content'>";

				if (!nullremover(notes).equalsIgnoreCase("")) {
					result[0] += "<p class='mb-2'>" + notes + "<br/></p>";
				}

				result[0] += "</div>";

				if (!filedetails.equalsIgnoreCase("")) {
					result[0] += "<p class='mb-2'>" + filedetails + "</p>";
				}

				if (!followerdetails.equalsIgnoreCase("")) {
					result[0] += followerdetails;
				}

				result[0] += "</li>";
			}

		});

		return result[0] + "<li class='timeline-end-indicator'>  <i class='bx bx-badge-check'></i> </li>";
	}

	public String nullremover(String str) {

		return str.toLowerCase().replace("null", "");
	}

	@PostMapping("convertodeal")
	public String convertodeal(@RequestParam Map<String, String> params) {
		int leadmasterid = Integer.parseInt(params.get("leadmasterid"));
		LeadMaster leadobj = leadMasterService.findById(leadmasterid);
		leadobj.setMovedtolead(true);
		leadMasterService.save(leadobj);

		DealMaster dealobj = new DealMaster();
		// dealobj.setContactPerson(leadobj.getContactPerson());
		dealobj.setOrganization(leadobj.getOrganization());
		dealobj.setTitle(leadobj.getTitle());
		dealobj.setSource(leadobj.getSource());
		dealobj.setReference(leadobj.getReference());
		dealobj.setNotes(leadobj.getNotes());
		// dealobj.setFollower(leadobj.getFollower());
		dealobj.setLeadid(leadobj.getId());
		dealobj.setPipeline("Deal In");
		dealobj.setCreateddate(displaydatetimeFormat.format(new Date()));
		dealMasterService.save(dealobj);

		return "redirect:/deal?new";
	}

	@ResponseBody
	@PostMapping("convertolead")
	public String convertolead(@RequestParam Map<String, String> params) {
		int dealmasterid = Integer.parseInt(params.get("ids"));

		DealMaster dealobj = dealMasterService.findById(dealmasterid);

		if (dealobj.getLeadid() == 0) {
			LeadMaster leadobj = new LeadMaster();
			// leadobj.setContactPerson(dealobj.getContactPerson());
			leadobj.setOrganization(dealobj.getOrganization());
			leadobj.setTitle(dealobj.getTitle());
			leadobj.setSource(dealobj.getSource());
			leadobj.setReference(dealobj.getReference());
			leadobj.setNotes(dealobj.getNotes());
			// leadobj.setFollower(dealobj.getFollower());
			leadobj.setCreateddate(displaydatetimeFormat.format(new Date()));
			leadobj.setMovedtolead(false);
			leadobj.setBackfromdeal(true);
			leadobj = leadMasterService.save(leadobj);

			dealobj.setLeadid(leadobj.getId());
			dealMasterService.save(dealobj);
		} else {
			LeadMaster leadobj = leadMasterService.findById(dealobj.getLeadid());
			leadobj.setMovedtolead(false);
			leadobj.setBackfromdeal(true);
			leadobj = leadMasterService.save(leadobj);
		}

		return "";
	}

	@GetMapping("deallist")
	public String deal(Model themodel) {

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("deal"));

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmlist);

		return "deallist";
	}

	@GetMapping("projectlist")
	public String project(Model themodel) {

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmlist);

		themodel.addAttribute("board", projectTemplateBoardService.findAll());

		return "projectlist";
	}

	@PostMapping("getdistrictlist")
	@ResponseBody
	public String getdistrictlist(@RequestParam("state") String state) {
		List<String> districtlist = dealMasterService.getDistrictAll(state);
		String output = "";
		for (String str : districtlist) {
			output += "<option value='" + str + "'>" + str + "</option>";
		}
		return output;
	}

	@GetMapping("projecttemplatelist")
	public String projecttemplatelist(Model themodel) {
		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		return "projecttemplatelist";
	}

	@GetMapping("projecttemplate")
	public String projecttemplate(Model themodel,
			@RequestParam(name = "id", required = false, defaultValue = "0") Integer id) {
		ProjectTemplatePhase proobj = new ProjectTemplatePhase();

		if (id > 0) {
			proobj = projectTemplateMasterService.findById(id);
		}
		themodel.addAttribute("projecttemplateobject", proobj);

		return "projecttemplate";
	}

	@ResponseBody
	@PostMapping("projecttemplatesave")

	public String projecttemplatesave(Model themodel, @RequestParam Map<String, String> params) {

		// System.out.println(params);

		if (params.containsKey("boardName")) {

			ProjectTemplateBoard projectTemplateBoard = new ProjectTemplateBoard();
			projectTemplateBoard.setBoardName(params.get("boardName"));
			projectTemplateBoardService.save(projectTemplateBoard);
		} else {

			ProjectTemplateBoard projectTemplateBoard = projectTemplateBoardService
					.findById(Integer.parseInt(params.get("boardid")));

			List<ProjectTemplatePhase> ProjectTemplatePhaselist = projectTemplateBoard.getProjectTemplatePhase();

			if (Integer.parseInt(params.get("Model_phase_id")) == 0) {
				ProjectTemplatePhase ptpobj = new ProjectTemplatePhase();
				ptpobj.setPhaseName(params.get("Model_Phasename"));
				ptpobj.setOrderID(Integer.parseInt(params.get("Model_orderID")));
				ProjectTemplatePhaselist.add(ptpobj);
				projectTemplateBoard.setProjectTemplatePhase(ProjectTemplatePhaselist);
			} else {
				List<ProjectTemplatePhase> ProjectTemplatePhaselisttemp = new ArrayList<>();

				for (ProjectTemplatePhase temp1 : ProjectTemplatePhaselist) {
					if (temp1.getId() == Integer.parseInt(params.get("Model_phase_id"))) {
						temp1.setPhaseName(params.get("Model_Phasename"));
						temp1.setOrderID(Integer.parseInt(params.get("Model_orderID")));
					}

					ProjectTemplatePhaselisttemp.add(temp1);
				}
				projectTemplateBoard.setProjectTemplatePhase(ProjectTemplatePhaselisttemp);
			}

			projectTemplateBoardService.save(projectTemplateBoard);
		}
		return "";
	}

	@ResponseBody
	@PostMapping("projectphasessave")

	public String projectphasessave(Model themodel, @RequestParam Map<String, String> params) {

		// System.out.println(params);
		ProjectMaster pmobj = projectMasterService.findById(Integer.parseInt(params.get("projectid")));

		List<ProjectPhases> pphls = pmobj.getProjectPhases();

		ProjectPhases ptpobj = new ProjectPhases();
		ptpobj.setPhaseName(params.get("Model_Phasename"));
		ptpobj.setOrderID(Integer.parseInt(params.get("Model_orderID")));
		pphls.add(ptpobj);
		pmobj.setProjectPhases(pphls);

		projectMasterService.save(pmobj);

		return "";
	}

	@ResponseBody
	@PostMapping("templatephasesave")
	public String templatephasesave(Model themodel, @RequestParam Map<String, String> params) {

		ProjectTemplateBoard projectTemplateBoard = new ProjectTemplateBoard();
		projectTemplateBoard.setBoardName(params.get("boardName"));
		projectTemplateBoardService.save(projectTemplateBoard);

		return "";
	}

	@ResponseBody
	@GetMapping("projecttemplatelistjson")
	public List<ProjectTemplateBoard> projecttemplatelistjson(Model themodel) {

		return projectTemplateBoardService.findAll();
	}

	@GetMapping("projecttemplateview")
	public String projecttemplateview(Model theModel, @RequestParam("id") int id) {
		ProjectTemplateBoard projectTemplateBoard = projectTemplateBoardService.findById(id);

		theModel.addAttribute("projectTemplateBoard", projectTemplateBoard);
		theModel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		theModel.addAttribute("projectTemplateactivityMaster", new ProjectTemplateActivityMaster());
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		return "projecttemplate";
	}

	@ResponseBody
	@PostMapping("getactivitydetails")
	public ActivityMaster getactivitydetails(@RequestParam("name") int id) {

		ActivityMaster amlist = activityMasterService.findById(id);
		return amlist;

	}

	@ResponseBody
	@PostMapping("activitydelete")
	public void deleteactivityids(@RequestParam("deleteid") int id) {

		activityMasterService.deletebyid(id);
	}

	@ResponseBody
	@PostMapping("addtaskdetails")
	public int addtaskdetails(@RequestParam("tskid") int projectdetailid, @RequestParam("tskname") String taskname) {

		return projectMasterService.addnewtask(projectdetailid, taskname);
	}

	@GetMapping("projectview")
	public String projectview(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}

		if (!followerids.equalsIgnoreCase("")) {
			followerids = followerids.substring(0, followerids.length() - 1);
		}

		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}
		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("activityMaster", new ActivityMaster());
		theModel.addAttribute("board", projectTemplateBoardService.findAll());

		return "projectevents";
	}

	@GetMapping("projectattachment")
	public String projectattachment(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		// theModel.addAttribute("OrganizationContacts", corg);
		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("board", projectTemplateBoardService.findAll());
		return "projectattachment";
	}

	@GetMapping("projectaccounts")
	public String projectaccounts(Model theModel, @RequestParam("id") int id) {
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");

		/*
		 * String NATUREOFWORKtemp=""; for(String str: NATUREOFWORK) { NATUREOFWORKtemp=
		 * NATUREOFWORKtemp.concat("<option value='"+ str + "'>"+ str+ "</option>"); }
		 */
		theModel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		/*
		 * String UNITStemp=""; for(String str: UNITS) { UNITStemp=
		 * UNITStemp.concat("<option value=\'"+ str + "\'>"+ str+ "</option>"); }
		 */
		theModel.addAttribute("UNITS", UNITS);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("board", projectTemplateBoardService.findAll());
		theModel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		theModel.addAttribute("expenselist", getaaccountsHeads_Expenses());

		return "projectaccounts";
	}

	@GetMapping("projectiteminfo")
	public String projectproject(Model theModel, @RequestParam("id") int id) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		theModel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		theModel.addAttribute("UNITS", UNITS);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("board", projectTemplateBoardService.findAll());
		return "projectiteminfo";
	}

	@ResponseBody
	@PostMapping("projectinvoicesave")
	public ProjectMaster projectinvoicesave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));
		List<InvoiceMaster> invls = new ArrayList();

		String tempinvoiceid = nullremover(String.valueOf(params.get("invoiceid")));

		if (!tempinvoiceid.equalsIgnoreCase("")) {
			List<InvoiceMaster> ls = new ArrayList();

			for (InvoiceMaster invm : pm.getInvoiceList()) {
				if (invm.getInvoiceid() == Integer.parseInt(tempinvoiceid)) {
					invm.setBilladdressline1(String.valueOf(params.get("billaddressline1")));
					invm.setBilladdressline2(String.valueOf(params.get("billaddressline2")));
					invm.setBillcity(String.valueOf(params.get("billaddresscity")));
					invm.setBillEmail(String.valueOf(params.get("billEmail")));
					invm.setBillGSTNo(String.valueOf(params.get("billGSTNo")));
					invm.setBillMobileno(String.valueOf(params.get("billMobileno")));
					invm.setBillpincode(String.valueOf(params.get("billaddresspincode")));
					invm.setBillstate(String.valueOf(params.get("billaddressState")));
					invm.setDueDate(String.valueOf(params.get("dueDate")));
					invm.setGSTCode(String.valueOf(params.get("GSTCode")));
					invm.setInvoiceaddresscity(String.valueOf(params.get("invoiceaddresscity")));
					invm.setInvoiceaddressline1(String.valueOf(params.get("invoiceaddressline1")));
					invm.setInvoiceaddressline2(String.valueOf(params.get("invoiceaddressline2")));
					invm.setInvoiceaddresspincode(String.valueOf(params.get("invoiceaddresspincode")));
					invm.setInvoiceaddressState(String.valueOf(params.get("invoiceaddressState")));
					invm.setInvoiceDate(String.valueOf(params.get("invoiceDate")));
					invm.setInvoiceEmail(String.valueOf(params.get("invoiceEmail")));
					invm.setInvoiceMobileno(String.valueOf(params.get("invoiceMobileno")));
					invm.setInvoiceType(String.valueOf(params.get("invoiceType")));
					invm.setInvoiceGSTNo(String.valueOf(params.get("invoiceGSTNo")));
					invm.setNotes(String.valueOf(params.get("note")));
					invm.setRvsaddress("29, Palani Illam, Sundaram Brothers Layout,Ramanathapuram, Tamil Nadu 641045");
					invm.setReceivable(String.valueOf(params.get("receivable")));
					invm.setInvoiceNo(String.valueOf(params.get("invoiceNo")));

					List<InvoiceItemMaster> invitemls = new ArrayList();
					for (int i = 1; i <= Integer.parseInt(params.get("invoiceitemcount")); i++) {

						InvoiceItemMaster initem = new InvoiceItemMaster();
						int invitemids = 0;
						if (!nullremover(String.valueOf(params.get("InvoiceItemid" + i))).equalsIgnoreCase("")) {
							invitemids = Integer.parseInt(params.get("InvoiceItemid" + i));
							final int tempi = i;
							initem = invm.getInvoiceItemMasterlist().stream().filter(
									C -> C.getInvoiceitemid() == Integer.parseInt(params.get("InvoiceItemid" + tempi)))
									.collect(Collectors.toList()).get(0);
						}

						invitemls.add(addupdatedInvoiceMaster(params, initem, i, invitemids));
					}
					invm.setInvoiceItemMasterlist(invitemls);

				}
				ls.add(invm);

			}
			pm.setInvoiceList(ls);

		} else {
			InvoiceMaster newinv = new InvoiceMaster();

			newinv.setBilladdressline1(String.valueOf(params.get("billaddressline1")));
			newinv.setBilladdressline2(String.valueOf(params.get("billaddressline2")));
			newinv.setBillcity(String.valueOf(params.get("billaddresscity")));
			newinv.setBillEmail(String.valueOf(params.get("billEmail")));
			newinv.setBillGSTNo(String.valueOf(params.get("billGSTNo")));
			newinv.setBillMobileno(String.valueOf(params.get("billMobileno")));
			newinv.setBillpincode(String.valueOf(params.get("billaddresspincode")));
			newinv.setBillstate(String.valueOf(params.get("billaddressState")));
			newinv.setDueDate(String.valueOf(params.get("dueDate")));
			newinv.setGSTCode(String.valueOf(params.get("GSTCode")));
			newinv.setInvoiceaddresscity(String.valueOf(params.get("invoiceaddresscity")));
			newinv.setInvoiceaddressline1(String.valueOf(params.get("invoiceaddressline1")));
			newinv.setInvoiceaddressline2(String.valueOf(params.get("invoiceaddressline2")));
			newinv.setInvoiceaddresspincode(String.valueOf(params.get("invoiceaddresspincode")));
			newinv.setInvoiceaddressState(String.valueOf(params.get("invoiceaddressState")));
			newinv.setInvoiceDate(String.valueOf(params.get("invoiceDate")));
			newinv.setInvoiceEmail(String.valueOf(params.get("invoiceEmail")));
			newinv.setInvoiceMobileno(String.valueOf(params.get("invoiceMobileno")));
			newinv.setInvoiceType(String.valueOf(params.get("invoiceType")));
			newinv.setInvoiceGSTNo(String.valueOf(params.get("invoiceGSTNo")));
			newinv.setNotes(String.valueOf(params.get("note")));
			newinv.setRvsaddress("29, Palani Illam, Sundaram Brothers Layout,Ramanathapuram, Tamil Nadu 641045");
			newinv.setReceivable(String.valueOf(params.get("receivable")));
			newinv.setInvoiceNo(String.valueOf(params.get("invoiceNo")));

			List<InvoiceItemMaster> invitemls = new ArrayList();
			for (int i = 1; i <= Integer.parseInt(params.get("invoiceitemcount")); i++) {

				invitemls.add(addupdatedInvoiceMaster(params, new InvoiceItemMaster(), i, 0));
			}
			newinv.setInvoiceItemMasterlist(invitemls);

			pm.getInvoiceList().add(newinv);
		}

		return projectMasterService.save(pm);
	}

	public InvoiceItemMaster addupdatedInvoiceMaster(Map<String, String> params, InvoiceItemMaster invitemmaster,
			int index, int itemid) {

		invitemmaster.setInvoiceitemid(itemid);

		double price = Double.parseDouble(String.valueOf(params.get("Price" + index)));
		double qty = Double.parseDouble(String.valueOf(params.get("Quantity" + index)));
		double taxableAmount = price * qty;

		double Discountper = Double.parseDouble(String.valueOf(params.get("Discountper" + index)));
		double CGSTper = Double.parseDouble(String.valueOf(params.get("CGSTper" + index)));
		double SGSTper = Double.parseDouble(String.valueOf(params.get("SGSTper" + index)));
		double IGSTper = Double.parseDouble(String.valueOf(params.get("IGSTper" + index)));

		double dt = Discountper / 100;
		double Discountamt = taxableAmount * dt;
		double afetdiscountamount = taxableAmount - Discountamt;

		double it = IGSTper / 100;
		double ct = CGSTper / 100;
		double st = SGSTper / 100;
		double IGSTamount = afetdiscountamount * it;
		double SGSTamount = afetdiscountamount * st;
		double CGSTamount = afetdiscountamount * ct;

		invitemmaster.setDiscountamt(Discountamt);
		invitemmaster.setDiscountper(Discountper);

		invitemmaster.setCGSTamount(CGSTamount);
		invitemmaster.setCGSTper(CGSTper);

		invitemmaster.setIGSTamount(IGSTamount);
		invitemmaster.setIGSTper(IGSTper);

		invitemmaster.setSGSTamount(SGSTamount);
		invitemmaster.setSGSTper(SGSTper);

		invitemmaster.setInvoiceItem(String.valueOf(params.get("InvoiceItem" + index)));
		invitemmaster.setDescription(String.valueOf(params.get("Description" + index)));
		invitemmaster.setQuantity(qty);
		invitemmaster.setPrice(price);
		invitemmaster.setUnit(String.valueOf(params.get("Unit" + index)));

		invitemmaster.setTaxableAmount(afetdiscountamount);
		invitemmaster.setTotalamountAmount(afetdiscountamount + CGSTamount + SGSTamount + IGSTamount);

		return invitemmaster;
	}

	@PostMapping("getprojectinvoicelist")
	@ResponseBody
	public List<InvoiceMaster> getprojectinvoicelist(@RequestParam Map<String, String> params) {

		List<InvoiceMaster> ls = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getInvoiceList();
		for (InvoiceMaster obj : ls) {
			try {
				obj.setInvoiceDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getInvoiceDate())).toString());
				obj.setDueDateMMMddyyyy(
						displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getprojectpurchaselist")
	@ResponseBody
	public List<ProjectpurchaseMaster> getprojectpurchaselist(@RequestParam Map<String, String> params) {

		List<ProjectpurchaseMaster> ls = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getProjectpurchaseMasterList();

		for (ProjectpurchaseMaster obj : ls) {
			try {
				obj.setProjectpurchaseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getProjectpurchaseDate())).toString());
				obj.setDueDateMMMddyyyy(
						displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getinvoiceitem")
	@ResponseBody
	public InvoiceMaster getinvoiceitem(@RequestParam Map<String, String> params) {

		InvoiceMaster obj = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getInvoiceList().stream().filter(C -> C.getInvoiceid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

		try {
			obj.setInvoiceDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getInvoiceDate())).toString());
			obj.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return obj;

	}

	@PostMapping("getpurchaseitem")
	@ResponseBody
	public ProjectpurchaseMaster getpurchaseitem(@RequestParam Map<String, String> params) {

		ProjectpurchaseMaster obj = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getProjectpurchaseMasterList().stream()
				.filter(C -> C.getProjectpurchaseid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

		try {
			obj.setProjectpurchaseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
					.format(displaydateFormatrev.parse(obj.getProjectpurchaseDate())).toString());
			obj.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		for (ProjectpurchaseItemMaster expensechild : obj.getProjectpurchaseItemMasterlist()) {
			Accountsheads ahobj = accountheadsService.findById(Integer.parseInt(expensechild.getProjectpurchaseItem()));
			expensechild.setProjectpurchaseItem_name(ahobj.getCategory());
		}

		return obj;

	}

	@PostMapping("getprojectreceiptlist")
	@ResponseBody
	public List<InvoiceReceiptMaster> getprojectreceiptlist(@RequestParam Map<String, String> params) {

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));
		List<InvoiceReceiptMaster> ls = pm.getReceiptList();
		List<InvoiceMaster> invls = pm.getInvoiceList();

		for (InvoiceReceiptMaster obj : ls) {
			try {
				obj.setRecepitDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());

				obj.setInvoiceNo(invls.stream().filter(C -> C.getInvoiceid() == Integer.parseInt(obj.getInvoiceid()))
						.collect(Collectors.toList()).get(0).getInvoiceNo());
				obj.setDepositedto_txt(
						accountheadsService.findById(Integer.parseInt(obj.getDepositedto())).getCategory());
			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getprojectpurpaymentlist")
	@ResponseBody
	public List<ProjectpurchasePaymentMaster> getprojectpurpaymentlist(@RequestParam Map<String, String> params) {

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));
		List<ProjectpurchasePaymentMaster> ls = pm.getPurchasePaymentMasterList();
		List<ProjectpurchaseMaster> invls = pm.getProjectpurchaseMasterList();

		for (ProjectpurchasePaymentMaster obj : ls) {
			try {
				obj.setRecepitDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());

				obj.setPurchaseNo(
						invls.stream().filter(C -> C.getProjectpurchaseid() == Integer.parseInt(obj.getPurchaseid()))
								.collect(Collectors.toList()).get(0).getProjectpurchaseNo());

				obj.setDepitedfrom_txt(
						accountheadsService.findById(Integer.parseInt(obj.getDepitedfrom())).getCategory());

			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getreceiptitem")
	@ResponseBody
	public InvoiceReceiptMaster getreceiptitem(@RequestParam Map<String, String> params) {

		InvoiceReceiptMaster obj = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getReceiptList().stream().filter(C -> C.getRecepitid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

		try {
			obj.setRecepitDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return obj;

	}

	@PostMapping("getpurpaymentreceiptitem")
	@ResponseBody
	public ProjectpurchasePaymentMaster getpurpaymentreceiptitem(@RequestParam Map<String, String> params) {

		ProjectpurchasePaymentMaster obj = projectMasterService
				.findById(Integer.parseInt(params.get("mastercategoryid"))).getPurchasePaymentMasterList().stream()
				.filter(C -> C.getRecepitid() == Integer.parseInt(params.get("projectid"))).collect(Collectors.toList())
				.get(0);

		try {
			obj.setRecepitDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return obj;

	}

	@PostMapping("getinvoicereceiptitem")
	@ResponseBody
	public Map<String, String> getinvoicereceiptitem(@RequestParam Map<String, String> params) {

		Map<String, String> map = new HashMap<>();

		double totalpaidamount = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getReceiptList().stream().filter(C -> C.getInvoiceid().equalsIgnoreCase(params.get("invoiceid")))
				.mapToDouble(InvoiceReceiptMaster::getAmount).sum();

		map.put("totalpaidamount", String.valueOf(totalpaidamount));

		InvoiceMaster inv = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getInvoiceList().stream().filter(C -> C.getInvoiceid() == Integer.parseInt(params.get("invoiceid")))
				.collect(Collectors.toList()).get(0);

		try {
			inv.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(inv.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		double totalinvoiceamount = inv.getInvoiceItemMasterlist().stream()
				.mapToDouble(InvoiceItemMaster::getTotalamountAmount).sum();
		map.put("Invoiceno", inv.getInvoiceNo());
		map.put("duedate", inv.getDueDateMMMddyyyy());
		map.put("amount", String.valueOf(totalinvoiceamount));
		map.put("balanceamount", String.valueOf(totalinvoiceamount - totalpaidamount));
		map.put("invoiceid", String.valueOf(inv.getInvoiceid()));

		return map;

	}

	@PostMapping("getpurchasepaymentMasteritem")
	@ResponseBody
	public Map<String, String> getpurchasepaymentMasteritem(@RequestParam Map<String, String> params) {

		Map<String, String> map = new HashMap<>();

		double totalpaidamount = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getPurchasePaymentMasterList().stream()
				.filter(C -> C.getPurchaseid().equalsIgnoreCase(params.get("projectpurchaseid")))
				.mapToDouble(ProjectpurchasePaymentMaster::getAmount).sum();

		map.put("totalpaidamount", String.valueOf(totalpaidamount));

		ProjectpurchaseMaster inv = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getProjectpurchaseMasterList().stream()
				.filter(C -> C.getProjectpurchaseid() == Integer.parseInt(params.get("projectpurchaseid")))
				.collect(Collectors.toList()).get(0);

		try {
			inv.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(inv.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		double totalinvoiceamount = inv.getProjectpurchaseItemMasterlist().stream()
				.mapToDouble(ProjectpurchaseItemMaster::getTotalamountAmount).sum();
		map.put("purchaseno", inv.getProjectpurchaseNo());
		map.put("duedate", inv.getDueDateMMMddyyyy());
		map.put("amount", String.valueOf(totalinvoiceamount));
		map.put("balanceamount", String.valueOf(totalinvoiceamount - totalpaidamount));
		map.put("purchaseid", String.valueOf(inv.getProjectpurchaseid()));

		return map;

	}

	@PostMapping("getpurchasepaymentitem")
	@ResponseBody
	public Map<String, String> getpurchasepaymentitem(@RequestParam Map<String, String> params) {

		Map<String, String> map = new HashMap<>();

		double totalpaidamount = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getPurchasePaymentMasterList().stream()
				.filter(C -> C.getPurchaseid().equalsIgnoreCase(params.get("projectpurchaseid")))
				.mapToDouble(ProjectpurchasePaymentMaster::getAmount).sum();

		map.put("totalpaidamount", String.valueOf(totalpaidamount));

		ProjectpurchaseMaster inv = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getProjectpurchaseMasterList().stream()
				.filter(C -> C.getProjectpurchaseid() == Integer.parseInt(params.get("projectpurchaseid")))
				.collect(Collectors.toList()).get(0);

		try {
			inv.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(inv.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		double totalinvoiceamount = inv.getProjectpurchaseItemMasterlist().stream()
				.mapToDouble(ProjectpurchaseItemMaster::getTotalamountAmount).sum();
		map.put("purchaseno", inv.getProjectpurchaseNo());
		map.put("duedate", inv.getDueDateMMMddyyyy());
		map.put("amount", String.valueOf(totalinvoiceamount));
		map.put("balanceamount", String.valueOf(totalinvoiceamount - totalpaidamount));
		map.put("purchaseid", String.valueOf(inv.getProjectpurchaseid()));

		return map;

	}

	@ResponseBody
	@PostMapping("projectreceiptsave")
	public ProjectMaster projectreceiptsave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));
		List<InvoiceReceiptMaster> invls = new ArrayList();

		String tempreceiptid = nullremover(String.valueOf(params.get("recepitid")));

		if (!tempreceiptid.equalsIgnoreCase("")) {
			List<InvoiceReceiptMaster> ls = new ArrayList();

			for (InvoiceReceiptMaster invm : pm.getReceiptList()) {
				if (invm.getRecepitid() == Integer.parseInt(tempreceiptid)) {

					invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
					invm.setAmount(Double.parseDouble(params.get("amount")));
					invm.setDepositedto(String.valueOf(params.get("depositedto")));
					invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
					invm.setNotes(String.valueOf(params.get("notes")));
					invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
				}
				ls.add(invm);

			}
			pm.setReceiptList(ls);
		} else {
			InvoiceReceiptMaster invm = new InvoiceReceiptMaster();

			invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
			invm.setAmount(Double.parseDouble(params.get("amount")));
			invm.setDepositedto(String.valueOf(params.get("depositedto")));
			invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
			invm.setNotes(String.valueOf(params.get("notes")));
			invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
			invm.setInvoiceid(String.valueOf(params.get("recinvoiceid")));

			pm.getReceiptList().add(invm);
		}

		return projectMasterService.save(pm);
	}

	@ResponseBody
	@PostMapping("projectpurchasepaymentsave")
	public ProjectMaster projectpurchasepaymentsave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));
		List<ProjectpurchasePaymentMaster> invls = new ArrayList();

		String tempreceiptid = nullremover(String.valueOf(params.get("recepitid")));

		if (!tempreceiptid.equalsIgnoreCase("")) {
			List<ProjectpurchasePaymentMaster> ls = new ArrayList();

			for (ProjectpurchasePaymentMaster invm : pm.getPurchasePaymentMasterList()) {
				if (invm.getRecepitid() == Integer.parseInt(tempreceiptid)) {

					invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
					invm.setAmount(Double.parseDouble(params.get("amount")));
					invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
					invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
					invm.setNotes(String.valueOf(params.get("notes")));
					invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
				}
				ls.add(invm);

			}
			pm.setPurchasePaymentMasterList(ls);

		} else {
			ProjectpurchasePaymentMaster invm = new ProjectpurchasePaymentMaster();

			invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
			invm.setAmount(Double.parseDouble(params.get("amount")));
			invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
			invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
			invm.setNotes(String.valueOf(params.get("notes")));
			invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
			invm.setPurchaseid(String.valueOf(params.get("recprojectpurchaseid")));

			pm.getPurchasePaymentMasterList().add(invm);
		}

		return projectMasterService.save(pm);
	}

	@GetMapping("accounttransfer")
	public String getaccounttranser(Model themodel) {

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("AccountTransfer"));
		themodel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		
		return "accounttransfer";
	}

	@ResponseBody
	@GetMapping("accounttransferlistjson")
	public List<AccountTransfer> accounttransferlistjson(Model themodel) {
		List<AccountTransfer> atList = accountTransferService.findAll();

		for (AccountTransfer am : atList) {

			try {
				am.setTDateMMMddyyyy(
						displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(am.getTDate().toString())));
				am.setTwithdrawfrom_name(accountheadsService.findById(Integer.parseInt(am.getTwithdrawfrom())).getCategory());
				am.setTDepositTo_name(accountheadsService.findById(Integer.parseInt(am.getTDepositTo())).getCategory());
				
			} catch (ParseException e) {
				// e.printStackTrace();
			}

		}
		atList = atList.stream().sorted(Comparator.comparing(AccountTransfer::getAccounttransferid).reversed())
				.toList();

		return atList;

	}

	@ResponseBody
	@PostMapping("accountTransfersavejson")
	public AccountTransfer accountTransfersavejson(@RequestParam Map<String, String> params) {

		AccountTransfer obj = new AccountTransfer();
		String accounttransferid = nullremover(String.valueOf(params.get("accounttransferid")));

		if (!accounttransferid.equalsIgnoreCase("")) {

			obj.setAccounttransferid(Integer.parseInt(accounttransferid));
		}
		obj.setNotes(String.valueOf(params.get("Notes")));
		obj.setTAmount(String.valueOf(params.get("tAmount")));
		obj.setTDate(String.valueOf(params.get("tDate")));
		obj.setTDepositTo(String.valueOf(params.get("tDepositTo")));
		obj.setTwithdrawfrom(String.valueOf(params.get("twithdrawfrom")));
		return accountTransferService.save(obj);
	}

	@ResponseBody
	@PostMapping("getaccounttransferitem")
	public AccountTransfer getaccounttransferitem(@RequestParam Map<String, String> params) {

		return accountTransferService.findById(Integer.parseInt(params.get("tid")));
	}

	// accountIncomeService

	@GetMapping("accountincome")
	public String getaccountincome(Model themodel) {

		themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Income"));
		themodel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		
		return "accountincome";
	}

	@ResponseBody
	@GetMapping("accountincomelistjson")
	public List<AccountsIncome> accountincomelistjson(Model themodel) {
		List<AccountsIncome> atList = accountIncomeService.findAll();

		for (AccountsIncome am : atList) {

			try {
				am.setIdateMMMddyyyy(
						displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(am.getIdate().toString())));
				am.setIdepositto_name(accountheadsService.findById(Integer.parseInt(am.getIdepositto())).getCategory());
				am.setIfrom_name(accountheadsService.findById(Integer.parseInt(am.getIfrom())).getCategory());
				
			
			} catch (ParseException e) {
				// e.printStackTrace();
			}

		}
		atList = atList.stream().sorted(Comparator.comparing(AccountsIncome::getAccountIncomeid).reversed()).toList();

		return atList;

	}

	@ResponseBody
	@PostMapping("accountsincomesavejson")
	public AccountsIncome accountsincomesavejson(@RequestParam Map<String, String> params) {
		// System.out.println(params);

		AccountsIncome obj = new AccountsIncome();
		String accounttransferid = nullremover(String.valueOf(params.get("accountIncomeid")));

		if (!accounttransferid.equalsIgnoreCase("")) {

			obj.setAccountIncomeid(Integer.parseInt(accounttransferid));
		}
		obj.setIamount(String.valueOf(params.get("iamount")));
		obj.setIcategory(String.valueOf(params.get("icategory")));
		obj.setIdate(String.valueOf(params.get("idate")));
		obj.setIdepositto(String.valueOf(params.get("idepositto")));
		obj.setIdescription(String.valueOf(params.get("idescription")));
		obj.setIfrom(String.valueOf(params.get("ifrom")));
		obj.setRefNo(String.valueOf(params.get("refNo")));

		return accountIncomeService.save(obj);
	}

	@ResponseBody
	@PostMapping("getaccountincomeitem")
	public AccountsIncome getaccountincomeitem(@RequestParam Map<String, String> params) {

		return accountIncomeService.findById(Integer.parseInt(params.get("tid")));
	}

	@ResponseBody
	@GetMapping("springtest")
	public String springtest() {
		return springtestobj.getDb_connect();
	}

	@GetMapping("projectpurchase")
	public String projectpurchase(Model theModel, @RequestParam("id") int id) {
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);
		theModel.addAttribute("supplierlist",
				corglis.stream().filter(C -> nullremover(C.getCustomer_supplier()).equalsIgnoreCase("Supplier"))
						.collect(Collectors.toList()));

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		theModel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");

		theModel.addAttribute("UNITS", UNITS);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("board", projectTemplateBoardService.findAll());
		theModel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		theModel.addAttribute("expenselist", getaaccountsHeads_Expenses());
		return "projectpurchase";
	}

	@ResponseBody
	@PostMapping("projectprojectpurchasesave")
	public ProjectMaster projectprojectpurchasesave(@RequestParam Map<String, String> params) {

//		 params.forEach((a,b) -> System.out.println(a + " - "+ b));

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));
		List<ProjectpurchaseMaster> invls = new ArrayList();

		String tempprojectpurchaseid = nullremover(String.valueOf(params.get("projectpurchaseid")));

		if (!tempprojectpurchaseid.equalsIgnoreCase("")) {
			List<ProjectpurchaseMaster> ls = new ArrayList();

			for (ProjectpurchaseMaster invm : pm.getProjectpurchaseMasterList()) {
				if (invm.getProjectpurchaseid() == Integer.parseInt(tempprojectpurchaseid)) {
					invm.setDueDate(String.valueOf(params.get("dueDate")));
					invm.setProjectpurchaseDate(String.valueOf(params.get("projectpurchaseDate")));
					invm.setProjectpurchaseType(String.valueOf(params.get("projectpurchaseType")));
					invm.setNotes(String.valueOf(params.get("note")));
					invm.setReceivable(String.valueOf(params.get("receivable")));
					invm.setProjectpurchaseNo(String.valueOf(params.get("projectpurchaseNo")));
					invm.setEmployeeid(String.valueOf(params.get("purchase_employeeid")));
					invm.setSupplierid(String.valueOf(params.get("purchase_supplierid")));

					List<ProjectpurchaseItemMaster> invitemls = new ArrayList();
					for (int i = 1; i <= Integer.parseInt(params.get("projectpurchaseitemcount")); i++) {

						ProjectpurchaseItemMaster initem = new ProjectpurchaseItemMaster();
						int invitemids = 0;
						if (!nullremover(String.valueOf(params.get("ProjectpurchaseItemid" + i)))
								.equalsIgnoreCase("")) {
							invitemids = Integer.parseInt(params.get("ProjectpurchaseItemid" + i));
							final int tempi = i;
							initem = invm.getProjectpurchaseItemMasterlist().stream()
									.filter(C -> C.getProjectpurchaseitemid() == Integer
											.parseInt(params.get("ProjectpurchaseItemid" + tempi)))
									.collect(Collectors.toList()).get(0);
						}

						invitemls.add(addupdatedProjectpurchaseMaster(params, initem, i, invitemids));
					}
					invm.setProjectpurchaseItemMasterlist(invitemls);

				}
				ls.add(invm);

			}
			pm.setProjectpurchaseMasterList(ls);

		} else {
			ProjectpurchaseMaster newinv = new ProjectpurchaseMaster();

			newinv.setDueDate(String.valueOf(params.get("dueDate")));
			newinv.setProjectpurchaseDate(String.valueOf(params.get("projectpurchaseDate")));
			newinv.setProjectpurchaseType(String.valueOf(params.get("projectpurchaseType")));
			newinv.setNotes(String.valueOf(params.get("note")));
			newinv.setReceivable(String.valueOf(params.get("receivable")));
			newinv.setProjectpurchaseNo(String.valueOf(params.get("projectpurchaseNo")));
			newinv.setEmployeeid(String.valueOf(params.get("purchase_employeeid")));
			newinv.setSupplierid(String.valueOf(params.get("purchase_supplierid")));

			List<ProjectpurchaseItemMaster> invitemls = new ArrayList();
			for (int i = 1; i <= Integer.parseInt(params.get("projectpurchaseitemcount")); i++) {

				invitemls.add(addupdatedProjectpurchaseMaster(params, new ProjectpurchaseItemMaster(), i, 0));
			}
			newinv.setProjectpurchaseItemMasterlist(invitemls);

			pm.getProjectpurchaseMasterList().add(newinv);
		}

		return projectMasterService.save(pm);
	}

	public ProjectpurchaseItemMaster addupdatedProjectpurchaseMaster(Map<String, String> params,
			ProjectpurchaseItemMaster invitemmaster, int index, int itemid) {

		invitemmaster.setProjectpurchaseitemid(itemid);

		double price = Double.parseDouble(String.valueOf(params.get("Price" + index)));
		double qty = Double.parseDouble(String.valueOf(params.get("Quantity" + index)));
		double taxableAmount = price * qty;

		double Discountper = Double.parseDouble(String.valueOf(params.get("Discountper" + index)));
		double CGSTper = Double.parseDouble(String.valueOf(params.get("CGSTper" + index)));
		double SGSTper = Double.parseDouble(String.valueOf(params.get("SGSTper" + index)));
		double IGSTper = Double.parseDouble(String.valueOf(params.get("IGSTper" + index)));

		double dt = Discountper / 100;
		double Discountamt = taxableAmount * dt;
		double afetdiscountamount = taxableAmount - Discountamt;

		double it = IGSTper / 100;
		double ct = CGSTper / 100;
		double st = SGSTper / 100;
		double IGSTamount = afetdiscountamount * it;
		double SGSTamount = afetdiscountamount * st;
		double CGSTamount = afetdiscountamount * ct;

		invitemmaster.setDiscountamt(Discountamt);
		invitemmaster.setDiscountper(Discountper);

		invitemmaster.setCGSTamount(CGSTamount);
		invitemmaster.setCGSTper(CGSTper);

		invitemmaster.setIGSTamount(IGSTamount);
		invitemmaster.setIGSTper(IGSTper);

		invitemmaster.setSGSTamount(SGSTamount);
		invitemmaster.setSGSTper(SGSTper);

		invitemmaster.setProjectpurchaseItem(String.valueOf(params.get("ProjectpurchaseItem" + index)));
		invitemmaster.setDescription(String.valueOf(params.get("Description" + index)));
		invitemmaster.setQuantity(qty);
		invitemmaster.setPrice(price);
		invitemmaster.setUnit(String.valueOf(params.get("Unit" + index)));

		invitemmaster.setTaxableAmount(afetdiscountamount);
		invitemmaster.setTotalamountAmount(afetdiscountamount + CGSTamount + SGSTamount + IGSTamount);

		return invitemmaster;
	}

	@GetMapping("projectexpense")
	public String projectexpense(Model theModel, @RequestParam("id") int id) {
		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster = projectMasterService.findById(id);

		if (!nullremover(String.valueOf(projectMaster.getOrganization())).equalsIgnoreCase("")) {
			projectMaster.setOrganizationName(contactOrganizationService
					.findById(Integer.parseInt(projectMaster.getOrganization())).getOrgname());
		}
		List<ProjectFollowers> projectfolloersls = new ArrayList();
		String followerids = "";
		for (ProjectFollowers lf : projectMaster.getProjectFollowers()) {

			followerids += lf.getEmpid() + ",";
			EmployeeMaster empobj = employeeMasterService.findById(lf.getEmpid());

			lf.setFollowername(empobj.getStaffName());

			List<EmployeeFiles> validProfilephoto = empobj.getEmployeeFiles().stream()
					.filter(c -> c.getDocumentType().equalsIgnoreCase("Photo")).collect(Collectors.toList());
			if (validProfilephoto.size() > 0) {

				lf.setFollowerimg(validProfilephoto.get(0).getFilePath());
			}

			projectfolloersls.add(lf);
		}
		followerids = followerids.substring(0, followerids.length() - 1);
		projectMaster.setProjectfollowerids(followerids);

		if (!nullremover(String.valueOf(projectMaster.getReference())).equalsIgnoreCase("")) {
			final String projectreference = projectMaster.getReference().toString();
			ContactPerson cp = contactPersonService.findById(Integer.parseInt(projectreference));
			projectMaster.setReferenceName(cp.getPeoplename());
		}

		if (!nullremover(String.valueOf(projectMaster.getBranch())).equalsIgnoreCase("")) {
			int branchid = projectMaster.getBranch();
			BranchMaster bm = branchMasterService.findById(branchid);
			projectMaster.setBranchname(bm.getBRANCH_NAME());
		}
		// ----------------------------------------------------------
		List<ContactPerson> cplist = new ArrayList<ContactPerson>();

		for (ProjectContact lc : projectMaster.getProjectContact()) {
			ContactPerson cp = contactPersonService.findById(lc.getContactPerson());

			// Set primary contact
			List<ContactPersonContact> bcls = cp.getContactPersonContact().stream()
					.filter(C -> C.getPrimarycontact() == true).collect(Collectors.toList());
			if (bcls.size() > 0) {
				cp.setPrimarymob(bcls.get(0).getPhonenumber());
				cp.setPrimaryemail(bcls.get(0).getEmail());
			}
			cplist.add(cp);

		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getExpectedclosingdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedclosingdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getExpectedclosingdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		if (!nullremover(String.valueOf(projectMaster.getStartdate())).equalsIgnoreCase("")) {
			try {
				projectMaster.setExpectedstartdateMMddYYY(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(projectMaster.getStartdate())).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		// ----------------------------------------------------------
		if (!nullremover(String.valueOf(projectMaster.getBoard())).equalsIgnoreCase("")) {
			projectMaster.setBoardName(
					projectTemplateBoardService.findById(Integer.parseInt(projectMaster.getBoard())).getBoardName());
		}

		// ----------------------------------------------------------
		theModel.addAttribute("cplist", cplist);
		theModel.addAttribute("projectMaster", projectMaster);
		List<String> CONTACTTYPE = itemlistService.findByFieldName("CONTACTTYPE");
		theModel.addAttribute("CONTACTTYPE", CONTACTTYPE);

		List<String> Documenttype = itemlistService.findByFieldName("Documenttype");
		theModel.addAttribute("Documenttype", Documenttype);

		List<String> industry_type = itemlistService.findByFieldName("industry_type");
		theModel.addAttribute("industry_type", industry_type);

		theModel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		theModel.addAttribute("personlist", cplis);
		theModel.addAttribute("organizationlist", corglis);

		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		theModel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		theModel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");

		/*
		 * String NATUREOFWORKtemp=""; for(String str: NATUREOFWORK) { NATUREOFWORKtemp=
		 * NATUREOFWORKtemp.concat("<option value='"+ str + "'>"+ str+ "</option>"); }
		 */
		theModel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		/*
		 * String UNITStemp=""; for(String str: UNITS) { UNITStemp=
		 * UNITStemp.concat("<option value=\'"+ str + "\'>"+ str+ "</option>"); }
		 */
		theModel.addAttribute("UNITS", UNITS);

		List<BranchMaster> bmlist = branchMasterService.findAll();
		theModel.addAttribute("branchlist", bmlist);

		theModel.addAttribute("contactPeopleList",
				contactPersonService.contactpersonlistbyorgname(projectMaster.getOrganization()));
		theModel.addAttribute("branchMasterList", branchMasterService.findAll());
		theModel.addAttribute("EffectiveEmployee", EffectiveEmployee(employeeMasterService.findAll()));
		// ---------------------------
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("project"));
		theModel.addAttribute("board", projectTemplateBoardService.findAll());

		theModel.addAttribute("vechiclels", assetMasterService.findAll().stream()
				.filter(C -> C.getAssetType().equalsIgnoreCase("Vehicles")).collect(Collectors.toList()));
		theModel.addAttribute("accountlist", getaaccountsHeads_AssetBank_Accounts());
		theModel.addAttribute("expenselist", getaaccountsHeads_Expenses_objectlist());
		return "projectexpense1";
	}

	@ResponseBody
	@PostMapping("projectexpensesave")
	public ProjectMaster projectexpensesave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("projectid")));

		List<ProjectExpense> invls = new ArrayList();

		String tempreceiptid = nullremover(String.valueOf(params.get("prjExpenseid")));

		if (!tempreceiptid.equalsIgnoreCase("")) {
			List<ProjectExpense> ls = new ArrayList();

			for (ProjectExpense invm : pm.getProjectExpenseList()) {
				if (invm.getPrjExpenseid() == Integer.parseInt(tempreceiptid)) {

					invm.setCategory(String.valueOf(params.get("category")));
					invm.setDescription(String.valueOf(params.get("Description")));
					invm.setModelofTravel(String.valueOf(params.get("modelofTravel")));
					invm.setNotes(String.valueOf(params.get("Notes")));
					invm.setPrjExpenseDate(String.valueOf(params.get("prjExpenseDate")));
					invm.setPrjreceiptno(String.valueOf(params.get("recepitNo")));
					invm.setQuantity(Double.parseDouble(params.get("Quantity")));
					invm.setStaff(String.valueOf(params.get("staff")));
					invm.setTotal(
							Double.parseDouble(params.get("Amount")) * Double.parseDouble(params.get("Quantity")));
					invm.setAmount(Double.parseDouble(params.get("Amount")));
					invm.setUnit(String.valueOf(params.get("unit")));
					invm.setVehicle(String.valueOf(params.get("vehicle")));
					invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
					invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));				
				}
				ls.add(invm);

			}
			pm.setProjectExpenseList(ls);

		} else {
			ProjectExpense invm = new ProjectExpense();

			invm.setCategory(String.valueOf(params.get("category")));
			invm.setDescription(String.valueOf(params.get("Description")));
			invm.setModelofTravel(String.valueOf(params.get("modelofTravel")));
			invm.setNotes(String.valueOf(params.get("Notes")));
			invm.setPrjExpenseDate(String.valueOf(params.get("prjExpenseDate")));
			invm.setPrjreceiptno(String.valueOf(params.get("recepitNo")));
			invm.setQuantity(Double.parseDouble(params.get("Quantity")));
			invm.setStaff(String.valueOf(params.get("staff")));
			invm.setTotal(Double.parseDouble(params.get("Amount")) * Double.parseDouble(params.get("Quantity")));
			invm.setAmount(Double.parseDouble(params.get("Amount")));
			invm.setUnit(String.valueOf(params.get("unit")));
			invm.setVehicle(String.valueOf(params.get("vehicle")));
			invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
			invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
			
			pm.getProjectExpenseList().add(invm);
		}

		return projectMasterService.save(pm);
	}

	@PostMapping("getprojectexpenselist")
	@ResponseBody
	public List<ProjectExpense> getprojectexpenselist(@RequestParam Map<String, String> params) {

		ProjectMaster pm = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));
		List<ProjectExpense> ls = pm.getProjectExpenseList();
		List<EmployeeMaster> emplist = employeeMasterService.findAll();

		for (ProjectExpense obj : ls) {
			try {

				obj.setPrjExpenseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getPrjExpenseDate())).toString());

				obj.setStaffname(emplist.stream()
						.filter(C -> C.getEmpMasterid() == Integer.parseInt(params.get("mastercategoryid")))

						.collect(Collectors.toList()).get(0).getStaffName());
				obj.setCategory_name(accountheadsService.findById(Integer.parseInt(obj.getCategory())).getCategory());

			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getexpenseitem")
	@ResponseBody
	public ProjectExpense getexpenseitem(@RequestParam Map<String, String> params) {

		ProjectExpense obj = projectMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getProjectExpenseList().stream()
				.filter(C -> C.getPrjExpenseid() == Integer.parseInt(params.get("projectid")))
				.collect(Collectors.toList()).get(0);

		return obj;

	}

	@ResponseBody
	@PostMapping("branchbranchpurchasesave")
	public BranchMaster branchbranchpurchasesave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		BranchMaster pm = branchMasterService.findById(Integer.parseInt(params.get("branchid")));
		List<BranchpurchaseMaster> invls = new ArrayList();

		String tempbranchpurchaseid = nullremover(String.valueOf(params.get("branchpurchaseid")));

		if (!tempbranchpurchaseid.equalsIgnoreCase("")) {
			List<BranchpurchaseMaster> ls = new ArrayList();

			for (BranchpurchaseMaster invm : pm.getBranchpurchaseMasterList()) {
				if (invm.getBranchpurchaseid() == Integer.parseInt(tempbranchpurchaseid)) {
					invm.setDueDate(String.valueOf(params.get("dueDate")));
					invm.setBranchpurchaseDate(String.valueOf(params.get("branchpurchaseDate")));
					invm.setBranchpurchaseType(String.valueOf(params.get("branchpurchaseType")));
					invm.setNotes(String.valueOf(params.get("note")));
					invm.setReceivable(String.valueOf(params.get("receivable")));
					invm.setBranchpurchaseNo(String.valueOf(params.get("branchpurchaseNo")));
					invm.setEmployeeid(String.valueOf(params.get("purchase_employeeid")));
					invm.setSupplierid(String.valueOf(params.get("purchase_supplierid")));

					List<BranchpurchaseItemMaster> invitemls = new ArrayList();
					for (int i = 1; i <= Integer.parseInt(params.get("branchpurchaseitemcount")); i++) {

						BranchpurchaseItemMaster initem = new BranchpurchaseItemMaster();
						int invitemids = 0;
						if (!nullremover(String.valueOf(params.get("BranchpurchaseItemid" + i))).equalsIgnoreCase("")) {
							invitemids = Integer.parseInt(params.get("BranchpurchaseItemid" + i));
							final int tempi = i;
							initem = invm.getBranchpurchaseItemMasterlist().stream()
									.filter(C -> C.getBranchpurchaseitemid() == Integer
											.parseInt(params.get("BranchpurchaseItemid" + tempi)))
									.collect(Collectors.toList()).get(0);
						}

						invitemls.add(addupdatedBranchpurchaseMaster(params, initem, i, invitemids));
					}
					invm.setBranchpurchaseItemMasterlist(invitemls);

				}
				ls.add(invm);

			}
			pm.setBranchpurchaseMasterList(ls);

		} else {
			BranchpurchaseMaster newinv = new BranchpurchaseMaster();

			newinv.setDueDate(String.valueOf(params.get("dueDate")));
			newinv.setBranchpurchaseDate(String.valueOf(params.get("branchpurchaseDate")));
			newinv.setBranchpurchaseType(String.valueOf(params.get("branchpurchaseType")));
			newinv.setNotes(String.valueOf(params.get("note")));
			newinv.setReceivable(String.valueOf(params.get("receivable")));
			newinv.setBranchpurchaseNo(String.valueOf(params.get("branchpurchaseNo")));
			newinv.setEmployeeid(String.valueOf(params.get("purchase_employeeid")));
			newinv.setSupplierid(String.valueOf(params.get("purchase_supplierid")));

			List<BranchpurchaseItemMaster> invitemls = new ArrayList();
			for (int i = 1; i <= Integer.parseInt(params.get("branchpurchaseitemcount")); i++) {

				invitemls.add(addupdatedBranchpurchaseMaster(params, new BranchpurchaseItemMaster(), i, 0));
			}
			newinv.setBranchpurchaseItemMasterlist(invitemls);

			pm.getBranchpurchaseMasterList().add(newinv);
		}

		return branchMasterService.save(pm);
	}

	public BranchpurchaseItemMaster addupdatedBranchpurchaseMaster(Map<String, String> params,
			BranchpurchaseItemMaster invitemmaster, int index, int itemid) {

		invitemmaster.setBranchpurchaseitemid(itemid);

		double price = Double.parseDouble(String.valueOf(params.get("Price" + index)));
		double qty = Double.parseDouble(String.valueOf(params.get("Quantity" + index)));
		double taxableAmount = price * qty;

		double Discountper = Double.parseDouble(String.valueOf(params.get("Discountper" + index)));
		double CGSTper = Double.parseDouble(String.valueOf(params.get("CGSTper" + index)));
		double SGSTper = Double.parseDouble(String.valueOf(params.get("SGSTper" + index)));
		double IGSTper = Double.parseDouble(String.valueOf(params.get("IGSTper" + index)));

		double dt = Discountper / 100;
		double Discountamt = taxableAmount * dt;
		double afetdiscountamount = taxableAmount - Discountamt;

		double it = IGSTper / 100;
		double ct = CGSTper / 100;
		double st = SGSTper / 100;
		double IGSTamount = afetdiscountamount * it;
		double SGSTamount = afetdiscountamount * st;
		double CGSTamount = afetdiscountamount * ct;

		invitemmaster.setDiscountamt(Discountamt);
		invitemmaster.setDiscountper(Discountper);

		invitemmaster.setCGSTamount(CGSTamount);
		invitemmaster.setCGSTper(CGSTper);

		invitemmaster.setIGSTamount(IGSTamount);
		invitemmaster.setIGSTper(IGSTper);

		invitemmaster.setSGSTamount(SGSTamount);
		invitemmaster.setSGSTper(SGSTper);

		invitemmaster.setBranchpurchaseItem(String.valueOf(params.get("BranchpurchaseItem" + index)));
		invitemmaster.setDescription(String.valueOf(params.get("Description" + index)));
		invitemmaster.setQuantity(qty);
		invitemmaster.setPrice(price);
		invitemmaster.setUnit(String.valueOf(params.get("Unit" + index)));

		invitemmaster.setTaxableAmount(afetdiscountamount);
		invitemmaster.setTotalamountAmount(afetdiscountamount + CGSTamount + SGSTamount + IGSTamount);

		return invitemmaster;
	}

	@ResponseBody
	@PostMapping("branchpurchasepaymentsave")
	public BranchMaster branchpurchasepaymentsave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		BranchMaster pm = branchMasterService.findById(Integer.parseInt(params.get("branchid")));
		List<BranchpurchasePaymentMaster> invls = new ArrayList();

		String tempreceiptid = nullremover(String.valueOf(params.get("recepitid")));

		if (!tempreceiptid.equalsIgnoreCase("")) {
			List<BranchpurchasePaymentMaster> ls = new ArrayList();

			for (BranchpurchasePaymentMaster invm : pm.getPurchasePaymentMasterList()) {
				if (invm.getRecepitid() == Integer.parseInt(tempreceiptid)) {

					invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
					invm.setAmount(Double.parseDouble(params.get("amount")));
					invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
					invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
					invm.setNotes(String.valueOf(params.get("notes")));
					invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
				}
				ls.add(invm);

			}
			pm.setPurchasePaymentMasterList(ls);

		} else {
			BranchpurchasePaymentMaster invm = new BranchpurchasePaymentMaster();

			invm.setRecepitNo(String.valueOf(params.get("recepitNo")));
			invm.setAmount(Double.parseDouble(params.get("amount")));
			invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
			invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
			invm.setNotes(String.valueOf(params.get("notes")));
			invm.setRecepitDate(String.valueOf(params.get("recepitDate")));
			invm.setPurchaseid(String.valueOf(params.get("recbranchpurchaseid")));

			pm.getPurchasePaymentMasterList().add(invm);
		}

		return branchMasterService.save(pm);
	}

	@PostMapping("getbranchpurchaselist")
	@ResponseBody
	public List<BranchpurchaseMaster> getbranchpurchaselist(@RequestParam Map<String, String> params) {

		List<BranchpurchaseMaster> ls = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchpurchaseMasterList();

		for (BranchpurchaseMaster obj : ls) {
			try {
				obj.setBranchpurchaseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getBranchpurchaseDate())).toString());
				obj.setDueDateMMMddyyyy(
						displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getbranchpurpaymentlist")
	@ResponseBody
	public List<BranchpurchasePaymentMaster> getbranchpurpaymentlist(@RequestParam Map<String, String> params) {

		BranchMaster pm = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")));
		List<BranchpurchasePaymentMaster> ls = pm.getPurchasePaymentMasterList();
		List<BranchpurchaseMaster> invls = pm.getBranchpurchaseMasterList();

		for (BranchpurchasePaymentMaster obj : ls) {
			try {
				obj.setRecepitDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());

				obj.setPurchaseNo(
						invls.stream().filter(C -> C.getBranchpurchaseid() == Integer.parseInt(obj.getPurchaseid()))
								.collect(Collectors.toList()).get(0).getBranchpurchaseNo());

				obj.setDepitedfrom_txt(
						accountheadsService.findById(Integer.parseInt(obj.getDepitedfrom())).getCategory());

			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getbranchpurchaseitem")
	@ResponseBody
	public BranchpurchaseMaster getbranchpurchaseitem(@RequestParam Map<String, String> params) {

		BranchpurchaseMaster obj = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchpurchaseMasterList().stream()
				.filter(C -> C.getBranchpurchaseid() == Integer.parseInt(params.get("branchid")))
				.collect(Collectors.toList()).get(0);

		try {
			obj.setBranchpurchaseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
					.format(displaydateFormatrev.parse(obj.getBranchpurchaseDate())).toString());
			obj.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		for (BranchpurchaseItemMaster expensechild : obj.getBranchpurchaseItemMasterlist()) {
			Accountsheads ahobj = accountheadsService.findById(Integer.parseInt(expensechild.getBranchpurchaseItem()));
			expensechild.setBranchpurchaseItem_name(ahobj.getCategory());
		}

		return obj;

	}

	@PostMapping("getbranchpurchasepaymentitem")
	@ResponseBody
	public Map<String, String> getbranchpurchasepaymentitem(@RequestParam Map<String, String> params) {

		Map<String, String> map = new HashMap<>();

		double totalpaidamount = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getPurchasePaymentMasterList().stream()
				.filter(C -> C.getPurchaseid().equalsIgnoreCase(params.get("branchpurchaseid")))
				.mapToDouble(BranchpurchasePaymentMaster::getAmount).sum();

		map.put("totalpaidamount", String.valueOf(totalpaidamount));

		BranchpurchaseMaster inv = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchpurchaseMasterList().stream()
				.filter(C -> C.getBranchpurchaseid() == Integer.parseInt(params.get("branchpurchaseid")))
				.collect(Collectors.toList()).get(0);

		try {
			inv.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(inv.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		double totalinvoiceamount = inv.getBranchpurchaseItemMasterlist().stream()
				.mapToDouble(BranchpurchaseItemMaster::getTotalamountAmount).sum();
		map.put("purchaseno", inv.getBranchpurchaseNo());
		map.put("duedate", inv.getDueDateMMMddyyyy());
		map.put("amount", String.valueOf(totalinvoiceamount));
		map.put("balanceamount", String.valueOf(totalinvoiceamount - totalpaidamount));
		map.put("purchaseid", String.valueOf(inv.getBranchpurchaseid()));

		return map;

	}

	@PostMapping("getbranchpurpaymentreceiptitem")
	@ResponseBody
	public BranchpurchasePaymentMaster getbranchpurpaymentreceiptitem(@RequestParam Map<String, String> params) {

		BranchpurchasePaymentMaster obj = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getPurchasePaymentMasterList().stream()
				.filter(C -> C.getRecepitid() == Integer.parseInt(params.get("branchid"))).collect(Collectors.toList())
				.get(0);

		try {
			obj.setRecepitDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(obj.getRecepitDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}
		return obj;

	}

	@PostMapping("getbranchpurchasepaymentMasteritem")
	@ResponseBody
	public Map<String, String> getbranchpurchasepaymentMasteritem(@RequestParam Map<String, String> params) {

		Map<String, String> map = new HashMap<>();

		double totalpaidamount = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getPurchasePaymentMasterList().stream()
				.filter(C -> C.getPurchaseid().equalsIgnoreCase(params.get("branchpurchaseid")))
				.mapToDouble(BranchpurchasePaymentMaster::getAmount).sum();

		map.put("totalpaidamount", String.valueOf(totalpaidamount));

		BranchpurchaseMaster inv = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchpurchaseMasterList().stream()
				.filter(C -> C.getBranchpurchaseid() == Integer.parseInt(params.get("branchpurchaseid")))
				.collect(Collectors.toList()).get(0);

		try {
			inv.setDueDateMMMddyyyy(
					displaydateFormatFirstMMMddYYY.format(displaydateFormatrev.parse(inv.getDueDate())).toString());
		} catch (ParseException e) {

			// e.printStackTrace();
		}

		double totalinvoiceamount = inv.getBranchpurchaseItemMasterlist().stream()
				.mapToDouble(BranchpurchaseItemMaster::getTotalamountAmount).sum();
		map.put("purchaseno", inv.getBranchpurchaseNo());
		map.put("duedate", inv.getDueDateMMMddyyyy());
		map.put("amount", String.valueOf(totalinvoiceamount));
		map.put("balanceamount", String.valueOf(totalinvoiceamount - totalpaidamount));
		map.put("purchaseid", String.valueOf(inv.getBranchpurchaseid()));

		return map;

	}

	@ResponseBody
	@PostMapping("branchbranchexpensesave")
	public BranchMaster branchbranchexpensesave(@RequestParam Map<String, String> params) {

		// params.forEach((a,b) -> System.out.println(a + " - "+ b));

		BranchMaster pm = branchMasterService.findById(Integer.parseInt(params.get("branchid")));
		List<BranchexpenseMaster> invls = new ArrayList();

		String tempbranchexpenseid = nullremover(String.valueOf(params.get("branchexpenseid")));

		if (!tempbranchexpenseid.equalsIgnoreCase("")) {
			List<BranchexpenseMaster> ls = new ArrayList();

			for (BranchexpenseMaster invm : pm.getBranchexpenseMasterList()) {
				if (invm.getBranchexpenseid() == Integer.parseInt(tempbranchexpenseid)) {
					invm.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
					invm.setBranchexpenseDate(String.valueOf(params.get("branchexpenseDate")));
					invm.setModeofPayment(String.valueOf(params.get("modeofPayment")));
					invm.setNotes(String.valueOf(params.get("note")));
					invm.setBranchexpenseNo(String.valueOf(params.get("branchexpenseNo")));
					invm.setEmployeeid(String.valueOf(params.get("expense_employeeid")));
					invm.setSupplierid(String.valueOf(params.get("expense_supplierid")));

					List<BranchexpenseItemMaster> invitemls = new ArrayList();
					for (int i = 1; i <= Integer.parseInt(params.get("branchexpenseitemcount")); i++) {

						BranchexpenseItemMaster initem = new BranchexpenseItemMaster();
						int invitemids = 0;
						if (!nullremover(String.valueOf(params.get("BranchexpenseItemid" + i))).equalsIgnoreCase("")) {
							invitemids = Integer.parseInt(params.get("BranchexpenseItemid" + i));
							final int tempi = i;
							initem = invm.getBranchexpenseItemMasterlist().stream()
									.filter(C -> C.getBranchexpenseitemid() == Integer
											.parseInt(params.get("BranchexpenseItemid" + tempi)))
									.collect(Collectors.toList()).get(0);
						}

						invitemls.add(addupdatedBranchexpenseMaster(params, initem, i, invitemids));
					}
					invm.setBranchexpenseItemMasterlist(invitemls);

				}
				ls.add(invm);

			}
			pm.setBranchexpenseMasterList(ls);

		} else {
			BranchexpenseMaster newinv = new BranchexpenseMaster();

			newinv.setDepitedfrom(String.valueOf(params.get("depitedfrom")));
			newinv.setBranchexpenseDate(String.valueOf(params.get("branchexpenseDate")));
			newinv.setModeofPayment(String.valueOf(params.get("modeofPayment")));
			newinv.setNotes(String.valueOf(params.get("note")));
			newinv.setBranchexpenseNo(String.valueOf(params.get("branchexpenseNo")));
			newinv.setEmployeeid(String.valueOf(params.get("expense_employeeid")));
			newinv.setSupplierid(String.valueOf(params.get("expense_supplierid")));

			List<BranchexpenseItemMaster> invitemls = new ArrayList();
			for (int i = 1; i <= Integer.parseInt(params.get("branchexpenseitemcount")); i++) {

				invitemls.add(addupdatedBranchexpenseMaster(params, new BranchexpenseItemMaster(), i, 0));
			}
			newinv.setBranchexpenseItemMasterlist(invitemls);

			pm.getBranchexpenseMasterList().add(newinv);
		}

		return branchMasterService.save(pm);
	}

	public BranchexpenseItemMaster addupdatedBranchexpenseMaster(Map<String, String> params,
			BranchexpenseItemMaster invitemmaster, int index, int itemid) {

		invitemmaster.setBranchexpenseitemid(itemid);

		double price = Double.parseDouble(String.valueOf(params.get("Price" + index)));
		double qty = Double.parseDouble(String.valueOf(params.get("Quantity" + index)));
		double taxableAmount = price * qty;

		double Discountper = Double.parseDouble(String.valueOf(params.get("Discountper" + index)));
		double CGSTper = Double.parseDouble(String.valueOf(params.get("CGSTper" + index)));
		double SGSTper = Double.parseDouble(String.valueOf(params.get("SGSTper" + index)));
		double IGSTper = Double.parseDouble(String.valueOf(params.get("IGSTper" + index)));

		double dt = Discountper / 100;
		double Discountamt = taxableAmount * dt;
		double afetdiscountamount = taxableAmount - Discountamt;

		double it = IGSTper / 100;
		double ct = CGSTper / 100;
		double st = SGSTper / 100;
		double IGSTamount = afetdiscountamount * it;
		double SGSTamount = afetdiscountamount * st;
		double CGSTamount = afetdiscountamount * ct;

		invitemmaster.setDiscountamt(Discountamt);
		invitemmaster.setDiscountper(Discountper);

		invitemmaster.setCGSTamount(CGSTamount);
		invitemmaster.setCGSTper(CGSTper);

		invitemmaster.setIGSTamount(IGSTamount);
		invitemmaster.setIGSTper(IGSTper);

		invitemmaster.setSGSTamount(SGSTamount);
		invitemmaster.setSGSTper(SGSTper);

		invitemmaster.setBranchexpenseItem(String.valueOf(params.get("BranchexpenseItem" + index)));
		invitemmaster.setDescription(String.valueOf(params.get("Description" + index)));
		invitemmaster.setQuantity(qty);
		invitemmaster.setPrice(price);
		invitemmaster.setUnit(String.valueOf(params.get("Unit" + index)));

		invitemmaster.setTaxableAmount(afetdiscountamount);
		invitemmaster.setTotalamountAmount(afetdiscountamount + CGSTamount + SGSTamount + IGSTamount);

		return invitemmaster;
	}

	@PostMapping("getbranchexpenselist")
	@ResponseBody
	public List<BranchexpenseMaster> getbranchexpenselist(@RequestParam Map<String, String> params) {

		List<BranchexpenseMaster> ls = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchexpenseMasterList();
		List<OrganizationContacts> corglis = contactOrganizationService.findAll();

		for (BranchexpenseMaster obj : ls) {
			try {
				obj.setBranchexpenseDateMMMddyyyy(displaydateFormatFirstMMMddYYY
						.format(displaydateFormatrev.parse(obj.getBranchexpenseDate())).toString());

				obj.setEmployee_name(
						employeeMasterService.findById(Integer.parseInt(obj.getEmployeeid())).getStaffName());
				obj.setSupplier_name(corglis.stream().filter(C -> C.getId() == Integer.parseInt(obj.getSupplierid()))
						.collect(Collectors.toList()).get(0).getOrgname());

			} catch (ParseException e) {

				// e.printStackTrace();
			}

		}

		return ls;
	}

	@PostMapping("getbranchexpenseitem")
	@ResponseBody
	public BranchexpenseMaster getbranchexpenseitem(@RequestParam Map<String, String> params) {

		BranchexpenseMaster obj = branchMasterService.findById(Integer.parseInt(params.get("mastercategoryid")))
				.getBranchexpenseMasterList().stream()
				.filter(C -> C.getBranchexpenseid() == Integer.parseInt(params.get("branchid")))
				.collect(Collectors.toList()).get(0);
		for (BranchexpenseItemMaster expensechild : obj.getBranchexpenseItemMasterlist()) {
			Accountsheads ahobj = accountheadsService.findById(Integer.parseInt(expensechild.getBranchexpenseItem()));

			expensechild.setBranchexpenseItem_name(ahobj.getCategory());
		}
		return obj;
	}

	public List<Accountsheads> getaaccountsHeads_AssetBank() {
		return accountheadsService.findAll().stream().filter(C -> C.getMastergroup().equalsIgnoreCase("Assets / Bank"))
				.collect(Collectors.toList());

	}

	public List<Accountsheads> getaaccountsHeads_AssetBank_Accounts() {
		List<String> strls = new ArrayList();

		List<Accountsheads> ls = accountheadsService.findAll().stream()
				.filter(C -> (C.getAccountheads().equalsIgnoreCase("Cash")
						|| C.getAccountheads().equalsIgnoreCase("Bank Account")))
				.collect(Collectors.toList());

		/*
		 * ls.forEach(obj ->{ strls.add( obj.getAccountheadid() +"|"+
		 * obj.getCategory()); });
		 */

		return ls;

	}

	public List<Accountsheads> getaaccountsHeads_Liabilities() {
		return accountheadsService.findAll().stream().filter(C -> C.getMastergroup().equalsIgnoreCase("Liabilities"))
				.collect(Collectors.toList());

	}

	public List<Accountsheads> getaaccountsHeads_Equity() {
		return accountheadsService.findAll().stream().filter(C -> C.getMastergroup().equalsIgnoreCase("Equity"))
				.collect(Collectors.toList());

	}

	public List<Accountsheads> getaaccountsHeads_Income() {
		return accountheadsService.findAll().stream().filter(C -> C.getMastergroup().equalsIgnoreCase("Income"))
				.collect(Collectors.toList());

	}

	public List<String> getaaccountsHeads_Expenses() {

		List<String> strls = new ArrayList();

		List<Accountsheads> ls = accountheadsService.findAll().stream()
				.filter(C -> C.getMastergroup().equalsIgnoreCase("Expenses")).collect(Collectors.toList());

		ls.forEach(obj -> {
			strls.add(obj.getAccountheadid() + "|" + obj.getCategory());
		});

		return strls;

	}

	public List<Accountsheads> getaaccountsHeads_Expenses_objectlist() {

		List<Accountsheads> ls = accountheadsService.findAll().stream()
				.filter(C -> C.getMastergroup().equalsIgnoreCase("Expenses")).collect(Collectors.toList());

		return ls;

	}

	@GetMapping("accounts")
	public String accountsreport(Model theModel) {
		List<Accountsheads> ls = accountheadsService.findAll().stream()
				.sorted(Comparator.comparing(Accountsheads::getRefnumber)).toList();

		// ----------------------------------------------------------------------------
		double getAccountsReceivableamt;
		List<Map<String, Object>> getAccountsReceivable = accountheadsService.getAccountsReceivable();
		getAccountsReceivableamt = (double) getAccountsReceivable.get(0).get("taxable_amount");

		// ----------------------------------------------------------------------------
		double getSalesIncomeamt;
		List<Map<String, Object>> getSalesIncome = accountheadsService.getSalesIncome();
		getSalesIncomeamt = (double) getSalesIncome.get(0).get("totalamount_amount");

		// ----------------------------------------------------------------------------
		double getAccountsPayableamt;
		List<Map<String, Object>> getAccountsPayable = accountheadsService.getAccountsPayable();
		getAccountsPayableamt = (double) getAccountsPayable.get(0).get("totalamount_amount");
		// ----------------------------------------------------------------------------
		double getGSTPayableamt;
		List<Map<String, Object>> getGSTPayable = accountheadsService.getGSTPayable();
		getGSTPayableamt = (double) getGSTPayable.get(0).get("taxamount");
		// ----------------------------------------------------------------------------
		double getGSTReceivableamt;
		List<Map<String, Object>> getGSTReceivable = accountheadsService.getGSTReceivable();
		getGSTReceivableamt = (double) getGSTReceivable.get(0).get("taxamount");
		// ----------------------------------------------------------------------------
		double getInterestIncomeamt;
		List<Map<String, Object>> getInterestIncome = accountheadsService.getInterestIncome();
		getInterestIncomeamt = (double) getInterestIncome.get(0).get("income");

		// ----------------------------------------------------------------------------
		double getOtherIncomeamt;
		List<Map<String, Object>> getOtherIncome = accountheadsService.getOtherIncome();
		getOtherIncomeamt = (double) getOtherIncome.get(0).get("income");
		// ----------------------------------------------------------------------------
		double getinvoice_receipt_masteramt;
		List<Map<String, Object>> getinvoice_receipt_master = accountheadsService.getinvoice_receipt_master();
		getinvoice_receipt_masteramt = (double) getinvoice_receipt_master.get(0).get("amount");
		// ----------------------------------------------------------------------------
		double getbranchpurchase_payment_masteramt;
		List<Map<String, Object>> getbranchpurchase_payment_master = accountheadsService.getbranchpurchase_payment_master();
		getbranchpurchase_payment_masteramt = (double) getbranchpurchase_payment_master.get(0).get("amount");
		// ----------------------------------------------------------------------------
		double getprojectpurchase_payment_masteramt;
		List<Map<String, Object>> getprojectpurchase_payment_master = accountheadsService.getprojectpurchase_payment_master();
		getprojectpurchase_payment_masteramt = (double) getprojectpurchase_payment_master.get(0).get("amount");
		// ----------------------------------------------------------------------------
		
		String oldstr = "";
		for (Accountsheads obj : ls) {
			if (!obj.getMastergroup().equalsIgnoreCase(oldstr)) {
				obj.setFirstelement(true);
			}
			
			//-----------------------------------------------------------------
			if(obj.getAccountheads().equalsIgnoreCase("Cash") || obj.getAccountheads().equalsIgnoreCase("Bank Account") )			
			{
				
				double branchexpense_masteramt1=0;
				
				for(BranchMaster bm :branchMasterService.findAll())
				{
					List<BranchexpenseMaster> lsbxm = bm.getBranchexpenseMasterList().stream().filter(C-> C.getDepitedfrom().equalsIgnoreCase(String.valueOf(obj.getAccountheadid()))).toList();
					branchexpense_masteramt1 =branchexpense_masteramt1 + lsbxm.stream().mapToDouble(x -> x.getBranchexpenseItemMasterlist().stream().mapToDouble(BranchexpenseItemMaster :: getTotalamountAmount).sum()).sum();
				 }
				// ----------------------------------------------------------------------------
				double getaccounttransferdepositamt;
				List<Map<String, Object>> getaccounttransferdeposit = accountheadsService.getaccounttransferdeposit(obj.getAccountheadid());
				getaccounttransferdepositamt = (double) getaccounttransferdeposit.get(0).get("t_amount");
				// ----------------------------------------------------------------------------
				double getaccounttransferwithdrawamt;
				List<Map<String, Object>> getaccounttransferwithdraw = accountheadsService.getaccounttransferwithdraw(obj.getAccountheadid());
				getaccounttransferwithdrawamt = (double) getaccounttransferwithdraw.get(0).get("t_amount");
				// ----------------------------------------------------------------------------
				double getaccountincomedepositamt;
				List<Map<String, Object>> getaccountincomedeposit = accountheadsService.getaccountincomedeposit(obj.getAccountheadid());
				getaccountincomedepositamt = (double) getaccountincomedeposit.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getaccountincomewithdrawamt;
				List<Map<String, Object>> getaccountincomewithdraw = accountheadsService.getaccountincomewithdraw(obj.getAccountheadid());
				getaccountincomewithdrawamt = (double) getaccountincomewithdraw.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getbranchpurchase_payment_masteramt1;
				List<Map<String, Object>> getbranchpurchase_payment_master1 = accountheadsService.getbranchpurchase_payment_master(obj.getAccountheadid());
				getbranchpurchase_payment_masteramt1 = (double) getbranchpurchase_payment_master1.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getinvoice_receipt_masteramt1;
				List<Map<String, Object>> getinvoice_receipt_master1 = accountheadsService.getinvoice_receipt_master(obj.getAccountheadid());
				getinvoice_receipt_masteramt1 = (double) getinvoice_receipt_master1.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getprojectpurchase_payment_masteramt1;
				List<Map<String, Object>> getprojectpurchase_payment_master1 = accountheadsService.getprojectpurchase_payment_master(obj.getAccountheadid());
				getprojectpurchase_payment_masteramt1 = (double) getprojectpurchase_payment_master1.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getproject_expensewithdrawamt;
				List<Map<String, Object>> getproject_expensewithdraw = accountheadsService.getproject_expensewithdraw(obj.getAccountheadid());
				getproject_expensewithdrawamt = (double) getproject_expensewithdraw.get(0).get("amount");
				// ----------------------------------------------------------------------------
				
				
				obj.setAmount((getaccounttransferdepositamt + getaccountincomedepositamt+getinvoice_receipt_masteramt1)-(getprojectpurchase_payment_masteramt1 + getbranchpurchase_payment_masteramt1+ getaccounttransferwithdrawamt + getaccountincomewithdrawamt+ branchexpense_masteramt1+ getproject_expensewithdrawamt ));
				
			}
			//-----------------------------------------------------------------
			if(obj.getAccountheads().equalsIgnoreCase("Expense"))			
			{
				double getproject_expense_categoryamt;
				List<Map<String, Object>> getproject_expense_category = accountheadsService.getproject_expense_category(obj.getAccountheadid());
				getproject_expense_categoryamt = (double) getproject_expense_category.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getbranchexpense_item_master_byexpenseItemamt;
				List<Map<String, Object>> getbranchexpense_item_master_byexpenseItem = accountheadsService.getbranchexpense_item_master_byexpenseItem(obj.getAccountheadid());
				getbranchexpense_item_master_byexpenseItemamt = (double) getbranchexpense_item_master_byexpenseItem.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getbranchpurchase_item_master_categoryamt;
				List<Map<String, Object>> getbranchpurchase_item_master_category = accountheadsService.getbranchpurchase_item_master_category(obj.getAccountheadid());
				getbranchpurchase_item_master_categoryamt = (double) getbranchpurchase_item_master_category.get(0).get("amount");
				// ----------------------------------------------------------------------------
				double getprojectpurchase_item_master_categoryamt;
				List<Map<String, Object>> getprojectpurchase_item_master_category = accountheadsService.getprojectpurchase_item_master_category(obj.getAccountheadid());
				getprojectpurchase_item_master_categoryamt = (double) getprojectpurchase_item_master_category.get(0).get("amount");
				// ----------------------------------------------------------------------------
				obj.setAmount(getproject_expense_categoryamt+ getbranchexpense_item_master_byexpenseItemamt+ getprojectpurchase_item_master_categoryamt + getbranchpurchase_item_master_categoryamt);		
			}
			//-----------------------------------------------------------------
			
			switch(obj.getRefnumber()) {
			case "1100":
				obj.setAmount(getSalesIncomeamt-getinvoice_receipt_masteramt);
				break;
			case "2100":
				obj.setAmount(getAccountsPayableamt-getprojectpurchase_payment_masteramt-getbranchpurchase_payment_masteramt);
				break;
			case "2201":
				obj.setAmount(getGSTPayableamt);
				break;
			case "2202":
				obj.setAmount(getGSTReceivableamt);
				break;
			case "4000":
				obj.setAmount(getAccountsReceivableamt);
				break;
			case "4100":
				obj.setAmount(getInterestIncomeamt);
				break;
			case "4900":
				obj.setAmount(getOtherIncomeamt);
				break;
			}
			
			oldstr = obj.getMastergroup();
		}

		theModel.addAttribute("accountslist", ls);
		theModel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist("Accounts"));
		return "accountsreport";
	}

}