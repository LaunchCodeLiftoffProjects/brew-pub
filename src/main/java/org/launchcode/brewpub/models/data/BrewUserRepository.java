package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.BrewUser;
import org.springframework.data.repository.CrudRepository;

public interface BrewUserRepository extends CrudRepository<BrewUser, Integer> {
    BrewUser findByUsername(String username);
    BrewUser findByEmail(String email);
}
