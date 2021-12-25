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

@Controller

public class HomeController {

	

	/*@ModelAttribute
	public void addAttributes(Model themodel, HttpSession session, HttpServletRequest request) {

		String dataLoginEmailID = "";
		String dataLoginClubID = "";
		try {

			try {
				if (request.getSession().getAttribute("dataLoginEmailID").toString().equals(null)) {
					dataLoginClubID = getLoginClubID();
					request.getSession().setAttribute("dataLoginClubID", dataLoginClubID);
					dataLoginEmailID = getLoginemailID();
					request.getSession().setAttribute("dataLoginEmailID", dataLoginEmailID);
				}
			} catch (NullPointerException e) {
				dataLoginClubID = getLoginClubID();
				request.getSession().setAttribute("dataLoginClubID", dataLoginClubID);
				dataLoginEmailID = getLoginemailID();
				request.getSession().setAttribute("dataLoginEmailID", dataLoginEmailID);
			}

			dataLoginEmailID = request.getSession().getAttribute("dataLoginEmailID").toString();
			dataLoginClubID = request.getSession().getAttribute("dataLoginClubID").toString();

		} catch (Exception e) {

		} finally {
			themodel.addAttribute("dataLoginEmailID", dataLoginEmailID);
			themodel.addAttribute("dataLoginClubID", dataLoginClubID);
		}

	}
*/
	@GetMapping("/")
	public String home(Model theModel, HttpSession session, HttpServletRequest request) {
		return "index";
/*		if (logintype("ROLE_MEMBER")) {

			theModel.addAttribute("MemberID", getLoginMemberID());
			return "indexMember";
		} else if (logintype("ROLE_CLUBADMIN")) {
			return "index";
		} else {
			return "redirect:logout";
		}*/
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

/*	public String getLoginemailID() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user2 = userRepository.findByEmail(currentPrincipalName);
		return user2.getEmail();

	}

	public String getLoginMemberID() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user2 = userRepository.findByEmail(currentPrincipalName);
		return user2.getmemberID();

	}

	public String getLoginClubID() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User user2 = userRepository.findByEmail(currentPrincipalName);
		return user2.getClubID();

	}
*/
	@GetMapping("/index")
	public String index(Model theModel) {
		return "index";
		/*if (logintype("ROLE_MEMBER")) {
			theModel.addAttribute("MemberID", getLoginMemberID());
			return "indexMember";
		} else if (logintype("ROLE_CLUBADMIN")) {
			return "index";
		} else {
			return "redirect:logout";
		}*/

	}
	@GetMapping("login")
	public String login(Model model) {

		return "login";
	}

	@GetMapping("403")
	public String accessDenied(Model model) {

		return "403";
	}

	@GetMapping("addnewbranch")
	public String addnewbranch(Model theModel) {
		return "branchadd";

	}
	@GetMapping("branchlist")
	public String branchlist(Model theModel) {
		return "branchlist";

	}
	
	@GetMapping("emplist")
	public String employeelist(Model theModel)
	{
		return "emplist";
	}
	
	@GetMapping("empnew")
	public String employeeadd(Model themodel)
	{
		return "empadd";
	}
	@GetMapping("empjob")
	public String employeejob(Model theModel)
	{
		return "empjob";
	}
	
	@GetMapping("empattendance")
	public String empattendance(Model theModel, @RequestParam(value="date", defaultValue="", required=false) String attdate)
	{
		SimpleDateFormat formatterdate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatteryear = new SimpleDateFormat("yyyy");
		SimpleDateFormat formattermonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterdd = new SimpleDateFormat("dd");
		SimpleDateFormat formattermonname = new SimpleDateFormat("MMMM");
		Date date = new Date();  
		if (!attdate.equalsIgnoreCase(""))
		{
			try {
				date =   formatterdate.parse(attdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String currentDate=formatterdate.format(date).toString();
	    int currentyear=Integer.parseInt(formatteryear.format(date).toString());
	    int currentmonth=Integer.parseInt(formattermonth.format(date).toString());
	    int currentdd=Integer.parseInt(formatterdd.format(date).toString());
	    String currentmonname=formattermonname.format(date).toString();
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(currentyear,currentmonth-1,1);
	    String stringDay=calendar.getTime().toString().substring(0,3);
	    int currentmonthnumDays = calendar.getActualMaximum(Calendar.DATE);
	    int firstdayofmonth=1;
	    switch(stringDay)
	    {
	    case "Sun": 	firstdayofmonth=1; break;
	    case "Mon": 	firstdayofmonth=2; break;
	    case "Tue": 	firstdayofmonth=3; break;
	    case "Wed": 	firstdayofmonth=4; break;
	    case "Thu": 	firstdayofmonth=5; break;
	    case "Fri": 	firstdayofmonth=6; break;
	    case "Sat": 	firstdayofmonth=7; break;
	    }
	    
	    String calhtml="<tr>";
	    
	    for(int i=1;i<firstdayofmonth;i++)
	    {
	    	calhtml=calhtml+"<td></td>";
	    }
	    
	    for(int i=1;i<=currentmonthnumDays;i++)
	    {
	    	if((firstdayofmonth+i-2)%7 ==0)
	    	{
	    		calhtml=calhtml+"</tr><tr>";
	    	}
	    	
	    	if(currentdd==i)
	    	{
	    		calhtml=calhtml+"<td class='selectdate' name='"+ (i) +"/"+ (currentmonth) +"/"+ (currentyear) +"'>"+ i+ "</td>";
	    	}else
	    	{
	    	calhtml=calhtml+"<td class='td' name='"+ (i) +"/"+ (currentmonth) +"/"+ (currentyear) +"'>"+ i+ "</td>";
	    	}
	    }
	    calhtml=calhtml+"</tr>";
	    
	    theModel.addAttribute("currentyear", currentyear);
	    theModel.addAttribute("currentmonname", currentmonname);
	    theModel.addAttribute("calhtml", calhtml);
		return "empattendance";
	}
	
}
