package com.co.restcontroller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.service.CoService;

@RestController
public class GeneratePdfRestController {

	@Autowired
	private CoService coService;

	@GetMapping(value = "/getPdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> studentReport() throws IOException {
		ByteArrayInputStream bais = coService.generatePDF();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; file = correspondence.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bais));

	}

}
