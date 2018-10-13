package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The driver has selected different car...")
public class DriverHasDifferentCarException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public DriverHasDifferentCarException(String message)
    {
        super(message);
    }

}
