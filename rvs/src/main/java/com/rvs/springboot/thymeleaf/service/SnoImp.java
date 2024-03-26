package com.rvs.springboot.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvs.springboot.thymeleaf.dao.SnoRepository;
import com.rvs.springboot.thymeleaf.entity.SnoMaster;

@Service
@Transactional
public class SnoImp implements SnoService {

	@Autowired
	SnoRepository snoRepo;
	
	@Override
	public SnoMaster save(SnoMaster obj) {
		return snoRepo.save(obj);
	}

	@Override
	public SnoMaster findById(Integer id) {
		Optional<SnoMaster> obj = snoRepo.findById(id);

		SnoMaster bm = null;

		if (obj.isPresent()) {
			bm = obj.get();
		} else {
			throw new RuntimeException("Did find any records of  id " + id);
		}
		return bm;
	}

	@Override
	public List<SnoMaster> findAll() {

		return snoRepo.findAll();
	}

	@Override
	public List<SnoMaster> findByCatogeryAndFinyear(String str,String finYear) {

		return snoRepo.findByCatogeryAndFinyear(str,finYear);
	}

}