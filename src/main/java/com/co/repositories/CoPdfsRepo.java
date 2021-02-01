package com.co.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.CoPdfs;

public interface CoPdfsRepo extends JpaRepository<CoPdfs, Serializable> {

}
