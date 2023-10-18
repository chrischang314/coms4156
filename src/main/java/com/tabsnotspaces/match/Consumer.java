package com.tabsnotspaces.match;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Consumer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long consumerId;
	private String consumerName;
	private long parentClientId; // TODO make as joint key?
	private String address;
	private List<Double> location;
	// TODO Fetch lat long from Maps API, for physical address
	// TODO add each rating given, to help in future filtering
	@ManyToMany
	private List<ConsumerRequest> consumerRequests;
	@ManyToMany
	private List<Appointment> appointments;
	//@ManyToMany
	//List<Ratings>
	// pastAppointments, pastRequests TODO fields
	
	public Consumer() {
		super();
	}
	// Default constructors are compulsory
	
	public Consumer(long parentClientId) {
		super();
		this.parentClientId = parentClientId;
		// TODO verify ID
	}
	
	public long getParentClientId() {
		return parentClientId;
	}
	
	public void setParentClientId(long parentClientId) {
		this.parentClientId = parentClientId;
	}
	
	public long getConsumerId() {
		return consumerId;
	}
	
	public void setConsumerId(long id) {
		consumerId = id;
	}
	
	public String getConsumerName() {
		return consumerName;
	}
	
	public void setConsumerName(String name) {
		consumerName = name;
	}
	
	public List<Double> getLocation() {
		return location;
	}
	
	public void setLocation(List<Double> location) {
		this.location = location;
	}

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public List<ConsumerRequest> getConsumerRequests() {
		return consumerRequests;
	}
	
	public void setConsumerRequests(List<ConsumerRequest> requests) {
		this.consumerRequests = requests;
	}
}
