package com.ganatan.backend_java.modules.users.security.UserDetails;

import com.ganatan.backend_java.modules.users.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementación de la interfaz UserDetailsService,
 * contiene la lógica de autenticación.
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return new CustomUserDetails(userService.getModelByEmail(email));
    }
}
