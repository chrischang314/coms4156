package com.tabsnotspaces.match;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long clientId;
	private String clientName;
	@ManyToMany
	private List<Consumer> consumers;
	// AddConsumer function - check service type to belong to a limited set
	@ManyToMany
	private List<ServiceProvider> serviceProviders;
	// TODO Services allowed
	
	
	public Client() {
		super();
	}
	// https://stackoverflow.com/questions/57369016/el1008e-property-or-field-username-cannot-be-found-on-object-of-type-userhttps://stackoverflow.com/questions/57369016/el1008e-property-or-field-username-cannot-be-found-on-object-of-type-user
	
	public long getClientId() {
		return clientId;
	}
	
	public void setClientId(long id) {
		clientId = id;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void setClientName(String name) {
		clientName = name;
	}
	
	public List<Consumer> getConsumers() {
		return consumers;
	}
	
	public void setConsumers(List<Consumer> consumers) {
		this.consumers = consumers;
	}

	public List<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}
	
	public void setServiceProviders(List<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}
}
