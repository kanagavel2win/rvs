package com.rvs.springboot.thymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class Reports {
	@Autowired
	private JdbcTemplate Jdbc;
	
	public List<String> getData() {
	    String Query = "SELECT count(*) FROM accountsheads"; 
	    List<String> result = Jdbc.queryForList(Query, String.class);
	    return result;
	}
	
	public List<Map<String,Object>> getallData(){
		String Query = "SELECT a.projectid,title,IF(state='' or state is null,'no','yes')state,status,a.id,if(b.projectid is null,'no','yes')pphase,if(c.id is null,'no','yes')files,if(d.projectid is null,'no','yes')invoice,if(e.projectid is null,'no','yes')invoice_receipt,if(f.projectid is null,'no','yes')purchase,if(g.projectid is null,'no','yes')purchase_payment,if(h.project_id is null,'no','yes')expense,if(i.project_id is null,'no','yes')works FROM `projectmaster` a left join (SELECT projectid FROM `projectphase` group by projectid)b on a.id=b.projectid left join (SELECT id FROM `projectfiles` group by id) c on a.id=c.id left join (SELECT projectid FROM `invoice_master` group by projectid)d on a.id=d.projectid left join (SELECT projectid FROM `invoice_receipt_master` group by projectid)e on a.id=e.projectid left join (SELECT projectid FROM `projectpurchase_master` group by projectid)f on a.id=f.projectid left join (SELECT projectid FROM `projectpurchase_payment_master` group by projectid)g on a.id=g.projectid left join (SELECT project_id FROM `project_expense` group by project_id)h on a.id=h.project_id  left join (SELECT project_id FROM `project_itemmaster` group by project_id)i on a.id=i.project_id";
	    List<Map<String, Object>> result = Jdbc.queryForList(Query);
	    return result;
		
	}


	 
}
