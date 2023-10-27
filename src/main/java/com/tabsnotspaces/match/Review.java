package com.tabsnotspaces.match;

import com.google.api.client.util.DateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.IdGeneratorType;

/**
 * This class represents a review
 */
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;
    private int rating;
    private String reviewText;
    private DateTime reviewDateTime;
    private long serviceProviderId;
    private long consumerId;

    public Review(){ super(); }

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
    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public DateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setServiceProviderId(long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewDateTime(DateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
