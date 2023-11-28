package com.tabsnotspaces.match;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents an Appointment entity.
 */
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long appointmentId;

    @NotNull(message = "appointment time should not be null")
    @NotEmpty(message = "appointment time should not be empty")
    private TupleDateTime appointmentTime;

    @NotNull(message = "service type should not be null")
    @NotEmpty(message = "service type should not be empty")
    private String serviceType;

    @Min(value = 1, message = "provider id should be greater than 0")
    private long providerID; // TODO convert to entity type?

    @Min(value = 1, message = "consumer id should be greater than 0")
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
     * @param serviceType     The type of service for the appointment.
     * @param providerID      The ID of the provider associated with the appointment.
     * @param consumerId      The ID of the consumer associated with the appointment.
     */
    public Appointment(TupleDateTime appointmentTime, String serviceType, long providerID, long consumerId) {
        this.appointmentTime = appointmentTime;
        this.serviceType = serviceType;
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
     * Getter for serviceType.
     *
     * @return The type of service for the appointment.
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Setter for serviceType.
     *
     * @param serviceType The type of service for the appointment to set.
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
