package com.co.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.batch.CoGenDlyBatch;

@RestController
public class CoBatchRunRestController {

	@Autowired
	private CoGenDlyBatch coGenDlyBatch;

	@GetMapping("/cogendly")
	public String testCoBatchExecution() {
		coGenDlyBatch.test();
		return "Batch Executed";
	}
}
