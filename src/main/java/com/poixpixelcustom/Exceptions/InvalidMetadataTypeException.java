package com.poixpixelcustom.Exceptions;

import com.poixpixelcustom.Object.Metadata.CustomDataField;

public class InvalidMetadataTypeException extends PoixpixelCustomException {

    private static final long serialVersionUID = 2335936343233569066L;

    public InvalidMetadataTypeException(CustomDataField<?> cdf) {
        super();
    }

}
