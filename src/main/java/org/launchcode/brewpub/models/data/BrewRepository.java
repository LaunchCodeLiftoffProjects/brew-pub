package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.Brew;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrewRepository extends CrudRepository<Brew, Integer> {
    List<Brew> findAllByPubId(int pubId);
    List<Brew> findAll();
}
