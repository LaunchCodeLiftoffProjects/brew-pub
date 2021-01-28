package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.Pub;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PubRepository extends CrudRepository<Pub, Integer> {
    List<Pub> findAll();
    List<Pub> findByName(String name);
    List<Pub> findByAddress(String address);
}
