package com.lipsoft.postt.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lipsoft.postt.mapper.UserMapper;
import com.lipsoft.postt.model.Role;
import com.lipsoft.postt.repository.user.RoleRepository;
import com.lipsoft.postt.repository.user.UserRepository;
import com.lipsoft.postt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@Slf4j
public class SecurityService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public SecurityService(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in database: {}", username);
            user.setLastAccessDate(LocalDateTime.now());
            userRepository.save(user);
        }
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                var refresh_token = authorizationHeader.substring("Bearer ".length());
                var algorithm = Algorithm.HMAC256("posttsecret".getBytes());
                var verifier = JWT.require(algorithm).build();
                var decodedJWT = verifier.verify(refresh_token);
                var username = decodedJWT.getSubject();

                var user = userMapper.toModel(userService.getUser(username));

                var access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) //10 minutes
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                var tokens = new HashMap<String, String>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception ex) {
                log.error("Error logging in: {}", ex.getMessage());
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());

                var error = new HashMap<String, String>();
                error.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing.");
        }
    }
}
