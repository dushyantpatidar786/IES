package com.co.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.BatchRunDtls;

public interface BatchRunDtlsRepo extends JpaRepository<BatchRunDtls, Serializable> {

}
