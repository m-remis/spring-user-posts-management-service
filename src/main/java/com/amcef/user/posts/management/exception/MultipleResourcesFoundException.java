package com.amcef.user.posts.management.exception;

import com.amcef.user.posts.management.dto.response.ErrorDto;

/**
 * @author Michal Remis
 */
public class MultipleResourcesFoundException extends RuntimeException {

    private final ErrorDto error;

    public MultipleResourcesFoundException(ErrorDto error) {
        super(error.toString());
        this.error = error;
    }

    public ErrorDto getError() {
        return error;
    }

    public static MultipleResourcesFoundException of(String message) {
        return new MultipleResourcesFoundException(new ErrorDto(message));
    }
}
