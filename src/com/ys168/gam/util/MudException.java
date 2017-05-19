package com.ys168.gam.util;

public class MudException extends RuntimeException {

    private static final long serialVersionUID = -896010100622452575L;

    public MudException() {
        super();
    }

    public MudException(String message) {
        super(message);
    }

    public MudException(Throwable e) {
        super(e);
    }

    public MudException(String message, Throwable cause) {
        super(message, cause);
    }

}
