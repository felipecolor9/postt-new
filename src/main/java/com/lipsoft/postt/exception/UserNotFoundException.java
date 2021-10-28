package com.lipsoft.postt.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(long id) { super(String.format("",id)); }
}
