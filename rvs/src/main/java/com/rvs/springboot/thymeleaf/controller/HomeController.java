package com.rvs.springboot.thymeleaf.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
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
import java.util.stream.Collectors;

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
import com.rvs.springboot.thymeleaf.entity.EmployeeJobHire;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobcompensation;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobempstatus;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobinfo;
import com.rvs.springboot.thymeleaf.entity.EmployeeLanguage;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;
import com.rvs.springboot.thymeleaf.service.BranchMasterService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobHireService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobcompensationService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobempstatusService;
import com.rvs.springboot.thymeleaf.service.EmployeeJobinfoService;
import com.rvs.springboot.thymeleaf.service.EmployeeMasterService;

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
		List<String> data=new ArrayList();
		
		List<EmployeeMaster> ls=new ArrayList();
		ls = employeeMasterService.findAll();
		
		for(EmployeeMaster obj:ls)
		{
			String str="";
			List<EmployeeFiles> validProfilephoto=obj.getEmployeeFiles().stream().filter(c -> c.getPhoto_Attach() != null)
			  .collect(Collectors.toList());
			
			str +=obj.getStaffName() +"|";
			if(validProfilephoto.size()>0)
			{
				str +=validProfilephoto.get(0).getPhoto_Attach()+ "|";
			}else
			{
				str +=" |";
			}
			
			str +=obj.getEmpMasterid() +"|";
			
			data.add(str);
		}
		System.out.println(data);
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

			if (!empEduid[farr].isEmpty()) {
				empedu.setEmpEduid(Integer.parseInt(empEduid[farr]));
			}

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

			if (!empExperienceid[farr].isEmpty()) {
				empexper.setEmpExperienceid(Integer.parseInt(empExperienceid[farr]));
			}
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
			String templangurow = languageid[farr];
			System.out.println("params.get(\"lan_read\" + templangurow) " + params.get("lan_read" + templangurow));
			System.out.println("params.get(\"lan_write\" + templangurow) " + params.get("lan_write" + templangurow));
			System.out.println("params.get(\"lan_speak\" + templangurow) " + params.get("lan_speak" + templangurow));
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
			emplang.setLanguage(language[farr]);

			langls.add(emplang);

		}
		employeemaster.setEmployeeLanguage(langls);
		System.out.println("--------------Step 4 end----------------------");
		System.out.println(Arrays.toString(photoempFileid));
		System.out.println(Arrays.toString(resumeempFileid));
		System.out.println(Arrays.toString(certificateempFileid));
		System.out.println(Arrays.toString(photoempFileidstr));
		System.out.println(Arrays.toString(resumeempFileidstr));
		System.out.println(Arrays.toString(certificateempFileidstr));

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
		System.out.println("uploadRootPath=" + profilephotouploadRootPath);

		File uploadRootDir = new File(profilephotouploadRootPath);
		// Create directory if it not exists.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		String resumeuploadRootPath = request.getServletContext().getRealPath("employeeresume");
		System.out.println("uploadRootPath=" + resumeuploadRootPath);

		File uploadRootDirresume = new File(resumeuploadRootPath);
		if (!uploadRootDirresume.exists()) {
			uploadRootDirresume.mkdirs();
		}

		String certificateuploadRootPath = request.getServletContext().getRealPath("employeecertification");
		System.out.println("uploadRootPath=" + certificateuploadRootPath);

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
		System.out.println("--------------Step 5 end----------------------");

		System.out.println("--------------Step 6 end----------------------");
		System.out.println(employeemaster);

		EmployeeMaster employeemasternew = new EmployeeMaster();
		employeemasternew = employeeMasterService.save(employeemaster);
		System.out.println(employeemasternew);

		Set<EmployeeEducation> edulsnew = new LinkedHashSet<EmployeeEducation>();
		Set<EmployeeEmgContact> emglsnew = new LinkedHashSet<EmployeeEmgContact>();
		Set<EmployeeExperience> exptrlsnew = new LinkedHashSet<EmployeeExperience>();
		Set<EmployeeLanguage> langlsnew = new LinkedHashSet<EmployeeLanguage>();
		Set<EmployeeFiles> filelsnew = new LinkedHashSet<EmployeeFiles>();

		System.out.println(employeemasternew.getEmployeeEducation().size());
		if (employeemasternew.getEmployeeEducation().size() > 0) {
			edulsnew.addAll(employeemasternew.getEmployeeEducation());
		} else {
			EmployeeEducation empedu = new EmployeeEducation();
			empedu.setDegree("-");
			edulsnew.add(empedu);

		}

		System.out.println(employeemasternew.getEmployeeEmgContact().size());
		if (employeemasternew.getEmployeeEmgContact().size() > 0) {
			emglsnew.addAll(employeemasternew.getEmployeeEmgContact());
		} else {
			EmployeeEmgContact empcont = new EmployeeEmgContact();
			empcont.setEmg_Relation("-");
			emglsnew.add(empcont);

		}
		System.out.println(employeemasternew.getEmployeeExperience().size());
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

		System.out.println(edulsnew);
		System.out.println(emglsnew);
		System.out.println(exptrlsnew);
		System.out.println(langlsnew);
		System.out.println(filelsnew);

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
		System.out.println(employeemasternew);

		Set<EmployeeEducation> edulsnew = new LinkedHashSet<EmployeeEducation>();
		Set<EmployeeEmgContact> emglsnew = new LinkedHashSet<EmployeeEmgContact>();
		Set<EmployeeExperience> exptrlsnew = new LinkedHashSet<EmployeeExperience>();
		Set<EmployeeLanguage> langlsnew = new LinkedHashSet<EmployeeLanguage>();
		Set<EmployeeFiles> filelsnew = new LinkedHashSet<EmployeeFiles>();

		System.out.println(employeemasternew.getEmployeeEducation().size());
		if (employeemasternew.getEmployeeEducation().size() > 0) {
			edulsnew.addAll(employeemasternew.getEmployeeEducation());
		} else {
			EmployeeEducation empedu = new EmployeeEducation();
			empedu.setDegree("-");
			edulsnew.add(empedu);

		}

		System.out.println(employeemasternew.getEmployeeEmgContact().size());
		if (employeemasternew.getEmployeeEmgContact().size() > 0) {
			emglsnew.addAll(employeemasternew.getEmployeeEmgContact());
		} else {
			EmployeeEmgContact empcont = new EmployeeEmgContact();
			empcont.setEmg_Relation("-");
			emglsnew.add(empcont);

		}
		System.out.println(employeemasternew.getEmployeeExperience().size());
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

		System.out.println(edulsnew);
		System.out.println(emglsnew);
		System.out.println(exptrlsnew);
		System.out.println(langlsnew);
		System.out.println(filelsnew);

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
		
		List<EmployeeJobempstatus> obj=new ArrayList<>();
		obj=employeeJobempstatusService.findByEmployeeid(empid);
		
		List<EmployeeJobinfo> infoobj=new ArrayList<>();
		infoobj=employeeJobinfoService.findByEmployeeid(empid);
		
		List<EmployeeJobcompensation> comoobj=new ArrayList<>();
		comoobj=employeeJobcompensationService.findByEmployeeid(empid);
		
		
		List<EmployeeJobHire> hireobj=new ArrayList<>();
		hireobj=employeeJobHireService.findByEmployeeid(empid);
		String stringhiredate="";
		if(hireobj.size()>0)
		{
			stringhiredate=hireobj.get(0).getEmployeehiredate();
		}
		System.out.println("stringhiredate" + stringhiredate);
		theModel.addAttribute("employeeJobemphiredate", stringhiredate);
		theModel.addAttribute("employeeJobempstatus", obj);
		theModel.addAttribute("employeeJobinfomation", infoobj);
		theModel.addAttribute("employeecompensation", comoobj);
		theModel.addAttribute("empid", empid);
		return "empjob";
	}
	
	@PostMapping("employeehiredate")
	@ResponseBody
	public String employeehiredate(@RequestParam Map<String, String> params)
	{
		EmployeeJobHire obj=new EmployeeJobHire();
		
		obj.setEmployeehiredate(params.get("hiredate"));
		obj.setEmployeeid(Integer.valueOf(params.get("empid")));
		
		if(params.get("hiredateid") != null && params.get("hiredateid") != "" )
		{
			obj.setEmployeehireid(Integer.valueOf(params.get("hiredateid")));
		}
		System.out.println(obj);
		employeeJobHireService.save(obj);
		return "Success" + obj.getEmployeehireid();
	}


	@PostMapping("employeeemploymentupdate")
	@ResponseBody
	public String employeeemploymentupdate(@RequestParam Map<String, String> params)
	{
		EmployeeJobempstatus obj=new EmployeeJobempstatus();
		
		obj.setEmployeeid(Integer.parseInt(params.get("empid")));
		
		obj.setEmpstatus_effectivedate(params.get("empstatus_effectivedate"));
		obj.setEmpstatus_employmentstatus(params.get("empstatus_employmentstatus"));
		obj.setEmpstatus_rehire(params.get("empstatus_rehire"));
		obj.setEmpstatus_remarks(params.get("empstatus_remarks"));
		obj.setEmpstatus_terminationreason(params.get("empstatus_terminationreason"));
		obj.setEmpstatus_terminationtype(params.get("empstatus_terminationtype"));
				
		if(params.get("employeeJobempstatusid") != null && params.get("employeeJobempstatusid") != "" )
		{
			obj.setEmployeejobempstatusid(Integer.parseInt(params.get("employeeJobempstatusid")));
		}
		
		employeeJobempstatusService.save(obj);
		return "Success" + obj.getEmployeejobempstatusid();
	}
	
	@PostMapping("employeejobinformationupdate")
	@ResponseBody
	public String employeejobinformationupdate(@RequestParam Map<String, String> params)
	{
		EmployeeJobinfo obj=new EmployeeJobinfo();
		
		obj.setEmployeeid(Integer.parseInt(params.get("empid")));
		obj.setJobdeparment(params.get("jobdeparment"));
		obj.setJobeffectivedate(params.get("jobeffectivedate"));
		obj.setJoblocation(params.get("joblocation"));
		obj.setJobreportsto(params.get("jobreportsto"));
		obj.setJobtitle(params.get("jobtitle"));
		
		if(params.get("employeejobinfoid") != null && params.get("employeejobinfoid") != "" )
		{
			obj.setEmployeejobinfoid(Integer.parseInt(params.get("employeejobinfoid")));
		}
		System.out.println(obj);
		employeeJobinfoService.save(obj);
		return "Success" + obj.getEmployeejobinfoid();
	}

	@PostMapping("employeecompensationupdate")
	@ResponseBody
	public String employeecompensationupdate(@RequestParam Map<String, String> params)
	{
		EmployeeJobcompensation obj=new EmployeeJobcompensation();
		
		obj.setEmployeeid(Integer.parseInt(params.get("empid")));
		obj.setComchangereason(params.get("comchangereason"));
		obj.setComcomments(params.get("comcomments"));
		obj.setComeffectivedate(params.get("comeffectivedate"));
		obj.setCompayrate(params.get("compayrate"));
		obj.setCompayratetype(params.get("compayratetype"));
		obj.setComPayschedule(params.get("comPayschedule"));
		obj.setCompaytype(params.get("compaytype"));
		
		if(params.get("employeejobcompensationid") != null && params.get("employeejobcompensationid") != "" )
		{
			obj.setEmployeejobcompensationid(Integer.parseInt(params.get("employeejobcompensationid")));
		}
		System.out.println(obj);
		employeeJobcompensationService.save(obj);
		return "Success" + obj.getEmployeejobcompensationid();
	}

	@PostMapping("employeedelete")
	@ResponseBody
	public String employeedelete(@RequestParam Map<String, String> params)
	{
		
		if(params.get("deletetype").equalsIgnoreCase("employmentstatus"))
		{
			employeeJobempstatusService.deleteById(Integer.parseInt(params.get("deleteid")));
		}else if(params.get("deletetype").equalsIgnoreCase("jobinformation"))
		{
			employeeJobinfoService.deleteById(Integer.parseInt(params.get("deleteid")));
		}else if(params.get("deletetype").equalsIgnoreCase("compensation"))
		{
			employeeJobcompensationService.deleteById(Integer.parseInt(params.get("deleteid")));
		}
		
		
		return "Success" ;
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
