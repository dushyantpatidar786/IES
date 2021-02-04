package com.co.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class PdfGenerator {

	private static Logger logger = (Logger) LoggerFactory.getLogger(PdfGenerator.class);

	public static ByteArrayInputStream generatePDFReport() throws IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			// Add Paragraph to PDF file->
			Paragraph para = new Paragraph("This is a correspondence model helps to generate pdf.");
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			// document.add(Chunk.NEWLINE);
			document.close();
		} catch (DocumentException e) {
			logger.error(e.toString());
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}
