package com.lipsoft.postt.controller;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.exception.InvalidCredentialsException;
import com.lipsoft.postt.exception.UsernameAlreadyInUseException;
import com.lipsoft.postt.model.User;
import com.lipsoft.postt.service.UserService;
import com.lipsoft.postt.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    SecurityService securityService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) throws UsernameAlreadyInUseException {
        var uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.register(userDTO));
    }

    //Not working properly
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        securityService.refreshToken(request,response);
    }
}