package com.pointel.monitor.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pointel.monitor.domain.Hourly;
import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.entity.MonitorData;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;
import com.pointel.monitor.service.HourlyMonitoringDataService;

@RestController
@RequestMapping("/api/v1/hourly")
@CrossOrigin("*")
public class HourlyReportController {
	@Autowired
	HourlyMonitoringDataService hourlyMonitorService;

	@PostMapping("/save")
	public ResponseEntity<HourlyReport> saveHourly(@RequestBody HourlyReport hourlyReport)
			throws DataAlreadyFoundException {
		HourlyReport hourlyReport1 = hourlyMonitorService.save(hourlyReport);
		return new ResponseEntity<HourlyReport>(hourlyReport1, HttpStatus.CREATED);
	}

	@GetMapping("/date/{date}")
	public ResponseEntity<Hourly> getDataByData(@PathVariable String date) throws DataNotFoundException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		Hourly hourly = hourlyMonitorService.getHourlyData(date1);
		return new ResponseEntity<Hourly>(hourly, HttpStatus.OK);
	}

	@GetMapping("/chart")
	public ResponseEntity<Hourly> getHourlyForChart(@RequestParam(name = "date") String date,
			@RequestParam(name = "businessUnit") String bu,@RequestParam(name = "metrics") String metrics) throws DataNotFoundException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		Hourly hourly = hourlyMonitorService.getHourlyforChart(date1, bu,metrics);
		return new ResponseEntity<Hourly>(hourly, HttpStatus.OK);
	}
	@GetMapping("/chartmultiple")
	public ResponseEntity<Hourly> getHourlyForChartMultiple(@RequestParam(name = "date") String date,
			@RequestParam(name = "businessUnit") String bu,@RequestParam(name = "metrics") String metrics) throws DataNotFoundException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
	    Hourly hourly=	hourlyMonitorService.getHourlyMultiple(date1, bu,metrics);
	    return new ResponseEntity<Hourly>(hourly, HttpStatus.OK);
	}
	
	@GetMapping("/getbydate")
	public ResponseEntity<Hourly> getbyDateAndTime(@RequestParam(name = "date") String date,
			@RequestParam(name = "businessUnit") String bu) throws DataNotFoundException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date1 = LocalDate.parse(date, formatter);
		Hourly hourly = hourlyMonitorService.getHourlyDateAndBu(date1, bu);
		return new ResponseEntity<Hourly>(hourly, HttpStatus.OK);
	}

}
