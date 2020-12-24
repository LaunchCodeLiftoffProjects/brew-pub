package org.launchcode.brewpub.models;

import org.launchcode.brewpub.models.data.BrewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private BrewUserRepository BrewUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final BrewUser brewUser = BrewUserRepository.findByUsername(username);
        if (brewUser == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = User.withUsername(brewUser.getUsername()).password(brewUser.getPwhash()).authorities("USER").build();
        return user;
    }

}
