package org.launchcode.helloworld.models.data;

import org.launchcode.helloworld.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
