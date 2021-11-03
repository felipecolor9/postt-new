package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.request.PostitDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.PostitNotFoundException;
import com.lipsoft.postt.mapper.PostitMapper;
import com.lipsoft.postt.repository.PostitRepository;
import com.lipsoft.postt.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostitService {

    private final PostitRepository postitRepository;
    private final UserRepository userRepository;
    private final MessageResponseDTO messageResponseDTO;
    private final PostitMapper postitMapper = PostitMapper.INSTANCE;

    @Autowired
    PostitService (PostitRepository postitRepository, UserRepository userRepository, MessageResponseDTO messageResponseDTO) {
        this.postitRepository = postitRepository;
        this.userRepository = userRepository;
        this.messageResponseDTO = messageResponseDTO;
    }

    public MessageResponseDTO add(PostitDTO postitDTO) {
        var postitToSave = postitMapper.toModel(postitDTO);
        postitToSave.setCreationDate(LocalDateTime.now());
        postitToSave.setUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        postitRepository.save(postitToSave);
        return MessageResponseDTO
                .builder()
                .message("Created Postit with ID '" + postitToSave.getId() + "'")
                .build();
    }

    public List<PostitDTO> findAllByUsername(String username) {
        var postits= postitRepository.findAll().stream()
                .filter(postit -> postit.getUser().getUsername().equals(username))
                .map(postitMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Fetching all postits from {}", username);
        return postits;
    }

    public PostitDTO findByID(Long id) throws PostitNotFoundException {
        return postitRepository.findById(id).map(postitMapper::toDTO).orElseThrow(() -> new PostitNotFoundException(id));
    }

    public MessageResponseDTO update(Long id, PostitDTO postitDTO) throws PostitNotFoundException {
        var postit = postitRepository.findById(id)
                .orElseThrow(() -> new PostitNotFoundException(id));
        postitRepository.save(postitMapper.toModel(postitDTO));

        return messageResponseDTO.createMessageResponse("Postit successfully updated with ID ", postit.getId());
    }

    public MessageResponseDTO delete(Long id) throws PostitNotFoundException {
        postitRepository.findById(id)
                .orElseThrow(() -> new PostitNotFoundException(id));
        postitRepository.deleteById(id);
        return messageResponseDTO.createMessageResponse("Postit successfully deleted with ID ", id);
    }
}
