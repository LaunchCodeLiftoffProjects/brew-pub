package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BrewReview extends Review{


    @ManyToOne
    private Brew brew;

    public BrewReview() {
    }

    public BrewReview(String reviewTitle, String reviewText, int rating) {
        super(reviewTitle, reviewText, rating);
    }

    public Brew getBrew() {
        return brew;
    }

    public void setBrew(Brew brew) {
        this.brew = brew;
    }
}
