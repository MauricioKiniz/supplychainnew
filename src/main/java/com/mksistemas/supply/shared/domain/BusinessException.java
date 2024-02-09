package com.mksistemas.supply.shared.domain;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3177161683410819272L;

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
