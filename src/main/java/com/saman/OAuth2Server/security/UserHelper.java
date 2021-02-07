package com.saman.OAuth2Server.security;

import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.security.core.userdetails.User;

public class UserHelper extends User {
    private static final long serialVersionUID = 1L;
    public UserHelper(UserData user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.getGrantedAuthorities()
        );
    }
}
