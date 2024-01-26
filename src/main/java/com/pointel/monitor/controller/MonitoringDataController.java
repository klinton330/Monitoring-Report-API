package com.pointel.monitor.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.pointel.monitor.entity.MonitorData;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;
import com.pointel.monitor.service.MonitoringDataService;
import com.pointel.monitor.utils.PDFGenerator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MonitoringDataController {

	@Autowired
	private MonitoringDataService monitoringDataService;

	@PostMapping("/save")
	public ResponseEntity<MonitorData> saveData(@RequestBody MonitorData monitorData) throws DataAlreadyFoundException {
		MonitorData monitorData2 = null;
		System.out.println("/save");
		monitorData2 = monitoringDataService.saveData(monitorData);
		return new ResponseEntity<MonitorData>(monitorData2, HttpStatus.CREATED);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<MonitorData> updateData(@PathVariable Long id, @RequestBody MonitorData monitorData) throws DataAlreadyFoundException {
		System.out.println("/update/id");
		MonitorData monitorData1 = monitoringDataService.updateData(id, monitorData);
		return new ResponseEntity<MonitorData>(monitorData1, HttpStatus.OK);
	}

	@GetMapping("/fetch/{date}")
	public ResponseEntity<List<MonitorData>> getData(@PathVariable String date)
			throws ParseException, DataNotFoundException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		List<MonitorData> listByDate = monitoringDataService.fetchDataBy(date1);
		return new ResponseEntity<List<MonitorData>>(listByDate, HttpStatus.OK);
	}

	@GetMapping("/fetch/total/{date}")
	public ResponseEntity<List<MonitorData>> getDataTotal(@PathVariable String date)
			throws ParseException, DataNotFoundException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		List<MonitorData> listByDate = monitoringDataService.fetchDataByTotal(date1);
		return new ResponseEntity<List<MonitorData>>(listByDate, HttpStatus.OK);
	}

	@GetMapping("/fetch")
	public ResponseEntity<MonitorData> getbyDateAndTime(@RequestParam(name = "date") String date,
			@RequestParam(name = "businessUnit") String bu) throws DataNotFoundException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		MonitorData monitorData = monitoringDataService.getByDateAndBu(date1, bu);
		return new ResponseEntity<MonitorData>(monitorData, HttpStatus.OK);

	}

	@GetMapping("/fetch/dates")
	public ResponseEntity<List<MonitorData>> getDataBetweenDates(@RequestParam("startdate") String startDate,
			@RequestParam("enddate") String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate start = LocalDate.parse(startDate, formatter);
		LocalDate end = LocalDate.parse(endDate, formatter);
		List<MonitorData> listOfData = monitoringDataService.dataBetweenTwoDates(start, end);
		return new ResponseEntity<List<MonitorData>>(listOfData, HttpStatus.OK);
	}

	@GetMapping("/fetchbyid/{id}")
	public ResponseEntity<MonitorData> getDataById(@PathVariable Long id) {

		MonitorData monitorData = monitoringDataService.getById(id);
		return new ResponseEntity<MonitorData>(monitorData, HttpStatus.OK);
	}

	@GetMapping("/fetchdata")
	public ResponseEntity<List<MonitorData>> getAll() {
		List<MonitorData> allData = monitoringDataService.fetchAllData();
		return new ResponseEntity<List<MonitorData>>(allData, HttpStatus.OK);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		String result = monitoringDataService.delete(id);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@GetMapping("/fetch/total/pdf/{date}")
	public ResponseEntity<byte[]> generatePDFFile(HttpServletResponse response, @PathVariable String date)
			throws DataNotFoundException, DocumentException, IOException {
		System.out.println("From Generate PDF");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		List<MonitorData> listByDate = monitoringDataService.fetchDataByTotal(date1);
		PDFGenerator pgfGenerator = new PDFGenerator();
		listByDate.forEach(x->System.out.println(x.getBusinessUnit()));
		byte[] pdfBytes = pgfGenerator.generate(listByDate, response);
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("inline", "Data_" + date1 + ".pdf");
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

	}
}
