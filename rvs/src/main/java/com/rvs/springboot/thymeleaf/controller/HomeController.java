package com.rvs.springboot.thymeleaf.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rvs.springboot.thymeleaf.entity.AttendanceMaster;
import com.rvs.springboot.thymeleaf.entity.BranchMaster;
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
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;
import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.LoginRegistrationDto;
import com.rvs.springboot.thymeleaf.entity.payslip;
import com.rvs.springboot.thymeleaf.service.AttendanceMasterService;
import com.rvs.springboot.thymeleaf.service.BranchMasterService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobHireService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobcompensationService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobempstatusService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobinfoService;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;
import com.rvs.springboot.thymeleaf.service.HireMasterService;
import com.rvs.springboot.thymeleaf.service.HolidayService;
import com.rvs.springboot.thymeleaf.service.LeaveMasterService;
import com.rvs.springboot.thymeleaf.service.LoginService;
import com.rvs.springboot.thymeleaf.service.PaySlipService;

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

	DateFormat displaydateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@ModelAttribute
	public void addAttributes(Model themodel, HttpSession session, HttpServletRequest request) {

		String dataLoginEmpID = "";
		String dataLoginEmpName = "";
		String dataLoginrole = "";
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
			} catch (NullPointerException e) {
				request.getSession().setAttribute("dataLoginEmpID", getLoginempID());
				request.getSession().setAttribute("dataLoginEmpName", getLoginEmpName());
				request.getSession().setAttribute("dataLoginrole", getdataLoginrole());
			}

			dataLoginEmpID = request.getSession().getAttribute("dataLoginEmpID").toString();
			dataLoginEmpName = request.getSession().getAttribute("dataLoginEmpName").toString();
			dataLoginrole = request.getSession().getAttribute("dataLoginrole").toString();
		} catch (Exception e) {

		} finally {
			themodel.addAttribute("dataLoginEmpID", dataLoginEmpID);
			themodel.addAttribute("dataLoginEmpName", dataLoginEmpName);
			themodel.addAttribute("dataLoginrole", dataLoginrole);
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

	@GetMapping("login")
	public String login(Model model) {

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
	public String employeeadd(Model themodel, ModelAndView themodelandview) {

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

		// System.out.println(empobj);

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
			if (Emg_Relation.length > 0)
				empcont.setEmg_Relation(Emg_Relation[farr]);
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
			if (language.length > 0)
				emplang.setLanguage(language[farr]);

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

		// System.out.println(edulsnew);
		// System.out.println(emglsnew);
		// System.out.println(exptrlsnew);
		// System.out.println(langlsnew);
		// System.out.println(filelsnew);

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

		// System.out.println(edulsnew);
		// System.out.println(emglsnew);
		// System.out.println(exptrlsnew);
		// System.out.println(langlsnew);
		// System.out.println(filelsnew);

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
		// System.out.println(obj);
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
			tempstr += rowMap.get("approvername").toString() + " ~";
			tempstr += rowMap.get("approvercomments").toString() + " ~";
			tempstr += rowMap.get("approverejectdate").toString() + " ~";
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

		ArrayList<String> report = new ArrayList<String>();
		List<Map<String, Object>> atm = attendanceMasterService.getpayrolldetails(selectedmonth);

		ArrayList<Double> totalnet = new ArrayList<Double>();
		totalnet.add(0, 0.0);
		atm.forEach(rowMap -> {

			int employeeid = (int) rowMap.get("employeeid");
			int P = ((BigDecimal) rowMap.get("P")).intValue();
			int A = ((BigDecimal) rowMap.get("A")).intValue();
			int T = ((BigDecimal) rowMap.get("T")).intValue();
			int HL = ((BigDecimal) rowMap.get("HL")).intValue();
			String staff_name = (String) rowMap.get("staff_name");
			String AccountNo = (String) rowMap.get("bankacno");
			String BankName = (String) rowMap.get("bank_name");
			String Locationstate = (String) rowMap.get("joblocation");
			int compayrate = Integer.parseInt(rowMap.get("compayrate").toString());

			double ctc = compayrate;
			double TotalWorkingDays = 0;
			double Absent = 0;
			double WorkingDays = 0;
			double ExtraWorkingDays = 0;
			double BasicSalary = 0;
			double DA = 0;
			double HRA = 0;
			double TOTALGROSS = 0;
			double ESI = 0;
			double EPF = 0;
			double Advance = 0;
			double TOTALDeduction = 0;
			double Monthlyincentives = 0;
			double net = 0;
			// ----------------------------------------------------
			TotalWorkingDays = P + T + (HL / 2);
			Absent = A;
			WorkingDays = TotalWorkingDays;
			if (TotalWorkingDays > 26) {
				ExtraWorkingDays = TotalWorkingDays - 26;
				WorkingDays = TotalWorkingDays - ExtraWorkingDays;
			}

			BasicSalary = Math.round((ctc / 26) * WorkingDays * 0.40);
			DA = Math.round((ctc / 26) * WorkingDays * 0.35);
			HRA = Math.round((ctc / 26) * WorkingDays * 0.25);
			TOTALGROSS = Math.round(BasicSalary + DA + HRA);
			ESI = Math.round(BasicSalary * (0.01) * 0);
			EPF = Math.round(BasicSalary * (0.12) * 0);
			TOTALDeduction = ESI + EPF + Advance;
			Monthlyincentives = Math.round(ExtraWorkingDays * (ctc / 26));
			net = Math.round((TOTALGROSS - TOTALDeduction) + Monthlyincentives);

			String str = employeeid + "-" + staff_name + "-" + ctc + "-" + TotalWorkingDays + "-" + Absent + "-"
					+ WorkingDays + "-" + ExtraWorkingDays;
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
				payslipboj.setTotalWorkingDays(String.valueOf(TotalWorkingDays));
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

	@GetMapping("attendancereport")
	public String empattendancereport(Model themodel,
			@RequestParam(name = "month", required = false, defaultValue="") String selectedmonth) {

		int prdenddate;
		String monthstr = "";
		if (!selectedmonth.equalsIgnoreCase("")) {
			LocalDate lastDayOfMonth = LocalDate.parse(selectedmonth + "-01", DateTimeFormatter.ofPattern("yyyy-M-dd"))
					.with(TemporalAdjusters.lastDayOfMonth());
		
			String prd[] = lastDayOfMonth.toString().split("-");
			prdenddate = Integer.parseInt(prd[2]);
			monthstr = prd[0] + "-" + prd[1];
		} else {
			Date currentdate = new Date();
			SimpleDateFormat formatterdate = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate lastDayOfMonth = LocalDate.parse(formatterdate.format(currentdate))
					.with(TemporalAdjusters.lastDayOfMonth());
			
			String prd[] = lastDayOfMonth.toString().split("-");
			prdenddate = Integer.parseInt(prd[2]);
			monthstr = prd[0] + "-" + prd[1];
		}

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

		themodel.addAttribute("reportarr", reportarr);
		themodel.addAttribute("prdenddate", prdenddate);

		return "empattendancereport";
	}

}
