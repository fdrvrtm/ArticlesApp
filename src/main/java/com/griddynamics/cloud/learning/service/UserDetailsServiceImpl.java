package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.User;
import com.griddynamics.cloud.learning.dao.repository.UserRepository;
import com.griddynamics.cloud.learning.web.UserNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = repository.findUserByUsername(username).orElseThrow(
                () -> new UserNotFoundException("No user found with username " + username));

        Set<GrantedAuthority> authorities = user.getRole().getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.toString()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
