package org.launchcode.brewpub.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Review extends AbstractEntity{

//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    private Pub pub;
//
//    @ManyToOne
//    private Brew brew;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, max = 80, message = "Title must be between 3 and 80 characters")
    private String reviewTitle;

    @Column(columnDefinition = "TEXT")
    @Size(max = 800, message = "Review cannot be longer than 800 characters")
    private String review;

    @NotEmpty(message = "Must select rating between 1 and 10")
    private int rating;

    // Constructors

    public Review() {
    }

    public Review(String reviewTitle, String review, int rating) {
        this.reviewTitle = reviewTitle;
        this.review = review;
        this.rating = rating;
    }

    // Getters / Setters

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
//    public Pub getPub() {
//        return pub;
//    }
//
//    public Brew getBrew() {
//        return brew;
//    }


}
