package com.michal.examples.user.posts.management.service;

import com.michal.examples.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.michal.examples.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.michal.examples.user.posts.management.dto.response.UserPostResponseDto;
import com.michal.examples.user.posts.management.entity.UserPostEntity;
import com.michal.examples.user.posts.management.vo.UserPostVo;

/**
 * @author Michal Remis
 */
public interface ConvertService {
    UserPostVo convert(CreateUserPostRequestDto dto, Integer userId);

    UserPostVo convert(UpdateUserPostRequestDto dto, Integer postId);

    UserPostResponseDto convert(UserPostVo userPostVo);

    UserPostVo convert(UserPostEntity entity);

    UserPostVo convert(JsonPlaceHolderPostResponseDto jsonPlaceHolderPostResponseDto);
}
