package com.michal.examples.user.posts.management.exception;

import com.michal.examples.user.posts.management.dto.response.ErrorDto;

/**
 * @author Michal Remis
 */
public class NotFoundException extends RuntimeException {

    private final ErrorDto error;

    public NotFoundException(ErrorDto error) {
        super(error.toString());
        this.error = error;
    }

    public ErrorDto getError() {
        return error;
    }

    public static NotFoundException of(String message) {
        return new NotFoundException(new ErrorDto(message));
    }
}
