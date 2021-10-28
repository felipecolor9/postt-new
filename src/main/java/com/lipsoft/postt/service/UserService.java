package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.InvalidCredentialsException;
import com.lipsoft.postt.exception.UserNotFoundException;
import com.lipsoft.postt.exception.UsernameAlreadyInUseException;
import com.lipsoft.postt.mapper.UserMapper;
import com.lipsoft.postt.repository.PostitRepository;
import com.lipsoft.postt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostitRepository postitRepository;
    private final MessageResponseDTO messageResponseDTO;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    UserService(UserRepository userRepository, PostitRepository postitRepository, MessageResponseDTO messageResponseDTO) {
        this.userRepository = userRepository;
        this.postitRepository = postitRepository;
        this.messageResponseDTO = messageResponseDTO;
    }

    //Account creation section
    public MessageResponseDTO createAccount(UserDTO userDTO) throws UsernameAlreadyInUseException {
        var userToSave = userMapper.toModel(userDTO);

        if (isUsernameAvailable(userToSave.getUsername())) {
            userToSave.setCreationDate(LocalDateTime.now());
            userRepository.save(userToSave);
            return messageResponseDTO.createMessageResponse("Created user with ID =", userToSave.getId());
        } else throw new UsernameAlreadyInUseException(userToSave.getUsername());
    }

    private boolean isUsernameAvailable(String username) {
        return userRepository.findAll().stream()
                .noneMatch(user -> user.getUsername().equals(username));
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

    private boolean isCredentialsValid(String username, String password) {
       return userRepository.findAll().stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }

    //Other API functions
    public void addAll(List<UserDTO> usersDTO) {
        usersDTO.stream()
                .map(userMapper::toModel)
                .forEach(userRepository::save);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findByID(Long id) throws UserNotFoundException {
        return userRepository.findById(id).map(userMapper::toDTO).orElseThrow(() -> new UserNotFoundException(id));
    }

    public MessageResponseDTO delete(Long id) throws UserNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return messageResponseDTO.createMessageResponse("User successfully deleted with ID =", id);
    }

}
