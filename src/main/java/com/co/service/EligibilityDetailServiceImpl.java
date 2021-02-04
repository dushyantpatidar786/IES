package com.co.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.entity.EligibilityDetails;
import com.co.repositories.EligibilityDetailsRepo;

@Service
public class EligibilityDetailServiceImpl implements EligibilityDetailService {
	
	@Autowired
	private EligibilityDetailsRepo eligibilityDetailsRepo;
	@Override
	public EligibilityDetails findByCaseNum(Integer caseNum) {
		return eligibilityDetailsRepo.findByCaseNum(caseNum);
	}

}
