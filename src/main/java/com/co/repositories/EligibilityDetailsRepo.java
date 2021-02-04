package com.co.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Serializable> {
	public EligibilityDetails findByCaseNum(Integer caseNum);
}
