package com.ys168.gam.util;

public class MudSystemException extends RuntimeException {

    private static final long serialVersionUID = -896010100622452575L;

    public MudSystemException() {
        super();
    }

    public MudSystemException(String message) {
        super(message);
    }

    public MudSystemException(Throwable e) {
        super(e);
    }

    public MudSystemException(String message, Throwable cause) {
        super(message, cause);
    }

}
