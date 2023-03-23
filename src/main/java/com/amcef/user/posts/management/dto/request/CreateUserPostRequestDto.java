package com.amcef.user.posts.management.dto.request;

import javax.validation.constraints.NotEmpty;

/**
 * @author Michal Remis
 */
public record CreateUserPostRequestDto(@NotEmpty String title,
                                       @NotEmpty String body) {
}
