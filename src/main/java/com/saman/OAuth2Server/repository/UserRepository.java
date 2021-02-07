package com.saman.OAuth2Server.repository;

import com.saman.OAuth2Server.security.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserData getUserDetails(String username) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String userSQLQuery = "SELECT * FROM USER_DATA WHERE USERNAME=?";

        List<UserData> list = jdbcTemplate.query(userSQLQuery,
                (ResultSet rs, int rowNum) -> {
                    UserData user = new UserData();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }, username);
        if (list.size() > 0) {
            if(list.size()>1) return null;//it's just will happened when database field is not unique.
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
            grantedAuthorities.add(grantedAuthority);
            list.get(0).setGrantedAuthorities(grantedAuthorities);
            return list.get(0);
        }
        return null;
    }
}
