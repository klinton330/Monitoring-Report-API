package com.pointel.monitor.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pointel.monitor.entity.MonitorData;
@Repository
public interface MontoringDataRepository extends JpaRepository<MonitorData, Long> {
	
	boolean existsByDateFieldAndBusinessUnit(LocalDate dateField, String businessUnit);
	List<MonitorData> findByDateField(LocalDate dateField);
    MonitorData findByDateFieldAndBusinessUnit(LocalDate dateField,String bu);
    @Query("SELECT e FROM MonitorData e WHERE e.dateField BETWEEN :startDate AND :endDate")
    List<MonitorData> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<MonitorData> findByDateFieldBetween(LocalDate startDate, LocalDate endDate);
   
}
