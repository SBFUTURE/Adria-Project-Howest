package be.howest.ti.adria.logic.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    private String reviewerName;
    private int score;
    private String rideId;

    public Review(){
    }

    public Review(String rideId, String reviewerName, int score) {
        this.rideId = rideId;
        setReviewerName(reviewerName);
        setScore(score);
    }

    @JsonProperty("reviewScore")
    public int getScore() {
        return score;
    }

    @JsonProperty("reviewer")
    public String getReviewerName() {
        return reviewerName;
    }

    public Review setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
        return this;
    }

    public Review setScore(int score) {
        this.score = score;
        return this;
    }

    @JsonProperty("rideId")
    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }
}
