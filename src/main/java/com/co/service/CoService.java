package com.co.service;

import java.io.ByteArrayInputStream;

import java.io.IOException;

public interface CoService {
	public ByteArrayInputStream generatePDF() throws IOException;
}
