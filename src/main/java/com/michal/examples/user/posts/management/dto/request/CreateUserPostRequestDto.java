package com.michal.examples.user.posts.management.dto.request;


import jakarta.validation.constraints.NotBlank;

/**
 * @author Michal Remis
 */
public record CreateUserPostRequestDto(@NotBlank String title,
                                       @NotBlank String body) {
}
