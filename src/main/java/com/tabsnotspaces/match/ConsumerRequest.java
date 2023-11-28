package com.tabsnotspaces.match;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents a ConsumerRequest entity.
 */
@Entity
public class ConsumerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @Min(value = 1, message = "consumer id should be greater than 0")
    private long consumerId;
    //private LocalDateTime startTime;
    //private LocalDateTime endTime; TODO restore these
    @NotNull(message = "request date time should not be null")
    @NotEmpty(message = "request date time should not be empty")
    private TupleDateTime requestDate;

    @ManyToOne
    private Service serviceType;

    @Min(value = 1, message = "provider id should be greater than 0")
    private long preferredProviderID; // TODO Convert to list

    /**
     * Default constructor for the ConsumerRequest class.
     */

    public ConsumerRequest() {
        super();
    }

    /**
     * Parameterized constructor for the ConsumerRequest class.
     *
     * @param requestDate         The date and time of the request.
     * @param serviceType         The type of service requested.
     * @param preferredProviderID The ID of the preferred service provider.
     */
    public ConsumerRequest(TupleDateTime requestDate,
                           Service serviceType,
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

    /**
     * Getter for requestId.
     *
     * @return The request ID.
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Setter for requestId.
     *
     * @param id The request ID to set.
     */
    public void setRequestId(long id) {
        requestId = id;
    }

    /**
     * Getter for consumerId.
     *
     * @return The ID of the consumer associated with the request.
     */
    public long getConsumerId() {
        return consumerId;
    }

    /**
     * Setter for consumerId.
     *
     * @param id The ID of the consumer to set.
     */
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

    /**
     * Getter for requestDate.
     *
     * @return The date and time of the request.
     */
    public TupleDateTime getRequestDate() {
        return requestDate;
    }

    /**
     * Setter for requestDate.
     *
     * @param requestDate The date and time of the request to set.
     */
    public void setRequestDate(TupleDateTime requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Getter for serviceType.
     *
     * @return The type of service requested.
     */
    public Service getServiceType() {
        return serviceType;
    }

    /**
     * Setter for serviceType.
     *
     * @param serviceType The type of service to set.
     */
    public void setServiceType(Service serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Getter for preferredProviderID.
     *
     * @return The ID of the preferred service provider.
     */
    public long getPreferredProviderID() {
        return preferredProviderID;
    }

    /**
     * Setter for preferredProviderID.
     *
     * @param preferredProviderID The ID of the preferred service provider to set.
     */
    public void setPreferredProviderID(long preferredProviderID) {
        this.preferredProviderID = preferredProviderID;
    }
}
