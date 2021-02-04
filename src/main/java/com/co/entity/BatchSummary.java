package com.co.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "BATCH_SUMMARY")
@Data
public class BatchSummary {
	@Id
	@GeneratedValue
	@Column(name = "SUMMARY_ID")
	private Integer summaryId;
	@Column(name = "BATCH_NAME")
	private String batchName;
	@Column(name = "FAILURE_TRIGGER_COUNT")
	private Long failureTriggerCount;
	@Column(name = "SUCCESS_TRIGGER_COUNT")
	private Long successTriggerCount;
	@Column(name = "TOTAL_TRIGGER_PROCESSED")
	private Long totalTriggerProcessed;

}
