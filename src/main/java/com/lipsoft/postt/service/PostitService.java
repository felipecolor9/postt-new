package com.lipsoft.postt.service;

import com.lipsoft.postt.dto.response.MessageResponseDTO;
import com.lipsoft.postt.mapper.PostitMapper;
import com.lipsoft.postt.repository.PostitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
