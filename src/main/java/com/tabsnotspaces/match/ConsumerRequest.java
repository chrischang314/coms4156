package com.tabsnotspaces.match;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class ConsumerRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long requestId;
	private long consumerId;
	//private LocalDateTime startTime;
	//private LocalDateTime endTime; TODO restore these
	private Date requestDate;
	private String serviceType;
	private long preferredProviderID; // TODO Convert to list
	
	public ConsumerRequest() {
		super();
	}
	
	public ConsumerRequest(Date requestDate,
			String serviceType,
			long preferredProviderID) {
		this.requestDate = requestDate;
		this.serviceType = serviceType;
		this.preferredProviderID = preferredProviderID;
	}
	
	/*
	 * public ConsumerRequest(LocalDateTime startTime, LocalDateTime endTime, String
	 * serviceType, long preferredProviderID) { this.startTime = startTime;
	 * this.endTime = endTime; this.serviceType = serviceType;
	 * this.preferredProviderID = preferredProviderID; }
	 */	
	
	public long getRequestId() {
		return requestId;
	}
	
	public void setRequestId(long id) {
		requestId = id;
	}
	
	public long getConsumerId() {
		return consumerId;
	}
	
	public void setConsumerId(long id) {
		consumerId = id;
	}

	/*
	 * public LocalDateTime getStartTime() { return startTime; }
	 * 
	 * public void setStartTime(LocalDateTime startTime) { this.startTime =
	 * startTime; }
	 * 
	 * public LocalDateTime getEndTime() { return endTime; }
	 * 
	 * public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
	 */	
	
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public long getPreferredProviderID() {
		return preferredProviderID;
	}
	
	public void setPreferredProviderID(long preferredProviderID) {
		this.preferredProviderID = preferredProviderID;
	}

}
