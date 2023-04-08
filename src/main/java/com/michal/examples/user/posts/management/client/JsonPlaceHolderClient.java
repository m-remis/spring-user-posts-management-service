package com.michal.examples.user.posts.management.client;

import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderUserResponseDto;

import java.util.List;

/**
 * @author Michal Remis
 */
public interface JsonPlaceHolderClient {

    List<JsonPlaceHolderUserResponseDto> findUserById(String baseUrl, Integer userId);

    List<JsonPlaceHolderPostResponseDto> findPostById(String baseUrl, Integer postId);

    List<JsonPlaceHolderPostResponseDto> findPostsByUserId(String baseUrl, Integer postId);
}
