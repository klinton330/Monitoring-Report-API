package com.pointel.monitor.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointel.monitor.domain.Chart;
import com.pointel.monitor.domain.ChartMetrics;
import com.pointel.monitor.domain.Hourly;
import com.pointel.monitor.entity.HourlyReport;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;
import com.pointel.monitor.repository.HourlyDataRepository;
import com.pointel.monitor.service.HourlyMonitoringDataService;

@Service
public class HourlyMonitoringDataServiceImpl implements HourlyMonitoringDataService {

	@Autowired
	HourlyDataRepository hourlyDataRepository;

	@Override
	public HourlyReport save(HourlyReport hourlyReport) throws DataAlreadyFoundException {
		System.out.println(existsByDateFieldAndBusinessUnitAndTime(hourlyReport.getDateField(),
				hourlyReport.getBusinessUnit(), hourlyReport.getTime()));
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

	@Override
	public Hourly getHourlyData(LocalDate localDate) throws DataNotFoundException {
		List<HourlyReport> getListOfData = hourlyDataRepository.findByDateField(localDate);
		Hourly hourly = new Hourly();
		hourly.setDate(localDate);
		hourly.setBusinessType("ALL");
		if (getListOfData.size() <= 0)
			throw new DataNotFoundException("Data not found for date:" + localDate);
		hourly.setHourly(getListOfData);
		return hourly;
	}

	@Override
	public Hourly getHourlyforChart(LocalDate localDate, String bu, String metrics) throws DataNotFoundException {
		List<HourlyReport> getListOfData = hourlyDataRepository.findByDateFieldAndBusinessUnit(localDate, bu);
		Hourly hourly = new Hourly();
		ChartMetrics cm = new ChartMetrics();
		hourly.setDate(localDate);
		if (getListOfData.size() <= 0)
			throw new DataNotFoundException("Data not found for date:" + localDate);
		hourly.setBusinessType(getListOfData.get(0).getBusinessUnit());
		hourly.setHourly(getListOfData);
		JSONObject jsonObject = new JSONObject(hourly);
		JSONArray hourlyArray = jsonObject.getJSONArray("hourly");

		ArrayList<String> times = new ArrayList<>();
		ArrayList<Integer> metricsdata = new ArrayList<>();

		for (int i = 0; i < hourlyArray.length(); i++) {
			JSONObject hourlyObject = hourlyArray.getJSONObject(i);
			String time = hourlyObject.getString("time");
			Integer metric = hourlyObject.getInt(metrics);
			times.add(time);
			metricsdata.add(metric);
		}
		cm.setMetricsName(metrics);
		cm.setTimes(times);
		cm.setMetricsdata(metricsdata);
		Chart ch = new Chart();
		List<ChartMetrics> chart = new ArrayList<>();
		chart.add(cm);
		ch.setChartData(chart);
		hourly.setChart(ch);
		return hourly;
	}

	public Hourly getHourlyMultiple(LocalDate localDate, String bu, String metrics) throws DataNotFoundException {
		List<HourlyReport> getListOfData = hourlyDataRepository.findByDateFieldAndBusinessUnit(localDate, bu);
		Hourly hourly = new Hourly();
		ChartMetrics cm = null;
		Chart ch = null;
		String time;
		int metric = 0;
		ArrayList<String> times;
		ArrayList<Integer> metricsdata;
		List<ChartMetrics> chart = new ArrayList<>();

		hourly.setDate(localDate);
		if (getListOfData.size() <= 0)
			throw new DataNotFoundException("Data not found for date:" + localDate);
		hourly.setBusinessType(getListOfData.get(0).getBusinessUnit());
		hourly.setHourly(getListOfData);
		JSONObject jsonObject = new JSONObject(hourly);
		JSONArray hourlyArray = jsonObject.getJSONArray("hourly");

		String[] data = metrics.split(",");
		for (int k = 0; k < data.length; k++) {// outbound
			cm = new ChartMetrics();
			times = new ArrayList<>();
			metricsdata = new ArrayList<>();

			for (int i = 0; i < hourlyArray.length(); i++) {

				JSONObject hourlyObject = hourlyArray.getJSONObject(i);
				time = hourlyObject.getString("time");
				metric = hourlyObject.getInt(data[k]);
				times.add(time);
				metricsdata.add(metric);

			}
			cm.setMetricsName(data[k]);
			cm.setTimes(times);
			cm.setMetricsdata(metricsdata);
			ch = new Chart();

			chart.add(cm);
			ch.setChartData(chart);
			hourly.setChart(ch);
		}

		return hourly;
	}

	@Override
	public Hourly getHourlyDateAndBu(LocalDate localDate, String bu) throws DataNotFoundException {
		List<HourlyReport> getListOfData = hourlyDataRepository.findByDateFieldAndBusinessUnit(localDate, bu);
		Hourly hourly = new Hourly();
		if (getListOfData.size() <= 0)
			throw new DataNotFoundException("Data not found for date:" + localDate);
		hourly.setDate(getListOfData.get(0).getDateField());
		hourly.setBusinessType(getListOfData.get(0).getBusinessUnit());
		hourly.setHourly(getListOfData);
		return hourly;
	}

}
