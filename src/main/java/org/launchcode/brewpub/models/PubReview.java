package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PubReview extends Review{


    @ManyToOne
    private Pub pub;


    public PubReview() {
    }

    public PubReview(String reviewTitle, String reviewText, int rating) {
        super(reviewTitle, reviewText, rating);
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }


}
