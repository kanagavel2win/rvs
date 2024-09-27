package com.rvs.springboot.thymeleaf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvs.springboot.thymeleaf.service.AssetMasterService;
import com.rvs.springboot.thymeleaf.service.Reports;
import com.rvs.springboot.thymeleaf.pojo.menuactivelist;
import com.rvs.springboot.thymeleaf.dao.AssetMasterRepository;
import com.rvs.springboot.thymeleaf.dao.CheckInMasterRepository;
import com.rvs.springboot.thymeleaf.dao.EmployeeMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AssetMaster;
import com.rvs.springboot.thymeleaf.entity.CheckIn;
import com.rvs.springboot.thymeleaf.entity.CheckInMaster;


@Controller
public class ReportController {
	@Autowired
	Reports report;
	@Autowired
	menuactivelist menuactivelistobj;
	@Autowired
	CheckInMasterRepository checkinMasterRepository;
	@Autowired
	AssetMasterService assetMasterService;
	@Autowired
	AssetMasterRepository assetRepo;
    @Autowired
    EmployeeMasterRepository employee;
	


	
	@GetMapping("reportprojectplan")
    public String reportprojectplan(Model themodel,HttpServletRequest request,HttpSession session) {
		 themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist(""));	 
		 List<Map<String,Object>> data = report.getallData();
		 themodel.addAttribute("data", data);
		 return "projecrtplan_report";
    }


	@GetMapping("reprintcheckin")
    public String reprintcheckin(Model themodel, HttpServletRequest request, HttpSession session, @RequestParam(name = "id") int id) {
    Optional<CheckInMaster> checkinMaster = checkinMasterRepository.findById(id);
    List<Map<String, String>> checkIn_data = new ArrayList<>();
    if(!checkinMaster.isPresent()) {
        themodel.addAttribute("custodian","");
        themodel.addAttribute("checkIn_data", checkIn_data);
        return "reprintcheckin";
    }

    List<CheckIn> checkIn = checkinMaster.get().getCheckIn();
    int staffid = Integer.parseInt(checkinMaster.get().getStaffID());
    String name = employee.findById(staffid).get().getStaffName();
    
    for (CheckIn obj : checkIn) {
        int assetid = Integer.parseInt(obj.getAssetId());
        AssetMaster asset = assetRepo.findById(assetid).get();
            checkIn_data.add(new HashMap<String, String>() {
                {
                    put("brand", asset.getBrand());
                    put("model", asset.getModel());
                    put("asset", asset.getAssetName());
                    put("serialNo", asset.getSerialNumber());
                    put("condition", obj.getACondition());
                    put("comments", obj.getComments());
                }
            });
        }

    themodel.addAttribute("custodian", name);
    themodel.addAttribute("checkIn",checkIn);
    themodel.addAttribute("checkIn_data", checkIn_data);
    return "reprintcheckin";
}

@GetMapping("checkinregister")
public String checkinregister(Model themodel, HttpServletRequest request, HttpSession session) {
    List<CheckInMaster> checkinMaster = checkinMasterRepository.findAll();
    List<Map<String, Object>> data = new ArrayList<>();
    for (CheckInMaster obj : checkinMaster) {
        int staffid = Integer.parseInt(obj.getStaffID());
        String name = employee.findById(staffid).get().getStaffName();
        data.add(new HashMap<String, Object>() {
            {
                put("date", obj.getCheckInDate());
                put("time", obj.getCheckInDateTime());
                put("custodian", name);
                put("asset_count",obj.getCheckIn().size());
                put("id",obj.getCheckInMasterID());
            }
        });
    }
    themodel.addAttribute("data", data);
    themodel.addAttribute("menuactivelist", menuactivelistobj.getactivemenulist(""));
    return "checkinregister";
}

}
