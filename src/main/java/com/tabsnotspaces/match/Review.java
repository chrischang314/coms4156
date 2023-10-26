package com.tabsnotspaces.match;

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
    private String review;
    private TupleDateTime reviewDateTime;
    private long providerId;
    private long consumerId;

    public Review(){ super(); }

    public Review(int rating, String review, TupleDateTime reviewDateTime, long providerId, long consumerId) {
        this.rating = rating;
        this.review = review;
        this.reviewDateTime = reviewDateTime;
        this.providerId = providerId;
        this.consumerId = consumerId;
    }

    public long getReviewId() {
        return reviewId;
    }
    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public TupleDateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewDateTime(TupleDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
