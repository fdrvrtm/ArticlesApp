package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.Permission;
import com.griddynamics.cloud.learning.dao.domain.Role;
import com.griddynamics.cloud.learning.dao.domain.User;
import com.griddynamics.cloud.learning.dao.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    @Test
    public void shouldCorrectlyTransformUserObject() {
        //given
        final Long id = 1L;
        final String username = "user";
        final String email = "user@gmail.com";
        final String password = "$2a$10$OSAs5O9bSvrAONTxZG7vgOa4JxcGRv8YY6Oh8Y0uCdlSHf6YIEr/6";
        final Permission permission = Permission.LIKE_ARTICLE;

        final Role userRole = new Role(23L, "USER", EnumSet.of(permission));
        final User user = new User(id, username, email, password, userRole, null);
        final Optional<User> userHolder = Optional.of(user);

        when(repository.findUserByUsername(username)).thenReturn(userHolder);

        //when
        final UserDetails userDetails = service.loadUserByUsername(username);

        //then
        verify(repository, times(1)).findUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());

        final Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
        assertEquals(permission.toString(), iterator.next().getAuthority());
    }
}
