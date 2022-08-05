package com.rvs.springboot.thymeleaf.service;

import java.util.List;

import com.rvs.springboot.thymeleaf.entity.ItemList;

public interface ItemListService {
	
	public ItemList save(ItemList obj);
	public void savesingletxt(String txt,String fieldname);
	public List<String> findByFieldName(String FieldName);
	
	
}
