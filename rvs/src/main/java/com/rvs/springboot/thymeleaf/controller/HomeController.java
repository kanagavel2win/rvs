package com.rvs.springboot.thymeleaf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.rvs.springboot.thymeleaf.entity.BranchMaster;
import com.rvs.springboot.thymeleaf.entity.EmployeeEducation;
import com.rvs.springboot.thymeleaf.entity.EmployeeEmgContact;
import com.rvs.springboot.thymeleaf.entity.EmployeeExperience;
import com.rvs.springboot.thymeleaf.entity.EmployeeFiles;
import com.rvs.springboot.thymeleaf.entity.EmployeeLanguage;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;
import com.rvs.springboot.thymeleaf.service.BranchMasterService;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;

@Controller

public class HomeController {

	@Autowired
	BranchMasterService branchMasterService;

	@Autowired
	EmployeeMasterService employeeMasterService;

	/*
	 * @ModelAttribute public void addAttributes(Model themodel, HttpSession
	 * session, HttpServletRequest request) {
	 * 
	 * String dataLoginEmailID = ""; String dataLoginClubID = ""; try {
	 * 
	 * try { if
	 * (request.getSession().getAttribute("dataLoginEmailID").toString().equals(null
	 * )) { dataLoginClubID = getLoginClubID();
	 * request.getSession().setAttribute("dataLoginClubID", dataLoginClubID);
	 * dataLoginEmailID = getLoginemailID();
	 * request.getSession().setAttribute("dataLoginEmailID", dataLoginEmailID); } }
	 * catch (NullPointerException e) { dataLoginClubID = getLoginClubID();
	 * request.getSession().setAttribute("dataLoginClubID", dataLoginClubID);
	 * dataLoginEmailID = getLoginemailID();
	 * request.getSession().setAttribute("dataLoginEmailID", dataLoginEmailID); }
	 * 
	 * dataLoginEmailID =
	 * request.getSession().getAttribute("dataLoginEmailID").toString();
	 * dataLoginClubID =
	 * request.getSession().getAttribute("dataLoginClubID").toString();
	 * 
	 * } catch (Exception e) {
	 * 
	 * } finally { themodel.addAttribute("dataLoginEmailID", dataLoginEmailID);
	 * themodel.addAttribute("dataLoginClubID", dataLoginClubID); }
	 * 
	 * }
	 */
	@GetMapping("/")
	public String home(Model theModel, HttpSession session, HttpServletRequest request) {
		return "index";
		/*
		 * if (logintype("ROLE_MEMBER")) {
		 * 
		 * theModel.addAttribute("MemberID", getLoginMemberID()); return "indexMember";
		 * } else if (logintype("ROLE_CLUBADMIN")) { return "index"; } else { return
		 * "redirect:logout"; }
		 */
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

	/*
	 * public String getLoginemailID() {
	 * 
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); String
	 * currentPrincipalName = authentication.getName(); User user2 =
	 * userRepository.findByEmail(currentPrincipalName); return user2.getEmail();
	 * 
	 * }
	 * 
	 * public String getLoginMemberID() {
	 * 
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); String
	 * currentPrincipalName = authentication.getName(); User user2 =
	 * userRepository.findByEmail(currentPrincipalName); return user2.getmemberID();
	 * 
	 * }
	 * 
	 * public String getLoginClubID() {
	 * 
	 * Authentication authentication =
	 * SecurityContextHolder.getContext().getAuthentication(); String
	 * currentPrincipalName = authentication.getName(); User user2 =
	 * userRepository.findByEmail(currentPrincipalName); return user2.getClubID();
	 * 
	 * }
	 */
	@GetMapping("/index")
	public String index(Model theModel) {
		return "index";
		/*
		 * if (logintype("ROLE_MEMBER")) { theModel.addAttribute("MemberID",
		 * getLoginMemberID()); return "indexMember"; } else if
		 * (logintype("ROLE_CLUBADMIN")) { return "index"; } else { return
		 * "redirect:logout"; }
		 */

	}

	/*
	 * @GetMapping("login") public String login(Model model) {
	 * 
	 * return "login"; }
	 */

	@GetMapping("403")
	public String accessDenied(Model model) {

		return "403";
	}

	@GetMapping("addnewbranch")
	public String addnewbranch(Model theModel) {

		BranchMaster obj_bm = new BranchMaster();
		theModel.addAttribute("BranchMaster", obj_bm);
		return "branchadd";

	}

	@PostMapping("branchsave")
	public String branchsave(HttpServletRequest req, @ModelAttribute("BranchMaster") BranchMaster bmobj,
			Model theModel) {
		branchMasterService.save(bmobj);
		theModel.addAttribute("BranchMaster", bmobj);
		theModel.addAttribute("save", true);
		return "branchadd";
	}

	@GetMapping("branchlist")
	public String branchList(Model themodel) {
		List<BranchMaster> bmList = branchMasterService.findAll();

		System.out.println("<---------List of Branch------------->");
		System.out.println(bmList);

		themodel.addAttribute("branchlist", bmList);
		return "branchlist";

	}

	@GetMapping("editbranch")
	public String getBranchMassterDetails(Model theModel, @RequestParam("id") int branchid) {
		BranchMaster obj_bm = branchMasterService.findById(branchid);
		theModel.addAttribute("BranchMaster", obj_bm);
		return "branchadd";
	}

	@GetMapping("emplist")
	public String employeelist(Model theModel) {
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

		System.out.println(empobj);

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
			@RequestParam("empEduid") String[] empEduid,
			@RequestParam("College_Institution") String[] College_Institution, @RequestParam("Degree") String[] Degree,
			@RequestParam("MajorSpecialization") String[] MajorSpecialization,
			@RequestParam("Percentage_GPA") String[] Percentage_GPA, @RequestParam("FromYear") String[] FromYear,
			@RequestParam("ToYear") String[] ToYear,

			@RequestParam("empLanguid") String[] empLanguid, @RequestParam("language") String[] language,
			@RequestParam("languageid") String[] languageid,
			@RequestParam(name = "lan_write", required = false) Boolean[] lan_write,
			@RequestParam(name = "lan_read", required = false) Boolean[] lan_read,
			@RequestParam(name = "lan_speak", required = false) Boolean[] lan_speak,

			@RequestParam("Photo_Attach") MultipartFile Photo_Attach,
			@RequestParam("Resume_Attach") MultipartFile Resume_Attach,
			@RequestParam("Certificates_Attach") MultipartFile Certificates_Attach,

			@RequestParam("empExperienceid") String[] empExperienceid, @RequestParam("Company") String[] Company,
			@RequestParam("Location") String[] Location, @RequestParam("expFromyear") String[] expFromyear,
			@RequestParam("expToyear") String[] expToyear, @RequestParam("JobTitle") String[] JobTitle,

			@RequestParam("empEmgContactid") String[] empEmgContactid, @RequestParam("emgid") String[] emgid,
			@RequestParam(name = "Emg_primarycontact", required = false) Boolean[] Emg_primarycontact,
			@RequestParam(name = "Emg_InsuranceNominee", required = false) Boolean[] Emg_InsuranceNominee,
			@RequestParam("Emg_Name") String[] Emg_Name, @RequestParam("Emg_Relation") String[] Emg_Relation,
			@RequestParam("Emg_PersonalPhone") String[] Emg_PersonalPhone,
			@RequestParam("Emg_OtherPhone") String[] Emg_OtherPhone, @RequestParam("Emg_EmailID") String[] Emg_EmailID,
			@RequestParam("Emg_Street1") String[] Emg_Street1, @RequestParam("Emg_Street2") String[] Emg_Street2,
			@RequestParam("Emg_Village") String[] Emg_Village, @RequestParam("Emg_Taluk") String[] Emg_Taluk,
			@RequestParam("Emg_City") String[] Emg_City, @RequestParam("Emg_State") String[] Emg_State,
			@RequestParam("Emg_ZIP") String[] Emg_ZIP, @RequestParam("Emg_Country") String[] Emg_Country,
			@RequestParam Map<String, String> params, Model themodel) {
		System.out.println("empEduid" + Arrays.toString(empEduid));
		System.out.println(College_Institution.length);
		System.out.println("College_Institution" + Arrays.toString(College_Institution));
		System.out.println("Degree" + Arrays.toString(Degree));
		System.out.println("MajorSpecialization" + Arrays.toString(MajorSpecialization));
		System.out.println("Percentage_GPA" + Arrays.toString(Percentage_GPA));
		System.out.println("FromYear" + Arrays.toString(FromYear));
		System.out.println("ToYear" + Arrays.toString(ToYear));
		System.out.println("------------------------------------");
		System.out.println("language" + Arrays.toString(language));
		System.out.println("languageid" + Arrays.toString(languageid));
		for (int farr = 0; farr < languageid.length; farr++) {
			String templangurow = languageid[farr];
			System.out.println(params.get("lan_read" + templangurow));
			System.out.println(params.get("lan_write" + templangurow));
			System.out.println(params.get("lan_speak" + templangurow));
		}
		System.out.println("------------------------------------");
		System.out.println("empExperienceid" + Arrays.toString(empExperienceid));
		System.out.println("Company" + Arrays.toString(Company));
		System.out.println("Location" + Arrays.toString(Location));
		System.out.println("expFromyear" + Arrays.toString(expFromyear));
		System.out.println("expToyear" + Arrays.toString(expToyear));
		System.out.println("JobTitle" + Arrays.toString(JobTitle));

		System.out.println("------------------------------------");

		System.out.println("------------------------------------");
		System.out.println("Emgid" + Arrays.toString(emgid));

		for (int farr = 0; farr < emgid.length; farr++) {
			String templangurow = emgid[farr];
			System.out.println(params.get("Emg_primarycontact" + templangurow));
			System.out.println(params.get("Emg_InsuranceNominee" + templangurow));

		}

		System.out.println("empEmgContactid" + Arrays.toString(empEmgContactid));
		System.out.println("Emg_Name" + Arrays.toString(Emg_Name));
		System.out.println("Emg_Relation" + Arrays.toString(Emg_Relation));
		System.out.println("Emg_PersonalPhone" + Arrays.toString(Emg_PersonalPhone));
		System.out.println("Emg_OtherPhone" + Arrays.toString(Emg_OtherPhone));
		System.out.println("Emg_EmailID" + Arrays.toString(Emg_EmailID));
		System.out.println("Emg_Street1" + Arrays.toString(Emg_Street1));
		System.out.println("Emg_Street2" + Arrays.toString(Emg_Street2));
		System.out.println("Emg_Village" + Arrays.toString(Emg_Village));
		System.out.println("Emg_Taluk" + Arrays.toString(Emg_Taluk));
		System.out.println("Emg_City" + Arrays.toString(Emg_City));
		System.out.println("Emg_State" + Arrays.toString(Emg_State));
		System.out.println("Emg_ZIP" + Arrays.toString(Emg_ZIP));
		System.out.println("Emg_Country" + Arrays.toString(Emg_Country));

		System.out.println("------------------------------------");
		Set<EmployeeEducation> eduls = new LinkedHashSet<EmployeeEducation>();
		for (int farr = 0; farr < College_Institution.length; farr++) {
			EmployeeEducation empedu = new EmployeeEducation();

			empedu.setCollege_Institution(College_Institution[farr]);
			empedu.setDegree(Degree[farr]);
			empedu.setEmpEduid(Integer.parseInt(empEduid[farr]));
			empedu.setFromYear(FromYear[farr]);
			empedu.setMajorSpecialization(MajorSpecialization[farr]);
			empedu.setPercentage_GPA(Percentage_GPA[farr]);
			empedu.setToYear(ToYear[farr]);
			eduls.add(empedu);
		}
		employeemaster.setEmployeeEducation(eduls);
		System.out.println("--------------Step 1 end----------------------");

		Set<EmployeeEmgContact> emgls = new LinkedHashSet<EmployeeEmgContact>();
		for (int farr = 0; farr < Emg_Name.length; farr++) {
			EmployeeEmgContact empcont = new EmployeeEmgContact();

			for (int farr1 = 0; farr1 < emgid.length; farr1++) {
				String templangurow = emgid[farr1];
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

			}

			empcont.setEmpEmgContactid(Integer.parseInt(empEmgContactid[farr]));
			empcont.setEmg_City(Emg_City[farr]);
			empcont.setEmg_Country(Emg_Country[farr]);
			empcont.setEmg_EmailID(Emg_EmailID[farr]);
			empcont.setEmg_Name(Emg_Name[farr]);
			empcont.setEmg_OtherPhone(Emg_OtherPhone[farr]);
			empcont.setEmg_PersonalPhone(Emg_PersonalPhone[farr]);
			empcont.setEmg_Relation(Emg_Relation[farr]);
			empcont.setEmg_State(Emg_State[farr]);
			empcont.setEmg_Street1(Emg_Street1[farr]);
			empcont.setEmg_Street2(Emg_Street2[farr]);
			empcont.setEmg_Taluk(Emg_Taluk[farr]);
			empcont.setEmg_Village(Emg_Village[farr]);
			empcont.setEmg_ZIP(Emg_ZIP[farr]);
			emgls.add(empcont);
		}
		employeemaster.setEmployeeEmgContact(emgls);
		System.out.println("--------------Step 2 End----------------------");

		Set<EmployeeExperience> exptrls = new LinkedHashSet<EmployeeExperience>();
		for (int farr = 0; farr < Company.length; farr++) {
			EmployeeExperience empexper = new EmployeeExperience();
			empexper.setCompany(Company[farr]);
			empexper.setEmpExperienceid(Integer.parseInt(empExperienceid[farr]));
			empexper.setExpFromyear(expFromyear[farr]);
			empexper.setExpToyear(expToyear[farr]);
			empexper.setJobTitle(JobTitle[farr]);
			empexper.setLocation(Location[farr]);
			exptrls.add(empexper);
		}
		employeemaster.setEmployeeExperience(exptrls);

		System.out.println("--------------Step 3 end----------------------");
		Set<EmployeeLanguage> langls = new LinkedHashSet<EmployeeLanguage>();
		for (int farr = 0; farr < language.length; farr++) {
			EmployeeLanguage emplang = new EmployeeLanguage();

			for (int farr1 = 0; farr1 < languageid.length; farr1++) {
				String templangurow = languageid[farr1];
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

			}
			emplang.setEmpLanguid(Integer.parseInt(empLanguid[farr]));
			emplang.setLanguage(language[farr]);

			langls.add(emplang);

		}
		employeemaster.setEmployeeLanguage(langls);

		System.out.println("--------------Step 5 end----------------------");
		EmployeeFiles empfiles = new EmployeeFiles();
		ArrayList<EmployeeFiles> filels = new ArrayList<EmployeeFiles>();
		filels.add(empfiles);
		System.out.println("--------------Step 6 end----------------------");
		System.out.println(employeemaster);

		employeeMasterService.save(employeemaster);
		themodel.addAttribute("employeeEducation", eduls);
		themodel.addAttribute("employeeEmgContact", emgls);
		themodel.addAttribute("employeeExperience", exptrls);
		themodel.addAttribute("employeeLanguage", langls);
		themodel.addAttribute("employeeFiles", empfiles);
		themodel.addAttribute("employeemaster", employeemaster);
		themodel.addAttribute("save", true);
		return "empadd";

	}

	@GetMapping("empjob")
	public String employeejob(Model theModel) {
		return "empjob";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("empattendance")
	public String empattendance(Model theModel,
			@RequestParam(value = "date", defaultValue = "", required = false) String attdate) {
		SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdd = new SimpleDateFormat("dd");
		SimpleDateFormat formattermonname = new SimpleDateFormat("MMMM");
		Date date = new Date();
		Date temppredate = new Date();
		Date tempnxtdate = new Date();
		String tempcurrentdate = formatterdate.format(new Date()).toString();

		if (!attdate.equalsIgnoreCase("")) {
			try {
				date = formatterdate.parse(attdate);
				temppredate = formatterdate.parse(attdate);
				tempnxtdate = formatterdate.parse(attdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String currentDate = formatterdate.format(date).toString();

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
			} else {
				calhtml = calhtml + "<td class='td' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear) + "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right'>" + i + "</div>";
			}

			calhtml = calhtml + "<div class='cal_inner_holder' id='" + i + "_div1'>" + "<a class='cal_inner cal_innerp"
					+ i + "' style='background:#8FBC8F;color:#000'>0</a>" + "<a class='cal_inner cal_innera" + i
					+ "' style='background:#FF8C69;color:#000'>0</a>" + "<a class='cal_inner cal_innert" + i
					+ "' style='background:#FFEC8B;color:#000'>0</a>" + "<a class='cal_inner cal_innerhl" + i
					+ "' style='background:#DEDEDE;color:#000'>0</a><div></td>";

		}
		calhtml = calhtml + "</tr>";

		theModel.addAttribute("preDate", preDate);
		theModel.addAttribute("nxtDate", nxtDate);
		theModel.addAttribute("tempcurrentdate", tempcurrentdate);
		theModel.addAttribute("currentdd", currentdd);
		theModel.addAttribute("currentyear", currentyear);
		theModel.addAttribute("currentmonname", currentmonname);
		theModel.addAttribute("calhtml", calhtml);
		return "empattendance";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("holidaydefine")
	public String holidaydefine(Model theModel,
			@RequestParam(value = "date", defaultValue = "", required = false) String attdate) {

		return "holidaydefine";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("leaverequest")
	public String leaverequest(Model theModel,
			@RequestParam(value = "date", defaultValue = "", required = false) String attdate) {
		SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdd = new SimpleDateFormat("dd");
		SimpleDateFormat formattermonname = new SimpleDateFormat("MMMM");
		Date date = new Date();
		Date temppredate = new Date();
		Date tempnxtdate = new Date();
		String tempcurrentdate = formatterdate.format(new Date()).toString();

		if (!attdate.equalsIgnoreCase("")) {
			try {
				date = formatterdate.parse(attdate);
				temppredate = formatterdate.parse(attdate);
				tempnxtdate = formatterdate.parse(attdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String currentDate = formatterdate.format(date).toString();

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
			String sunday = "";
			if ((firstdayofmonth + i - 2) % 7 == 0) {
				calhtml = calhtml + "</tr><tr>";
				sunday = " sunday";
			}

			if (currentdd == i) {
				calhtml = calhtml + "<td class='td selectdate " + sunday + "' name='" + (i) + "/" + (currentmonth) + "/"
						+ (currentyear) + "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right_today'>" + i + "</div>";
			} else {
				calhtml = calhtml + "<td class='td  " + sunday + "' name='" + (i) + "/" + (currentmonth) + "/"
						+ (currentyear) + "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right'>" + i + "</div>";
			}

			calhtml = calhtml + "<div class='cal_inner_holder' id='" + i + "_div1'>" + "<div></td>";

		}
		calhtml = calhtml + "</tr>";

		theModel.addAttribute("preDate", preDate);
		theModel.addAttribute("nxtDate", nxtDate);
		theModel.addAttribute("tempcurrentdate", tempcurrentdate);
		theModel.addAttribute("currentdd", currentdd);
		theModel.addAttribute("currentyear", currentyear);
		theModel.addAttribute("currentmonname", currentmonname);
		theModel.addAttribute("calhtml", calhtml);
		return "leaverequest";
	}

	@GetMapping("hire")
	public String hire(Model theModel) {
		return "hiring";
	}

	@GetMapping("hireaddjob")
	public String hirejobadd(Model theModel) {
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

}
