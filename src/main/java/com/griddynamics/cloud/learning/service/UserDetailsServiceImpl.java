package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.SpringUserImpl;
import com.griddynamics.cloud.learning.dao.domain.User;
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

    private UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = service.getUserByUsername(username);

        Set<GrantedAuthority> authorities = user.getRole().getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.toString()))
                .collect(Collectors.toSet());

        return new SpringUserImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }
}
