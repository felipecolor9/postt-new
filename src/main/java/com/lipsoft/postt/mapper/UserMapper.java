package com.lipsoft.postt.mapper;

import com.lipsoft.postt.dto.request.UserDTO;
import com.lipsoft.postt.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastAccessDate", ignore = true)
    @Mapping(target = "postits", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}