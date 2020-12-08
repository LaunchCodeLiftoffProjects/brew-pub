package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.PubReview;
import org.launchcode.brewpub.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubReviewRepository extends CrudRepository<PubReview, Integer> {
}