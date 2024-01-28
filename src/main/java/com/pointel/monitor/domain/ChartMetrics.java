package com.pointel.monitor.domain;

import java.util.ArrayList;

public class ChartMetrics {

	private String metricsName;
	private ArrayList<String> times = new ArrayList<>();
	private ArrayList<Integer> metricsdata = new ArrayList<>();
	
	
	public String getMetricsName() {
		return metricsName;
	}
	public void setMetricsName(String metricsName) {
		this.metricsName = metricsName;
	}
	public ArrayList<String> getTimes() {
		return times;
	}
	public void setTimes(ArrayList<String> times) {
		this.times = times;
	}
	public ArrayList<Integer> getMetricsdata() {
		return metricsdata;
	}
	public void setMetricsdata(ArrayList<Integer> metricsdata) {
		this.metricsdata = metricsdata;
	}
	
	
	
}
