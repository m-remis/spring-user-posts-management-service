package com.amcef.user.posts.management.client;

import com.amcef.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.amcef.user.posts.management.dto.response.JsonPlaceHolderUserResponseDto;

import java.util.List;

/**
 * @author Michal Remis
 */
public interface JsonPlaceHolderClient {

    JsonPlaceHolderUserResponseDto findUserById(String baseUrl, Integer userId);

    JsonPlaceHolderPostResponseDto findPostById(String baseUrl, Integer postId);

    List<JsonPlaceHolderPostResponseDto> findPostsByUserId(String baseUrl, Integer postId);
}
