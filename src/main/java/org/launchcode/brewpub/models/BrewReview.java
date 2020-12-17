package org.launchcode.brewpub.models;

import javax.persistence.Entity;

@Entity
public class BrewReview extends Review{

//
//    @ManyToOne
//    private Pub pub;
//

    public BrewReview() {
    }

    public BrewReview(String reviewTitle, String reviewText, int rating) {
        super(reviewTitle, reviewText, rating);
    }



}
