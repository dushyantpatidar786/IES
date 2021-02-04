package com.co.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.CoTriggers;

public interface CoTriggersRepo extends JpaRepository<CoTriggers, Serializable> {
	List<CoTriggers> findByTrgStatus(String trgStatus);
}
