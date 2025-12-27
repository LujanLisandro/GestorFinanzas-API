package com.lisandro.gestorfinanzas.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    
    public UsernameAlreadyExistsException(String username) {
        super("El nombre de usuario '" + username + "' ya está en uso");
    }
    
    public UsernameAlreadyExistsException(String username, Throwable cause) {
        super("El nombre de usuario '" + username + "' ya está en uso", cause);
    }
}
