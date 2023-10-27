package com.tabsnotspaces.match;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

/**
 * This class represents a Client entity.
 */

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
	@JoinTable(
			name = "client_service",
			joinColumns = @JoinColumn(name = "client_id"),
			inverseJoinColumns = @JoinColumn(name = "service_id")
	)
	private Set<Service> services;

	@ManyToMany
	private List<ServiceProvider> serviceProviders;

	@ManyToMany
	private List<Review> reviews;

	/**
	 * Default constructor for the Client class.
	 */
	public Client() {
		super();
	}
	// https://stackoverflow.com/questions/57369016/el1008e-property-or-field-username-cannot-be-found-on-object-of-type-userhttps://stackoverflow.com/questions/57369016/el1008e-property-or-field-username-cannot-be-found-on-object-of-type-user

	public Client(String clientName, List<Consumer> consumers, Set<Service> services, List<ServiceProvider> serviceProviders) {
		this.clientName = clientName;
		this.consumers = consumers;
		this.services = services;
		this.serviceProviders = serviceProviders;
	}

	/**
	 * Getter for clientId.
	 *
	 * @return The client ID.
	 */
	public long getClientId() {
		return clientId;
	}

	/**
	 * Setter for clientId.
	 *
	 * @param id The client ID to set.
	 */
	public void setClientId(long id) {
		clientId = id;
	}

	/**
	 * Getter for clientName.
	 *
	 * @return The name of the client.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Setter for clientName.
	 *
	 * @param name The name of the client to set.
	 */

	public void setClientName(String name) {
		clientName = name;
	}

	/**
	 * Getter for consumers.
	 *
	 * @return The list of consumers associated with the client.
	 */
	public List<Consumer> getConsumers() {
		return consumers;
	}

	/**
	 * Setter for consumers.
	 *
	 * @param consumers The list of consumers to set.
	 */
	public void setConsumers(List<Consumer> consumers) {
		this.consumers = consumers;
	}

	/**
	 * Getter for serviceProviders.
	 *
	 * @return The list of service providers associated with the client.
	 */
	public List<ServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

	/**
	 * Setter for serviceProviders.
	 *
	 * @param serviceProviders The list of service providers to set.
	 */
	public void setServiceProviders(List<ServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
  }
    
	public List<Review> getReviews() {
		return reviews;
	}
}
