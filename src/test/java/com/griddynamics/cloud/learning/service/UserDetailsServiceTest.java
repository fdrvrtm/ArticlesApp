package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.Permission;
import com.griddynamics.cloud.learning.dao.domain.Role;
import com.griddynamics.cloud.learning.dao.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.EnumSet;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserService userService;

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

        when(userService.getUserByUsername(username)).thenReturn(user);

        //when
        final UserDetails userDetails = service.loadUserByUsername(username);

        //then
        verify(userService, times(1)).getUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());

        final Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
        assertEquals(permission.toString(), iterator.next().getAuthority());
    }
}
