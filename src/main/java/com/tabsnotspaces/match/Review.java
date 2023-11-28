package com.tabsnotspaces.match;

import com.google.api.client.util.DateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents a review
 */
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    @Min(value = 1, message = "rating should be greater than 0")
    private int rating;

    @NotNull(message = "review text should not be null")
    @NotEmpty(message = "review text should not be empty")
    private String reviewText;

    @NotNull(message = "review date time should not be null")
    @NotEmpty(message = "review date time should not be empty")
    private DateTime reviewDateTime;

    @Min(value = 1, message = "provider id should be greater than 0")
    private long serviceProviderId;

    @Min(value = 1, message = "consumer id should be greater than 0")
    private long consumerId;

    public Review() {
        super();
    }

    public Review(int rating, String reviewText, DateTime reviewDateTime, long serviceProviderId, long consumerId) {
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDateTime = reviewDateTime;
        this.serviceProviderId = serviceProviderId;
        this.consumerId = consumerId;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public DateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public void setReviewDateTime(DateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
}
