package com.saman.OAuth2Server.repository;

import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.data.repository.CrudRepository;


public interface UserRepositoryCRUD extends CrudRepository<UserData, Long> {

}
