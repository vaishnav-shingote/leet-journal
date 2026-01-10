package com.hardik.auth;

import com.hardik.auth.repository.AuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails {

    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    CustomUserDetails(PasswordEncoder passwordEncoder, AuthRepository authRepository, JdbcUserDetailsManager jdbcUserDetailsManager) {
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
    }

    public ResponseEntity<String> newUser(String username, String password) throws UsernameNotFoundException {

        boolean existingUser = jdbcUserDetailsManager.userExists(username);
        IO.println("User: " + existingUser);
        if(existingUser) {
            return ResponseEntity.ofNullable("User already exists");
        }

        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();

        jdbcUserDetailsManager.createUser(user);

        var newContext = SecurityContextHolder.createEmptyContext();
        newContext
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                username, passwordEncoder.encode(password)
                        )
                );
        SecurityContextHolder.setContext(newContext);

        return ResponseEntity.ok(user.getUsername());
    }
}
