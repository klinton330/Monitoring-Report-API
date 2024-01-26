package com.pointel.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.service.HourlyMonitoringDataService;

@RestController
@RequestMapping("/api/v1/hourly")
@CrossOrigin("*")
public class HourlyReportController {
	@Autowired
	HourlyMonitoringDataService hourlyMonitorService;

	@PostMapping("save")
	public ResponseEntity<HourlyReport> saveHourly(@RequestBody HourlyReport hourlyReport) {
		HourlyReport hourlyReport1 = hourlyMonitorService.save(hourlyReport);
		return new ResponseEntity<HourlyReport>(hourlyReport1, HttpStatus.CREATED);
	}
}
