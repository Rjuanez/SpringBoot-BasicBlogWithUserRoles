package com.tutorial.tutorialspringboot.Controllers;

import com.tutorial.tutorialspringboot.Config.SecurityConfig;
import com.tutorial.tutorialspringboot.DTO.JwtAuthResponseDTO;
import com.tutorial.tutorialspringboot.DTO.LoginDTO;
import com.tutorial.tutorialspringboot.DTO.RegisterDTO;
import com.tutorial.tutorialspringboot.Entities.Role;
import com.tutorial.tutorialspringboot.Entities.User;
import com.tutorial.tutorialspringboot.Exeption.BlogAppException;
import com.tutorial.tutorialspringboot.Repositories.RoleRespository;
import com.tutorial.tutorialspringboot.Repositories.UserRepository;
import com.tutorial.tutorialspringboot.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        // guardmaos en objeto authneticate
        Authentication auhtorization = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auhtorization);
        String token = jwtTokenProvider.generateToken(auhtorization);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role role = roleRespository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }
}
