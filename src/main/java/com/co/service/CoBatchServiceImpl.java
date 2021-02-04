package com.co.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.entity.BatchRunDtls;
import com.co.entity.BatchSummary;
import com.co.entity.CoPdfs;
import com.co.entity.CoTriggers;
import com.co.repositories.BatchRunDtlsRepo;
import com.co.repositories.BatchSummaryRepo;
import com.co.repositories.CoPdfsRepo;
import com.co.repositories.CoTriggersRepo;

@Service
public class CoBatchServiceImpl implements CoBatchService {
	
	@Autowired
	private BatchRunDtlsRepo batchRunDtlsRepo;
	
	@Autowired
	private CoTriggersRepo coTriggersRepo;
	
	@Autowired
	private BatchSummaryRepo batchSummaryRepo;
	
	@Autowired
	private CoPdfsRepo coPdfsRepo;
	
	@Override
	public BatchRunDtls insertBatchRunDetails(BatchRunDtls batchRunDtls) {
		return batchRunDtlsRepo.save(batchRunDtls);
	}

	@Override
	public List<CoTriggers> findPendingTriggers() {
		return coTriggersRepo.findByTrgStatus("P");
	}

	@Override
	public void updatePendingTrigger(CoTriggers trigger) {
		trigger.setTrgStatus("Completed");
		coTriggersRepo.save(trigger);
	}

	@Override
	public BatchRunDtls findByRunSeqNum(Integer runSeqNo) {
		return batchRunDtlsRepo.findByBatchRunSeq(runSeqNo);
	}

	@Override
	public void updateBatchRunDetails(BatchRunDtls batchRunDtls) {
		batchRunDtlsRepo.save(batchRunDtls);
	}

	@Override
	public void saveBatchSummary(BatchSummary batchSummary) {
		batchSummaryRepo.save(batchSummary);
	}

	@Override
	public void savePdf(CoPdfs coPdfs) {
		coPdfsRepo.save(coPdfs);
	}

}
