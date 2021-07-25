package com.lipsoft.postt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostitNotFoundException extends Exception{

    public PostitNotFoundException(Long id) {
        super(String.format("User with ID %s not found!", id));
    }

}
