package com.achartechnologies.security_demo_api.service;



import com.achartechnologies.security_demo_api.entity.User;
import com.achartechnologies.security_demo_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Converte o conjunto de roles em uma lista de strings simples
        Set<String> roles = user.getRoles();
        String[] roleNames = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role) // Remove o prefixo 'ROLE_', se necessário
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roleNames) // Define as roles
                .build();
    }
}