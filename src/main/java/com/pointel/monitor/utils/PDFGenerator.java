package com.pointel.monitor.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pointel.monitor.entity.MonitorData;

import jakarta.servlet.http.HttpServletResponse;

public class PDFGenerator {
	public byte[] generate(List<MonitorData> list, HttpServletResponse response) throws DocumentException, IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, outputStream);
		// Opening the created document to change it
		document.open();
		Image logo = Image.getInstance(new ClassPathResource("static/pointel.jpg").getURL());
		logo.scaleAbsolute(70,70); // Set the dimensions as needed
		logo.setBorder(1);
		document.add(logo);
		Font fontTiltle = FontFactory.getFont(FontFactory.COURIER_BOLD);
		fontTiltle.setSize(20);
		Paragraph paragraph1 = new Paragraph("Report for:" + list.get(0).getDateField(), fontTiltle);
		// Aligning the paragraph in the document
		paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
		// Adding the created paragraph in the document
		document.add(paragraph1);
		// Creating a table of the 4 columns
		PdfPTable table = new PdfPTable(list.size() + 1);

		table.setWidthPercentage(100f);
		table.setSpacingBefore(5);

		// Create Table Cells for the table header
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.ORANGE);
		cell.setPadding(5);
		cell.setBorderColor(CMYKColor.BLUE);
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		font.setColor(CMYKColor.BLACK);
		// Adding headings in the created table cell or header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Business Unit", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			cell.setPhrase(new Phrase(monitordata.getBusinessUnit(), font));
			table.addCell(cell);
		}
		cell.setPhrase(new Phrase("Outbound", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getOutbound()));
		}
		cell.setPhrase(new Phrase("Inbound", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getInbound()));
		}
		cell.setPhrase(new Phrase("Offerred", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getOfferred()));
		}
		cell.setPhrase(new Phrase("Answered", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getAnswered()));
		}
		cell.setPhrase(new Phrase("Abandoned", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getAbandoned()));
		}
		cell.setPhrase(new Phrase("Transferred", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getTransferred()));
		}
		cell.setPhrase(new Phrase("Data-action Calls", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getApiExecution()));
		}
		cell.setPhrase(new Phrase("Global Exception", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getGlobalException()));
		}
		cell.setPhrase(new Phrase("SMS", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getSms()));
		}
		cell.setPhrase(new Phrase("CallFlow Disconnect", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getCallflowDisconnect()));
		}
		cell.setPhrase(new Phrase("Customer Disconnect", font));
		table.addCell(cell);
		for (MonitorData monitordata : list) {
			table.addCell(String.valueOf(monitordata.getCustomerDisconnect()));
		}

		document.add(table);
		document.close();
		return outputStream.toByteArray();
	}
}
