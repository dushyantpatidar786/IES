package com.co.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.co.entity.CoTriggers;

public interface CoTriggersRepo extends JpaRepository<CoTriggers, Serializable> {

}
