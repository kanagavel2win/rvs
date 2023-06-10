package com.rvs.springboot.thymeleaf.service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		Optional<AssetMaster> obj = assetRepo.findById(id);

		AssetMaster bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of Asset id " + id);
		}
		return bm;
	}

	@Override
	public List<AssetMaster> findAll() {

		return assetRepo.findAll();
	}

	@Override
	public void updatetheAssetStatus(String status, int rowid, String updatetime, String StaffID) {

		String sql = "UPDATE `assetmaster` SET status='" + status + "' , lastupdate='" + updatetime + "',StaffID='"
				+ StaffID + "' WHERE asset_id=" + rowid;

		jdbctemplate.execute(sql);
	}

	@Override
	public void updatetheAssetStatus(String status, int rowid, String updatetime, String StaffID, String acondition) {

		String sql = "UPDATE `assetmaster` SET status='" + status + "' , lastupdate='" + updatetime + "',StaffID='"
				+ StaffID + "',acondition='" + acondition + "' WHERE asset_id=" + rowid;

		jdbctemplate.execute(sql);
	}

	@Override
	public int getmaxid() {

		return jdbctemplate.queryForObject("select IFNULL(max(asset_id),1) maxid from assetmaster", Integer.class);

	}

	@Override
	public List<AssetMaster> findbyStaffID(String StaffID) {
		return assetRepo.findByStaffID(StaffID);
	}

	@Override
	public int insertassetFiles(String FilePath, int assetid) {
		final String sql = "INSERT INTO `assetmasterfiles`(`files_attach`, `asset_id`) VALUES ('" + FilePath + "'," + assetid + ")";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbctemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			return ps;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int deleteassetFiles(int id) {
		String sql ="DELETE FROM `assetmasterfiles` where asset_fileid=" +id ;
		return jdbctemplate.update(sql);
	}

	@Override
	public List<AssetMaster> findByManyassetIds(List<Integer> assetidlist) {
	
		return assetRepo.findByManyassetIds(assetidlist);
	}

}
