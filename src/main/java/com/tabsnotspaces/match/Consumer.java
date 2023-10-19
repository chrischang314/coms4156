package com.tabsnotspaces.match;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * This class represents a Consumer entity.
 */
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

	/**
	 * Default constructor for the Consumer class.
	 */
	public Consumer() {
		super();
	}
	// Default constructors are compulsory

	/**
	 * Constructor for the Consumer class with a specified parentClientId.
	 *
	 * @param parentClientId The ID of the parent client to associate with the consumer.
	 */
	public Consumer(long parentClientId) {
		super();
		this.parentClientId = parentClientId;
		// TODO verify ID
	}

	/**
	 * Getter for parentClientId.
	 *
	 * @return The ID of the parent client associated with the consumer.
	 */
	public long getParentClientId() {
		return parentClientId;
	}

	/**
	 * Setter for parentClientId.
	 *
	 * @param parentClientId The ID of the parent client to set.
	 */
	public void setParentClientId(long parentClientId) {
		this.parentClientId = parentClientId;
	}

	/**
	 * Getter for consumerId.
	 *
	 * @return The consumer ID.
	 */
	public long getConsumerId() {
		return consumerId;
	}

	/**
	 * Setter for consumerId.
	 *
	 * @param id The consumer ID to set.
	 */
	public void setConsumerId(long id) {
		consumerId = id;
	}

	/**
	 * Getter for consumerName.
	 *
	 * @return The name of the consumer.
	 */
	public String getConsumerName() {
		return consumerName;
	}

	/**
	 * Setter for consumerName.
	 *
	 * @param name The name of the consumer to set.
	 */
	public void setConsumerName(String name) {
		consumerName = name;
	}

	/**
	 * Getter for location.
	 *
	 * @return The location (latitude and longitude) of the consumer.
	 */
	public List<Double> getLocation() {
		return location;
	}

	/**
	 * Setter for location.
	 *
	 * @param location The location (latitude and longitude) to set.
	 */
	public void setLocation(List<Double> location) {
		this.location = location;
	}

	/**
	 * Getter for address.
	 *
	 * @return The physical address of the consumer.
	 */
	public String getAddress() { return address; }

	/**
	 * Setter for address.
	 *
	 * @param address The physical address to set.
	 */
	public void setAddress(String address) { this.address = address; }

	/**
	 * Getter for appointments.
	 *
	 * @return The list of appointments associated with the consumer.
	 */
	public List<Appointment> getAppointments() {
		return appointments;
	}

	/**
	 * Setter for appointments.
	 *
	 * @param appointments The list of appointments to set.
	 */
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	/**
	 * Getter for consumerRequests.
	 *
	 * @return The list of consumer requests associated with the consumer.
	 */
	public List<ConsumerRequest> getConsumerRequests() {
		return consumerRequests;
	}

	/**
	 * Setter for consumerRequests.
	 *
	 * @param requests The list of consumer requests to set.
	 */
	public void setConsumerRequests(List<ConsumerRequest> requests) {
		this.consumerRequests = requests;
	}
}
