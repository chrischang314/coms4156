package com.tabsnotspaces.match;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long appointmentId;

	//private LocalDateTime startTime; TODO
	//private LocalDateTime endTime;
	private Date appointmentDate;
	private String serviceType;
	private long providerID; // TODO convert to entity type?
	private long consumerId;
	
	public Appointment() {
		super();
	}

	public Appointment(Date appointmentDate, String serviceType, long providerID, long consumerId) {
		super();
		this.appointmentDate = appointmentDate;
		this.serviceType = serviceType;
		this.providerID = providerID;
		this.consumerId = consumerId;
	}
	
	/*
	 * public Appointment(LocalDateTime startTime, LocalDateTime endTime, String
	 * serviceType, long providerID, long customerId) { super(); this.startTime = startTime;
	 * this.endTime = endTime; this.serviceType = serviceType; this.providerID =
	 * providerID; this.consumerId = customerId; }
	 */		
	
	public long getAppointmentId() {
		return appointmentId;
	}
	
	public void setAppointmentId(long id) {
		appointmentId = id;
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
	
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public long getProviderID() {
		return providerID;
	}
	
	public void setProviderID(long providerID) {
		this.providerID = providerID;
	}

	public long getConsumerId() {
		return consumerId;
	}
	
	public void setConsumerId(long customerId) {
		this.consumerId = customerId;
	}
}
