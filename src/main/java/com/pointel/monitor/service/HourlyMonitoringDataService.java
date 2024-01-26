package com.pointel.monitor.service;

import java.time.LocalDate;

import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.exception.DataAlreadyFoundException;

public interface HourlyMonitoringDataService {
	
	public HourlyReport save(HourlyReport hourlyReport)throws DataAlreadyFoundException;
	boolean existsByDateFieldAndBusinessUnitAndTime(LocalDate dateField, String businessUnit,String time);

}
