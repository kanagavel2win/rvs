package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.AssetMasterRepository;
import com.rvs.springboot.thymeleaf.entity.AssetMaster;

@Service
public class AssetMasterImp implements AssetMasterService {

	@Autowired
	AssetMasterRepository assetRepo;
	
	@Autowired
	JdbcTemplate jdbctemplate;

	
	@Override
	public AssetMaster save(AssetMaster obj) {
		return assetRepo.save(obj);
	}

	@Override
	public AssetMaster findById(Integer id) {
		Optional<AssetMaster> obj=assetRepo.findById(id);
		
		AssetMaster bm=null;
		
		if(obj.isPresent())
		{	
			bm=obj.get();
		}else
		{
			throw new RuntimeException("Did find any records of Asset id "+ id);
		}
		return bm;
	}

	@Override
	public List<AssetMaster> findAll() {
		
		return assetRepo.findAll();
	}

	@Override
	public void updatetheAssetStatus(String status, int rowid) {

		String sql = "UPDATE `assetmaster` SET status='" + status +"' WHERE asset_id="+rowid;

		 jdbctemplate.execute(sql);
	}

	@Override
	public int getmaxid() {
		
		
		return jdbctemplate.queryForObject("select max(asset_id) maxid from assetmaster", Integer.class);
		
	}
		
	
}
