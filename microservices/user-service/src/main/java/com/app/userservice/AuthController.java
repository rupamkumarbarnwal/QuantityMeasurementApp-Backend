package com.app.userservice;


//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import com.app.userservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

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
    public void oauthSuccess(Authentication authentication, HttpServletResponse response) throws IOException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setUsername(name != null ? name : email);
            user.setPassword("OAUTH_USER");
            user.setRole("USER");
            return userRepository.save(user);
        });

        String token = jwtUtil.generateToken(email);
        response.sendRedirect(frontendUrl + "/oauth-success?token=" + token);
    }
}
