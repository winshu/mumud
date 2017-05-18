package com.ys168.gam.util;

public class MudRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -896010100622452575L;

    public MudRuntimeException() {
        super();
    }

    public MudRuntimeException(String message) {
        super(message);
    }

    public MudRuntimeException(Throwable e) {
        super(e);
    }

    public MudRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
