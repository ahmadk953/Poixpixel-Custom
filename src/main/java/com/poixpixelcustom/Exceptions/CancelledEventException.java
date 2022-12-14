package com.poixpixelcustom.Exceptions;

import com.poixpixelcustom.Event.CancellablePoixpixelCustomEvent;

public class CancelledEventException extends PoixpixelCustomException {

    private static final long serialVersionUID = 3114821661008750136L;
    private final String cancelMessage;

    public CancelledEventException(CancellablePoixpixelCustomEvent event) {
        super(event.getCancelMessage());
        cancelMessage = event.getCancelMessage();
    }

    public String getCancelMessage() {
        return cancelMessage;
    }
}
