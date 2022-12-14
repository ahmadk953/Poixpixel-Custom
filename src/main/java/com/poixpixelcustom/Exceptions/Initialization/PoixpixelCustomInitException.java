package com.poixpixelcustom.Exceptions.Initialization;

import org.jetbrains.annotations.NotNull;

public class PoixpixelCustomInitException extends RuntimeException {
    private static final long serialVersionUID = -1943705202251722549L;
    private final PoixpixelCustomError error;

    public PoixpixelCustomInitException(@NotNull String message, @NotNull PoixpixelCustomError error) {
        super(message);
        this.error = error;
    }

    public PoixpixelCustomInitException(@NotNull String message, @NotNull PoixpixelCustomError error, @NotNull Throwable t) {
        super(message, t);
        this.error = error;
    }

    public PoixpixelCustomError getError() {
        return error;
    }

    public enum PoixpixelCustomError {
        OTHER,
        MAIN_CONFIG,
        DATABASE,
        DATABASE_CONFIG,
        PERMISSIONS,
    }

}
