package com.pointel.monitor.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pointel.monitor.chartdomain.DaywiseChart;
import com.pointel.monitor.entity.MonitorData;

public class DayWise {
	private LocalDate startdate;
	private LocalDate enddate;
	@JsonIgnore
	private List<MonitorData> data;
	private DaywiseChart daywise;
	private int total;
	
	public DaywiseChart getDaywise() {
		return daywise;
	}
	public void setDaywise(DaywiseChart daywise) {
		this.daywise = daywise;
	}
	public LocalDate getStartdate() {
		return startdate;
	}
	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}
	public LocalDate getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	public List<MonitorData> getData() {
		return data;
	}
	public void setData(List<MonitorData> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
	
}
