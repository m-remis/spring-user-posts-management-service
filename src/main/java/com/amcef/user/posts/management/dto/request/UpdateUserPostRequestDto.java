package com.amcef.user.posts.management.dto.request;

import com.amcef.user.posts.management.annotation.UpdateUserPostFieldValidation;

/**
 * @author Michal Remis
 */
public record UpdateUserPostRequestDto(@UpdateUserPostFieldValidation String title,
                                       @UpdateUserPostFieldValidation String body) {
}
