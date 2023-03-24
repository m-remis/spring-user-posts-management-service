package com.amcef.user.posts.management.service;

import com.amcef.user.posts.management.dto.request.CreateUserPostRequestDto;
import com.amcef.user.posts.management.dto.request.UpdateUserPostRequestDto;
import com.amcef.user.posts.management.dto.response.JsonPlaceHolderPostResponseDto;
import com.amcef.user.posts.management.dto.response.UserPostResponseDto;
import com.amcef.user.posts.management.entity.UserPostEntity;
import com.amcef.user.posts.management.vo.UserPostVo;

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
