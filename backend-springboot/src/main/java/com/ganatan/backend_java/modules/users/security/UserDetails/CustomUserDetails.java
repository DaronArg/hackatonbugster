package com.ganatan.backend_java.modules.users.security.UserDetails;

import com.ganatan.backend_java.modules.users.user.UserDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class CustomUserDetails implements UserDetails {

    private final UserDTO user;

    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return "";
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombre de usuario.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Obtiene si la cuenta del usuario está bloqueada.
     * @return si la cuenta del usuario está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Obtiene si las credenciales del usuario están expiradas.
     * @return si las credenciales del usuario están expiradas.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Obtiene si el usuario está habilitado.
     * @return si el usuario está habilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
