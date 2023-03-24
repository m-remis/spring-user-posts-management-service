package com.amcef.user.posts.management.dto.request;


import jakarta.validation.constraints.NotEmpty;

/**
 * @author Michal Remis
 */
public record CreateUserPostRequestDto(@NotEmpty String title,
                                       @NotEmpty String body) {
}
