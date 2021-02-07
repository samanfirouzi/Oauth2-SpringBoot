package com.saman.OAuth2Server.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter{

    private String clientId = "saman";
    private String clientSecret = "saman-secure-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEArVVhi/y0KqzU39dGluak72aJRK9SFokC1HL0NWafGHIAabrU\n" +
            "kTDpFnNoT84zNo/sNrP1OKJizkQ97GZEAaUehO6FeRbguYJZaJHuE3eafIg1usvR\n" +
            "pJ/oqzhlCyfMPgaerqoogI7Fw3wKE3Qil0fHAMOJOoMUC8LJSKzRSE8r9WzKGy+f\n" +
            "n/Dxi39AIb50XVxpM6Zc6U3cCPb+zaaM1u+Vt41FyjZR++Wqps7RftpDr10yGr1J\n" +
            "u0WmEc6tmcjfP8mukOPujncpl9jqdDko3VhGM4foLEsCZCOs/aqgK1M8VBPiqCS7\n" +
            "Se43GYIhvj5W4iKmeNPjvXwjjVZJ7sTDY3ru3wIDAQABAoIBAAUk6I04BbzSfkra\n" +
            "X7zwwWpdd6tzLJOAo3Xcp4TCM+yMzZhVtCNO+UvyRuE/dGOlJ9SDyL5Si24ltlTC\n" +
            "wDfwR5SYY5uxYx/+T1r8Ib2wKgSSIk4V6HXjTQtv1AfduYEJv0HdyO/vzMy9bKsh\n" +
            "IjmtJqnOI895vmmO8VxXzKpBt0NEV8CFFWcXVNl1oNc6+JRUCbdcTUuYZzoevNJd\n" +
            "H9FVh6P8PbjI70awGkTgTlxaLp8FN5MyBzMX4rlCH7V5rncvnY5GVgM6sjdVz1U6\n" +
            "L4w0aX8hgbfU4cwkXS4hSsQJ7KadDqne6W6mJFZ4ld1JKX1N5uRqB4hgR5duLZgQ\n" +
            "0OhWbjkCgYEA37HcyoO0QasOMN156ZH18Gl7t6iL97SqgMcRnoMuifFzye147UqG\n" +
            "5ZVXXuDV/X+d8P2Hg695lsqn1keat5/3uzkCYdxVa6yBkb5XCxUKE9mL0pr2l8Nb\n" +
            "usxyGklwZGLsvIKpEqkvv2NrvX0r6xoDsDRqXyg2V+wNooUNx4BKlzsCgYEAxl2h\n" +
            "jRdm8dh7SVRZoFc7CBIo+tG1zmlc0h4EY4oy5nUrSoBjW4d3IAUmeHQXGWjCd2su\n" +
            "Wda+Q4MPBbQveo/iLHsJp93eKZR4CCeIxrsEZb5HQkYYlBXFAg2fEXy2Ijx6S8yQ\n" +
            "aiqbEE8rzSwCZJzl8eHW5pYWSPhQjERpmFzkdK0CgYBqymekiqGoY/gfXexQlGz7\n" +
            "+++jKyS++ZIYbnC74tZ55jon4WXEqrqhqd0PH3kpTx5gqThjpob+dPsRMhfrp8PF\n" +
            "rp6zh1OEVtN4d4zobn/kIAw/W9lzdEE7eVTTtdGhKxTnnd0HWpu/27/Ys4Qe9cli\n" +
            "+nL5wKHI8ZlEyA5e+qEhdwKBgEcJDqpFACOJP5Uh9LqbolMglbZEVRPm3UuNXkr7\n" +
            "GYE34lnaojoLx9gaoA0wnXkG7+Tt5/paEWaBz2ZegUYlsnv1T57isAU52sYoyi1I\n" +
            "mamJu2D7Ux/sr9EtaZJMk7is6aqtReYO/dqYT3o40V6QZTP6iQeoJbDZ45ZnhVz/\n" +
            "nvw9AoGAXgWc8U+LNgJDSNDvT4Aqjxobhwj/V4YVaACm9XBmB1yV/PdvYHKW1TC5\n" +
            "LlXpdakr1/e+hMgsxSBpLEqmBkFK5h9LQLTwtNtTqMazhGKWj9kynEyn9DIjR8+s\n" +
            "c9a30Y11w+HNVspncZfsztgkDwOrqfnRjakO7gD5N4+QWK5L4Vc=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArVVhi/y0KqzU39dGluak\n" +
            "72aJRK9SFokC1HL0NWafGHIAabrUkTDpFnNoT84zNo/sNrP1OKJizkQ97GZEAaUe\n" +
            "hO6FeRbguYJZaJHuE3eafIg1usvRpJ/oqzhlCyfMPgaerqoogI7Fw3wKE3Qil0fH\n" +
            "AMOJOoMUC8LJSKzRSE8r9WzKGy+fn/Dxi39AIb50XVxpM6Zc6U3cCPb+zaaM1u+V\n" +
            "t41FyjZR++Wqps7RftpDr10yGr1Ju0WmEc6tmcjfP8mukOPujncpl9jqdDko3VhG\n" +
            "M4foLEsCZCOs/aqgK1M8VBPiqCS7Se43GYIhvj5W4iKmeNPjvXwjjVZJ7sTDY3ru\n" +
            "3wIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(1200)
                .refreshTokenValiditySeconds(5000);

    }
}
