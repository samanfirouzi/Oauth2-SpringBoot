package com.saman.OAuth2Server.service;

import com.saman.OAuth2Server.repository.UserRepository;
import com.saman.OAuth2Server.security.UserHelper;
import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserHelper loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserData userData = null;
        try {
            userData = userRepository.getUserDetails(username);
            return new UserHelper(userData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("'" + username + "' not found.");
        }
    }

}