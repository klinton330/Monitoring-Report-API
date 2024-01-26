package com.pointel.monitor.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.repository.HourlyDataRepository;
import com.pointel.monitor.service.HourlyMonitoringDataService;

@Service
public class HourlyMonitoringDataServiceImpl implements HourlyMonitoringDataService {

	@Autowired
	HourlyDataRepository hourlyDataRepository;

	@Override
	public HourlyReport save(HourlyReport hourlyReport) {
		HourlyReport houReport=hourlyDataRepository.save(hourlyReport);
		return houReport;
	}
	

}
