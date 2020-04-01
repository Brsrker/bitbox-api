package com.jarvi.bitboxapi.service;

import com.jarvi.bitboxapi.model.UserDTO;
import com.jarvi.bitboxapi.persistence.entity.Privilege;
import com.jarvi.bitboxapi.persistence.entity.Role;
import com.jarvi.bitboxapi.persistence.entity.User;
import com.jarvi.bitboxapi.persistence.repository.RoleRepository;
import com.jarvi.bitboxapi.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true,
                true, getAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> getAuthorities(
            Set<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        return roles.stream()
                .map(Role::getPrivileges)
                .flatMap(privileges -> privileges.stream()
                        .map(Privilege::getName))
                .collect(Collectors.toList());

    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public User save(UserDTO user)  {
        return userService.save(user);
    }

}