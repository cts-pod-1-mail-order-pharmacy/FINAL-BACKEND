package com.cts.subscriptionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid token..")
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String msg) {
        super(msg);
    }
}