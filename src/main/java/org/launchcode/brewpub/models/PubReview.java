package org.launchcode.brewpub.models;

import javax.persistence.Entity;

@Entity
public class PubReview extends Review{

//
//    @ManyToOne
//    private Pub pub;
//

    public PubReview() {
    }

    public PubReview(String reviewTitle, String reviewText, int rating) {
        super(reviewTitle, reviewText, rating);
    }


//
//    public Brew getBrew() {
//        return brew;
//    }
}
