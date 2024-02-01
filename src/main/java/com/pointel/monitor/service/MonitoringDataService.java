package com.pointel.monitor.service;

import java.time.LocalDate;
import java.util.List;

import com.pointel.monitor.entity.MonitorData;
import com.pointel.monitor.exception.DataAlreadyFoundException;
import com.pointel.monitor.exception.DataNotFoundException;

public interface MonitoringDataService {

	public MonitorData saveData(MonitorData monitorData)throws DataAlreadyFoundException;
	boolean existsByDateFieldAndBusinessUnit(LocalDate dateField, String businessUnit);
	public List<MonitorData> fetchDataBy(LocalDate localDate)throws DataNotFoundException;
    public MonitorData getByDateAndBu(LocalDate dateField, String businessUnit)throws DataNotFoundException ;
    public List<MonitorData>dataBetweenTwoDates(LocalDate startDate,LocalDate endDate)throws DataNotFoundException ;
    public MonitorData getById(Long id);
    public MonitorData updateData(Long id ,MonitorData monitorData)throws DataAlreadyFoundException;
    public List<MonitorData> fetchAllData();
    public String delete(Long id);
    public List<MonitorData> fetchDataByTotal(LocalDate localDate)throws DataNotFoundException;

}
