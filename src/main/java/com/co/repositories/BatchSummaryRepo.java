package com.co.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.BatchSummary;

public interface BatchSummaryRepo extends JpaRepository<BatchSummary,Serializable> {

}
