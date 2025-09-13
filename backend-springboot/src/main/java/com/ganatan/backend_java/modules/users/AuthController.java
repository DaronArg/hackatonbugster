package com.ganatan.backend_java.modules.users;

import com.ganatan.backend_java.modules.users.services.AuthServiceImpl;
import com.ganatan.backend_java.modules.users.user.UserDTO;
import com.ganatan.backend_java.modules.users.user.login.LoginRequest;
import com.ganatan.backend_java.modules.users.user.login.RegisterRequest;
import com.ganatan.backend_java.modules.users.user.login.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request) {
        TokenResponse token = authService.authenticate(request);

        return ResponseEntity.ok(token);
    }

    @GetMapping(value = "/validEmail")
    public ResponseEntity<Boolean> validEmail(@RequestParam("email") String email) {
        Boolean emailExists = this.authService.getEmailExists(email);
        return ResponseEntity.ok(emailExists);
    }

}

