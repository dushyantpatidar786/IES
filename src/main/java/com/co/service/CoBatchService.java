package com.co.service;

import java.util.List;

import com.co.entity.BatchRunDtls;
import com.co.entity.BatchSummary;
import com.co.entity.CoPdfs;
import com.co.entity.CoTriggers;

public interface CoBatchService {
	public BatchRunDtls insertBatchRunDetails(BatchRunDtls batchRunDtls);

	public List<CoTriggers> findPendingTriggers();

	public void updatePendingTrigger(CoTriggers trigger);

	public BatchRunDtls findByRunSeqNum(Integer runSeqNo);

	public void updateBatchRunDetails(BatchRunDtls batchRunDtls);

	public void saveBatchSummary(BatchSummary batchSummary);

	public void savePdf(CoPdfs coPdfs);
}
