package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The driver is offline...")
public class DriverIsOfflineException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public DriverIsOfflineException(String message)
    {
        super(message);
    }

}
