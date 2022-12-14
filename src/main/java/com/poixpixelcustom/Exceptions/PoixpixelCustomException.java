package com.poixpixelcustom.Exceptions;

import org.bukkit.command.CommandSender;

public class PoixpixelCustomException extends Exception{

    private static final long serialVersionUID = -6821768221748544277L;
    private Object message;

    public PoixpixelCustomException() {
        super("unknown");
    }

    public PoixpixelCustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return (String) message;
    }

    public String getMessage(CommandSender sender) {
        return (String) message;
    }

}
