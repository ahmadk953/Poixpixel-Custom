package com.poixpixelcustom.Exceptions;

public class NotRegisteredException extends PoixpixelCustomException {

    private static final long serialVersionUID = 175945283391669005L;

    public NotRegisteredException() {

        super("not_registered");
    }

    public NotRegisteredException(String message) {

        super(message);
    }

}
