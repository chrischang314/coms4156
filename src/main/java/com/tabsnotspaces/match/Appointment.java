package com.tabsnotspaces.match;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long appointmentId;

    private TupleDateTime appointmentTime;
    private String serviceType;
    private long providerID; // TODO convert to entity type?
    private long consumerId;

    public Appointment() {
        super();
    }

    public Appointment(TupleDateTime appointmentTime, String serviceType, long providerID, long consumerId) {
        this.appointmentTime = appointmentTime;
        this.serviceType = serviceType;
        this.providerID = providerID;
        this.consumerId = consumerId;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long id) {
        appointmentId = id;
    }

    public TupleDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(TupleDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
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
