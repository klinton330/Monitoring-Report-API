package com.pointel.monitor.service;

import java.time.LocalDate;

import com.pointel.monitor.domain.Hourly;
import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;

public interface HourlyMonitoringDataService {
	
	public HourlyReport save(HourlyReport hourlyReport)throws DataAlreadyFoundException;
	boolean existsByDateFieldAndBusinessUnitAndTime(LocalDate dateField, String businessUnit,String time);
	public Hourly getHourlyData(LocalDate localDate)throws DataNotFoundException;
	public Hourly getHourlyforChart(LocalDate localDate,String bu,String metrics)throws DataNotFoundException;
	public Hourly getHourlyDateAndBu(LocalDate date1, String bu) throws DataNotFoundException;
	public Hourly getHourlyMultiple(LocalDate localDate, String bu, String metrics) throws DataNotFoundException;

}
