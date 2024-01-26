package com.pointel.monitor.service.Impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.repository.HourlyDataRepository;
import com.pointel.monitor.service.HourlyMonitoringDataService;

@Service
public class HourlyMonitoringDataServiceImpl implements HourlyMonitoringDataService {

	@Autowired
	HourlyDataRepository hourlyDataRepository;

	@Override
	public HourlyReport save(HourlyReport hourlyReport) throws DataAlreadyFoundException {
		System.out.println(existsByDateFieldAndBusinessUnitAndTime(hourlyReport.getDateField(), hourlyReport.getBusinessUnit(),
				hourlyReport.getTime()));
		if (!existsByDateFieldAndBusinessUnitAndTime(hourlyReport.getDateField(), hourlyReport.getBusinessUnit(),
				hourlyReport.getTime()))
			return hourlyDataRepository.save(hourlyReport);
		else
			throw new DataAlreadyFoundException("Data is already found for this date and Time");
	}

	@Override
	public boolean existsByDateFieldAndBusinessUnitAndTime(LocalDate dateField, String businessUnit, String time) {
		Boolean value = hourlyDataRepository.existsByDateFieldAndBusinessUnitAndTime(dateField, businessUnit, time);
		return value;
	}

}
