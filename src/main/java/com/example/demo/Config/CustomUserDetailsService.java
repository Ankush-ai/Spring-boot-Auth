package com.example.demo.Config;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
            .flatMap(role -> {
                // Create a stream of authorities for the role itself
                Stream<GrantedAuthority> roleAuthorities = Stream.of(new SimpleGrantedAuthority(role.getName()));
                // Create a stream of authorities for each permission of the role
                Stream<GrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()));
                // Combine both streams into a single stream
                return Stream.concat(roleAuthorities, permissionAuthorities);
            })
            .collect(Collectors.toList());
    }
}
