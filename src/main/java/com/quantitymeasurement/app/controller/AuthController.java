package com.quantitymeasurement.app.controller;

import com.quantitymeasurement.app.dto.LoginDTO;
import com.quantitymeasurement.app.dto.SignupDTO;
import com.quantitymeasurement.app.entity.User;
import com.quantitymeasurement.app.repository.UserRepository;
import com.quantitymeasurement.app.service.AuthService;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO dto) {
        return authService.signup(dto);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO dto) {

        String token = authService.login(dto);

        return Map.of(
                "token", token,
                "message", "Login successful"
        );
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/oauth-success")
    public String oauthSuccess(Authentication authentication) {

        String email = authentication.getName();

        userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setUsername(email);
            user.setPassword("OAUTH_USER");
            user.setRole("USER");
            return userRepository.save(user);
        });

        return "OAuth Login Successful for: " + email;
    }
}
