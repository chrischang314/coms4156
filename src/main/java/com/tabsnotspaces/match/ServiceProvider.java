package com.tabsnotspaces.match;

//import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class ServiceProvider {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long providerId;
	private long parentClientId; // TODO define as a joint key?
	private String providerName;
	private String location;
	private List<String> servicesOffered; // TODO convert to an entity type serviceNum?
	private Date availability;
	//private List<LocalDateTime> availability; // TODO convert to a List of Pair<start,end>

	private long avgRating; 
	// TODO make list of ratings for each servicesOffered
	
	@ManyToMany
	private List<Appointment> bookings;
	
	public ServiceProvider() {
		super();
	}
	
	public ServiceProvider(long parentClientId) {
		super();
		this.parentClientId = parentClientId;
	}

	public ServiceProvider(long providerId,
			long parentClientId, String providerName,
			String location, List<String> servicesOffered,
			//List<LocalDateTime> availability,
			Date availability,
			long avgRating, List<Appointment> bookings) {
		super();
		this.parentClientId = parentClientId;
		this.providerId = providerId;
		this.providerName = providerName;
		this.location = location;
		this.servicesOffered = servicesOffered;
		this.availability = availability;
		this.avgRating = avgRating;
		this.bookings = bookings;
	}
		
	public long getParentClientId() {
		return parentClientId;
	}
	
	public void setParentClientId(long parentClientId) {
		this.parentClientId = parentClientId;
	}
	
	public long getProviderId() {
		return providerId;
	}
	
	public void setProviderId(long id) {
		providerId = id;
	}
	
	public String getProviderName() {
		return providerName;
	}
	
	public void setProviderName(String name) {
		providerName = name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public List<String> getServicesOffered() {
		return servicesOffered;
	}
	
	public void setServicesOffered(List<String> servicesOffered) {
		this.servicesOffered = servicesOffered;
	}
	
	/*
	 * public List<LocalDateTime> getAvailability() { return availability; }
	 * 
	 * public void setAvailability(List<LocalDateTime> availability) {
	 * this.availability = availability; }
	 */	
	
	public Date getAvailability() { return availability; }

	public void setAvailability(Date availability) {
		this.availability = availability;
	}
	
	public long getAvgRating() {
		return avgRating;
	}
	
	public void setAvgRating(long avgRating) {
		this.avgRating = avgRating;
	}

	public List<Appointment> getBookings() {
		return bookings;
	}
	
	public void setBookings(List<Appointment> bookings) {
		this.bookings = bookings;
	}
}
