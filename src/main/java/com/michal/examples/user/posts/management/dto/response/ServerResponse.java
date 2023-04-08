package com.michal.examples.user.posts.management.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michal Remis
 */
public class ServerResponse<T> {

    private final T payload;
    private final ErrorDto error;

    public ServerResponse(T payload) {
        this.payload = payload;
        this.error = null;
    }

    @JsonCreator
    public ServerResponse(@JsonProperty("payload") T payload, @JsonProperty("error") ErrorDto error) {
        this.payload = payload;
        this.error = error;
    }

    public static ServerResponse<Void> error(ErrorDto error) {
        return new ServerResponse<>(null, error);
    }

    public T getPayload() {
        return payload;
    }

    public ErrorDto getError() {
        return error;
    }

}
