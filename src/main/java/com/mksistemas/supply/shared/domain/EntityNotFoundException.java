package com.mksistemas.supply.shared.domain;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3177161683410819283L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
