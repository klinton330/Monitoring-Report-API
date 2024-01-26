package com.pointel.monitor.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pointel.monitor.entity.HourlyReport;

public interface HourlyDataRepository extends JpaRepository<HourlyReport, Long> {
	boolean existsByDateFieldAndBusinessUnitAndTime(LocalDate dateField, String businessUnit,String time);
}
