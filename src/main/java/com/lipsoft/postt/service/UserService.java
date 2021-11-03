package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.UsernameAlreadyInUseException;
import com.lipsoft.postt.mapper.UserMapper;
import com.lipsoft.postt.model.User;
import com.lipsoft.postt.repository.user.RoleRepository;
import com.lipsoft.postt.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MessageResponseDTO messageResponseDTO;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, RoleRepository roleRepository, MessageResponseDTO messageResponseDTO, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageResponseDTO = messageResponseDTO;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDTO userDTO) throws UsernameAlreadyInUseException {
        var userToSave = userMapper.toModel(userDTO);

        if (isUsernameAvailable(userToSave.getUsername())) {

            userToSave.setRoles(new ArrayList<>());
            userToSave.setPostits(new ArrayList<>());
            userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
            userToSave.getRoles().add(roleRepository.findByName("ROLE_USER"));
            userToSave.setCreationDate(LocalDateTime.now());
            log.info("Saving new user {} to the database", userToSave.getUsername());
            return userRepository.save(userToSave);

        } else throw new UsernameAlreadyInUseException(userToSave.getUsername());
    }

    //validation methods
    private boolean isUsernameAvailable(String username) {
        return userRepository.findAll().stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }

    //Common API functions
    public UserDTO getUser(String username) {
        log.info("Fetching user {}", username);
        return userMapper.toDTO(userRepository.findByUsername(username));
    }

    public List<UserDTO> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
