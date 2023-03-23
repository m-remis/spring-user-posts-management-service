package com.amcef.user.posts.management.dto.response;

/**
 * @author Michal Remis
 */
public record ServerResponse<T>(T payload,
                                ErrorDto error) {

    public static ServerResponse<Void> error(ErrorDto error) {
        return new ServerResponse<>(null, error);
    }
}
