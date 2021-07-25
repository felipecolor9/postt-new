package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.request.PostitDTO;
import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.exception.PostitNotFoundException;
import com.lipsoft.postt.mapper.PostitMapper;
import com.lipsoft.postt.repository.PostitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostitService {

    private final PostitRepository postitRepository;
    private final MessageResponseDTO messageResponseDTO;
    private final PostitMapper postitMapper = PostitMapper.INSTANCE;

    @Autowired
    PostitService (PostitRepository postitRepository, MessageResponseDTO messageResponseDTO) {
        this.postitRepository = postitRepository;
        this.messageResponseDTO = messageResponseDTO;
    }

    public MessageResponseDTO add(PostitDTO postitDTO) {
        var postit = postitRepository.save(postitMapper.toModel(postitDTO));
        return MessageResponseDTO
                .builder()
                .message("Created Postit with ID '" + postit.getId() + "'")
                .build();
    }

    public void addAll(List<PostitDTO> postitsDTO) {
        postitsDTO.stream()
                .map(postitMapper::toModel)
                .forEach(postitRepository::save);
    }

    public List<PostitDTO> findAll() {
        return postitRepository.findAll().stream()
                .map(postitMapper::toDTO)
                .collect(Collectors.toList());
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
