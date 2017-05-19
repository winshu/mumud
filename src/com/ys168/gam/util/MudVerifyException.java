package com.ys168.gam.util;

public class MudVerifyException extends RuntimeException {

    private static final long serialVersionUID = -896010100622452575L;

    public MudVerifyException() {
        super();
    }

    public MudVerifyException(String message) {
        super(message);
    }

    public MudVerifyException(Throwable e) {
        super(e);
    }

    public MudVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

}
