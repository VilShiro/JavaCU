package org.fbs.jcu.exception;

import java.io.IOException;

public class SaverException extends IOException {

    public SaverException(String errorMessage){
        super(errorMessage);
    }

}
