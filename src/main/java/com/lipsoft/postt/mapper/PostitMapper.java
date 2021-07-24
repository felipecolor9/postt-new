package com.lipsoft.postt.mapper;

import com.lipsoft.postt.dto.request.PostitDTO;
import com.lipsoft.postt.model.Postit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostitMapper {

    PostitMapper INSTANCE = Mappers.getMapper(PostitMapper.class);

    Postit toModel(PostitDTO postitDTO);

    PostitDTO toDTO(Postit postit);
}
