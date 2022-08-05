package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.ItemListRepository;
import com.rvs.springboot.thymeleaf.entity.ItemList;

@Service
public class ItemListImp implements ItemListService {

	@Autowired
	ItemListRepository ItemListRepo;
	
	@Autowired
	public JdbcTemplate jdbctemplate;
	
	
	@Override
	public ItemList save(ItemList obj) {
		return ItemListRepo.save(obj);
	}

	@Override
	public List<String> findByFieldName(String FieldName) {
		return ItemListRepo.findByFieldName(FieldName);
	}

	@Override
	public void savesingletxt(String txt, String fieldname) {
		String sql="select count(*) from item_list where field_name='"+ fieldname +"' and itemsvalue='"+ txt +"'";
		
		int rowcount= jdbctemplate.queryForObject(sql, Integer.class);
		
		if(rowcount==0)
		{
			ItemList obj=new ItemList();
			obj.setFieldName(fieldname);
			obj.setItemsvalue(txt);
			ItemListRepo.save(obj);
		
		}
	}
		
	
}
