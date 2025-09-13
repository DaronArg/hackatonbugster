package com.ganatan.backend_java.modules.users.services;

import com.ganatan.backend_java.modules.users.entities.User;
import com.ganatan.backend_java.modules.users.repository.UserRepository;
import com.ganatan.backend_java.modules.users.security.jwt.JwtService;
import com.ganatan.backend_java.modules.users.user.UserDTO;
import com.ganatan.backend_java.modules.users.user.login.LoginRequest;
import com.ganatan.backend_java.modules.users.user.login.RegisterRequest;
import com.ganatan.backend_java.modules.users.user.login.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserDTO register(RegisterRequest post) {

        String emailDomain = post.getEmail().split("@")[1];

        if (!(emailDomain.equalsIgnoreCase("tecnicatura.frc.utn.edu.ar") ||
                emailDomain.equalsIgnoreCase("frc.utn.edu.ar"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email domain is not allowed for registration.");
        }

        if (userRepository.findByEmail(post.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use.");
        }

        User user = new User(post);
        user.setPassword(passwordEncoder.encode(post.getPassword()));
        return new UserDTO(userRepository.save(user));
    }

    public TokenResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String accessToken = jwtService.generateToken(user);
        return new TokenResponse(accessToken);
    }

    public Boolean getEmailExists(String email) {
        Optional<User> userWithEmail = userRepository.findByEmail(email);
        return userWithEmail.isEmpty();
    }
}
