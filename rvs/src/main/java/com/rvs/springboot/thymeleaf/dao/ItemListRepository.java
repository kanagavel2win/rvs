package com.rvs.springboot.thymeleaf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rvs.springboot.thymeleaf.entity.ItemList;

public interface ItemListRepository extends JpaRepository<ItemList,Integer>{
	
	@Query("select ls.itemsvalue from ItemList ls where ls.fieldName=:FieldName ")
	public List<String> findByFieldName(@Param("FieldName") String FieldName);

}
