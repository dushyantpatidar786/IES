package com.co.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.co.utility.PdfGenerator;

@Service
public class CoServiceImpl implements CoService {

	@Override
	public ByteArrayInputStream generatePDF() throws IOException {
		ByteArrayInputStream byteArrayInputStream = PdfGenerator.generatePDFReport();
		return byteArrayInputStream;
	}

}
