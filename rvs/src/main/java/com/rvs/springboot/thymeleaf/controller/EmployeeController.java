package com.rvs.springboot.thymeleaf.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.rvs.springboot.thymeleaf.entity.LeaveMaster;
import com.rvs.springboot.thymeleaf.entity.Login;
import com.rvs.springboot.thymeleaf.entity.payslip;
import com.rvs.springboot.thymeleaf.service.AttendanceMasterService;
import com.rvs.springboot.thymeleaf.service.BranchMasterService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobHireService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobcompensationService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobempstatusService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobinfoService;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;
import com.rvs.springboot.thymeleaf.service.LeaveMasterService;
import com.rvs.springboot.thymeleaf.service.LoginService;
import com.rvs.springboot.thymeleaf.service.PaySlipService;

@Controller
@RequestMapping("/rvsemp")
public class EmployeeController {

	@Autowired
	HomeController homeController;
	@Autowired
	BranchMasterService branchMasterService;
	@Autowired
	LeaveMasterService leaveMasterService;
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
	private LoginService loginService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PaySlipService paySlipService;

	@ModelAttribute
	public void addAttributes(Model themodel, HttpSession session, HttpServletRequest request) {
		homeController.addAttributes(themodel, session, request);
	}

	@GetMapping("index")
	public String index(Model themodel) {
		return "employee/index";

	}

	@GetMapping
	public String index1(Model themodel) {
		return "employee/index";

	}

	@GetMapping("leaverequest")
	public String leaverequest(Model theModel, HttpSession session, HttpServletRequest request) {
		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		LeaveMaster leavemaster = new LeaveMaster();
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());

		theModel.addAttribute("leavemaster", leavemaster);
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
		return "employee/leaverequest";
	}

	@ResponseBody
	@GetMapping("leaverequestlistjson")
	public List<LeaveMaster> leaverequestlistjson(Model theModel, HttpSession session, HttpServletRequest request) {
		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		LeaveMaster leavemaster = new LeaveMaster();
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		return leaveMasterlist;
	}

	@ResponseBody
	@PostMapping("leaverequestsave")
	public LeaveMaster leaverequestsave(Model theModel, @RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());

		LeaveMaster leaveMasterobj = new LeaveMaster();
		// params.get("branch_type")

		leaveMasterobj.setFromadate(params.get("startdate"));
		leaveMasterobj.setTodate(params.get("enddate"));
		leaveMasterobj.setHalfday(Boolean.parseBoolean(params.get("halfday")));
		leaveMasterobj.setLeavetype(params.get("leavetype"));
		leaveMasterobj.setNotes(params.get("notes"));
		leaveMasterobj.setPermissionendtime(params.get("endtime"));
		leaveMasterobj.setPermissionstarttime(params.get("starttime"));

		// Emp Location and Branch
		List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
		infoobjjob = employeeJobinfoService.findByEmployeeid(empid);
		if (infoobjjob.size() > 0) {
			List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjjobgreen.size() > 0) {
				leaveMasterobj.setApprover(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJobreportsto());

			}
		}
		// ------------------------------------------
		leaveMasterobj.setApprovercomments(params.get(""));
		leaveMasterobj.setApproverejectdate(params.get(""));

		List<Map<String, Object>> history = leaveMasterService.findByDatesEmpid(empid, leaveMasterobj.getFromadate(),
				leaveMasterobj.getTodate());

		if (history.size() == 0) {
			leaveMasterobj.setStatus("Pending");
			leaveMasterobj.setEmpid(empid);
			LeaveMaster leaveMasterobjtemp = leaveMasterService.save(leaveMasterobj);
			return leaveMasterobjtemp;
		} else {
			if (leaveMasterobj.isHalfday()) {
				if (history.size() == 1) {
					leaveMasterobj.setStatus("Pending");
					leaveMasterobj.setEmpid(empid);
					LeaveMaster leaveMasterobjtemp = leaveMasterService.save(leaveMasterobj);
					return leaveMasterobjtemp;

				} else {
					new RuntimeException("Already Leave raised for this Date");
					return leaveMasterobj;
				}

			} else {
				new RuntimeException("Already Leave raised for this Date");
				return leaveMasterobj;
			}

		}

	}

	@PostMapping("requestcancelled")
	@ResponseBody
	public String requestcancelled(@RequestParam("cancelid") int id) {
		LeaveMaster obj = leaveMasterService.findById(id);
		obj.setStatus("Cancelled");
		leaveMasterService.save(obj);

		return "Success";
	}

	@GetMapping("leavereqlist")
	public String leavereqlist(Model theModel, HttpSession session, HttpServletRequest request) {

		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		List<LeaveMaster> leaveMasterlist = leaveMasterService.findByEmpid(empid);
		Collections.sort(leaveMasterlist, Collections.reverseOrder());
		theModel.addAttribute("leaveMasterlist", leaveMasterlist);
		return "employee/leaverequesthistory";
	}

	@GetMapping("viewprofile")
	public String viewprofile(Model themodel, HttpSession session, HttpServletRequest request) {

		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());
		EmployeeMaster empobj = employeeMasterService.findById(empid);

		Set<EmployeeEducation> eduls = empobj.getEmployeeEducation();
		Set<EmployeeEmgContact> emgls = empobj.getEmployeeEmgContact();
		Set<EmployeeExperience> exptrls = empobj.getEmployeeExperience();
		Set<EmployeeLanguage> langls = empobj.getEmployeeLanguage();
		Set<EmployeeFiles> filels = empobj.getEmployeeFiles();

		themodel.addAttribute("employeeEducation", eduls);
		themodel.addAttribute("employeeEmgContact", emgls);
		themodel.addAttribute("employeeExperience", exptrls);
		themodel.addAttribute("employeeLanguage", langls);
		themodel.addAttribute("employeeFiles", filels);
		themodel.addAttribute("employeemaster", empobj);
		return "employee/empview";
	}

	@GetMapping("viewjob")
	public String employeejob(Model theModel, HttpSession session, HttpServletRequest request) {

		int empid = Integer.parseInt(request.getSession().getAttribute("dataLoginEmpID").toString());

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
		return "employee/empjob";
	}

	@GetMapping("changepassword")
	public String changepassword(Model themodel) {

		return "employee/changepassword";
	}

	@PostMapping("changepassword")
	public String changepasswordpost(Model themodel, @RequestParam(name = "oldpwd", required = true) String oldpwd,
			@RequestParam(name = "newpwd", required = true) String newpwd,
			@RequestParam(name = "renewpwd", required = true) String renewpwd, HttpSession session,
			HttpServletRequest request) {

		if (renewpwd.equals(newpwd)) {
			Login user = null;
			String empid = request.getSession().getAttribute("dataLoginEmpID").toString();

			user = loginService.findByEmpid(empid);

			if (user == null) {
				themodel.addAttribute("error", "Invalid username or password.");

			} else if (!passwordEncoder.matches(oldpwd, user.getPassword().toString())) {
				themodel.addAttribute("error", "Invalid old password.");

			} else {
				user.setPassword(passwordEncoder.encode(newpwd));
				user = loginService.savePasswordchange(user);
				themodel.addAttribute("save", true);
			}

		} else {
			themodel.addAttribute("error", "New password doesn't match");
		}
		return "employee/changepassword";
	}

	@GetMapping("paysliplist")
	public String paysliplist(Model themodel, HttpSession session, HttpServletRequest request) {

		String empid = request.getSession().getAttribute("dataLoginEmpID").toString();

		List<payslip> paysliplist = paySlipService.findByEmpid(empid);
		Collections.sort(paysliplist, Collections.reverseOrder());
		themodel.addAttribute("paysliplist", paysliplist);
		return "employee/paysliplist";
	}

	@GetMapping("payslipview")
	public String payslipview(Model themodel, @RequestParam("p") int payid) {

		payslip payslip = paySlipService.findById(payid);

		String Str = this.theMonth(Integer.parseInt(String.valueOf(payslip.getPaymonth()).substring(4, 6)))
				.toUpperCase() + " " + String.valueOf(payslip.getPaymonth()).substring(0, 4);

		themodel.addAttribute("payslip", payslip);
		themodel.addAttribute("monthtext", Str);
		return "employee/payslipview";
	}

	public String theMonth(int month) {
		String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		return monthNames[month - 1];
	}
}
