package com.pointel.monitor.domain;

import java.time.LocalDate;
import java.util.List;

import com.pointel.monitor.entity.HourlyReport;

public class Hourly {
	
	private LocalDate date;
	private String businessType;
	private List<HourlyReport> hourly;
	private Chart chart;
	
	
	public Chart getChart() {
		return chart;
	}
	public void setChart(Chart chart) {
		this.chart = chart;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public List<HourlyReport> getHourly() {
		return hourly;
	}
	public void setHourly(List<HourlyReport> hourly) {
		this.hourly = hourly;
	}
	
	
    
	
	

}
