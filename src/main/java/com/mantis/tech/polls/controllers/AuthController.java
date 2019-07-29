package com.mantis.tech.polls.controllers;

import com.mantis.tech.polls.exceptions.AppException;
import com.mantis.tech.polls.models.Role;
import com.mantis.tech.polls.models.RoleName;
import com.mantis.tech.polls.models.User;
import com.mantis.tech.polls.payloads.ApiResponse;
import com.mantis.tech.polls.payloads.JwtAuthenticationResponse;
import com.mantis.tech.polls.payloads.LoginRequest;
import com.mantis.tech.polls.payloads.SignUpRequest;
import com.mantis.tech.polls.repositories.RoleRepository;
import com.mantis.tech.polls.repositories.UserRepository;
import com.mantis.tech.polls.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserNameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmailAddress(signUpRequest.getEmailAddress())) {
            return new ResponseEntity(new ApiResponse(false, "Email address already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUserName(), signUpRequest.getPassword(), signUpRequest.getEmailAddress());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(
                () -> new AppException("User role not set.")
        );

        user.setUserRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "You have been registered successfully."));
    }

}
