package org.launchcode.brewpub.models;

import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = UserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPwhash()).authorities("USER").build();
        return userDetails;
    }

}
