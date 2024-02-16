package com.pointel.monitor.service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointel.monitor.chartdomain.ChartMetrics;
import com.pointel.monitor.chartdomain.DaywiseChart;
import com.pointel.monitor.domain.DayWise;
import com.pointel.monitor.entity.MonitorData;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;
import com.pointel.monitor.repository.MontoringDataRepository;
import com.pointel.monitor.service.MonitoringDataService;

@Service
public class MonitoringDataServiceImpl implements MonitoringDataService {

	@Autowired
	private MontoringDataRepository monitoringDataRepo;

	@Override
	public MonitorData saveData(MonitorData monitorData) throws DataAlreadyFoundException {
		System.out.println(existsByDateFieldAndBusinessUnit(monitorData.getDateField(), monitorData.getBusinessUnit()));
		if (!existsByDateFieldAndBusinessUnit(monitorData.getDateField(), monitorData.getBusinessUnit()))
			return monitoringDataRepo.save(monitorData);
		else
			throw new DataAlreadyFoundException("Data is Already found in DB");
	}

	@Override
	public boolean existsByDateFieldAndBusinessUnit(LocalDate dateField, String businessUnit) {
		return monitoringDataRepo.existsByDateFieldAndBusinessUnit(dateField, businessUnit);
	}

	@Override
	public List<MonitorData> fetchDataBy(LocalDate localDate) throws DataNotFoundException {
		List<MonitorData> listByDate = monitoringDataRepo.findByDateField(localDate);
		if (listByDate.size() < 1)
			throw new DataNotFoundException("Data not found in System");
		return listByDate;
	}

	@Override
	public MonitorData getByDateAndBu(LocalDate dateField, String businessUnit) throws DataNotFoundException {
		MonitorData monitorData = monitoringDataRepo.findByDateFieldAndBusinessUnit(dateField, businessUnit);
		if (monitorData == null)
			throw new DataNotFoundException("Data not found in System");
		return monitorData;
	}

	@Override
	public List<MonitorData> dataBetweenTwoDates(LocalDate startDate, LocalDate endDate) throws DataNotFoundException {
		List<MonitorData> getData = monitoringDataRepo.findByDateRange(startDate, endDate);
		return getData;
	}

	@Override
	public MonitorData getById(Long id) {
		return monitoringDataRepo.findById(id).get();
	}

	@Override
	public MonitorData updateData(Long id, MonitorData monitorData) throws DataAlreadyFoundException {
		MonitorData getMonitor = monitoringDataRepo.findById(id).get();
		LocalDate updateddate = monitorData.getDateField();
		String updatedbusiness = monitorData.getBusinessUnit();
		LocalDate existingDate = getMonitor.getDateField();
		String existingbusiness = getMonitor.getBusinessUnit();
		System.out.println("BEFORE" + getMonitor.getAbandoned());
		getMonitor.setDateField(monitorData.getDateField());
		getMonitor.setBusinessUnit(monitorData.getBusinessUnit());
		getMonitor.setInbound(monitorData.getInbound());
		getMonitor.setAbandoned(monitorData.getAbandoned());
		getMonitor.setOutbound(monitorData.getOutbound());
		getMonitor.setAnswered(monitorData.getAnswered());
		getMonitor.setOfferred(monitorData.getOfferred());
		getMonitor.setApiExecution(monitorData.getApiExecution());
		getMonitor.setGlobalException(monitorData.getGlobalException());
		getMonitor.setCallflowDisconnect(monitorData.getCallflowDisconnect());
		getMonitor.setCustomerDisconnect(monitorData.getCustomerDisconnect());
		getMonitor.setSms(monitorData.getSms());
		System.out.println(existsByDateFieldAndBusinessUnit(monitorData.getDateField(), monitorData.getBusinessUnit())
				|| (updateddate.equals(existingDate) || updatedbusiness.equalsIgnoreCase(existingbusiness)));
		if (!existsByDateFieldAndBusinessUnit(monitorData.getDateField(), monitorData.getBusinessUnit())
				|| (updateddate.equals(existingDate) && updatedbusiness.equalsIgnoreCase(existingbusiness)))
			return monitoringDataRepo.save(monitorData);
		else
			throw new DataAlreadyFoundException(
					"Date or business Unit already found for this date so cannot be updated");

	}

	public List<MonitorData> fetchAllData() {
		return monitoringDataRepo.findAll();
	}

	@Override
	public String delete(Long id) {
		monitoringDataRepo.deleteById(id);
		return "Record has been deleted successfully";

	}

	@Override
	public List<MonitorData> fetchDataByTotal(LocalDate localDate) throws DataNotFoundException {
		int totalInbound = 0;
		int totaloutbound = 0;
		int totalofferred = 0;
		int totalabandoned = 0;
		int totalanswered = 0;
		int totaltransferred = 0;
		int totalapiExecution = 0;
		int totalsms = 0;
		int totalglobalException = 0;
		int totalcustomerDisconnect = 0;
		int totalcallflowDisconnect = 0;
		MonitorData total = new MonitorData();
		List<MonitorData> listByDate = monitoringDataRepo.findByDateField(localDate);
		if (listByDate.size() < 1)
			throw new DataNotFoundException("Data not found in System");
		else {
			for (MonitorData monitor : listByDate) {
				totalInbound += monitor.getInbound();
				totaloutbound += monitor.getOutbound();
				totalofferred += monitor.getOfferred();
				totalabandoned += monitor.getAbandoned();
				totalanswered += monitor.getAnswered();
				totaltransferred += monitor.getTransferred();
				totalapiExecution += monitor.getApiExecution();
				totalglobalException += monitor.getGlobalException();
				totalsms += monitor.getSms();
				totalcustomerDisconnect += monitor.getCustomerDisconnect();
				totalcallflowDisconnect += monitor.getCallflowDisconnect();
			}

		}
		total.setBusinessUnit("TOTAL");
		total.setOutbound(totaloutbound);
		total.setInbound(totalInbound);
		total.setOfferred(totalofferred);
		total.setAbandoned(totalabandoned);
		total.setAnswered(totalanswered);
		total.setTransferred(totaltransferred);
		total.setApiExecution(totalapiExecution);
		total.setSms(totalsms);
		total.setGlobalException(totalglobalException);
		total.setCallflowDisconnect(totalcallflowDisconnect);
		total.setCustomerDisconnect(totalcustomerDisconnect);
		listByDate.add(total);
		return listByDate;
	}

	@Override
	public DayWise getDataAllBu(LocalDate startdate, LocalDate enddate) throws DataNotFoundException {
		List<MonitorData> data = dataBetweenTwoDates(startdate, enddate);
		Collections.sort(data, new Comparator<MonitorData>() {
			@Override
			public int compare(MonitorData o1, MonitorData o2) {
				System.out.println("Inside compare");
				LocalDate date1 = o1.getDateField();
				LocalDate date2 = o2.getDateField();
				return date1.compareTo(date2);
			}

		});
		DayWise daywise = new DayWise();
		if (data.size() > 0) {
			daywise.setStartdate(data.get(0).getDateField());
			daywise.setEnddate(data.get(data.size() - 1).getDateField());
			
		} else {
			daywise.setStartdate(startdate);
			daywise.setEnddate(enddate);
		}
		daywise.setData(data);
		daywise.setTotal(data.size());
		return daywise;
	}

	@Override
	public DayWise getDataOneBu(LocalDate startDate, LocalDate endDate, String bu, String metrics)
			throws DataNotFoundException {
		List<MonitorData> daywisedata = dataBetweenTwoDates(startDate, endDate);
		daywisedata = daywisedata.stream().filter((datas) -> datas.getBusinessUnit().equalsIgnoreCase(bu))
				.collect(Collectors.toList());
		Collections.sort(daywisedata, new Comparator<MonitorData>() {
			@Override
			public int compare(MonitorData o1, MonitorData o2) {
				LocalDate date1 = o1.getDateField();
				LocalDate date2 = o2.getDateField();
				return date1.compareTo(date2);
			}

		});
		DayWise daywise = new DayWise();
		if (!daywisedata.isEmpty()) {
			daywise.setStartdate(daywisedata.get(0).getDateField());
			daywise.setEnddate(daywisedata.get(daywisedata.size() - 1).getDateField());
			} else {
			daywise.setStartdate(startDate);
			daywise.setEnddate(endDate);
		}
		daywise.setData(daywisedata);
		daywise.setTotal(daywisedata.size());

		DaywiseChart daywisechart;
		ChartMetrics cm = null;
		//Store Date/Time
		ArrayList<String> times;
		//Store Metric data
		ArrayList<Integer> metricsdata;
		List<ChartMetrics> chartData = new ArrayList<>();
		String time;
		int metric = 0;
		JSONObject jsonObject = new JSONObject(daywise);
		System.out.println("Data"+jsonObject);
		JSONArray dayWiseArray = jsonObject.getJSONArray("data");
		String[] data = metrics.split(",");// inbound
		for (int k = 0; k < data.length; k++) {
			cm = new ChartMetrics();
			times = new ArrayList<>();
			metricsdata = new ArrayList<>();
			// Iterate through JSON RESPONSE
			for (int i = 0; i < dayWiseArray.length(); i++) {
				JSONObject hourlyObject = dayWiseArray.getJSONObject(i);
				time = hourlyObject.getString("dateField");
				metric = hourlyObject.getInt(data[k]);
				times.add(time);
				metricsdata.add(metric);
			}
			cm.setMetricsName(data[k]);
			cm.setTimes(times);
			cm.setMetricsdata(metricsdata);
			
			chartData.add(cm);
			daywisechart = new DaywiseChart();
			daywisechart.setChartData(chartData);
			daywise.setDaywise(daywisechart);
		}
		return daywise;
	}

}
