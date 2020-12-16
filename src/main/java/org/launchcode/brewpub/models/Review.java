package org.launchcode.brewpub.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class Review extends AbstractEntity{

//    @ManyToOne
//    private User user;
//

    @Size(max = 80, message = "Title cannot be longer than 80 characters")
    private String reviewTitle;

    // Optional
    @Column(columnDefinition = "TEXT")
    @Size(max = 800, message = "Review cannot be longer than 800 characters")
    private String reviewText;

    @NotNull
    @Min(value=1, message = "Must select rating between 1 and 5")
    @Max(value=5, message = "Must select rating between 1 and 5")
    private int rating;

    // Constructors

    public Review() {
    }

    public Review(String reviewTitle, String reviewText, int rating) {
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters / Setters

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }


    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String review) {
        this.reviewText = review;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


//    public User getUser() {
//        return user;
//    }
//



}
