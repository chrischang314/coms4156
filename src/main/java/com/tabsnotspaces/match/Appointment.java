package com.tabsnotspaces.match;

import jakarta.persistence.*;

/**
 * This class represents an Appointment entity.
 */
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long appointmentId;

    private TupleDateTime appointmentTime;
    @ManyToOne
    private Service service;
    private long providerID; // TODO convert to entity type?
    private long consumerId;

    /**
     * Default constructor for the Appointment class.
     */
    public Appointment() {
        super();
    }

    /**
     * Parameterized constructor for the Appointment class.
     *
     * @param appointmentTime The date and time of the appointment.
     * @param service    The type of service for the appointment.
     * @param providerID     The ID of the provider associated with the appointment.
     * @param consumerId     The ID of the consumer associated with the appointment.
     */
    public Appointment(TupleDateTime appointmentTime, Service service, long providerID, long consumerId) {
        this.appointmentTime = appointmentTime;
        this.service = service;
        this.providerID = providerID;
        this.consumerId = consumerId;
    }

    /**
     * Getter for appointmentId.
     *
     * @return The appointment ID.
     */
    public long getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter for appointmentId.
     *
     * @param id The appointment ID to set.
     */
    public void setAppointmentId(long id) {
        appointmentId = id;
    }

    /**
     * Getter for appointmentTime.
     *
     * @return The date and time of the appointment.
     */
    public TupleDateTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Setter for appointmentTime.
     *
     * @param appointmentTime The date and time of the appointment to set.
     */
    public void setAppointmentTime(TupleDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Getter for service.
     *
     * @return The type of service for the appointment.
     */
    public Service getService() {
        return service;
    }

    /**
     * Setter for service.
     *
     * @param service The type of service for the appointment to set.
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Getter for providerID.
     *
     * @return The ID of the provider associated with the appointment.
     */
    public long getProviderID() {
        return providerID;
    }

    /**
     * Setter for providerID.
     *
     * @param providerID The ID of the provider associated with the appointment to set.
     */
    public void setProviderID(long providerID) {
        this.providerID = providerID;
    }

    /**
     * Getter for consumerId.
     *
     * @return The ID of the consumer associated with the appointment.
     */
    public long getConsumerId() {
        return consumerId;
    }

    /**
     * Setter for consumerId.
     *
     * @param customerId The ID of the consumer associated with the appointment to set.
     */
    public void setConsumerId(long customerId) {
        this.consumerId = customerId;
    }
}
