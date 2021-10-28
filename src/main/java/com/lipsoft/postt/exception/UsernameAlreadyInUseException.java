package com.lipsoft.postt.exception;

public class UsernameAlreadyInUseException extends Exception {

    public UsernameAlreadyInUseException(String msg) { super(String.format("",msg)); }
}
