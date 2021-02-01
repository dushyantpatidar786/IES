package com.co.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CO_TRIGGERS")
@Data
public class CoTriggers {

	@Id
	@GeneratedValue
	@Column(name = "TRG_ID")
	private Integer trgId;
	@Column(name = "CASE_NUM")
	private Integer caseNum;
	@Column(name = "CREATE_DT")
	private Date createDt;
	@Column(name = "TRG_STATUS")
	private String trgStatus;
	@Column(name = "UPDATE_DT")
	private Date updateDt;
	
}
