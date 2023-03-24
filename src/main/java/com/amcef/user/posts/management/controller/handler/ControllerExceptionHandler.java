package com.amcef.user.posts.management.controller.handler;

import com.amcef.user.posts.management.exception.MultipleResourcesFoundException;
import com.amcef.user.posts.management.exception.NotFoundException;
import com.amcef.user.posts.management.dto.response.ErrorDto;
import com.amcef.user.posts.management.dto.response.ServerResponse;
import com.amcef.user.posts.management.exception.ClientIntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Michal Remis
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({ClientIntegrationException.class})
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public ServerResponse<Void> handleException(ClientIntegrationException exception) {
        LOGGER.error("Client integration exception occurred: ", exception);
        return ServerResponse.error(exception.getError());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ServerResponse<Void> handleException(NotFoundException exception) {
        LOGGER.error("Client integration exception occurred: ", exception);
        return ServerResponse.error(exception.getError());
    }

    @ExceptionHandler({MultipleResourcesFoundException.class})
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @ResponseBody
    public ServerResponse<Void> handleException(MultipleResourcesFoundException exception) {
        LOGGER.error("Multiple resources exception occurred: ", exception);
        return ServerResponse.error(exception.getError());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ServerResponse<Void> handleException(HttpMessageNotReadableException exception) {
        LOGGER.error("JSON processing error occurred", exception);
        return ServerResponse.error(new ErrorDto("Can't deserialize provided JSON"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ServerResponse<Void> onValidationError(Exception ex) {
        LOGGER.error("Validation exception occurred");
        return ServerResponse.error(new ErrorDto("BAD_REQUEST"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ServerResponse<Void> handleRequestMethodNotSupportedException(Exception exception) {
        LOGGER.error("Method not allowed exception occurred: ", exception);
        return ServerResponse.error(new ErrorDto(exception.getMessage()));
    }

}
