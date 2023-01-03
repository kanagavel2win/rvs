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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

import com.rvs.springboot.thymeleaf.entity.ActivityMaster;
import com.rvs.springboot.thymeleaf.entity.ActivityMasterFiles;
import com.rvs.springboot.thymeleaf.entity.ActivityMasterGuest;
import com.rvs.springboot.thymeleaf.entity.AssetAudit;
import com.rvs.springboot.thymeleaf.entity.AssetAuditFiles;
import com.rvs.springboot.thymeleaf.entity.AssetMaster;
import com.rvs.springboot.thymeleaf.entity.AssetMasterFiles;
import com.rvs.springboot.thymeleaf.entity.AssetService;
import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;
import com.rvs.springboot.thymeleaf.entity.CheckIn;
import com.rvs.springboot.thymeleaf.entity.CheckInFiles;
import com.rvs.springboot.thymeleaf.entity.CheckOut;
import com.rvs.springboot.thymeleaf.entity.CheckOutFiles;
import com.rvs.springboot.thymeleaf.entity.ContactOrganization;
import com.rvs.springboot.thymeleaf.entity.ContactPerson;
import com.rvs.springboot.thymeleaf.entity.DealMaster;
import com.rvs.springboot.thymeleaf.entity.DealProjectMaster;
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
import com.rvs.springboot.thymeleaf.entity.InsuranceDetails;
import com.rvs.springboot.thymeleaf.entity.InsuranceMaster;
import com.rvs.springboot.thymeleaf.entity.LeadMaster;
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;
import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.LoginRegistrationDto;
import com.rvs.springboot.thymeleaf.entity.ProjectMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectTaskMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateActivityMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectTemplateTaskMaster;
import com.rvs.springboot.thymeleaf.entity.ProjectdetailsMaster;
import com.rvs.springboot.thymeleaf.entity.VendorEmgContact;
import com.rvs.springboot.thymeleaf.entity.VendorFiles;
import com.rvs.springboot.thymeleaf.entity.VendorMaster;
import com.rvs.springboot.thymeleaf.entity.payslip;
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
import com.rvs.springboot.thymeleaf.service.ProjectTemplateMasterService;
import com.rvs.springboot.thymeleaf.service.VendorMasterService;

@Controller

public class HomeController {

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

	DateFormat displaydateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat displaydateFormatrev = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat displaydatetimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

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
		String profilephoto="";
		if(obj.getEmployeeFiles().size()>0)
		{
			List<EmployeeFiles> empfile=obj.getEmployeeFiles().stream().filter(C -> C.getPhoto_Attach() != null).collect(Collectors.toList());
			if(empfile.size()>0)
			{
				profilephoto= empfile.get(0).getPhoto_Attach();
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
		return "branchadd";
	}

	@GetMapping("branchlist")
	public String branchList(Model themodel) {
		List<BranchMaster> bmList = branchMasterService.findAll();

		// System.out.println("<---------List of Branch------------->");
		// System.out.println(bmList);

		themodel.addAttribute("branchlist", bmList);
		return "branchlist";

	}

	@GetMapping("editbranch")
	public String getBranchMassterDetails(Model theModel, @RequestParam("id") int branchid) {
		List<BranchMaster> bmlist = branchMasterService.findAll().stream()
				.filter(c -> c.getB_TYPE().equalsIgnoreCase("Head Office")).collect(Collectors.toList());
		theModel.addAttribute("Headofficelist", bmlist);

		BranchMaster obj_bm = branchMasterService.findById(branchid);
		theModel.addAttribute("BranchMaster", obj_bm);
		return "branchadd";
	}

	@GetMapping("emplist")
	public String employeelist(Model theModel) {
		List<String> data = new ArrayList<String>();

		List<EmployeeMaster> ls = new ArrayList<EmployeeMaster>();
		ls = employeeMasterService.findAll();

		for (EmployeeMaster obj : ls) {
			String str = "";
			List<EmployeeFiles> validProfilephoto = obj.getEmployeeFiles().stream()
					.filter(c -> c.getPhoto_Attach() != null).collect(Collectors.toList());

			str += obj.getStaffName() + "|";
			if (validProfilephoto.size() > 0) {
				str += validProfilephoto.get(0).getPhoto_Attach() + "|";
			} else {
				str += " |";
			}

			str += obj.getEmpMasterid() + "|";

			// ----------------------------------------------------------------------

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();

			List<EmployeeJobempstatus> objjob = new ArrayList<>();
			objjob = employeeJobempstatusService.findByEmployeeid(obj.getEmpMasterid());
			if (objjob.size() > 0) {
				List<EmployeeJobempstatus> objjobgreen = objjob.stream()
						.filter(c -> dateFormat.format(date).compareTo(c.getEmpstatus_effectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				objjobgreen.sort(Comparator.comparing(EmployeeJobempstatus::getEmpstatus_effectivedate));
				if (objjobgreen.size() > 0) {

					str += objjobgreen.get(objjobgreen.size() - 1).getEmpstatus_employmentstatus() + "|";
				} else {
					str += " |";
				}

			} else {
				str += " |";
			}

			List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
			infoobjjob = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());
			if (infoobjjob.size() > 0) {
				List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
						.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
						.collect(Collectors.toList());
				infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
				if (infoobjjobgreen.size() > 0) {
					str += infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJobtitle() + "|";
					str += infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation() + "|";
				} else {
					str += " | |";
				}
			} else {
				str += " | |";
			}

			List<EmployeeJobHire> hireobj = new ArrayList<>();
			hireobj = employeeJobHireService.findByEmployeeid(obj.getEmpMasterid());

			if (hireobj.size() > 0) {
				try {

					str += displaydateFormat.format(
							new SimpleDateFormat("yyyy-MM-dd").parse(hireobj.get(0).getEmployeehiredate().toString()))
							+ "|";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else {
				str += " |";
			}

			// ----------------------------------------------------------------------

			data.add(str);
		}
		// System.out.println(data);
		theModel.addAttribute("emplist", data);
		return "emplist";
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
		// System.out.println("empEduid" + Arrays.toString(empEduid));
		// System.out.println(College_Institution.length);
		// System.out.println("College_Institution" +
		// Arrays.toString(College_Institution));
		// System.out.println("Degree" + Arrays.toString(Degree));
		// System.out.println("MajorSpecialization" +
		// Arrays.toString(MajorSpecialization));
		// System.out.println("Percentage_GPA" + Arrays.toString(Percentage_GPA));
		// System.out.println("FromYear" + Arrays.toString(FromYear));
		// System.out.println("ToYear" + Arrays.toString(ToYear));
		// System.out.println("------------------------------------");
		// System.out.println("language" + Arrays.toString(language));
		// System.out.println("languageid" + Arrays.toString(languageid));
		for (int farr = 0; farr < languageid.length; farr++) {
			String templangurow = languageid[farr];
			// System.out.println(params.get("lan_read" + templangurow));
			// System.out.println(params.get("lan_write" + templangurow));
			// System.out.println(params.get("lan_speak" + templangurow));
		}
		// System.out.println("------------------------------------");
		// System.out.println("empExperienceid" + Arrays.toString(empExperienceid));
		// System.out.println("Company" + Arrays.toString(Company));
		// System.out.println("Location" + Arrays.toString(Location));
		// System.out.println("expFromyear" + Arrays.toString(expFromyear));
		// System.out.println("expToyear" + Arrays.toString(expToyear));
		// System.out.println("JobTitle" + Arrays.toString(JobTitle));

		// System.out.println("------------------------------------");

		// System.out.println("------------------------------------");
		// System.out.println("Emgid" + Arrays.toString(emgid));

		for (int farr = 0; farr < emgid.length; farr++) {
			String templangurow = emgid[farr];
			// System.out.println(params.get("Emg_primarycontact" + templangurow));
			// System.out.println(params.get("Emg_InsuranceNominee" + templangurow));

		}
		/*
		 * System.out.println("empEmgContactid" + Arrays.toString(empEmgContactid));
		 * System.out.println("Emg_Name" + Arrays.toString(Emg_Name));
		 * System.out.println("Emg_Relation" + Arrays.toString(Emg_Relation));
		 * System.out.println("Emg_PersonalPhone" + Arrays.toString(Emg_PersonalPhone));
		 * System.out.println("Emg_OtherPhone" + Arrays.toString(Emg_OtherPhone));
		 * System.out.println("Emg_EmailID" + Arrays.toString(Emg_EmailID));
		 * System.out.println("Emg_Street1" + Arrays.toString(Emg_Street1));
		 * System.out.println("Emg_Street2" + Arrays.toString(Emg_Street2));
		 * System.out.println("Emg_Village" + Arrays.toString(Emg_Village));
		 * System.out.println("Emg_Taluk" + Arrays.toString(Emg_Taluk));
		 * System.out.println("Emg_City" + Arrays.toString(Emg_City));
		 * System.out.println("Emg_State" + Arrays.toString(Emg_State));
		 * System.out.println("Emg_ZIP" + Arrays.toString(Emg_ZIP));
		 * System.out.println("Emg_Country" + Arrays.toString(Emg_Country));
		 */
		// System.out.println("------------------------------------");
		Set<EmployeeEducation> eduls = new LinkedHashSet<EmployeeEducation>();
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

		Set<EmployeeEmgContact> emgls = new LinkedHashSet<EmployeeEmgContact>();
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
			if (Emg_Taluk.length > 0)
				empcont.setEmg_Taluk(Emg_Taluk[farr]);
			if (Emg_Village.length > 0)
				empcont.setEmg_Village(Emg_Village[farr]);
			if (Emg_ZIP.length > 0)
				empcont.setEmg_ZIP(Emg_ZIP[farr]);
			emgls.add(empcont);
		}
		employeemaster.setEmployeeEmgContact(emgls);
		// System.out.println("--------------Step 2 End----------------------");

		Set<EmployeeExperience> exptrls = new LinkedHashSet<EmployeeExperience>();
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
		Set<EmployeeLanguage> langls = new LinkedHashSet<EmployeeLanguage>();
		for (int farr = 0; farr < language.length; farr++) {
			EmployeeLanguage emplang = new EmployeeLanguage();
			String templangurow = languageid[farr];
			// System.out.println("params.get(\"lan_read\" + templangurow) " +
			// params.get("lan_read" + templangurow));
			// System.out.println("params.get(\"lan_write\" + templangurow) " +
			// params.get("lan_write" + templangurow));
			// System.out.println("params.get(\"lan_speak\" + templangurow) " +
			// params.get("lan_speak" + templangurow));
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
		// System.out.println(Arrays.toString(photoempFileid));
		// System.out.println(Arrays.toString(resumeempFileid));
		// System.out.println(Arrays.toString(certificateempFileid));
		// System.out.println(Arrays.toString(photoempFileidstr));
		// System.out.println(Arrays.toString(resumeempFileidstr));
		// System.out.println(Arrays.toString(certificateempFileidstr));

		Set<EmployeeFiles> filels = new LinkedHashSet<EmployeeFiles>();

		if (photoempFileid != null)
			for (int farr = 0; farr < photoempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (photoempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(photoempFileid[farr]));
				}
				obj.setPhoto_Attach(photoempFileidstr[farr]);
				filels.add(obj);
			}

		if (resumeempFileid != null)
			for (int farr = 0; farr < resumeempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (resumeempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(resumeempFileid[farr]));
				}
				obj.setResume_Attach(resumeempFileidstr[farr]);
				filels.add(obj);
			}

		if (certificateempFileid != null)
			for (int farr = 0; farr < certificateempFileid.length; farr++) {
				EmployeeFiles obj = new EmployeeFiles();

				if (certificateempFileid[farr] != null) {
					obj.setEmpFileid(Integer.parseInt(certificateempFileid[farr]));
				}
				obj.setCertificates_Attach(certificateempFileidstr[farr]);
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
			empfiles.setPhoto_Attach("employeeprofilephoto/" + filename);
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
			empfiles.setResume_Attach("employeeresume/" + filename);
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
			empfiles.setCertificates_Attach("employeecertification/" + filename);
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

		Set<EmployeeEducation> edulsnew = new LinkedHashSet<EmployeeEducation>();
		Set<EmployeeEmgContact> emglsnew = new LinkedHashSet<EmployeeEmgContact>();
		Set<EmployeeExperience> exptrlsnew = new LinkedHashSet<EmployeeExperience>();
		Set<EmployeeLanguage> langlsnew = new LinkedHashSet<EmployeeLanguage>();
		Set<EmployeeFiles> filelsnew = new LinkedHashSet<EmployeeFiles>();

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

		EmployeeMaster employeemasternew = new EmployeeMaster();
		employeemasternew = employeeMasterService.findById(id);
		// System.out.println(employeemasternew);

		Set<EmployeeEducation> edulsnew = new LinkedHashSet<EmployeeEducation>();
		Set<EmployeeEmgContact> emglsnew = new LinkedHashSet<EmployeeEmgContact>();
		Set<EmployeeExperience> exptrlsnew = new LinkedHashSet<EmployeeExperience>();
		Set<EmployeeLanguage> langlsnew = new LinkedHashSet<EmployeeLanguage>();
		Set<EmployeeFiles> filelsnew = new LinkedHashSet<EmployeeFiles>();

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
		return "empadd";
	}

	public String stringdatetime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@GetMapping("empjob")
	public String employeejob(Model theModel, @RequestParam("id") int empid) {

		int greenpointemployementstatus = 0;
		int greenpointjobstatus = 0;
		int greenpointcompensationstatus = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		EmployeeMaster emobj = employeeMasterService.findById(empid);

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
		infoobj = employeeJobinfoService.findByEmployeeid(empid);
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
		theModel.addAttribute("employeeJobempstatus", obj);
		theModel.addAttribute("employeeJobinfomation", infoobj);
		theModel.addAttribute("employeecompensation", comoobj);
		theModel.addAttribute("empid", empid);
		theModel.addAttribute("emptitle",
				emobj.getEmpid() + "000" + emobj.getEmpMasterid() + " - " + emobj.getStaffName());
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
	public String employeejobinformationupdate(@RequestParam Map<String, String> params) {
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
		return "Success" + obj.getEmployeejobinfoid();
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
					+ i + "' style='background:#8FBC8F;color:#000'>" + p + "</a>" + "<a class='cal_inner cal_innera" + i
					+ "' style='background:#FF8C69;color:#000'>" + a + "</a>" + "<a class='cal_inner cal_innert" + i
					+ "' style='background:#FFEC8B;color:#000'>" + t + "</a>" + "<a class='cal_inner cal_innerhl" + i
					+ "' style='background:#DEDEDE;color:#000'>" + hl + "</a><div></td>";

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

		Holiday obj = new Holiday();

		if (param.get("calid") != null && (!param.get("calid").equalsIgnoreCase(""))) {
			obj.setId(Integer.parseInt(param.get("calid").toString()));
		}

		obj.setTitle(param.get("title").toString());
		obj.setStart(param.get("fromdate").toString());
		obj.setEnd(param.get("todate").toString());
		obj.setAllDay(Boolean.valueOf(param.get("allDay")));
		obj.setBackgroundColor(param.get("holidaycolor").toString());
		obj.setBorderColor(param.get("holidaycolor").toString());
		obj.setColor(param.get("color").toString());
		obj.setDescription(param.get("description"));
		holidayService.save(obj);

		return String.valueOf(obj.getId());

	}

	@GetMapping("holidaydefine")
	public String holidaydefine(Model theModel) {
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
			 * HashSet<HireMasterQuestions>(list));
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

		Set<HireMasterQuestions> setHireMasterQuestions = new HashSet<HireMasterQuestions>();

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

		LeaveMaster leavemaster = new LeaveMaster();
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findAll().stream()
				.filter(c -> c.getStatus().equalsIgnoreCase("Pending")).collect(Collectors.toList());
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		List<EmployeeMaster> em = employeeMasterService.findAll();

		Map<Integer, String> emmap = em.stream()
				.collect(Collectors.toMap(EmployeeMaster::getEmpMasterid, EmployeeMaster::getStaffName));

		Date date = new Date();
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdate = new SimpleDateFormat("yyyy-MM-dd");

		int currentyear = Integer.parseInt(formatteryear.format(date).toString());
		int currentmonth = Integer.parseInt(formattermonth.format(date).toString());

		Calendar nxtcal = Calendar.getInstance();
		nxtcal.set(currentyear, currentmonth - 1, 1);
		nxtcal.add(Calendar.MONTH, 6);

		Calendar precal = Calendar.getInstance();
		precal.set(currentyear, currentmonth - 1, 1);
		precal.add(Calendar.MONTH, -6);
		String preDate = formatterdate.format(precal.getTime()).toString();
		String nxtDate = formatterdate.format(nxtcal.getTime()).toString();

		theModel.addAttribute("range", "?sd=" + preDate + "&ed=" + nxtDate);
		theModel.addAttribute("emmap", emmap);
		theModel.addAttribute("leavemaster", leavemaster);
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
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

	@GetMapping("leavehistory")
	public String leavehistory(Model theModel, @RequestParam(name = "startdate", required = false) String startdate,
			@RequestParam(name = "enddate", required = false) String enddate) {

		List<Map<String, Object>> lmhistory = leaveMasterService.findByDates(startdate, enddate);

		ArrayList<String> leavhistorylist = new ArrayList<String>();

		lmhistory.forEach(rowMap -> {

			String tempstr = "";

			tempstr += rowMap.get("empname").toString() + " ~";
			String fromdate[] = rowMap.get("fromadate").toString().split("-");
			String todate[] = rowMap.get("todate").toString().split("-");
			tempstr += fromdate[2] + "-" + fromdate[1] + "-" + fromdate[0] + " ~";
			tempstr += todate[2] + "-" + todate[1] + "-" + todate[0] + " ~";
			tempstr += rowMap.get("halfday").toString() + " ~";
			tempstr += rowMap.get("leavetype").toString() + " ~";
			tempstr += rowMap.get("status").toString() + " ~";
			tempstr += rowMap.get("notes").toString() + " ~";
			tempstr += rowMap.get("permissionstarttime").toString() + " ~";
			tempstr += rowMap.get("permissionendtime").toString() + " ~";

			if (!(rowMap.get("approvername") == null)) {
				tempstr += rowMap.get("approvername").toString() + " ~";
			} else {
				tempstr += " ~";
			}
			if (!(rowMap.get("approverejectdate") == null)) {
				tempstr += rowMap.get("approverejectdate").toString() + " ~";
			} else {
				tempstr += " ~";
			}
			if (!(rowMap.get("approvercomments") == null)) {
				tempstr += rowMap.get("approvercomments").toString() + " ~";
			} else {
				tempstr += " ~";
			}

			leavhistorylist.add(tempstr);

		});
		theModel.addAttribute("leavhistorylist", leavhistorylist);

		return "leavehistory";
	}

	@GetMapping("payroll")
	public String payrollget(Model themodel) {

		return "payroll";
	}

	@PostMapping("payroll")
	public String payrollpost(@RequestParam(name = "month") String selectedmonth, Model themodel,
			@RequestParam(value = "save", defaultValue = "", required = false) String save) {

		LocalDate lastDayOfMonth = LocalDate.parse(selectedmonth + "-01", DateTimeFormatter.ofPattern("yyyy-M-dd"))
				.with(TemporalAdjusters.lastDayOfMonth());

		String prd[] = lastDayOfMonth.toString().split("-");
		String prdenddate = prd[2] + "." + prd[1] + "." + prd[0];
		String prdStartdate = "01." + prd[1] + "." + prd[0];

		String Payperiod = prdStartdate + " - " + prdenddate;
		if (!save.equalsIgnoreCase("")) {
			payslipserive.deleteByPayperiod(Payperiod);
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
		List<Map<String, Object>> atm = attendanceMasterService.getpayrolldetails(selectedmonth, Holidaysqlstr);

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

		return "payroll";
	}

	@PostMapping("payrollpdf")
	public String payrollpdf(@RequestParam(name = "month") String selectedmonth, Model themodel,
			@RequestParam(value = "report") String report) {
		// System.out.println(selectedmonth);
		String Str = this.theMonth(Integer.parseInt(String.valueOf(selectedmonth).substring(5, 7)) - 1).toUpperCase()
				+ " " + String.valueOf(selectedmonth).substring(0, 4);
		themodel.addAttribute("report", report.replace("]", ""));
		themodel.addAttribute("monthtext", Str);
		themodel.addAttribute("selectedmonth", selectedmonth);

		return "payslippdf";
	}

	@GetMapping("attendancereport")
	public String empattendancereport(Model themodel,
			@RequestParam(name = "month", required = false, defaultValue = "") String selectedmonth) {

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
		List<Map<String, Object>> atm = attendanceMasterService.getatttendancereport(monthstr, prdenddate);

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

			String hstartdate = rowMap.get("start").toString();
			String henddate = rowMap.get("end").toString();
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

		Set<VendorEmgContact> emglsnew = new LinkedHashSet<VendorEmgContact>();
		Set<VendorFiles> filelsnew = new LinkedHashSet<VendorFiles>();

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

		Set<VendorEmgContact> vengls = new LinkedHashSet<VendorEmgContact>();
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
		// Systven.out.println("--------------Step 2 End----------------------");

		Set<VendorFiles> filels = new LinkedHashSet<VendorFiles>();

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

		Set<VendorEmgContact> venglsnew = new LinkedHashSet<VendorEmgContact>();
		Set<VendorFiles> filelsnew = new LinkedHashSet<VendorFiles>();

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
		List<String> data = new ArrayList<String>();

		List<AssetMaster> ls = new ArrayList<AssetMaster>();
		ls = assetMasterService.findAll();
		List<BranchMaster> bm = branchMasterService.findAll();
		String custodian = "";
		for (AssetMaster obj : ls) {
			String str = "";
			str += obj.getAssetId() + "|";
			str += obj.getAssetName() + "|";
			str += obj.getAssetType() + "|";

			if (obj.getBranch() != null) {
				str += bm.stream().filter(C -> C.getId() == Integer.parseInt(obj.getBranch()))
						.collect(Collectors.toList()).get(0).getBRANCH_NAME() + "|";
			} else {
				str += "- |";
			}

			str += obj.getStatus() + " |";
			custodian = "";
			if (!obj.getStatus().equalsIgnoreCase("In Stock") && (obj.getStaffID() != null)) {
				custodian = employeeMasterService.findById(Integer.parseInt(obj.getStaffID())).getStaffName();
			}
			str += custodian + " |";
			str += obj.getLastupdate() + " |";
			str = nullremover(String.valueOf(str));
			data.add(str);
		}

		theModel.addAttribute("assetlist", data);
		return "assetlist";

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
		Set<AssetMasterFiles> filelsnew = new LinkedHashSet<AssetMasterFiles>();

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

		Set<AssetMasterFiles> filelsnew = new LinkedHashSet<AssetMasterFiles>();

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

		List<AssetMaster> assetMaster = assetMasterService.findAll();

		List<AssetMaster> AssetMasterobj = assetMaster.stream().filter(C -> C.getStatus().equalsIgnoreCase("In Stock"))
				.collect(Collectors.toList());

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
		return "checkout";
	}

	@PostMapping("getassetlistbasedonbranch")
	@ResponseBody
	public String getassetlistbasedonbranch(@RequestParam("branchid") String branch) {
		String result = "";

		// ------------------------------------------------------------------------------------
		List<AssetMaster> AssetMasterobj = assetMasterService.findAll().stream()
				.filter(C -> C.getStatus().equalsIgnoreCase("In Stock")
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
				.filter(C -> C.getStatus().equalsIgnoreCase("In Stock")).collect(Collectors.toList());
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
					Set<CheckOutFiles> checkMasterfiles = new LinkedHashSet<CheckOutFiles>();
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
				.filter(C -> C.getStatus().equalsIgnoreCase("In Stock")).collect(Collectors.toList());

		themodel.addAttribute("printstr", printstr);
		request.getSession().setAttribute("printcheckoutstr", printstr);
		themodel.addAttribute("AssetMasterobj", AssetMasterobj1);
		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		return "checkout";
	}

	@PostMapping("checkoutprint")
	public String checkoutprint(Model themodel, HttpSession session, HttpServletRequest request) {

		ArrayList<String> printstr = (ArrayList<String>) request.getSession().getAttribute("printcheckoutstr");
		String temp[] = String.valueOf(printstr.get(0)).split("\\|");

		themodel.addAttribute("staffname", temp[0]);
		themodel.addAttribute("printstr", printstr);

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

		return "checkin";
	}

	@GetMapping("assettransfer")
	public String assettransfer(Model themodel,
			@RequestParam(name = "id", required = false, defaultValue = "") String ids) {

		List<AssetMaster> assetMaster = assetMasterService.findAll();
		List<AssetMaster> selectedasset = new ArrayList<AssetMaster>();
		if (!ids.equalsIgnoreCase("")) {
			String[] strarr = ids.split(",");

			for (String ai : strarr) {
				selectedasset.add(assetMaster.stream().filter(C -> C.getAssetId() == Integer.parseInt(ai))
						.collect(Collectors.toList()).get(0));
			}
		}

		List<AssetMaster> aobjls = selectedasset.stream()
				.filter(C -> !(String.valueOf(C.getStaffID())).equalsIgnoreCase("null")
						&& !(String.valueOf(C.getStaffID())).equalsIgnoreCase(""))
				.collect(Collectors.toList());
		EmployeeMaster emp = new EmployeeMaster();

		if (aobjls.size() > 0) {
			emp = employeeMasterService.findById(Integer.parseInt(aobjls.get(0).getStaffID()));

		}
		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("emp", emp);
		themodel.addAttribute("selectedasset", selectedasset);

		return "assettransfer";
	}

	@ResponseBody
	@PostMapping("getCheckoutlistfromassetMaster")
	public String getCheckoutlistfromassetMaster(@RequestParam("StaffID") String StaffID) {

		List<AssetMaster> amls = assetMasterService.findbyStaffID(StaffID);

		String res = "";
		int i = 0;
		for (AssetMaster am : amls) {
			i++;
			res += "<tr><td align='center'><div class='checkbox checkbox-success checkbox-glow'>";
			res += "<input type='checkbox' id='checkboxGlow" + i + "' name='checkbox' checked=''>";
			res += "<label for='checkboxGlow" + i + "'></label></div></td>";
			res += "<td align='left'><input type='hidden' 	value='" + am.getAssetId()
					+ "' name='AssetName' /> <span>&nbsp; " + am.getAssetName() + "&nbsp;</span></td>";
			res += "<td><select required='' id='ACondition' name='ACondition' class='form-control select'>";
			res += "<option value='" + am.getACondition() + "' > " + am.getACondition()
					+ "</option><option value='Fair'>Fair</option><option value='Good'>Good</option><option value='New'>New</option><option value='Poor'>Poor</option>";
			res += "</select></td>";
			res += "<td><select  required='' id='Status' name='Status' class='form-control selectstatus'><option value='In Stock'>Return</option><option value='Scrap'>Scrap</option>";
			res += "</select></td>";
			res += "<td class='Photo_Attach'><input type='file' name='Photo_Attach' class='form-control Photo_Attach' /></td>";
			res += "<td><input type='text' name='Comments' class='form-control'/></td></tr>";

		}

		return res;
	}

	@PostMapping("checkinsave")
	public String checkinsave(Model themodel, @RequestParam(name = "StaffID") String StaffID,
			@RequestParam(name = "CheckInDate") String CheckInDate, @RequestParam(name = "checkbox") boolean[] checkbox,
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
						Set<CheckInFiles> checkMasterfiles = new LinkedHashSet<CheckInFiles>();
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
				.filter(C -> !(C.getStatus().equalsIgnoreCase("In Stock"))).collect(Collectors.toList());

		themodel.addAttribute("printstr", printstr);
		request.getSession().setAttribute("printcheckinstrEmpname", Empname);
		request.getSession().setAttribute("printcheckinstr", printstr);

		themodel.addAttribute("AssetMasterobj", AssetMasterobj1);
		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		return "checkin";
	}

	@PostMapping("checkinprint")
	public String checkinprint(Model themodel, HttpSession session, HttpServletRequest request) {

		ArrayList<String> printstr = (ArrayList<String>) request.getSession().getAttribute("printcheckinstr");
		String printcheckinstrEmpname = (String) request.getSession().getAttribute("printcheckinstrEmpname");

		themodel.addAttribute("printstr", printstr);
		themodel.addAttribute("printcheckinstrEmpname", printcheckinstrEmpname);

		return "checkinprint";
	}

	@PostMapping("assettranferprint")
	public String assettranferprint(Model themodel, HttpSession session, HttpServletRequest request) {

		ArrayList<String> printstr = (ArrayList<String>) request.getSession().getAttribute("printcheckinstr");
		String printcheckinstrEmpname = (String) request.getSession().getAttribute("printcheckinstrEmpname");
		String printcheckinstrEmpnameto = (String) request.getSession().getAttribute("printcheckinstrEmpnameto");
		themodel.addAttribute("printstr", printstr);
		themodel.addAttribute("printcheckinstrEmpname", printcheckinstrEmpname);
		themodel.addAttribute("printcheckinstrEmpnameto", printcheckinstrEmpnameto);
		return "assettranferprint";
	}

	@PostMapping("assettransfersave")
	public String assettransfersave(Model themodel, @RequestParam(name = "StaffID") String StaffID,
			@RequestParam(name = "StaffIDto") String StaffIDto, @RequestParam(name = "CheckInDate") String CheckInDate,
			@RequestParam(name = "checkbox") boolean[] checkbox, @RequestParam(name = "AssetName") String[] AssetId,
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
				obj.setStatus("In Stock");
				obj.setACondition(Condition[i]);
				obj.setStaffIDto(StaffIDto);
				obj.setStaffIDtoapproved("No");

				if (Comments.length > 0) {
					obj.setComments(Comments[i]);
				}
				// assetMasterService.updatetheAssetStatus(Status[i],
				// Integer.parseInt(AssetId[i]), sysdate, "");

				if (Photo_Attach.length > 0) {
					if (Photo_Attach[i].getOriginalFilename().toString().length() > 0) {
						Set<CheckInFiles> checkMasterfiles = new LinkedHashSet<CheckInFiles>();
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
		String Empnameto = "";
		for (CheckIn obj : CheckInobj) {
			String str = "";

			int staffid = Integer.parseInt(obj.getStaffID());
			int assetid = Integer.parseInt(obj.getAssetId());
			int staffidto = Integer.parseInt(obj.getStaffIDto());
			AssetMaster assobj = AssetMasterobj.stream().filter(C -> C.getAssetId() == assetid)
					.collect(Collectors.toList()).get(0);

			EmployeeMaster empobj = EmployeeMasterobj.stream().filter(C -> C.getEmpMasterid() == staffid)
					.collect(Collectors.toList()).get(0);

			EmployeeMaster empobjto = EmployeeMasterobj.stream().filter(C -> C.getEmpMasterid() == staffidto)
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
			Empnameto = empobjto.getStaffName();
		}

		List<AssetMaster> AssetMasterobj1 = assetMasterService.findAll().stream()
				.filter(C -> !(C.getStatus().equalsIgnoreCase("In Stock"))).collect(Collectors.toList());

		themodel.addAttribute("printstr", printstr);
		request.getSession().setAttribute("printcheckinstrEmpname", Empname);
		request.getSession().setAttribute("printcheckinstrEmpnameto", Empnameto);
		request.getSession().setAttribute("printcheckinstr", printstr);

		themodel.addAttribute("AssetMasterobj", AssetMasterobj1);
		themodel.addAttribute("EmployeeMasterobj", EffectiveEmployee(EmployeeMasterobj));
		return "assettransfer";
	}

	@GetMapping("assetaudit")
	public String assetaudit(Model themodel) {

		List<AssetMaster> AssetMasterobj = assetMasterService.findAll();
		List<EmployeeMaster> EmployeeMasterobj = EffectiveEmployee(employeeMasterService.findAll());

		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();
		themodel.addAttribute("branchls", branchls);

		themodel.addAttribute("AssetMasterobj", AssetMasterobj);
		themodel.addAttribute("EmployeeMasterobj", EmployeeMasterobj);
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
					Set<AssetAuditFiles> assetauditMasterfiles = new LinkedHashSet<AssetAuditFiles>();
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
		return "assetaudit";
	}

	/*
	 * @GetMapping("assetservice") public String assetservice(@RequestParam("id")
	 * int id, Model themodel, ModelAndView themodelandview) {
	 * 
	 * AssetMaster AssetMasterobj = assetMasterService.findById(id);
	 * List<AssetService> AssetServiceobjlist =
	 * assetserviceService.findByAssetId(String.valueOf(id)); AssetService
	 * AssetServiceobj = new AssetService(); if (AssetServiceobjlist.size() > 0) {
	 * AssetServiceobj = AssetServiceobjlist.get(0); } else {
	 * AssetServiceobj.setAssetId(String.valueOf(AssetMasterobj.getAssetId())); }
	 * 
	 * List<String> ServiceItem = itemlistService.findByFieldName("ServiceItem");
	 * themodel.addAttribute("ServiceItem", ServiceItem);
	 * 
	 * themodel.addAttribute("AssetServiceobj", AssetServiceobj);
	 * themodel.addAttribute("AssetMasterobj", AssetMasterobj);
	 * 
	 * return "assetservice"; }
	 * 
	 * @PostMapping("assetservicesave") public String assetservicesave(Model
	 * themodel, @ModelAttribute("AssetServiceobj") AssetService AssetServiceobj) {
	 * 
	 * itemlistService.savesingletxt(AssetServiceobj.getServiceItem(),
	 * "ServiceItem");
	 * 
	 * AssetService AssetServicesave = assetserviceService.save(AssetServiceobj);
	 * AssetMaster AssetMasterobj =
	 * assetMasterService.findById(Integer.parseInt(AssetServiceobj.getAssetId()));
	 * themodel.addAttribute("AssetServiceobj", AssetServicesave);
	 * themodel.addAttribute("AssetMasterobj", AssetMasterobj);
	 * themodel.addAttribute("save", true);
	 * 
	 * List<String> ServiceItem = itemlistService.findByFieldName("ServiceItem");
	 * themodel.addAttribute("ServiceItem", ServiceItem);
	 * 
	 * return "assetservice"; }
	 */
	@GetMapping("insurancelist")
	public String insurancelist(Model theModel) {
		List<String> datastaff = new ArrayList<String>();
		List<String> dataasset = new ArrayList<String>();
		List<InsuranceMaster> ls = new ArrayList<InsuranceMaster>();
		ls = insuranceMasterService.findAll();

		Date todaydate = new Date();

		for (InsuranceMaster obj : ls) {

			VendorMaster vendor = vendorMasterService.findById(Integer.parseInt(obj.getVendorName()));

			String namestr = "";

			if (obj.getInsuranceTo().equalsIgnoreCase("Asset")) {
				AssetMaster asset = assetMasterService.findById(Integer.parseInt(obj.getAssetNameID()));
				namestr = asset.getAssetName();

				for (InsuranceDetails objindetail : obj.getInsuranceDetails()) {
					String str = "";
					str += obj.getInsuranceid() + " |";
					str += vendor.getName() + " |";
					str += namestr + " |";
					str += objindetail.getPolicyName() + " |";

					if (!String.valueOf(objindetail.getPTo()).equalsIgnoreCase("")) {
						try {
							str += displaydateFormat
									.format(new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo())) + " |";

							long differ_in_time = todaydate.getTime()
									- new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo()).getTime();

							str += insuranetimecolor((differ_in_time) / (1000 * 60 * 60 * 24)) + " |";

						} catch (ParseException e) {
						}
					} else {
						str += objindetail.getPTo() + " |";
					}

					dataasset.add(str);
				}

			} else {
				EmployeeMaster employee = employeeMasterService.findById(Integer.parseInt(obj.getStaffID()));

				namestr = employee.getStaffName();

				EmployeeEmgContact emglsnew = new EmployeeEmgContact();

				for (InsuranceDetails objindetail : obj.getInsuranceDetails()) {
					String str = "";
					str += obj.getInsuranceid() + " |";
					str += vendor.getName() + " |";
					str += namestr + " |";
					str += objindetail.getPolicyName() + " |";
					if (!String.valueOf(objindetail.getPTo()).equalsIgnoreCase("")) {
						try {
							str += displaydateFormat
									.format(new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo())) + " |";

							long differ_in_time = todaydate.getTime()
									- new SimpleDateFormat("yyyy-MM-dd").parse(objindetail.getPTo()).getTime();

							str += insuranetimecolor((differ_in_time) / (1000 * 60 * 60 * 24)) + " |";

						} catch (ParseException e) {
						}
					} else {
						str += objindetail.getPTo() + " | |";
					}

					datastaff.add(str);
				}

			}

		}

		theModel.addAttribute("datastaff", datastaff);
		theModel.addAttribute("dataasset", dataasset);
		return "insurancelist";

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

	@GetMapping("insurancenew")
	public String insurancenew(Model themodel) {

		InsuranceMaster insuranceobj = new InsuranceMaster();
		List<InsuranceDetails> setinsuranceDetails = new ArrayList<InsuranceDetails>();
		setinsuranceDetails.add(new InsuranceDetails());
		insuranceobj.setInsuranceDetails(setinsuranceDetails);
		List<VendorMaster> vm = vendorMasterService.findAll();
		List<BranchMaster> branchls = new ArrayList<BranchMaster>();
		branchls = branchMasterService.findAll();

		themodel.addAttribute("branchls", branchls);
		themodel.addAttribute("insurancemaster", insuranceobj);
		themodel.addAttribute("vm", vm);
		List<String> PolicyCover = itemlistService.findByFieldName("PolicyCover");
		themodel.addAttribute("PolicyCover", PolicyCover);

		return "insurance";
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

		return "insurance";
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

		List<ContactPerson> contactPersonlist = contactPersonService.findAll();
		themodel.addAttribute("contactPersonlist", contactPersonlist);

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
	public String contactpersonview(Model themodel, @RequestParam("id") int id) {

		ContactPerson contactperson = contactPersonService.findById(id);

		themodel.addAttribute("contactperson", contactperson);
		List<String> TYPEOFINDUSTRY = itemlistService.findByFieldName("TYPEOFINDUSTRY");
		themodel.addAttribute("TYPEOFINDUSTRY", TYPEOFINDUSTRY);
		List<String> MEMBERIN = itemlistService.findByFieldName("MEMBERIN");
		themodel.addAttribute("MEMBERIN", MEMBERIN);
		themodel.addAttribute("organizationlist", contactOrganizationService.findAll());
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));

		return "contactpersonadd";
	}

	@PostMapping("contactpersonsave")
	public String contactpersonsave(Model themodel, @ModelAttribute("contactperson") ContactPerson contactperson) {

		String collectorgids = "";
		String srcOrg = String.valueOf(contactperson.getOrganization()).replace("null", "");
		if (srcOrg.length() > 0) {
			for (String str : srcOrg.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectorgids += str + ",";
				} else {
					ContactOrganization contactOrganization = new ContactOrganization();
					contactOrganization.setOrgname(str);

					collectorgids += contactOrganizationService.save(contactOrganization).getId() + ",";
				}
			}
		}
		if (collectorgids.length() > 0) {
			collectorgids = collectorgids.substring(0, collectorgids.length() - 1);
		}
		contactperson.setOrganization(collectorgids);

		itemlistService.savesingletxt(contactperson.getTypeofindustry(), "TYPEOFINDUSTRY");
		itemlistService.savesingletxt(contactperson.getMemberin(), "MEMBERIN");
		contactperson = contactPersonService.save(contactperson);
		// -------------------------------
		mappersonstoOrganization(collectorgids, contactperson.getId());
		// --------------------------------
		themodel.addAttribute("contactperson", contactperson);
		List<String> TYPEOFINDUSTRY = itemlistService.findByFieldName("TYPEOFINDUSTRY");
		themodel.addAttribute("TYPEOFINDUSTRY", TYPEOFINDUSTRY);

		List<String> MEMBERIN = itemlistService.findByFieldName("MEMBERIN");
		themodel.addAttribute("MEMBERIN", MEMBERIN);
		themodel.addAttribute("save", true);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("organizationlist", contactOrganizationService.findAll());

		return "contactpersonadd";
	}

	@GetMapping("contactsOrganizationadd")
	public String contactsOrganizationadd(Model themodel) {

		ContactOrganization contactOrganization = new ContactOrganization();
		themodel.addAttribute("contactOrganization", contactOrganization);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("personlist", contactPersonService.findAll());
		return "contactorganizationadd";
	}

	@GetMapping("contactOrganizationview")
	public String contactOrganizationview(Model themodel, @RequestParam("id") int id) {

		ContactOrganization contactOrganization = contactOrganizationService.findById(id);
		themodel.addAttribute("contactOrganization", contactOrganization);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("personlist", contactPersonService.findAll());

		return "contactorganizationadd";
	}

	@PostMapping("contactOrganizationsave")
	public String contactOrganizationsave(Model themodel,
			@ModelAttribute("contactOrganization") ContactOrganization contactOrganization) {

		String collectpeopleids = "";
		String srcPer = String.valueOf(contactOrganization.getPersonid()).replace("null", "");
		if (srcPer.length() > 0) {
			for (String str : srcPer.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectpeopleids += str + ",";
				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					collectpeopleids += contactPersonService.save(contactperson).getId() + ",";
				}
			}
		}
		if (collectpeopleids.length() > 0) {
			collectpeopleids = collectpeopleids.substring(0, collectpeopleids.length() - 1);
		}
		contactOrganization.setPersonid(collectpeopleids);

		contactOrganization = contactOrganizationService.save(contactOrganization);

		// -------------------------------
		mapOrganizationtopersons(collectpeopleids, contactOrganization.getId());
		// --------------------------------

		themodel.addAttribute("contactOrganization", contactOrganization);
		themodel.addAttribute("save", true);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		themodel.addAttribute("personlist", contactPersonService.findAll());
		return "contactorganizationadd";
	}

	public void mappersonstoOrganization(String collectorgids, int personId) {

		final String perid = String.valueOf(personId);
		if (!collectorgids.equalsIgnoreCase("")) {
			for (String s : collectorgids.split(",")) {
				ContactOrganization contactOrganization1 = contactOrganizationService.findById(Integer.parseInt(s));
				String[] arr = String.valueOf(contactOrganization1.getPersonid()).split(",");

				if (!Arrays.stream(arr).anyMatch(x -> x.equalsIgnoreCase(perid))) {
					String temp = String.valueOf(contactOrganization1.getPersonid()).replace("null", "");
					if (temp.length() > 0) {
						contactOrganization1.setPersonid(temp + "," + personId);
					} else {
						contactOrganization1.setPersonid(String.valueOf(personId));
					}
				}
				contactOrganizationService.save(contactOrganization1);
			}
		}
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

	@GetMapping("contactsorganizationslist")
	public String contactsorganizationslist(Model themodel) {

		List<ContactOrganization> contactOrganizationlist = contactOrganizationService.findAll();
		themodel.addAttribute("contactOrganizationlist", contactOrganizationlist);

		return "contactorganizationlist";
	}

	@GetMapping("leadlist")
	public String leadlist(Model themodel) {

		List<EmployeeMaster> emplist = EffectiveEmployee(employeeMasterService.findAll());
		themodel.addAttribute("employeelist", emplist);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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
		// --------------------------------------------------
		List<LeadMaster> leadmasterls = leadMasterService.findAll();

		// Next Activity & Followers Details
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> followersmap = new HashMap();
		String followerstr = "";

		for (LeadMaster tmp1obj : leadmasterls) {
			// --------------------------------------------------
			List<Map<String, Object>> ls = activityMasterService.nextactivity("Lead", String.valueOf(tmp1obj.getId()));
			if (ls.size() > 0) {
				ls.forEach(rowMap -> {
					String activitytitle = String.valueOf(rowMap.get("activitytitle"));
					String activitytype = String.valueOf(rowMap.get("activitytype"));
					nextactmap.put(tmp1obj.getId(), activitytype + " - " + activitytitle);
				});
			} else {
				nextactmap.put(tmp1obj.getId(), "<span class='red'>No Activity</span>");
			}
			// --------------------------------------------------
			followerstr = nullremover(String.valueOf(tmp1obj.getFollower()));
			String followernames = "";
			for (String locstr : followerstr.split(",")) {
				if (!locstr.equalsIgnoreCase(""))
					followernames += emplist.stream().filter(C -> C.getEmpMasterid() == Integer.parseInt(locstr))
							.collect(Collectors.toList()).get(0).getStaffName() + " ,";
			}
			if (followernames.length() > 0) {
				followernames = followernames.substring(0, followernames.length() - 1);
			}
			followersmap.put(tmp1obj.getId(), followernames);
		}

		// --------------------------------------------------
		themodel.addAttribute("leadmasterlist", leadmasterls);
		themodel.addAttribute("followersmap", followersmap);
		themodel.addAttribute("nextactmap", nextactmap);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);
		return "leadlist";
	}

	@PostMapping("contactpersondetails")
	@ResponseBody
	public String organizationlist(@RequestParam Map<String, String> params) {
		ContactPerson obj = new ContactPerson();
		obj = contactPersonService.findById(Integer.parseInt(params.get("personid")));
		String str = obj.getPhonework() + " |" + obj.getPhonepersonal() + " |" + obj.getPhoneothers() + " |"
				+ obj.getEmailwork() + " |" + obj.getEmailpersonal() + " |" + obj.getEmailothers() + " |"
				+ obj.getFollowers() + " |";
		str = nullremover(String.valueOf(str));

		return str;
	}

	@PostMapping("leadsavestage1")
	@ResponseBody
	public String leadsavestage1(@RequestParam Map<String, String> params) {
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String Label = params.get("Label");
		String notes = params.get("notes");
		String followers = params.get("followers");
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
					ContactOrganization contactOrganization = new ContactOrganization();
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
				if (NumberUtils.isParsable(str)) {
					collectpeopleids += str + ",";
					ContactPerson contactperson = contactPersonService.findById(Integer.parseInt(str));
					contactperson.setFollowers(followers);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					contactperson.setFollowers(followers);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);

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
		LeadMaster leadMaster = new LeadMaster();
		leadMaster.setContactPerson(collectpeopleids);
		leadMaster.setOrganization(collectorgids);
		leadMaster.setTitle(Title);
		leadMaster.setSource(Source);
		leadMaster.setReference(Reference);
		leadMaster.setLabel(Label);
		leadMaster.setNotes(notes);
		leadMaster.setFollower(followers);
		leadMaster.setCreateddate(displaydatetimeFormat.format(new Date()));
		leadMasterService.save(leadMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		// ----------------------------
		return "";
	}

	@GetMapping("leadevents")
	public String leadevents(@RequestParam("id") int id, Model themodel) {
		LeadMaster leadMaster = leadMasterService.findById(id);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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

		if (!nullremover(String.valueOf(leadMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (leadMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

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
					ContactOrganization contactOrganization = new ContactOrganization();
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
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);

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

		leadMaster.setContactPerson(collectpeopleids);
		leadMaster.setOrganization(collectorgids);
		leadMasterService.save(leadMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		// ----------------------------

		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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

		if (!nullremover(String.valueOf(leadMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (leadMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

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
		themodel.addAttribute("save", true);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		return "leadevents";
	}

	@PostMapping("leadeventpart2save")
	public String leadeventpart2save(@ModelAttribute("activityMaster") ActivityMaster activityMaster,
			@RequestParam Map<String, String> params, Model themodel,
			@RequestParam(name = "activityfiles", required = false) MultipartFile activityfiles,
			HttpServletRequest request) {
		// --------------------------------------------------
		List<ActivityMasterGuest> lsactivityMasterguest = activityMaster.getActivityMasterGuest();
		String guestStaff = "";
		if (lsactivityMasterguest.size() > 0) {
			guestStaff = String.valueOf(lsactivityMasterguest.get(0).getGuestid());
			guestStaff = guestStaff.replace("null", "");
			themodel.addAttribute("guestStaff", guestStaff);
		}

		String status = String.valueOf(params.get("status"));
		status = status.replace("null", "");

		// --------------------------------------------------
		if (!status.equalsIgnoreCase("")) {
			activityMaster.setStatus("Completed");
		}
		// --------------------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("activityfiles");
		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		if (activityfiles.getOriginalFilename().toString().length() > 0) {
			List<ActivityMasterFiles> actfilelist = new ArrayList();

			ActivityMasterFiles actfiles = new ActivityMasterFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + activityfiles.getOriginalFilename();
			Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
			filename.append(tempfilename);
			actfiles.setFiles_Attach("activityfiles/" + filename);
			try {
				Files.write(fileNameandPath, activityfiles.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			actfilelist.add(actfiles);
			activityMaster.setActivityMasterFiles(actfilelist);
		}

		// --------------------------------------------------

		if (!guestStaff.equalsIgnoreCase("")) {
			List<ActivityMasterGuest> actguestlist = new ArrayList();
			String guestStaffarr1[] = guestStaff.split(",");

			for (String str : guestStaffarr1) {
				ActivityMasterGuest activityMasterGuest = new ActivityMasterGuest();
				activityMasterGuest.setGuestid(String.valueOf(str));
				actguestlist.add(activityMasterGuest);
			}
			activityMaster.setActivityMasterGuest(actguestlist);
		}

		// --------------------------------------------------
		if (String.valueOf(activityMaster.getCreatedtime()).equalsIgnoreCase("")) {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		activityMaster.setActivitycategory("Activity");
		activityMaster.setMastercategory("Lead");
		activityMaster = activityMasterService.save(activityMaster);
		// --------------------------------------------------
		LeadMaster leadMaster = leadMasterService.findById(Integer.parseInt(activityMaster.getMastercategoryid()));
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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

		if (!nullremover(String.valueOf(leadMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (leadMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

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

		themodel.addAttribute("activityMaster", activityMaster);

		return "leadevents";

	}

	@PostMapping("dealeventpart2save")
	public String dealeventpart2save(@ModelAttribute("activityMaster") ActivityMaster activityMaster,
			@RequestParam Map<String, String> params, Model themodel,
			@RequestParam(name = "activityfiles", required = false) MultipartFile activityfiles,
			HttpServletRequest request) {
		// --------------------------------------------------
		List<ActivityMasterGuest> lsactivityMasterguest = activityMaster.getActivityMasterGuest();
		String guestStaff = "";
		if (lsactivityMasterguest.size() > 0) {
			guestStaff = String.valueOf(lsactivityMasterguest.get(0).getGuestid());
			guestStaff = guestStaff.replace("null", "");
			themodel.addAttribute("guestStaff", guestStaff);
		}

		String status = String.valueOf(params.get("status"));
		status = status.replace("null", "");

		// --------------------------------------------------
		if (!status.equalsIgnoreCase("")) {
			activityMaster.setStatus("Completed");
		}
		// --------------------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("activityfiles");
		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		if (activityfiles.getOriginalFilename().toString().length() > 0) {
			List<ActivityMasterFiles> actfilelist = new ArrayList();

			ActivityMasterFiles actfiles = new ActivityMasterFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + activityfiles.getOriginalFilename();
			Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
			filename.append(tempfilename);
			actfiles.setFiles_Attach("activityfiles/" + filename);
			try {
				Files.write(fileNameandPath, activityfiles.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			actfilelist.add(actfiles);
			activityMaster.setActivityMasterFiles(actfilelist);
		}

		// --------------------------------------------------

		if (!guestStaff.equalsIgnoreCase("")) {
			List<ActivityMasterGuest> actguestlist = new ArrayList();
			String guestStaffarr1[] = guestStaff.split(",");

			for (String str : guestStaffarr1) {
				ActivityMasterGuest activityMasterGuest = new ActivityMasterGuest();
				activityMasterGuest.setGuestid(String.valueOf(str));
				actguestlist.add(activityMasterGuest);
			}
			activityMaster.setActivityMasterGuest(actguestlist);
		}

		// --------------------------------------------------
		if (String.valueOf(activityMaster.getCreatedtime()).equalsIgnoreCase("")) {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		activityMaster.setActivitycategory("Activity");
		activityMaster.setMastercategory("Deal");
		activityMaster = activityMasterService.save(activityMaster);
		// --------------------------------------------------
		DealMaster dealMaster = dealMasterService.findById(Integer.parseInt(activityMaster.getMastercategoryid()));
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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

		if (!nullremover(String.valueOf(dealMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (dealMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

			}

		}
		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("dealMaster", dealMaster);

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		themodel.addAttribute("activityMaster", activityMaster);

		return "dealevents";

	}

	@ResponseBody
	@PostMapping("leadeventnotesave")
	public String leadeventnotesave(@RequestParam Map<String, String> params) {

		String editor = params.get("editor");
		String noteckbox = params.get("noteckbox");
		String mastercategoryid = params.get("mastercategoryid");
		int noteactivityid = Integer.parseInt(params.get("noteactivityid"));

		ActivityMaster activityMaster = new ActivityMaster();
		if (noteactivityid > 0) {
			activityMaster = activityMasterService.findById(noteactivityid);
		} else {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		if (noteckbox.equalsIgnoreCase("true")) {
			activityMaster.setStatus("Completed");
		}

		activityMaster.setHtmlnotes(editor);
		activityMaster.setMastercategoryid(mastercategoryid);
		activityMaster.setActivitycategory("Note");
		activityMaster.setMastercategory("Lead");
		activityMaster = activityMasterService.save(activityMaster);

		return "Saved";
	}

	@ResponseBody
	@PostMapping("dealeventnotesave")
	public String dealeventnotesave(@RequestParam Map<String, String> params) {

		String editor = params.get("editor");
		String noteckbox = params.get("noteckbox");
		String mastercategoryid = params.get("mastercategoryid");
		int noteactivityid = Integer.parseInt(params.get("noteactivityid"));

		ActivityMaster activityMaster = new ActivityMaster();
		if (noteactivityid > 0) {
			activityMaster = activityMasterService.findById(noteactivityid);
		} else {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		if (noteckbox.equalsIgnoreCase("true")) {
			activityMaster.setStatus("Completed");
		}

		activityMaster.setHtmlnotes(editor);
		activityMaster.setMastercategoryid(mastercategoryid);
		activityMaster.setActivitycategory("Note");
		activityMaster.setMastercategory("Deal");
		activityMaster = activityMasterService.save(activityMaster);

		return "Saved";
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
	@PostMapping("gettimelinelist")
	public String gettimelinelist(@RequestParam Map<String, String> params) {

		String mastercategoryid = params.get("mastercategoryid");
		String categoryType = params.get("categoryType");

		List<Map<String, Object>> ls = activityMasterService.gettimelinelist(categoryType, mastercategoryid);

		String[] result = { "" };

		ls.forEach(rowMap -> {

			String activitytitle = String.valueOf(rowMap.get("activitytitle"));
			String activitytype = nullremover(String.valueOf(rowMap.get("activitytype")));
			String startdate = nullremover(String.valueOf(rowMap.get("startdate")));
			String starttime = nullremover(String.valueOf(rowMap.get("starttime")));
			String enddate = nullremover(String.valueOf(rowMap.get("enddate")));
			String endtime = nullremover(String.valueOf(rowMap.get("endtime")));
			String location = nullremover(String.valueOf(rowMap.get("location")));
			String description = nullremover(String.valueOf(rowMap.get("description")));
			String notes = nullremover(String.valueOf(rowMap.get("notes")));
			String htmlnotes = nullremover(String.valueOf(rowMap.get("htmlnotes")));
			String followers = nullremover(String.valueOf(rowMap.get("activityfollowers")));
			String status = nullremover(String.valueOf(rowMap.get("status")));
			ActivityMaster actimaster = activityMasterService
					.findById(Integer.parseInt(String.valueOf(rowMap.get("activity_id"))));
			// -------------------------------------------
			// guest Details
			String guestdetails = "";
			for (ActivityMasterGuest gobj : actimaster.getActivityMasterGuest()) {
				if (gobj.getGuestid() != null) {
					EmployeeMaster empobj = employeeMasterService.findById(Integer.parseInt(gobj.getGuestid()));

					if (empobj != null) {
						guestdetails += "<div class='badge badge-pill badge-light-secondary mr-1 mb-1'>"
								+ empobj.getStaffName() + "</div>";
					}
				}
			}
			// -------------------------------------------
			// Activity File
			String filedetails = "";
			for (ActivityMasterFiles aobj : actimaster.getActivityMasterFiles()) {

				filedetails += "<a href='" + aobj.getFiles_Attach() + "' target='_blank' title='"
						+ (aobj.getFiles_Attach()).toString().substring(29, aobj.getFiles_Attach().length())
						+ "'><div class='avatar mr-1 avatar-sm bg-info'><span class='avatar-content'><i class='avatar-icon bx bx-link'></i></span></div></a>";

			}
			// -------------------------------------------
			String followerdetails = "";
			EmployeeMaster empobj = null;
			for (String str : followers.split(",")) {
				if (!str.equalsIgnoreCase("")) {
					empobj = employeeMasterService.findById(Integer.parseInt(str));

					if (empobj != null) {
						followerdetails += "<div class='badge badge-pill badge-light-secondary mr-1 mb-1'>"
								+ empobj.getStaffName() + "</div>";
					}
				}

			}
			// -------------------------------------------
			if (status.equalsIgnoreCase("Completed")) {
				status = "<span class='badge-circle-light-success'><i class='bx bx-check font-size-base'></i></span>";
			} else {
				status = "<span id='" + actimaster.getActivityId()
						+ "NC' class='badge-circle-light-danger'><i class='bx bxs-flag-alt font-size-base'></i></span>"
						+ "<a id='" + actimaster.getActivityId()
						+ "' class='badge-circle-light-warning' title='Mark it As Completed'><i class='bx bx-like font-size-base'></i></a>";
			}
			// -------------------------------------------
			// time calculator
			String timecalculator = "";
			long differdate = (long) rowMap.get("differdate");
			String differtime = String.valueOf(rowMap.get("differtime"));
			long differmins = 0;
			long differhr = 0;

			if (!nullremover(String.valueOf(rowMap.get("differmins"))).equalsIgnoreCase("")) {
				differmins = (long) rowMap.get("differmins");
			}

			if (!nullremover(String.valueOf(rowMap.get("differhr"))).equalsIgnoreCase("")) {
				differmins = (long) rowMap.get("differhr");
			}

			String sorteddates = (String) rowMap.get("sorteddates");

			if (differdate > 30) {
				timecalculator = sorteddates + "";
			} else {

				if (differdate == -1) {
					timecalculator = " Tomorrow ";
				} else if (differdate < -1) {
					timecalculator = " Next Coming in  " + differdate + " days";
				} else if (differdate > 0) {
					timecalculator = differdate + " days ago";
				} else {
					timecalculator = differhr + "hrs " + differmins + "  mins ago";
				}
			}
			// -------------------------------------------------
			if (activitytitle.equalsIgnoreCase("null")) {
				result[0] += "<li class='timeline-items timeline-icon-secondary active'><div class='timeline-time'>"
						+ timecalculator + "</div>";
				result[0] += "<h6 class='timeline-title'>Notes " + status + "</h6><div class='timeline-content'>";
				result[0] += htmlnotes;
				result[0] += "</div> </li>";
			} else {
				result[0] += "<li class='timeline-items timeline-icon-secondary active'><div class='timeline-time'>"
						+ timecalculator + "</div>";
				result[0] += "<h6 class='timeline-title'>" + activitytitle + " " + status
						+ "</h6><p class='timeline-text'>" + activitytype + " (" + startdate + " - " + starttime
						+ " to " + enddate + " - " + endtime + ") </p><div class='timeline-content'>";

				if (!location.equalsIgnoreCase("")) {
					result[0] += "Location : " + location;
				}
				if (!description.equalsIgnoreCase("")) {
					result[0] += "<br/>Description : " + description;
				}
				if (!notes.equalsIgnoreCase("")) {
					result[0] += "<br/>Notes : " + notes;
				}

				result[0] += "</div>";

				if (!guestdetails.equalsIgnoreCase("")) {
					result[0] += "<div>Guest : " + guestdetails + "</div>";
				}
				if (!filedetails.equalsIgnoreCase("")) {
					result[0] += "<div>" + filedetails + "</div>";
				}

				if (!guestdetails.equalsIgnoreCase("")) {
					result[0] += "Follower: " + followerdetails;
				}

				result[0] += "<hr/></li>";
			}

		});

		return result[0];
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
		dealobj.setContactPerson(leadobj.getContactPerson());
		dealobj.setOrganization(leadobj.getOrganization());
		dealobj.setTitle(leadobj.getTitle());
		dealobj.setSource(leadobj.getSource());
		dealobj.setReference(leadobj.getReference());
		dealobj.setNotes(leadobj.getNotes());
		dealobj.setFollower(leadobj.getFollower());
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
			leadobj.setContactPerson(dealobj.getContactPerson());
			leadobj.setOrganization(dealobj.getOrganization());
			leadobj.setTitle(dealobj.getTitle());
			leadobj.setSource(dealobj.getSource());
			leadobj.setReference(dealobj.getReference());
			leadobj.setNotes(dealobj.getNotes());
			leadobj.setFollower(dealobj.getFollower());
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

	@GetMapping("deal")
	public String deal(Model themodel) {

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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
		// --------------------------------------------------
		List<DealMaster> dealmasterls = dealMasterService.findAll();
		HashMap<Integer, Integer> maptotalamt = new HashMap();
		HashMap<Integer, String> nextactmap = new HashMap();

		for (DealMaster objg : dealmasterls) {
			int totalamount = 0;
			for (DealProjectMaster objpr : objg.getDealProjectMaster()) {
				if (!nullremover(String.valueOf(objpr.getAmount())).equalsIgnoreCase("")) {
					totalamount += Integer.parseInt(objpr.getAmount());
				}
			}
			maptotalamt.put(objg.getId(), totalamount);

			// Next Activity & Followers Details
			List<Map<String, Object>> ls = activityMasterService.nextactivity("Deal", String.valueOf(objg.getId()));
			if (ls.size() > 0) {
				ls.forEach(rowMap -> {
					String activitytitle = String.valueOf(rowMap.get("activitytitle"));
					String activitytype = String.valueOf(rowMap.get("activitytype"));
					nextactmap.put(objg.getId(), "(" + activitytype + ") - " + activitytitle);
				});
			} else {
				nextactmap.put(objg.getId(), "No");
			}
		}

		// --------------------------------------------------
		themodel.addAttribute("dealmasterlist", dealmasterls);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("maptotalamt", maptotalamt);
		themodel.addAttribute("nextactmap", nextactmap);

		themodel.addAttribute("todaydate", displaydateFormat.format(new Date()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);
		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);
		return "deal";
	}

	@PostMapping("dealsavestage3")
	@ResponseBody
	public String dealsavestage3(@RequestParam Map<String, String> params) {
		String ids = String.valueOf(params.get("ids"));
		String txt = String.valueOf(params.get("txt"));
		String notes = String.valueOf(params.get("notes")).replace("null", "");
		dealMasterService.updatepipeline(ids, txt, notes);
		return "";
	}
	
	@PostMapping("dealsavestage4")
	@ResponseBody
	public String dealsavestage4(@RequestParam Map<String, String> params) {
		String ids = String.valueOf(params.get("ids"));
		
		int dealid= Integer.parseInt(ids);
		DealMaster dmobj=dealMasterService.findById(dealid);
	
		ArrayList<ProjectdetailsMaster> ls= new ArrayList<ProjectdetailsMaster>();
		
		for(DealProjectMaster dpm : dmobj.getDealProjectMaster())
		{
			ProjectdetailsMaster pdm = new ProjectdetailsMaster();
			pdm.setAmount(dpm.getAmount());
			pdm.setPrice(dpm.getPrice());
			pdm.setProjecttype(dpm.getProjecttype());
			pdm.setQuantity(dpm.getQuantity());
			pdm.setUnit(dpm.getUnit());
			ls.add(pdm);
		}
		
		ProjectMaster obj= new ProjectMaster();
		obj.setProjectdetailMaster(ls);
		obj.setTitle(dmobj.getTitle());
		obj.setContactPerson(dmobj.getContactPerson());
		obj.setOrganization(dmobj.getOrganization());
		obj.setFollowers(dmobj.getFollower());
		obj.setDealid(String.valueOf(dmobj.getId()));
		obj.setCreateddate(displaydatetimeFormat.format(new Date()));
		obj.setPipeline("Unscheduled");
		projectMasterService.save(obj);
		
		dealMasterService.updatepipeline(ids, "Won", "");
		return "";
	}

	@PostMapping("dealsavestage1")
	@ResponseBody
	public String dealsavestage1(@RequestParam Map<String, String> params) {
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Title = params.get("Title");
		String Source = params.get("Source");
		String Reference = params.get("Reference");
		String expectedclosingdate = params.get("expectedclosingdate");
		String pipeline = params.get("pipeline");
		String purpose = params.get("purpose");
		String notes = params.get("notes");
		String followers = params.get("followers");
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
					ContactOrganization contactOrganization = new ContactOrganization();
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
				if (NumberUtils.isParsable(str)) {
					collectpeopleids += str + ",";
					ContactPerson contactperson = contactPersonService.findById(Integer.parseInt(str));
					contactperson.setFollowers(followers);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					contactperson.setFollowers(followers);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);

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
		DealMaster dealMaster = new DealMaster();
		dealMaster.setContactPerson(collectpeopleids);
		dealMaster.setOrganization(collectorgids);
		dealMaster.setTitle(Title);
		dealMaster.setSource(Source);
		dealMaster.setReference(Reference);
		dealMaster.setExpectedclosingdate(expectedclosingdate);
		dealMaster.setPipeline(pipeline);
		dealMaster.setPurpose(purpose);
		dealMaster.setNotes(notes);
		dealMaster.setFollower(followers);
		dealMaster.setCreateddate(displaydatetimeFormat.format(new Date()));
		dealMasterService.save(dealMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		itemlistService.savesingletxt(purpose, "PURPOSE");
		// ----------------------------
		return "";
	}

	@GetMapping("dealevents")
	public String dealevents(@RequestParam("id") int id, Model themodel) {
		DealMaster dealMaster = dealMasterService.findById(id);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();

		List<String> statelist = dealMasterService.getStateAll();
		String state = nullremover(String.valueOf(dealMaster.getState()));
		List<String> districtlist = new ArrayList();
		if (state.length() > 0) {
			districtlist = dealMasterService.getDistrictAll(state);
		}
		themodel.addAttribute("statelist", statelist);
		themodel.addAttribute("districtlist", districtlist);

		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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
		if (dealMaster.getDealProjectMaster().size() == 0) {
			List<DealProjectMaster> dmls = new ArrayList();
			DealProjectMaster dpmobj = new DealProjectMaster();
			dmls.add(dpmobj);
			dealMaster.setDealProjectMaster(dmls);
		}

		if (!nullremover(String.valueOf(dealMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (dealMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

			}

		}
		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("dealMaster", dealMaster);

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);

		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		themodel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		themodel.addAttribute("UNITS", UNITS);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		return "dealevents";
	}

	@PostMapping("dealeventpart1save")
	public String dealeventpart1save(@ModelAttribute("dealMaster") DealMaster dealMaster,
			@RequestParam Map<String, String> params, Model themodel) {

		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String Source = params.get("Source");
		String phonework = params.get("phonework");
		String phonepersonal = params.get("phonepersonal");
		String phoneothers = params.get("phoneothers");
		String emailwork = params.get("emailwork");
		String emailpersonal = params.get("emailpersonal");
		String emailothers = params.get("emailothers");
		// ----------------------------
		List<DealProjectMaster> dpmls = dealMaster.getDealProjectMaster().stream()
				.filter(C -> !String.valueOf(C.getProjecttype()).equalsIgnoreCase("null")).collect(Collectors.toList());
		dealMaster.setDealProjectMaster(dpmls);

		for (DealProjectMaster o : dpmls) {
			itemlistService.savesingletxt(o.getProjecttype(), "NATUREOFWORK");
			itemlistService.savesingletxt(o.getUnit(), "UNITS");
		}
		// -------------------------------
		String collectorgids = "";
		String srcOrg = String.valueOf(Organization).replace("null", "");
		if (srcOrg.length() > 0) {
			for (String str : srcOrg.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectorgids += str + ",";
				} else {
					ContactOrganization contactOrganization = new ContactOrganization();
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
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					contactperson.setPhonework(phonework);
					contactperson.setPhonepersonal(phonepersonal);
					contactperson.setPhoneothers(phoneothers);
					contactperson.setEmailwork(emailwork);
					contactperson.setEmailpersonal(emailpersonal);
					contactperson.setEmailothers(emailothers);

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

		dealMaster.setContactPerson(collectpeopleids);
		dealMaster.setOrganization(collectorgids);
		dealMaster = dealMasterService.save(dealMaster);
		// ----------------------------
		itemlistService.savesingletxt(Source, "SOURCE");
		// ----------------------------

		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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

		if (!nullremover(String.valueOf(dealMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (dealMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

			}

		}
		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("dealMaster", dealMaster);

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);
		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		themodel.addAttribute("NATUREOFWORK", NATUREOFWORK);
		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		themodel.addAttribute("UNITS", UNITS);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		List<String> statelist = dealMasterService.getStateAll();
		String state = nullremover(String.valueOf(dealMaster.getState()));
		List<String> districtlist = new ArrayList();
		if (state.length() > 0) {
			districtlist = dealMasterService.getDistrictAll(state);
		}
		themodel.addAttribute("statelist", statelist);
		themodel.addAttribute("districtlist", districtlist);

		themodel.addAttribute("save", true);
		return "dealevents";
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

	@GetMapping("projectls")
	public String projectls(Model themodel) {

		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {

			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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
		// --------------------------------------------------
		List<ProjectMaster> projectmasterls = projectMasterService.findAll();
		HashMap<Integer, Integer> maptotalamt = new HashMap();
		HashMap<Integer, String> nextactmap = new HashMap();
		HashMap<Integer, String> hispendingmap = new HashMap();

		for (ProjectMaster objg : projectmasterls) {
			int totalamount = 0;
			ArrayList<String> projecttaskids= new ArrayList<String>();
			
			for (ProjectdetailsMaster objpr : objg.getProjectdetailMaster()) {
				if (!nullremover(String.valueOf(objpr.getAmount())).equalsIgnoreCase("")) {
					totalamount += Integer.parseInt(objpr.getAmount());
				}
				
				objpr.getProjecttaskMaster().forEach(rowobj -> {
					projecttaskids.add(String.valueOf(rowobj.getProjecttaskid()));
				});
			}
			maptotalamt.put(objg.getId(), totalamount);
			String taskid=String.join(",",projecttaskids);
			
			if(!taskid.equalsIgnoreCase(""))
			{
				// ---------------------------------------------------------------
				// Next Activity & Followers Details
				List<Map<String, Object>> ls = activityMasterService.nextactivity("Project",taskid);
				if (ls.size() > 0) {
					ls.forEach(rowMap -> {
						String activitytitle = String.valueOf(rowMap.get("activitytitle"));
						String activitytype = String.valueOf(rowMap.get("activitytype"));
						nextactmap.put(objg.getId(),  activitytitle);
					});
				} else {
					nextactmap.put(objg.getId(), "No");
				}
				// ---------------------------------------------------------------
				// Pending Activity
				List<Map<String, Object>> pendingls = activityMasterService.historypendingactivity("Project",
						taskid);
				if (pendingls.size() > 0) {
					ArrayList<String> pendingactlist = new ArrayList<String>();
					pendingls.forEach(rowMap -> {
						String activitytitle = String.valueOf(rowMap.get("activitytitle"));
						String activitytype = String.valueOf(rowMap.get("activitytype"));
						pendingactlist.add(activitytitle);
					});
	
					hispendingmap.put(objg.getId(), String.join(",",pendingactlist));
				}
				// ---------------------------------------------------------------
			}
		}

		// --------------------------------------------------
		List<BranchMaster> bmlist = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmlist);
		// --------------------------------------------------

		themodel.addAttribute("projectmasterlist", projectmasterls);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		themodel.addAttribute("maptotalamt", maptotalamt);
		themodel.addAttribute("nextactmap", nextactmap);
		themodel.addAttribute("hispendingmap",hispendingmap);

		themodel.addAttribute("todaydate", displaydateFormat.format(new Date()));
		List<String> MEMBERIN = itemlistService.findByFieldName("SOURCE");
		themodel.addAttribute("SOURCE", MEMBERIN);
		List<String> PURPOSE = itemlistService.findByFieldName("PURPOSE");
		themodel.addAttribute("PURPOSE", PURPOSE);
		return "project";
	}

	@PostMapping("projectsavestage3")
	@ResponseBody
	public String projectsavestage3(@RequestParam Map<String, String> params) {
		String ids = String.valueOf(params.get("ids"));
		String txt = String.valueOf(params.get("txt"));
		String notes = String.valueOf(params.get("notes")).replace("null", "");

		projectMasterService.updatepipeline(ids, txt, notes);
		return "";
	}

	@PostMapping("projectsavestage1")
	@ResponseBody
	public String projectsavestage1(@RequestParam Map<String, String> params) {
		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String deal = params.get("deal");
		String Title = params.get("Title");
		String startdate = params.get("startdate");
		String enddate = params.get("enddate");
		String pipeline = params.get("pipeline");
		String followers = params.get("followers");
		String label = params.get("label");
		String branch = params.get("branch");
		String assigntouser = params.get("assigntouser");
		String Description = params.get("Description");
		// ----------------------------

		String collectorgids = "";
		String srcOrg = String.valueOf(Organization).replace("null", "");
		if (srcOrg.length() > 0) {
			for (String str : srcOrg.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectorgids += str + ",";
				} else {
					ContactOrganization contactOrganization = new ContactOrganization();
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
				if (NumberUtils.isParsable(str)) {
					collectpeopleids += str + ",";
					ContactPerson contactperson = contactPersonService.findById(Integer.parseInt(str));
					contactperson.setFollowers(followers);
					contactPersonService.save(contactperson);

				} else {
					ContactPerson contactperson = new ContactPerson();
					contactperson.setPeoplename(str);
					contactperson.setFollowers(followers);
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

		ProjectMaster projectMaster = new ProjectMaster();
		projectMaster.setContactPerson(collectpeopleids);
		projectMaster.setOrganization(collectorgids);
		projectMaster.setTitle(Title);
		projectMaster.setStartdate(startdate);
		projectMaster.setEnddate(enddate);
		projectMaster.setPipeline(pipeline);
		projectMaster.setFollowers(followers);
		projectMaster.setLabel(label);
		projectMaster.setBranch(branch);
		projectMaster.setAssigntouser(assigntouser);
		projectMaster.setDescription(Description);
		projectMaster.setDealid(deal);

		projectMaster.setCreateddate(displaydatetimeFormat.format(new Date()));
		projectMasterService.save(projectMaster);
		return "";
	}

	@GetMapping("projectevents")
	public String projectevents(@RequestParam("id") int id, Model themodel) {
		ProjectMaster projectMaster = projectMasterService.findById(id);
		List<ContactPerson> cplis = contactPersonService.findAll();
		List<ContactOrganization> corglis = contactOrganizationService.findAll();

		ContactPerson contactPersonobj = null;
		// --------------------------------------------------
		ArrayList<String> personorgls = new ArrayList<String>();
		for (ContactPerson cp : cplis) {
			if (!nullremover(String.valueOf(cp.getOrganization())).equalsIgnoreCase("")) {
				for (String str1 : (cp.getOrganization().toString()).split(",")) {
					String temp2 = "";
					String str2 = nullremover(String.valueOf(str1));
					if (str2.length() > 0) {
						ContactOrganization obj = corglis.stream().filter(C -> C.getId() == Integer.parseInt(str2))
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
		/*if (projectMaster.getProjectdetailMaster().size() == 0) {
			List<ProjectdetailsMaster> dmls = new ArrayList();
			ProjectdetailsMaster dpmobj = new ProjectdetailsMaster();

			dmls.add(dpmobj);
			projectMaster.setProjectdetailMaster(dmls);
		}*/

		if (!nullremover(String.valueOf(projectMaster.getContactPerson())).equalsIgnoreCase("")) {
			for (String str1 : (projectMaster.getContactPerson().toString()).split(",")) {

				ContactPerson cplistemp = contactPersonService.findById(Integer.parseInt(str1));
				contactPersonobj = cplistemp;
				break;

			}

		}
		// --------------------------------------------------
		List<BranchMaster> bmlist = branchMasterService.findAll();
		themodel.addAttribute("branchlist", bmlist);

		if (nullremover(String.valueOf(projectMaster.getBranch())).length() > 0) {
			String branchname = branchMasterService.findById(Integer.parseInt(projectMaster.getBranch()))
					.getBRANCH_NAME();
			List<EmployeeMaster> emlist = getEmployeelistbasedonbranchbyid(branchname);
			themodel.addAttribute("employeelistuser", emlist);
		}
		// --------------------------------------------------
		List<ActivityMaster> amlist = new ArrayList<ActivityMaster>();

		for (ProjectdetailsMaster m : projectMaster.getProjectdetailMaster()) {
			/*
			 * if(m.getProjecttaskMaster().size()==0) { List<ProjectTaskMaster>
			 * projecttaskMasterls= new ArrayList(); projecttaskMasterls.add(new
			 * ProjectTaskMaster()); m.setProjecttaskMaster(projecttaskMasterls); }
			 */
			if (m.getProjecttaskMaster() != null) {
				for (ProjectTaskMaster prjtaskmobj : m.getProjecttaskMaster()) {
					amlist.addAll(activityMasterService.findByMastercategoryAndMastercategoryid("Project",
							String.valueOf(prjtaskmobj.getProjecttaskid())));
				}
			}
		}
		themodel.addAttribute("activitymaster", amlist);
		// --------------------------------------------------
		// Employee Name list
		HashMap<String, String> empmap = new HashMap<String, String>();

		for (EmployeeMaster em : employeeMasterService.findAll()) {
			empmap.put(String.valueOf(em.getEmpMasterid()), em.getStaffName());
		}
		themodel.addAttribute("employeemaster", empmap);

		// --------------------------------------------------
		List<ProjectTemplateMaster> projecttemplatemasterobj = projectTemplateMasterService.findAll();
		themodel.addAttribute("projecttemplatemasterobj", projecttemplatemasterobj);
		// --------------------------------------------------
		themodel.addAttribute("contactPersonobj", contactPersonobj);
		themodel.addAttribute("personlist", cplis);
		themodel.addAttribute("organizationlist", corglis);
		themodel.addAttribute("personorgls", personorgls);
		// System.out.println(projectMaster);
		themodel.addAttribute("projectMaster", projectMaster);
		themodel.addAttribute("employeelist", EffectiveEmployee(employeeMasterService.findAll()));

		List<String> NATUREOFWORK = itemlistService.findByFieldName("NATUREOFWORK");
		themodel.addAttribute("NATUREOFWORK", NATUREOFWORK);

		List<String> UNITS = itemlistService.findByFieldName("UNITS");
		themodel.addAttribute("UNITS", UNITS);

		ActivityMaster amobj = new ActivityMaster();
		amobj.setActivitytype("Task");
		amobj.setActivityfollowers(contactPersonobj.getFollowers());
		themodel.addAttribute("activityMaster", amobj);

		return "projectevents";
	}

	@PostMapping("projecteventpart1save")
	public String projecteventpart1save(@ModelAttribute("projectMaster") ProjectMaster projectMaster,
			@RequestParam Map<String, String> params, Model themodel) {

		String ContactPerson = params.get("ContactPerson");
		String Organization = params.get("Organization");
		String followers = params.get("followers");

		// ----------------------------
		List<ProjectdetailsMaster> dpmls = projectMaster.getProjectdetailMaster().stream()
				.filter(C -> !String.valueOf(C.getProjecttype()).equalsIgnoreCase("null")).collect(Collectors.toList());
		projectMaster.setProjectdetailMaster(dpmls);

		for (ProjectdetailsMaster o : dpmls) {
			itemlistService.savesingletxt(o.getProjecttype(), "NATUREOFWORK");
			itemlistService.savesingletxt(o.getUnit(), "UNITS");
		}
		// -------------------------------
		String collectorgids = "";
		String srcOrg = String.valueOf(Organization).replace("null", "");
		if (srcOrg.length() > 0) {
			for (String str : srcOrg.split(",")) {
				if (NumberUtils.isParsable(str)) {
					collectorgids += str + ",";
				} else {
					ContactOrganization contactOrganization = new ContactOrganization();
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

		projectMaster.setFollowers(followers);
		projectMaster.setContactPerson(collectpeopleids);
		projectMaster.setOrganization(collectorgids);
		projectMaster = projectMasterService.save(projectMaster);
		// ----------------------------

		

		return "redirect:projectevents?id=" + String.valueOf(projectMaster.getId()) + "&save";
		
	}

	@PostMapping("projecteventpart2save")
	public String projecteventpart2save(@ModelAttribute("activityMaster") ActivityMaster activityMaster,
			@RequestParam Map<String, String> params,
			@RequestParam(name = "activityfiles", required = false) MultipartFile activityfiles,
			HttpServletRequest request) {
		// --------------------------------------------------
		List<ActivityMasterGuest> lsactivityMasterguest = activityMaster.getActivityMasterGuest();
		String guestStaff = "";

		
		// --------------------------------------------------
		// File Uploading
		String profilephotouploadRootPath = request.getServletContext().getRealPath("activityfiles");
		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		if (activityfiles.getOriginalFilename().toString().length() > 0) {
			List<ActivityMasterFiles> actfilelist = new ArrayList();

			ActivityMasterFiles actfiles = new ActivityMasterFiles();
			StringBuilder filename = new StringBuilder();
			String tempfilename = stringdatetime() + activityfiles.getOriginalFilename();
			Path fileNameandPath = Paths.get(profilephotouploadRootPath, tempfilename);
			filename.append(tempfilename);
			actfiles.setFiles_Attach("activityfiles/" + filename);
			try {
				Files.write(fileNameandPath, activityfiles.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			actfilelist.add(actfiles);
			activityMaster.setActivityMasterFiles(actfilelist);
		}

		// --------------------------------------------------

		if (!guestStaff.equalsIgnoreCase("")) {
			List<ActivityMasterGuest> actguestlist = new ArrayList();
			String guestStaffarr1[] = guestStaff.split(",");

			for (String str : guestStaffarr1) {
				ActivityMasterGuest activityMasterGuest = new ActivityMasterGuest();
				activityMasterGuest.setGuestid(String.valueOf(str));
				actguestlist.add(activityMasterGuest);
			}
			activityMaster.setActivityMasterGuest(actguestlist);
		}

		// --------------------------------------------------
		if (String.valueOf(activityMaster.getCreatedtime()).equalsIgnoreCase("")) {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		activityMaster.setActivitycategory("Activity");
		activityMaster.setMastercategory("Project");
		activityMaster = activityMasterService.save(activityMaster);
		// --------------------------------------------------

		return "redirect:projectevents?id=" + String.valueOf(params.get("projectMasterid")) + "&eventsaved";
		// return "projectevents";

	}

	@ResponseBody
	@PostMapping("projecteventnotesave")
	public ActivityMaster projecteventnotesave(@RequestParam Map<String, String> params) {

		String editor = params.get("editor");
		String noteckbox = params.get("noteckbox");
		String mastercategoryid = params.get("mastercategoryid");
		String taskduedate = params.get("taskduedate");
		String taskactivitytitle = params.get("taskactivitytitle");
		int noteactivityid = Integer.parseInt(params.get("noteactivityid"));

		ActivityMaster activityMaster = new ActivityMaster();
		if (noteactivityid > 0) {
			activityMaster = activityMasterService.findById(noteactivityid);
		} else {
			activityMaster.setCreatedtime(displaydatetimeFormat.format(new Date()));
		}
		if (noteckbox.equalsIgnoreCase("true")) {
			activityMaster.setStatus("Completed");
		}

		activityMaster.setActivitytitle(taskactivitytitle);
		activityMaster.setDuedate(taskduedate);
		activityMaster.setHtmlnotes(editor);
		activityMaster.setMastercategoryid(mastercategoryid);
		activityMaster.setActivitycategory("Note");
		activityMaster.setMastercategory("Project");
		activityMaster = activityMasterService.save(activityMaster);

		return activityMaster;
	}

	@GetMapping("projecttemplatelist")
	public String projecttemplatelist(Model themodel) {
		List<ProjectTemplateMaster> prols = projectTemplateMasterService.findAll();
		themodel.addAttribute("projecttemplatelist", prols);
		return "projecttemplatelist";
	}

	@ResponseBody
	@PostMapping("loadprojecttemplate")
	public String loadprojecttemplate(@RequestParam Map<String, String> params) {
		int projecttemplateid = Integer.parseInt(params.get("projecttemplistVal"));
		int srcprojectdetailid = Integer.parseInt(params.get("srcprojectdetailid"));
		int projectMasterid = Integer.parseInt(params.get("projectMasterid"));

		ProjectTemplateMaster obj = projectTemplateMasterService.findById(projecttemplateid);
		ProjectMaster projectMasterObj = projectMasterService.findById(projectMasterid);

		List<ProjectTaskMaster> projectTaskMaster = new ArrayList<ProjectTaskMaster>();

		// Create Task from Project Template
		for (ProjectTemplateTaskMaster pttmobj : obj.getProjectTemplateTaskMaster()) {
			projectTaskMaster.add(new ProjectTaskMaster(0, pttmobj.getTasktitle()));
		}

		projectMasterObj.getProjectdetailMaster().stream().filter(C -> C.getProjectdetailid() == srcprojectdetailid)
				.findFirst().ifPresent(C -> C.setProjecttaskMaster(projectTaskMaster));

		projectMasterObj = projectMasterService.save(projectMasterObj);
		// Create Activity from Project Template
		List<ProjectTaskMaster> ptmstobj = projectMasterObj.getProjectdetailMaster().stream()
				.filter(C -> C.getProjectdetailid() == srcprojectdetailid).collect(Collectors.toList()).get(0)
				.getProjecttaskMaster();

		List<ActivityMaster> actvitymasterlist = new ArrayList<ActivityMaster>();

		ProjectTemplateTaskMaster PprojectTemplateTaskMasterobj = null;
		for (ProjectTaskMaster tempobj : ptmstobj) {
			PprojectTemplateTaskMasterobj = obj.getProjectTemplateTaskMaster().stream()
					.filter(C -> C.getTasktitle().equalsIgnoreCase(tempobj.getProjecttasktitle()))
					.collect(Collectors.toList()).get(0);
			for (ProjectTemplateActivityMaster actobj : PprojectTemplateTaskMasterobj
					.getProjectTemplateActivityMaster()) {
				ActivityMaster am = new ActivityMaster();
				am.setActivitytype(actobj.getActivitytype());
				am.setActivitycategory("Activity");
				am.setMastercategory("Project");
				am.setActivitytitle(actobj.getActivitytitle());
				am.setMastercategoryid(String.valueOf(tempobj.getProjecttaskid()));
				actvitymasterlist.add(am);
			}

		}
		activityMasterService.saveall(actvitymasterlist);

		return "Loaded";
	}

	@GetMapping("projecttemplate")
	public String projecttemplate(Model themodel,
			@RequestParam(name = "id", required = false, defaultValue = "0") Integer id) {
		ProjectTemplateMaster proobj = new ProjectTemplateMaster();

		if (id > 0) {
			proobj = projectTemplateMasterService.findById(id);
		}
		themodel.addAttribute("projecttemplateobject", proobj);

		return "projecttemplate";
	}

	@PostMapping("projecttemplatesave")
	public String projecttemplatesave(Model themodel,
			@ModelAttribute("projecttemplateobject") ProjectTemplateMaster obj) {

		obj = projectTemplateMasterService.save(obj);
		projectTemplateMasterService.deletenullrows();
		return "redirect:projecttemplate?id=" + obj.getId() + "&sucess";
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
	
	

}
