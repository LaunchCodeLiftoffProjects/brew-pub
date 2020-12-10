package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
