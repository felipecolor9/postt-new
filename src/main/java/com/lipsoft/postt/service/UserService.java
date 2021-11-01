package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.InvalidCredentialsException;
import com.lipsoft.postt.exception.UsernameAlreadyInUseException;
import com.lipsoft.postt.mapper.UserMapper;
import com.lipsoft.postt.model.User;
import com.lipsoft.postt.repository.PostitRepository;
import com.lipsoft.postt.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PostitRepository postitRepository;
    private final MessageResponseDTO messageResponseDTO;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository, PostitRepository postitRepository, MessageResponseDTO messageResponseDTO, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postitRepository = postitRepository;
        this.messageResponseDTO = messageResponseDTO;
        this.passwordEncoder = passwordEncoder;
    }

    //Login section
    public boolean login(UserDTO userDTO) throws InvalidCredentialsException {
        var userToLogin = userMapper.toModel(userDTO);

        if (isCredentialsValid(userToLogin.getUsername(), userDTO.getPassword())) {
            userToLogin.setLastAccessDate(LocalDateTime.now());
            userRepository.save(userToLogin);
            return true;
        } else throw new InvalidCredentialsException();
    }

    private boolean isUsernameAvailable(String username) {
        return userRepository.findAll().stream()
                .noneMatch(user -> user.getUsername().equals(username));
    }

    private boolean isCredentialsValid(String username, String password) {
        return userRepository.findAll().stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    //Common API functions
    public UserDTO getUser(String username) {
        log.info("Fetching user {} to user {}", username);
        return userMapper.toDTO(userRepository.findByUsername(username));
    }

    public List<UserDTO> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public User saveUser(UserDTO userDTO) throws UsernameAlreadyInUseException {
        var userToSave = userMapper.toModel(userDTO);
        log.info("Saving new user {} to the database", userToSave.getUsername());
        //Encode password
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        if (isUsernameAvailable(userToSave.getUsername())) {
            userToSave.setCreationDate(LocalDateTime.now());
            return userRepository.save(userToSave);

        } else throw new UsernameAlreadyInUseException(userToSave.getUsername());
    }

}
