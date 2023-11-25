package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ProjectMasterRepository;
import com.rvs.springboot.thymeleaf.entity.ProjectMaster;
import com.rvs.springboot.thymeleaf.pojo.emppojoPrivillage;

@Service
@Transactional
public class ProjectMasterImp implements ProjectMasterService {

	@Autowired
	ProjectMasterRepository projectMasterRepo;
	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public ProjectMaster save(ProjectMaster obj) {
		return projectMasterRepo.save(obj);
	}

	@Override
	public ProjectMaster findById(Integer id) {
		Optional<ProjectMaster> obj = projectMasterRepo.findById(id);

		ProjectMaster bm = null;

		if (obj.isPresent()) {
			// ----- Object Validation------------------
			Optional<ProjectMaster> privillageObject = privillageValidation(obj);
			if (privillageObject.isPresent()) {
				bm = privillageObject.get();
			}

		} else {
			throw new RuntimeException("Did find any records of projectMaster id " + id);
		}
		return bm;
	}

	@Override
	public List<ProjectMaster> findAll() {

		List<ProjectMaster> ls = new ArrayList<>();
		for (ProjectMaster as : projectMasterRepo.findAll()) {
			// ----- Object Validation------------------
			Optional<ProjectMaster> privillageObject = privillageValidation(Optional.of(as));
			if (privillageObject.isPresent()) {
				ls.add(privillageObject.get());
			}
		}

		return ls;
	}

	@Override
	public List<ProjectMaster> saveall(List<ProjectMaster> objList) {
		return projectMasterRepo.saveAll(objList);
	}

	@Override
	public void updatepipeline(String str, String pipeline, String notes) {

		String sql = "";
		if (notes.equalsIgnoreCase("")) {
			sql = "update projectmaster set pipeline ='" + pipeline + "'  where id in (" + str + ")";
		} else {
			sql = "update projectmaster set pipeline ='" + pipeline + "', lossbacktoleadreason= '" + notes.trim()
					+ "'  where id in (" + str + ")";
		}

		JdbcTemplate.execute(sql);

	}

	@Override
	public List<String> getStateAll() {

		String sql = "select distinct(State) from statedistrict";
		return JdbcTemplate.queryForList(sql, String.class);
	}

	@Override
	public List<String> getDistrictAll(String state) {
		String sql = "select distinct(District) from statedistrict where State='" + state + "'";

		return JdbcTemplate.queryForList(sql, String.class);
	}

	@Override
	public int addnewtask(int projectdetailid, String taskname) {
		String sql = "INSERT INTO projecttaskmaster(projecttasktitle, projectdetailid) VALUES ('" + taskname + "','"
				+ projectdetailid + "')";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		JdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();

	}

	@Override
	public int insertFiles(String DocumentType, String DocumentNo, String FilePath, int projectid, String createddate) {
		final String sql = "INSERT INTO `projectfiles`(`document_no`, `document_type`, `file_path`, `id`, createddate) VALUES ('"
				+ DocumentNo + "','" + DocumentType + "','" + FilePath + "'," + projectid + ",'" + createddate + "')";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		JdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteFiles(int id) {
		String sql = "DELETE FROM `projectfiles` WHERE  fileid=" + id;
		return JdbcTemplate.update(sql);
	}

	@Override
	public int insertContact(int contactpersonid, int projectid) {
		final String sql = "INSERT INTO project_contact(`contact_person`, `id`) VALUES (" + contactpersonid + ","
				+ projectid + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		JdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteContact(int contactpersonid, int projectid) {
		String sql = "DELETE FROM `project_contact` WHERE  contact_person=" + contactpersonid + " and id=" + projectid;
		return JdbcTemplate.update(sql);
	}

	private Optional<ProjectMaster> privillageValidation(Optional<ProjectMaster> obj) {

		if (emppojoPrivillage.allowBranches.contains(obj.get().getBranch())) {
			return obj;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getdatainvoicereceipt_graph() {
		String sql="SELECT sum(amount) as ramt,MONTH(STR_TO_DATE(recepit_date, '%Y-%m-%d')) as rmonth , Year(STR_TO_DATE(recepit_date, '%Y-%m-%d')) as ryear FROM invoice_receipt_master group by Month(STR_TO_DATE(recepit_date, '%Y-%m-%d')) , Year(STR_TO_DATE(recepit_date, '%Y-%m-%d')) order by  Year(STR_TO_DATE(recepit_date, '%Y-%m-%d')) desc,Month(STR_TO_DATE(recepit_date, '%Y-%m-%d')) desc;";
		
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
		
		return atm;
	}

	@Override
	public List<Map<String, Object>> getdataexpense_graph() {
		String sql="SELECT sum(amount) as eamt,MONTH(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) as emonth , Year(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) as eyear FROM project_expense group by Month(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) , Year(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) order by Year(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) desc,  Month(STR_TO_DATE(prj_expense_date, '%Y-%m-%d')) desc;";
		
		List<Map<String, Object>> atm = JdbcTemplate.queryForList(sql);
		
		return atm;
	}
}
