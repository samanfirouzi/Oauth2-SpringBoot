package com.saman.OAuth2Server.controller;

import com.saman.OAuth2Server.repository.UserRepositoryCRUD;
import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepositoryCRUD userRepositoryCRUD;

    @Autowired
    PasswordEncoder encoder;

    @CrossOrigin("*")
    @GetMapping("/addUser")
    public UserData addUser(@RequestParam String username,@RequestParam String password){
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setPassword(encoder.encode(password));
        return userRepositoryCRUD.save(userData);
    }
}
