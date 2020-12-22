package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.PubReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PubReviewRepository extends CrudRepository<PubReview, Integer> {
    List<PubReview> findAllByPubId(int pubId);
}