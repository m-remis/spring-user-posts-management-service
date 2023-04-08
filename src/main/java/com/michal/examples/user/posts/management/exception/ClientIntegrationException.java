package com.michal.examples.user.posts.management.exception;

import com.michal.examples.user.posts.management.dto.response.ErrorDto;

/**
 * @author Michal Remis
 */
public class ClientIntegrationException extends RuntimeException {

    private final ErrorDto error;

    public ClientIntegrationException(ErrorDto error) {
        super(error.toString());
        this.error = error;
    }

    public ErrorDto getError() {
        return error;
    }

    public static ClientIntegrationException of(String message) {
        return new ClientIntegrationException(new ErrorDto(message));
    }
}
