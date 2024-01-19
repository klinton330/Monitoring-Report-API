package com.pointel.monitor.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_monitoring_data")
public class MonitorData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateField;
    private String businessUnit;
    private int outbound;
	private int inbound;
	private int offerred;
	private int abandoned;
	private int answered;
	private int transferred;
	private int apiExecution;
	private int sms;
	private int globalException;
	private int customerDisconnect;
	private int callflowDisconnect;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDateField() {
		return dateField;
	}

	public void setDateField(LocalDate dateField) {
		this.dateField = dateField;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public int getOutbound() {
		return outbound;
	}

	public void setOutbound(int outbound) {
		this.outbound = outbound;
	}

	public int getInbound() {
		return inbound;
	}

	public void setInbound(int inbound) {
		this.inbound = inbound;
	}

	public int getOfferred() {
		return offerred;
	}

	public void setOfferred(int offerred) {
		this.offerred = offerred;
	}

	public int getAbandoned() {
		return abandoned;
	}

	public void setAbandoned(int abandoned) {
		this.abandoned = abandoned;
	}

	public int getAnswered() {
		return answered;
	}

	public void setAnswered(int answered) {
		this.answered = answered;
	}

	public int getTransferred() {
		return transferred;
	}

	public void setTransferred(int transferred) {
		this.transferred = transferred;
	}

	public int getApiExecution() {
		return apiExecution;
	}

	public void setApiExecution(int apiExecution) {
		this.apiExecution = apiExecution;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public int getGlobalException() {
		return globalException;
	}

	public void setGlobalException(int globalException) {
		this.globalException = globalException;
	}

	public int getCustomerDisconnect() {
		return customerDisconnect;
	}

	public void setCustomerDisconnect(int customerDisconnect) {
		this.customerDisconnect = customerDisconnect;
	}

	public int getCallflowDisconnect() {
		return callflowDisconnect;
	}

	public void setCallflowDisconnect(int callflowDisconnect) {
		this.callflowDisconnect = callflowDisconnect;
	}

}
