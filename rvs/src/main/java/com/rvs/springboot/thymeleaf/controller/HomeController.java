package com.rvs.springboot.thymeleaf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvs.springboot.thymeleaf.entity.BranchMaster;

@Controller

public class HomeController {

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

	/*@GetMapping("login")
	public String login(Model model) {

		return "login";
	}*/

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

	@GetMapping("branchlist")
	public String branchlist(Model theModel) {
		return "branchlist";

	}

	@GetMapping("emplist")
	public String employeelist(Model theModel) {
		return "emplist";
	}

	@GetMapping("empnew")
	public String employeeadd(Model themodel) {
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
		Date temppredate=new Date();
		Date tempnxtdate=new Date();
		String tempcurrentdate= formatterdate.format(new Date()).toString();
		
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
	/*	SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdd = new SimpleDateFormat("dd");
		SimpleDateFormat formattermonname = new SimpleDateFormat("MMMM");
		Date date = new Date();
		Date temppredate=new Date();
		Date tempnxtdate=new Date();
		String tempcurrentdate= formatterdate.format(new Date()).toString();
		
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
			String sunday= "";
			if ((firstdayofmonth + i - 2) % 7 == 0) {
				calhtml = calhtml + "</tr><tr>";
				sunday=" sunday";
			}

			
			if (currentdd == i) {
				calhtml = calhtml + "<td class='td selectdate "+ sunday + "' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear)
						+ "'  ondrop='drop(event)' ondragover='allowDrop(event)'";
				calhtml = calhtml + "<div class='cal_inner_holder_right_today'>" + i + "</div>";
			} else {
				calhtml = calhtml + "<td class='td  "+ sunday + "' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear) 
						+ "' ondrop='drop(event)' ondragover='allowDrop(event)'";
				calhtml = calhtml + "<div class='cal_inner_holder_right'>" + i + "</div>";
			}
			
			calhtml = calhtml + "<div class='cal_inner_holder' id='" + i + "_div1' name='"  + (i) + "/" + (currentmonth) + "/" + (currentyear)+ "'>" + "<div></td>";

		}
		calhtml = calhtml + "</tr>";

		theModel.addAttribute("preDate", preDate);
		theModel.addAttribute("nxtDate", nxtDate);
		theModel.addAttribute("tempcurrentdate", tempcurrentdate);
		theModel.addAttribute("currentdd", currentdd);
		theModel.addAttribute("currentyear", currentyear);
		theModel.addAttribute("currentmonname", currentmonname);
		theModel.addAttribute("calhtml", calhtml);*/
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
		Date temppredate=new Date();
		Date tempnxtdate=new Date();
		String tempcurrentdate= formatterdate.format(new Date()).toString();
		
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
			String sunday= "";
			if ((firstdayofmonth + i - 2) % 7 == 0) {
				calhtml = calhtml + "</tr><tr>";
				sunday=" sunday";
			}

			
			if (currentdd == i) {
				calhtml = calhtml + "<td class='td selectdate "+ sunday + "' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear)
						+ "'>";
				calhtml = calhtml + "<div class='cal_inner_holder_right_today'>" + i + "</div>";
			} else {
				calhtml = calhtml + "<td class='td  "+ sunday + "' name='" + (i) + "/" + (currentmonth) + "/" + (currentyear) + "'>";
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
	public String calendarMaster(Model themodel)
	{
		return "calendarMaster";
	}
}
