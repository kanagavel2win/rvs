package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.EmployeeMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AssetMaster;
import com.rvs.springboot.thymeleaf.entity.EmployeeEducation;
import com.rvs.springboot.thymeleaf.entity.EmployeeEmgContact;
import com.rvs.springboot.thymeleaf.entity.EmployeeExperience;
import com.rvs.springboot.thymeleaf.entity.EmployeeJobinfo;
import com.rvs.springboot.thymeleaf.entity.EmployeeLanguage;
import com.rvs.springboot.thymeleaf.entity.EmployeeMaster;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

@Service
public class EmployeeMasterImp implements EmployeeMasterService {

	@Autowired
	EmployeeMasterRepository employeeRepo;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	EmployeeJobinfoService employeeJobinfoService;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();

	@Override
	public EmployeeMaster save(EmployeeMaster obj) {
		return employeeRepo.save(obj);
	}

	@Override
	public EmployeeMaster findById(Integer id) {
		Optional<EmployeeMaster> obj = employeeRepo.findById(id);

		EmployeeMaster bm = null;

		if (obj.isPresent()) {
			// ----- Object Validation------------------
			Optional<EmployeeMaster> privillageObject = Optional.ofNullable(privillageValidation(obj.get()));
			if (privillageObject.isPresent()) {
				bm = privillageObject.get();
			}
		} else {
			throw new RuntimeException("Did find any records of Branch id " + id);
		}
		return bm;
	}

	@Override
	public List<EmployeeMaster> findAll() {

		List<EmployeeMaster> ls = new ArrayList<>();
		for (EmployeeMaster as : employeeRepo.findAll()) {
			// ----- Object Validation------------------
			Optional<EmployeeMaster> privillageObject = Optional.ofNullable(privillageValidation(as));
			if (privillageObject.isPresent()) {
				ls.add(privillageObject.get());
			}
		}
		return ls;
	}

	@Override
	public int insertemployeeContact(String dep, String phonenumber, String email, int employeeid, boolean primary) {

		if (privillageValidation_id(employeeid)) {
			final String sql = "INSERT INTO `employee_contact`(`department`, `email`, `phonenumber`, `emp_masterid`,primarycontact) VALUES('"
					+ dep + "','" + email + "','" + phonenumber + "'," + employeeid + "," + primary + ")";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int updateemployeeContact(int id, String dep, String phonenumber, String email, boolean primary) {

		String sql = "UPDATE `employee_contact` SET `department`='" + dep + "',`email`='" + email + "',`phonenumber`='"
				+ phonenumber + "',primarycontact=" + primary + "  WHERE employeecontactid=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteemployeeContact(int id) {
		String sql = "DELETE FROM `employee_contact` WHERE  employeecontactid=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeAccountdetails(int acid, String acno, String acname, String bankname, String branchname,
			String ifsccode, int employeeid) {
		if (privillageValidation_id(employeeid)) {
			String sql = "UPDATE `employee_acc_no` SET `acname`='" + acname + "',`acno`='" + acno + "',`bankname`='"
					+ bankname + "',`branchname`='" + branchname + "',`ifsccode`='" + ifsccode + "',`emp_masterid`='"
					+ employeeid + "' WHERE accnoid=" + acid;
			return jdbcTemplate.update(sql);
		} else {
			return 0;
		}
	}

	@Override
	public int insertemployeeFiles(String DocumentType, String DocumentNo, String FilePath, int employeeid) {
		if (privillageValidation_id(employeeid)) {
			final String sql = "INSERT INTO `employeefiles`(`document_no`, `document_type`, `file_path`, `emp_masterid`) VALUES ('"
					+ DocumentNo + "','" + DocumentType + "','" + FilePath + "'," + employeeid + ")";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int deleteemployeeFiles(int id) {
		String sql = "DELETE FROM `employeefiles` WHERE  emp_fileid=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeEmgContact(int id, EmployeeEmgContact emg) {
		if (privillageValidation_id(id)) {

			final String sql = "INSERT INTO `employeeemgcontact`( `emg_city`, `emg_country`, `emg_insurance_nominee`, `emg_name`, `emg_personal_phone`, `emg_relation`, `emg_state`, `emg_street1`, `emg_street2`, `emg_village`, `emg_zip`, `emp_masterid`, `emg_landmark`) VALUES ('"
					+ emg.getEmg_City() + "','" + emg.getEmg_Country() + "'," + emg.getEmg_InsuranceNominee() + ",'"
					+ emg.getEmg_Name() + "','" + emg.getEmg_PersonalPhone() + "','" + emg.getEmg_Relation() + "','"
					+ emg.getEmg_State() + "','" + emg.getEmg_Street1() + "','" + emg.getEmg_Street2() + "','"
					+ emg.getEmg_Village() + "','" + emg.getEmg_ZIP() + "'," + id + ",'" + emg.getEmg_Landmark() + "')";
			// System.out.println(sql);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int updateemployeeEmgContact(EmployeeEmgContact emg) {
		String sql = "UPDATE `employeeemgcontact` SET `emg_city`='" + emg.getEmg_City() + "',`emg_country`='"
				+ emg.getEmg_Country() + "',`emg_insurance_nominee`=" + emg.getEmg_InsuranceNominee() + ",`emg_name`='"
				+ emg.getEmg_Name() + "',`emg_personal_phone`='" + emg.getEmg_PersonalPhone() + "',`emg_relation`='"
				+ emg.getEmg_Relation() + "',`emg_state`='" + emg.getEmg_State() + "',`emg_street1`='"
				+ emg.getEmg_Street1() + "',`emg_street2`='" + emg.getEmg_Street2() + "',`emg_village`='"
				+ emg.getEmg_Village() + "',`emg_zip`='" + emg.getEmg_ZIP() + "',`emg_landmark`='"
				+ emg.getEmg_Landmark() + "' WHERE `emp_emg_contactid`=" + emg.getEmpEmgContactid();
		// System.out.println(sql);
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeLanguag(int id, EmployeeLanguage langu) {
		if (privillageValidation_id(id)) {
			final String sql = "INSERT INTO `employeelanguage`(`language`, `lan_read`, `lan_speak`, `lan_write`,  `emp_masterid`) VALUES ('"
					+ langu.getLanguage() + "'," + langu.getLan_read() + "," + langu.getLan_speak() + ","
					+ langu.getLan_write() + "," + id + ")";
			// System.out.println(sql);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int updateemployeeLanguag(EmployeeLanguage langu) {
		String sql = "UPDATE `employeelanguage` SET `language`='" + langu.getLanguage() + "',`lan_read`="
				+ langu.getLan_read() + ",`lan_speak`=" + langu.getLan_speak() + ",`lan_write`=" + langu.getLan_write()
				+ " WHERE `emp_languid`=" + langu.getEmpLanguid();
		// System.out.println(sql);
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeQualification(int id, EmployeeEducation edu) {
		if (privillageValidation_id(id)) {
			final String sql = "INSERT INTO `employeeeducation`(`college_institution`, `degree`, `from_year`, `major_specialization`, `percentage_gpa`, `to_year`, `emp_masterid`) VALUES ('"
					+ edu.getCollege_Institution() + "','" + edu.getDegree() + "','" + edu.getFromYear() + "','"
					+ edu.getMajorSpecialization() + "','" + edu.getPercentage_GPA() + "','" + edu.getToYear() + "',"
					+ id + ")";
			// System.out.println(sql);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int updateemployeeQualification(EmployeeEducation edu) {
		String sql = "UPDATE `employeeeducation` SET `college_institution`='" + edu.getCollege_Institution()
				+ "',`degree`='" + edu.getDegree() + "',`from_year`='" + edu.getFromYear()
				+ "',`major_specialization`='" + edu.getMajorSpecialization() + "',`percentage_gpa`='"
				+ edu.getPercentage_GPA() + "',`to_year`='" + edu.getToYear() + "' WHERE `emp_eduid`="
				+ edu.getEmpEduid();
		// System.out.println(sql);
		return jdbcTemplate.update(sql);
	}

	@Override
	public int insertemployeeExperience(int id, EmployeeExperience exp) {
		if (privillageValidation_id(id)) {
			final String sql = "INSERT INTO `employeeexperience`( `company`, `job_title`, `location`, `exp_fromyear`, `exp_toyear`, `emp_masterid`) VALUES ('"
					+ exp.getCompany() + "','" + exp.getJobTitle() + "','" + exp.getLocation() + "','"
					+ exp.getExpFromyear() + "','" + exp.getExpToyear() + "'," + id + ")";
			// System.out.println(sql);
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				return ps;
			}, keyHolder);

			return keyHolder.getKey().intValue();
		} else {
			return 0;
		}
	}

	@Override
	public int updateemployeeExperience(EmployeeExperience exp) {
		String sql = "UPDATE `employeeexperience` SET `company`='" + exp.getCompany() + "',`job_title`='"
				+ exp.getJobTitle() + "',`location`='" + exp.getLocation() + "',`exp_fromyear`='" + exp.getExpFromyear()
				+ "',`exp_toyear`='" + exp.getExpToyear() + "' WHERE `emp_experienceid`=" + exp.getEmpExperienceid();
		// System.out.println(sql);
		return jdbcTemplate.update(sql);
	}

	private EmployeeMaster privillageValidation(EmployeeMaster obj) {

		List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
		infoobjjob = employeeJobinfoService.findByEmployeeid(obj.getEmpMasterid());
		if (infoobjjob.size() > 0) {
			List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjjobgreen.size() > 0) {
				if (emppojoPrivillage.allowBranches
						.contains(Integer.parseInt(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation()))) {
					return obj;
				}

			}
		}

		return null;
	}

	private boolean privillageValidation_id(int empid) {

		List<EmployeeJobinfo> infoobjjob = new ArrayList<>();
		infoobjjob = employeeJobinfoService.findByEmployeeid(empid);
		if (infoobjjob.size() > 0) {
			List<EmployeeJobinfo> infoobjjobgreen = infoobjjob.stream()
					.filter(c -> dateFormat.format(date).compareTo(c.getJobeffectivedate().toString()) >= 0)
					.collect(Collectors.toList());
			infoobjjobgreen.sort(Comparator.comparing(EmployeeJobinfo::getJobeffectivedate));
			if (infoobjjobgreen.size() > 0) {
				if (emppojoPrivillage.allowBranches
						.contains(Integer.parseInt(infoobjjobgreen.get(infoobjjobgreen.size() - 1).getJoblocation()))) {
					return true;
				}

			}
		}

		return false;
	}

	@Override
	public EmployeeMaster findByLoginId(Integer id) {
		Optional<EmployeeMaster> obj = employeeRepo.findById(id);

		EmployeeMaster bm = null;

		if (obj.isPresent()) {
			bm = obj.get();

		} else {
			throw new RuntimeException("Did find any records of Branch id " + id);
		}
		return bm;
	}

}
