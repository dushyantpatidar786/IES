package com.co.batch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.co.entity.BatchRunDtls;
import com.co.entity.BatchSummary;
import com.co.entity.CoPdfs;
import com.co.entity.CoTriggers;
import com.co.entity.EligibilityDetails;
import com.co.service.CoBatchService;
import com.co.service.EligibilityDetailService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class CoGenDlyBatch {

	private static final String BATCH_NAME = "CO-GEN-DL";

	@Autowired
	private CoBatchService coBatchService;

	@Autowired
	private EligibilityDetailService edService;

	private static Long SUCC_TRGS_CNT = 0l;
	private static Long FAILED_TRGS_CNT = 0l;

	public void test() {
		Integer runSeqNo = this.preProcess();
		this.start();
		this.postProcess(runSeqNo);
	}

	public Integer preProcess() {
		BatchRunDtls batchRunDtls = new BatchRunDtls();
		batchRunDtls.setBatchName(BATCH_NAME);
		batchRunDtls.setBatchRunStatus("ST");
		batchRunDtls.setStartDate(new Date());
		batchRunDtls = coBatchService.insertBatchRunDetails(batchRunDtls);
		return batchRunDtls.getBatchRunSeq();
	}

	public void start() {
		// Read Pending Triggers from CO_TRIGGERS table
		List<CoTriggers> coPendTrgs = coBatchService.findPendingTriggers();
		for (CoTriggers coTriggers : coPendTrgs) {
			process(coTriggers);
		}
	}

	public void process(CoTriggers trigger) {
		try {
			// Based on Trigger case number read elig data
			EligibilityDetails eligibilityDetails = edService.findByCaseNum(trigger.getCaseNum());
			// Based on Eligibility Data generate pdf
			String planStatus = eligibilityDetails.getPlanStatus();
			if ("AP".equals(planStatus)) {
				buildPlanApPdf(eligibilityDetails);
			} else if ("DN".equals(planStatus)) {
				buildPlanDnPdf(eligibilityDetails);
			}
			// coBatchService.savePdf(eligibilityDetails);
			storePdf(eligibilityDetails);
			// If processed successfully mark trigger as Completed
			coBatchService.updatePendingTrigger(trigger);
			SUCC_TRGS_CNT++;
		} catch (Exception e) {
			FAILED_TRGS_CNT++;
		}
	}

	public void buildPlanApPdf(EligibilityDetails eligibilityDetails) {
		try {
			Document document = new Document();
			FileOutputStream fos = new FileOutputStream(
					"D:\\CO-PDFS\\" + eligibilityDetails.getCaseNum().toString() + ".pdf");
			PdfWriter.getInstance(document, fos);
			// open document
			document.open();
			// Creating paragraph
			Paragraph p = new Paragraph();
			p.add("Approved Plan Details");
			p.setAlignment(Element.ALIGN_CENTER);
			// adding paragraph to document
			document.add(p);
			// Create Table object, Here 2 specify the no. of columns
			PdfPTable pdfPTable = new PdfPTable(2);
			// First row in table
			pdfPTable.addCell(new PdfPCell(new Paragraph("Case Number")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getCaseNum().toString())));
			// Second Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Name")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanName())));
			// Third Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanStatus())));
			// Fourth Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Start Date")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanStartDt())));
			// Fifth Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan End Date")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanEndDt())));
			// sixth Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Benfit Amount")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getBenefitAmt())));
			// Add content to the document using Table objects.
			document.add(pdfPTable);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buildPlanDnPdf(EligibilityDetails eligibilityDetails) {
		try {
			Document document = new Document();
			FileOutputStream fos = new FileOutputStream(
					"D:\\CO-PDFS\\" + eligibilityDetails.getCaseNum().toString() + ".pdf");
			PdfWriter.getInstance(document, fos);
			// open document
			document.open();
			// Creating paragraph
			Paragraph p = new Paragraph();
			p.add("Denied Plan Details");
			p.setAlignment(Element.ALIGN_CENTER);
			// adding paragraph to document
			document.add(p);
			// Create Table object, Here 2 specify the no. of columns
			PdfPTable pdfPTable = new PdfPTable(2);
			// First row in table
			pdfPTable.addCell(new PdfPCell(new Paragraph("Case Number")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getCaseNum().toString())));
			// Second row in table
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Name")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanName().toString())));
			// Third Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getPlanStatus())));
			// Fourth Row
			pdfPTable.addCell(new PdfPCell(new Paragraph("Denial Reason")));
			pdfPTable.addCell(new PdfPCell(new Paragraph(eligibilityDetails.getDenialReason())));
			// Add content to the document using Table objects.
			document.add(pdfPTable);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void postProcess(Integer runSeqNo) {
		// Update batch end_time and batch_stats in BATCH_RUN_DTLS
		BatchRunDtls batchRunDtls = coBatchService.findByRunSeqNum(runSeqNo);
		batchRunDtls.setBatchRunStatus("EN");
		batchRunDtls.setEndDate(new Date());
		coBatchService.updateBatchRunDetails(batchRunDtls);
		// Insert batch execution summary in BATCH_SUMMARY table
		BatchSummary batchSummary = new BatchSummary();
		batchSummary.setBatchName(BATCH_NAME);
		batchSummary.setFailureTriggerCount(FAILED_TRGS_CNT);
		batchSummary.setSuccessTriggerCount(SUCC_TRGS_CNT);
		batchSummary.setTotalTriggerProcessed(FAILED_TRGS_CNT + SUCC_TRGS_CNT);
		coBatchService.saveBatchSummary(batchSummary);
	}

	private String storePdf(EligibilityDetails eligibilityDetails) {
		CoPdfs coPdfs = null;
		byte[] casePdf = null;
		FileSystemResource pdfFile = null;
		coPdfs = new CoPdfs();
		try {
			pdfFile = new FileSystemResource("D:\\COPDFS\\" + eligibilityDetails.getCaseNum().toString() + ".pdf");
			casePdf = new byte[(int) pdfFile.contentLength()];
			pdfFile.getInputStream().read(casePdf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		coPdfs.setCaseNumber(eligibilityDetails.getCaseNum());
		coPdfs.setPlanName(eligibilityDetails.getPlanName());
		coPdfs.setPlanStatus(eligibilityDetails.getPlanStatus());
		coPdfs.setPdfDocument(casePdf);
// call service class method
		coBatchService.savePdf(coPdfs);
		return eligibilityDetails.getCaseNum().toString();
	}

}