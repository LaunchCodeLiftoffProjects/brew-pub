package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.BrewReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrewReviewRepository extends CrudRepository<BrewReview, Integer> {
    List<BrewReview> findAllByBrewId(int brewId);

}
