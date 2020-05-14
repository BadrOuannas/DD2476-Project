14
https://raw.githubusercontent.com/fawad1997/SpringWebAPI/master/src/main/java/com/restfulspring/apiexample/exception/NotFoundException.java
package com.restfulspring.apiexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
