package com.amcef.user.posts.management.dto.response;

/**
 * @author Michal Remis
 */
public record JsonPlaceHolderPostResponseDto(Integer userId,
                                             Integer id,
                                             String title,
                                             String body) {
}
